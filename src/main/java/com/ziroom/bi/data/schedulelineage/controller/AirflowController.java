package com.ziroom.bi.data.schedulelineage.controller;


import com.ziroom.bi.data.schedulelineage.domain.dao.DagRunEntity;
import com.ziroom.bi.data.schedulelineage.service.AirflowService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/airflow")

/**
 * airflow封装接口，需要实现对airflow的增删改查功能，增加功能最后做
 */
public class AirflowController {
    @Autowired
    AirflowService airflowService;

    @GetMapping("/operation")
    @ApiOperation(value ="CRUD")
    public void operate(@RequestBody DagRunEntity dagRunEntity) {

       // airflowService.operate(dagRunEntity);
    }


}
