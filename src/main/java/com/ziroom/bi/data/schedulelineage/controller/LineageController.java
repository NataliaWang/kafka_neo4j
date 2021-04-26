package com.ziroom.bi.data.schedulelineage.controller;

import com.ziroom.bi.data.schedulelineage.domain.dao.DagEntity;
import com.ziroom.bi.data.schedulelineage.domain.dao.DagRunEntity;
import com.ziroom.bi.data.schedulelineage.service.LineageService;
import com.ziroom.bi.data.schedulelineage.util.RestResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/lineage")

/**
 * 血缘查询接口，主要是对neo4j中存储的任务血缘封装查询接口
 */
public class LineageController {

    @Autowired
    LineageService lineageService;

    @PostMapping("/save")
    @ApiOperation(value = "保存DAGEntity")   //create or update
    public RestResult save(@RequestBody DagRunEntity dagRunEntity){
        lineageService.save(dagRunEntity);
        return RestResult.success(dagRunEntity);
    }


    @DeleteMapping("/delete")
    @ApiOperation(value = "delete a DagRunEntity")
    public RestResult delete(@RequestParam("String id") String id) { //id = dagId
        lineageService.delete(id);
        return RestResult.success();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "find the related down_DagRunEntity by id")
    public RestResult find(@Param("id") String id) {
        Set<DagRunEntity> dagRunEntities = lineageService.findById(id);
        return RestResult.success(dagRunEntities);
    }




}
