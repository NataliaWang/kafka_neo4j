package com.ziroom.bi.data.schedulelineage.service.impl;

import com.ziroom.bi.data.schedulelineage.domain.dao.DagEntity;
import com.ziroom.bi.data.schedulelineage.domain.dao.DagRunEntity;
import com.ziroom.bi.data.schedulelineage.domain.dto.KafkaDagInfo;
import com.ziroom.bi.data.schedulelineage.mapper.lineage.DagRepository;
import com.ziroom.bi.data.schedulelineage.mapper.lineage.DagRunRepository;
import com.ziroom.bi.data.schedulelineage.service.LineageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LineageServiceImpl implements LineageService {

    @Autowired
    DagRepository dagRepository;

    @Autowired
    DagRunRepository dagRunRepository;

    public void saveKafka(KafkaDagInfo kafkaDagInfo) {

        DagRunEntity dagRunEntity = saveDagRunKafka(kafkaDagInfo);
        DagEntity dagEntity= saveDag(dagRunEntity);
        dagRunEntity.setDag(dagEntity);

    }

    private DagRunEntity saveDagRunKafka(KafkaDagInfo kafkaDagInfo) {
        //if not existed the dagRunEntity, find the upstreams and update the upstreams's outputs and
        String dagId = kafkaDagInfo.getDagId();
        String runId = kafkaDagInfo.getRunId();
        //String id = RandomId.getUUID();

        DagRunEntity dagRunEntity = dagRunRepository.findByDagIdRunId(dagId, runId);
        if(dagRunEntity == null) {//未完待处理
            //firstly, create the dagRunEntity

            DagRunEntity newdagRunEntity = new DagRunEntity();
            newdagRunEntity.setDagId(dagId);
            newdagRunEntity.setRunId(runId);
            newdagRunEntity.setId(dagId + "_" + runId);
            newdagRunEntity.setRunInputs(kafkaDagInfo.getRunInputs());
            newdagRunEntity.setRunOutputs(kafkaDagInfo.getRunOutputs());
            List<String> sqlList = new ArrayList<>();
            sqlList.add(kafkaDagInfo.getSql());
            newdagRunEntity.setSqls(sqlList);

            newdagRunEntity.setBaseUri(kafkaDagInfo.getBaseUri());
            rebuildRelationship(newdagRunEntity);
            dagRunRepository.save(newdagRunEntity);
            return newdagRunEntity;
        }else{
            //exist the dagRunEntity, just update (add the info of inputs and outputs, and then
            //delte all upstreams relationship, and then rebuild the upstream relationship

            dagRunEntity.getRunInputs().addAll(kafkaDagInfo.getRunInputs());
            dagRunEntity.getRunOutputs().addAll(kafkaDagInfo.getRunOutputs());
            dagRunEntity.getSqls().add(kafkaDagInfo.getSql());
            //do we need update the BaseUrl?
            //update the initial existed dagRunEntity

            //delete all upstreams realtionship
            dagRunRepository.deleteAllUpRelationshipsByDagId(dagRunEntity.getDagId());
            //rebuild the upstreams relationship
            rebuildRelationship(dagRunEntity);
            dagRunRepository.save(dagRunEntity);
            return dagRunEntity;

            }

            //还有新的dagRun中属性的修改，baseUri如何修改，startDate等属性修改？

        }

    private void rebuildRelationship(DagRunEntity dagRunEntity) {
        Set<DagRunEntity> upstreams = new HashSet<>();
        //secondly, create the relationship
        for(String runInput: dagRunEntity.getRunInputs()) {
            upstreams.addAll(dagRunRepository.findDagRunEntitiesByRunOutputsContains(runInput));
        }
        if(upstreams.isEmpty()) { //新插入的没有上游节点,就保持孤独状态
            return ;
        }
        dagRunEntity.setRunUpStreams(upstreams);
        for(DagRunEntity upDagRunEntity: upstreams) {
            dagRunRepository.createUpRelationship(dagRunEntity.getDagId(),upDagRunEntity.getDagId());
        }
    }




    /**
     * 通过保存某dagRunEntity，同时更新dagEntity，并重塑/更新该dagRunEntity在整个关系链中的关系
     * @param dagRunEntity
     */
    @Override
    public void save(DagRunEntity dagRunEntity){
        DagEntity dagEntity= saveDag(dagRunEntity);
        dagRunEntity.setDag(dagEntity);
        saveDagRun(dagRunEntity);
    }


    @Override
    public void delete(String id) { //id = dagId
        DagRunEntity dagRunEntity = dagRunRepository.findByDagId(id);
        DagEntity dagEntity = dagRepository.findByDagId(id);
        if(dagEntity == null || dagRunEntity == null) {
            return;
        }

        //删除dagRunRepository上游的relationship
        dagRunRepository.deleteAllUpRelaitionShipsByDagId(id);
        //删除dagRepository的上游关系，因为下游节点需要更新与上游节点的relationship，所以只考虑当前节点与上游节点的关系即可
        dagRepository.deleteAllUpRelaitionShipsByDagId(id);
        //将上游的outputs不需更新， 不做处理，至于后续可能出现的环形结构，后续另写API进行统一大规模删除优化处理
        dagRunRepository.deleteByDagId(id);
        dagRunRepository.save(dagRunEntity);

       //暂且不考虑对下游nodes 的影响，后期进行深入考虑
    }


    @Override
    public Set<DagRunEntity> findById(String id) {
        return dagRunRepository.findDagRunById(id);
    }

    @Override
    public void clear(String dagId) {
        if(dagRepository.findByDagId(dagId) != null && dagRunRepository.findByDagId(dagId) != null) {
            Set<DagEntity> dagEntities = dagRepository.findDownDagEntitiesByDagId(dagId);
            for(DagEntity dagEntity:dagEntities) {
            }

            Set<DagRunEntity> dagRunEntities = dagRunRepository.findDagRunById(dagId);
            for(DagRunEntity dagRunEntity:dagRunEntities) {

            }
        }
    }

    @Override
    public void clear(DagRunEntity dagRunEntity) {
        if(dagRunRepository.findByDagRunEntity(dagRunEntity) == null) {
            saveDagRun(dagRunEntity);
        }else{
            clear(dagRunEntity.getDagId());
        }
    }


    public void saveDagRun(DagRunEntity dagRunEntity){
        // if dagRunEntity is already existed， 合并其input，output信息，更新该dayRunEntity信息，？其sql会合并，set增加
        //startDate是最早的，endDate是最晚的，
        DagRunEntity initialDRunEntity = dagRunRepository.findByDagRunEntity(dagRunEntity);
        if(initialDRunEntity != null) {
            initialDRunEntity.getSqls().add(dagRunEntity.getSqls().get(0));
            //initialDRunEntity.getRunInputs().add(dagRunEntity.getRunInputs());
            dagRunRepository.save(initialDRunEntity);
        }else {
            //if not existed, things would get easier
            //just find the dagRunEntity's input = some other dagRunEntity's output(寻找上游节点）
            dagRunEntity.setId(dagRunEntity.getDagId() + "_" + dagRunEntity.getRunId());
            dagRunRepository.deleteAllUpRelaitionShipsByDagId(dagRunEntity.getId());
            final Set<String> runInputs = dagRunEntity.getRunInputs();
            if (!runInputs.isEmpty()) {
                final HashSet<DagRunEntity> upStreams = new HashSet<>();
                runInputs.stream().map(dagRunRepository::findDagRunEntitiesByRunOutputsContains).forEach(upStreams::addAll);
                dagRunEntity.setRunUpStreams(upStreams);

            }
            dagRunRepository.save(dagRunEntity);
        }
    }


    public DagEntity saveDag(DagRunEntity dagRunEntity){
        String dagId = dagRunEntity.getDagId();
        DagEntity dagEntity = dagRepository.findByDagId(dagId);

        //dag不存在，创建该dagEntity
        //firstly, create some info of dagEntity
        if(dagEntity == null) {
            dagEntity = new DagEntity();
        }else{
            dagEntity.getInputs().addAll(dagRunEntity.getRunInputs());
        }
        dagEntity.setDagId(dagRunEntity.getDagId());
        dagEntity.setOwners(dagRunEntity.getOwners());
        dagEntity.setBaseUri(dagRunEntity.getBaseUri());
        final Set<String> runInputs = dagRunEntity.getRunInputs();

        //filter some info from dagRunEntity to get the inputs and outputs of dagEntity
        final HashSet<String> inputs = new HashSet<>();
        for (String runInput : runInputs) {
            final String[] arr = runInput.split("@");
            inputs.add(arr[0]+ "@" + arr[1]);
        }
        dagEntity.setInputs(inputs);
        final Set<String> runOutputs = dagRunEntity.getRunOutputs();
        final HashSet<String> outputs = new HashSet<>();
        for (String runOutput : runOutputs) {
            final String[] arr = runOutput.split("@");
            outputs.add(arr[0]+ "@" + arr[1]);
        }
        dagEntity.setOutputs(outputs);

        //secondly, deal with some relationship
        //??????如果创建了新的dagEntity，这时dagRepository.findByDagId(dagId)是否为空？？？？？？？？？！！！！！！
        if(dagRepository.findByDagId(dagId) != null) {
            dagRepository.deleteAllUpRelaitionShipsByDagId(dagEntity.getDagId());
        }
        if(!inputs.isEmpty()){
            final HashSet<DagEntity> upStreams = new HashSet<>();
            inputs.stream().map(dagRepository::findDagEntitiesByOutputsContains).forEach(upStreams::addAll);
            dagEntity.setUpStreams(upStreams);
        }
        dagRepository.save(dagEntity);
        return dagEntity;
    }

}
