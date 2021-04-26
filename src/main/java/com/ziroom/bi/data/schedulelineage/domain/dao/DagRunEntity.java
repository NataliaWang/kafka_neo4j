package com.ziroom.bi.data.schedulelineage.domain.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ziroom.bi.data.schedulelineage.constant.NeoRelation;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Node("DagRun")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize()
public class DagRunEntity {

    @Id
    private String id;

    private String dagId;
    private String runId;

    private String executionDate;

    private String startDate;

    private String endDate;

    private String state;

    private List<String> sqls;
    private String baseUri;
    private Set<String> owners;
    private Set<String> runInputs;
    private Set<String> runOutputs;

    @Relationship(type = NeoRelation.R_DAGRUN_DEPENDENCY, direction = Relationship.Direction.INCOMING)
    private Set<DagRunEntity> runUpStreams;

    @Relationship(type = NeoRelation.R_DAGRUN_BELONGS_TO_DAG, direction = Relationship.Direction.INCOMING)
    private DagEntity dag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDagId() {
        return dagId;
    }

    public void setDagId(String dagId) {
        this.dagId = dagId;
    }

    public String getRunId() {
        return runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public String getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(String executionDate) {
        this.executionDate = executionDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<String> getSqls() {
        return sqls;
    }

    public void setSqls(List<String> sqls) {
        this.sqls = sqls;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    public Set<String> getOwners() {
        return owners;
    }

    public void setOwners(Set<String> owners) {
        this.owners = owners;
    }

    public Set<String> getRunInputs() {
        return runInputs;
    }

    public void setRunInputs(Set<String> runInputs) {
        this.runInputs = runInputs;
    }

    public Set<String> getRunOutputs() {
        return runOutputs;
    }

    public void setRunOutputs(Set<String> runOutputs) {
        this.runOutputs = runOutputs;
    }

    public Set<DagRunEntity> getRunUpStreams() {
        return runUpStreams;
    }

    public void setRunUpStreams(Set<DagRunEntity> runUpStreams) {
        this.runUpStreams = runUpStreams;
    }

    public DagEntity getDag() {
        return dag;
    }

    public void setDag(DagEntity dag) {
        this.dag = dag;
    }

    public DagRunEntity() {

    }
    //    public DagRunEntity(String dagId, String runId, String executionDate,
//                        List<String> sqls, String baseUri, Set<String> runInputs, Set<String> runOutputs) {
//
//        this.dagId = dagId;
//        this.runId = runId;
//        this.executionDate = executionDate;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.state = state;
//        this.sqls = sqls;
//        this.baseUri = baseUri;
//        this.owners = owners;
//        this.runInputs = runInputs;
//        this.runOutputs = runOutputs;
//        this.runUpStreams = runUpStreams;
//        this.dag = dag;
//    }
}
