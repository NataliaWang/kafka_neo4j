package com.ziroom.bi.data.schedulelineage.CombineHashset;

import lombok.Data;

import java.util.Set;

@Data
public class Message {

    private String dagId;
    private String runId;
    private String executionDate;
    private Set<String> sql;
    private String baseUri;
    private Set<String> runInputs;
    private Set<String> runOutputs;

}

