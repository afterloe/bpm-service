# 获取流程详细信息

## v1/process/{processId}
## GET

> 直接调用

### 请求参数

参数名 | 类型 | 必要 | 描述
------ | ---- | ---- | ----
processId | String | t | 流程id
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

### mock

#### SOA Gateway

> 获取流程id 为``的详细信息

**/v1/process/{processId}**

```sbtshell
curl -X GET \
  'http://10.10.1.103/bpm-server/v1/process/5007?uid=3' \
  -H 'access-token: FDC77FC8A39AC10B984FE62C50F0B0319887EC370355A11E0EF4D1535021156A4E173EDE44761A4EA4AA6B215B2A8AD9' \
  -H 'cache-control: no-cache' \
  -H 'postman-token: 6cbf9b0d-1ee0-3713-ee18-b6c81c969dea'
```

```json
{
    "data": {
        "type": "activeProcess",
        "businessKey": "121bcd99ab2643ed93b3524f1be18301",
        "name": null,
        "id": "5007",
        "version": 1,
        "definitionName": null,
        "definitionId": "supervisionIncident:1:4",
        "processId": "5007",
        "activeTaskId": "5024",
        "activeTaskInfo": {
            "id": "5024",
            "type": "activeTask",
            "name": "河长办公室处理",
            "dueDate": null,
            "localVariables": {},
            "processVariables": {},
            "assignee": null,
            "owner": null,
            "description": null,
            "createTime": "2017-10-19 15:08:03"
        }
    },
    "code": 200,
    "msg": null
}
```
