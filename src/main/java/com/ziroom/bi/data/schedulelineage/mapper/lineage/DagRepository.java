package com.ziroom.bi.data.schedulelineage.mapper.lineage;

import com.ziroom.bi.data.schedulelineage.domain.dao.DagEntity;
import com.ziroom.bi.data.schedulelineage.domain.dao.DagRunEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DagRepository extends Neo4jRepository<DagEntity,String> {

    //find 上游节点
    @Query("match (n:Dag) where $input in n.outputs  return n ")
    Set<DagEntity> findDagEntitiesByOutputsContains(@Param("input") String input);

    @Query("match (n:Dag) <-[r:upStreams]- (up_node:Dag) where n.dagId=$dagId delete r ")
    void deleteAllUpRelaitionShipsByDagId(@Param("dagId") String dagId);

    //通过id找node,//寻找是否存在该dagId
    @Query("match (n:Dag{dagId:$dagId}) return n")
    DagEntity findByDagId(@Param("dagId") String dagId);


    //way1
    //find all 下游节点们 by outputs
    @Query("match (down_node:Dag)  where $output in down_node.inputs return down_node")
    Set<DagEntity> findDagEntitiesByRunInputsContains(@Param("output") String output);

    //way2
    //find all 下游节点们 by dagId
    @Query("match (n:Dag) <-[r:upStreams]- (up_node:Dag) where up_node.dagId = $dagId return n")
    Set<DagEntity> findDownDagEntitiesByDagId(@Param("dagId") String dagId);

    //　删除节点的下游关系
    @Query("match (n:Dag) ->[r:upStreams] -> (down_node:Dag) where n.id = $id delete r")
    void deleteAllDownRelationshipsByDagId(@Param("id") String id);

    //通过id找下游的relationship
    @Query("match (n:Dag) <-[r:upStreams]- (up_node:Dag) where up_node.dagId = $dagId return r")
    Set<DagEntity> findDownDagRelationshipsByDagId(@Param("dagId") String dagId);
}
