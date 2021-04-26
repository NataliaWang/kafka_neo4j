package com.ziroom.bi.data.schedulelineage.domain.dto;


import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class KafkaDagInfo {

    private String dagId;
    private String runId;
    private String executionDate;
    private String sql;
    private String baseUri;
    private Set<String> runInputs;
    private Set<String> runOutputs;

}
