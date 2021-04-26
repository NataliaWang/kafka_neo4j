血缘上报接口示例:

```shell

http://127.0.0.1:8080/lineage/save
post
{"dagId":"dwd1","runId":"scheduled__2020-12-01T00:00:00+08:00","executionDate":"2021-03-07 00:00:00.000000","state":"success","startDate":"2021-03-08 00:05:03.866359","endDate":"2021-03-08 00:09:02.225089","runInputs":["ods@ods1@dt=20210307"],"runOutputs":["dwd@dwd1@dt=20210307"],"baseUri":"http://airflow.ziroom.com/","owners":["caol5"],"sqls":["insert overwrite table dwd.dwd1 partition(dt=20210307 select * from ods.ods1 where dt=20210307)"]}
http://127.0.0.1:8080/lineage/save
post
{"dagId":"ods1","runId":"scheduled__2020-12-01T00:00:00+08:00","executionDate":"2021-03-07 00:00:00.000000","state":"success","startDate":"2021-03-08 00:05:03.866359","endDate":"2021-03-08 00:09:02.225089","runInputs":["rds@rds1"],"runOutputs":["ods@ods1@dt=20210307"],"baseUri":"http://airflow.ziroom.com/","owners":["caol5"],"sqls":["insert overwrite table ods.ods1 partition(dt=20210307 select * from rds.rds1)"]}
```