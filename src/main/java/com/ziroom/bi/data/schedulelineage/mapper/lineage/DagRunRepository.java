package com.ziroom.bi.data.schedulelineage.mapper.lineage;

import com.ziroom.bi.data.schedulelineage.domain.dao.DagEntity;
import com.ziroom.bi.data.schedulelineage.domain.dao.DagRunEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface  DagRunRepository extends Neo4jRepository<DagRunEntity,String> {
    //依据俩个结点的id，outputs创建relationship (relationship name is "upstream")
    @Query("match (up:DagRun{dagId : $upDagRunId})" +
            "match (down:DagRun{dagId : $downDagRunId})" +
            " create (up)-[r:upStreams]->(down)")
    //@Query("match (n:DagRun) where $down.runInput in n.runOutputs  create (n) - (r) -> (")
    void createUpRelationship(@Param("downDagRunId") String downDagRunId, @Param("upDagRunId") String upDagRunId);
//如果in没有多对多的，那就1对多 //?????能传类型是DagRun嘛
    //寻找上游节点们
    @Query("match (n:DagRun) where $runInput in n.runOutputs  return n ")
    Set<DagRunEntity> findDagRunEntitiesByRunOutputsContains(@Param("runInput") String runInput);

    @Query("match (n:DagRun) <-[r:upStreams]- (up_node:DagRun) where n.dagId= $dagId delete r ")
    void deleteAllUpRelaitionShipsByDagId(@Param("dagId") String dagId);

    //通过dagId找node
    @Query("match (n:DagRun{dagId:$dagId}) return n")
    DagRunEntity findByDagId(@Param("dagId") String dagId);

//    //way1
//    //find all 下游节点们 by runOutputs
//    @Query("match (down_node:DagRun)  where $runOutput in down_node.runInputs return down_node")
//    Set<DagRunEntity> findDagRunEntitiesByRunInputsContains(@Param("runOutput") String runOutput);

//    //way2
//    //find all 下游节点们 by dagId
//    @Query("match (n:DagRun) <-[r:upStreams]- (up_node:DagRun) where up_node.dagId = $dagId return n")
//    Set<DagRunEntity> findDownDagRunEntitiesByDagId(@Param("dagId") String dagId);

    //　删除节点的上游关系(???这里的关系是upStreams还是任意关系？）
    @Query("match (n:DagRun)-[r:upStreams]-> (n2:DagRun) where n2.dagId = $dagId delete r")
    void deleteAllUpRelationshipsByDagId(@Param("dagId") String dagId);

//    //通过id找下游的relationship
//    @Query("match (n:DagRun) <-[r:]- (up_node:Dag) where up_node.dagId = $dagId return r")
//    Set<DagRunEntity> findDownDagRunRelationshipsByDagId(@Param("dagId") String dagId);

    //是否存在这个DagRun //??? param传的是个entity可以嘛？
    @Query("match (n:DagRun) where dagRunEntity.dagId = $n.dagId return n")
    DagRunEntity findByDagRunEntity(@Param("dagRunEntity") DagRunEntity dagRunEntity);

    //match必须后面接其他逻辑（create ～/delete ～）或者return ～， （这里return n 必须要写，不写报错！）
    @Query("match (n:DagRun) where n.dagId = $dagId and n.runId = $runId return n")
    DagRunEntity findByDagIdRunId(@Param("dagId") String dagId, @Param("runId") String runId);

    //删除dagRunEntity by dagId
    @Query("match (n:DagRun) where n.dagId = $dagId delete n")
    void deleteByDagId(@Param("dagId") String dagId);

    //通过dagRunId 找到DagRunEntity
    @Query("match (n:DagRun) where n.dagId = $dagId return n")
    Set<DagRunEntity> findDagRunById(@Param("dagId") String dagId);
}
