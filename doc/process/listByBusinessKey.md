# 通过业务外键获取流程实例

## v1/process/list/byBusinessKey/{businessKey}
## GET

> 直接调用

### 请求参数

参数名 | 类型 | 必要 | 描述
------ | ---- | ---- | ----
businessKey | String | t | 流程id
uid | String | t | 用户id

### 返回参数

参数名 | 类型 | 描述
------ | ---- | ----
type | String | 流程类型 activeProcess - 活动中, historyProcess - 已经完成的任务
businessKey | String | 业务外键
name | String | 流程名
id | Number | 流程id
version | Number | 流程版本
definitionName | String | 流程部署名
definitionId | String | 流程部署id
processId | Number | 流程id
activeTaskId | Number | 当前活动任务id
activeTaskInfo | Object | 活动任务详情
startTime | String | 启动时间
endTime | String | 结束时间
durationInMillis | Number | 消耗时间
processVariables | Object | 流程实力数据

### mock

#### SOA Gateway

> 获取流程id 为``的详细信息

**/v1/process/list/byBusinessKey/{businessKey}**

```sbtshell
curl -X GET \
  'http://10.10.1.103/bpm-server/v1/process/list/byBusinessKey/276040d36b6a4c9aa9e0d0f360c13952?uid=3' \
  -H 'access-token: FDC77FC8A39AC10B984FE62C50F0B0319887EC370355A11E0EF4D1535021156A4E173EDE44761A4EA4AA6B215B2A8AD9' \
  -H 'cache-control: no-cache' \
  -H 'postman-token: af8bff0d-f0dc-90d6-79e0-98288cdd87c3'
```

```json
{
    "data": [
        {
            "type": "historyProcess",
            "businessKey": "276040d36b6a4c9aa9e0d0f360c13952",
            "name": null,
            "id": "15572",
            "definitionId": "supervisionIncident:2:7504",
            "processId": "15572",
            "startTime": "2017-11-06 09:00:11",
            "endTime": "2017-11-06 10:55:52",
            "durationInMillis": 6940096,
            "processVariables": {}
        }
    ],
    "code": 200,
    "msg": null
}
```
