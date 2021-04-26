package com.ziroom.bi.data.schedulelineage.constant;

public interface NeoRelation {
    /**
     * dag的上游
     */
    String R_DAG_DEPENDENCY ="R_DAG_DEPENDENCY";


    /**
     * dagrun的上游
     */
    String R_DAGRUN_DEPENDENCY ="R_DAGRUN_DEPENDENCY";


    /**
     * DAGRUN 属于 DAG
     */
    String R_DAGRUN_BELONGS_TO_DAG ="R_DAGRUN_BELONGS_TO_DAG";
}
