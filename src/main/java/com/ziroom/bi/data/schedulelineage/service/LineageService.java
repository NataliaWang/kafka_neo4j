package com.ziroom.bi.data.schedulelineage.service;


import com.ziroom.bi.data.schedulelineage.domain.dao.DagRunEntity;
import com.ziroom.bi.data.schedulelineage.domain.dto.KafkaDagInfo;

import java.util.List;
import java.util.Set;
public interface LineageService {
   void saveKafka(KafkaDagInfo kafkaDagInfo);

   void save(DagRunEntity dagRunEntity);
   //void create(DagRunEntity dagRunEntity);
   void delete(String Id);
   //void update(DagRunEntity dagRunEntity);
   Set<DagRunEntity> findById(String id);

   void clear(String Id);
   void clear(DagRunEntity dagRunEntity);
}
