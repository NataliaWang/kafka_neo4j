package com.ziroom.bi.data.schedulelineage.domain.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ziroom.bi.data.schedulelineage.constant.NeoRelation;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node("Dag")
@ApiModel("Dag实体")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize()
public class DagEntity {

    @Id
    private String dagId;
    private Set<String> owners;

    private String baseUri;

    private Set<String> inputs=new HashSet<>();

    private Set<String> outputs=new HashSet<>();

    @Relationship(type = NeoRelation.R_DAG_DEPENDENCY, direction = Relationship.Direction.INCOMING)
    private Set<DagEntity> upStreams;

}
