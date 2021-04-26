package com.ziroom.bi.data.schedulelineage.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


import com.ziroom.bi.data.schedulelineage.domain.dao.DagEntity;
import com.ziroom.bi.data.schedulelineage.domain.dao.DagRunEntity;
import com.ziroom.bi.data.schedulelineage.domain.dto.KafkaDagInfo;
import com.ziroom.bi.data.schedulelineage.service.LineageService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class KafkaConsumer {

    @Autowired
    LineageService lineageService;

    @KafkaListener(topics = {"wangfy_test"})
    public void listen(ConsumerRecord<String, String> record) {

        Optional<String> kafkaMessage = Optional.ofNullable(record.value());

        if (kafkaMessage.isPresent()) {

            String message = kafkaMessage.get();
            System.out.println("---->" + record);
            System.out.println("---->" + message);

           KafkaDagInfo kafkaDagInfo = JSONObject.parseObject(message,KafkaDagInfo.class);
            //System.out.println(kafkaDagInfo.getBaseUri());
           //Set<String> set = new HashSet(Collections.singleton(kafkaDagInfo.getSql()));

            //目前通过dagRunId, dagId俩个相同来判断是否是同一个dagRunEntity

//DagRunEntity dagRunEntity = new DagRunEntity(kafkaDagInfo.getDagId(),kafkaDagInfo.
//        getRunId(),kafkaDagInfo.getExecutionDate(), kafkaDagInfo.getSql(),
//        kafkaDagInfo.getBaseUri(),kafkaDagInfo.getRunInputs(),kafkaDagInfo.getRunOutputs());
//
//        //引用LineageService中的saveOrUpdate方法
//            lineageService.save(dagRunEntity);

            lineageService.saveKafka(kafkaDagInfo);







//                JSONObject messageObject = new JSONObject().getJSONObject(message);
//                dagEntity.setDagId(messageObject.getString("dagId"));
//                dagEntity.setBaseUri(messageObject.getString("baseUri"));
//
//                dagRunEntity.setDagId(messageObject.getString("dagId"));
//                dagRunEntity.setBaseUri(messageObject.getString("baseUri"));
//                dagRunEntity.setExecutionDate(messageObject.getString("executionDate"));
//                dagRunEntity.setRunId(messageObject.getString("runId"));
//               // dagRunEntity.setSqls(messageObject.getString("sql"));
//                // 返回json的数组
//                JSONArray jsonInputs = new JSONObject().getJSONArray("runInputs");
//                Set<String> runInputs = new HashSet<>();
//                for (int i = 0; i < jsonInputs.size(); i++) {
//                    String msg = jsonInputs.getString(i);
//                    runInputs.add(msg);
//
//                }
//                dagRunEntity.setRunInputs(runInputs);
//
//
//                JSONArray jsonRunOutputs = new JSONObject().getJSONArray("runOutputs");
//                Set<String> runOutputs = new HashSet<>();
//                for (int i = 0; i < jsonRunOutputs.size(); i++) {
//                    String msg = jsonRunOutputs.getString(i);
//                    runOutputs.add(msg);
//
//                }
//                dagRunEntity.setRunOutputs(runOutputs);
//
//
//                JSONArray jsonSql = new JSONObject().getJSONArray("sql");
//                List<String> sqls = new ArrayList<>();
//                for (int i = 0; i < jsonSql.size(); i++) {
//                    String msg = jsonSql.getString(i);
//                    sqls.add(msg);
//
//                }
//                dagRunEntity.setSqls(sqls);
//                System.out.println("test------------dagEntity");
//                System.out.println(dagEntity);
//                System.out.println("test------------dagRunEntity");
//                System.out.println(dagRunEntity);




        }
    }
}
