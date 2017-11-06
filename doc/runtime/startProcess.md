# 启动流程

## v1/runtime
## POST

> 直接调用

### 请求参数

参数名 | 类型 | 必要 | 描述
------ | ---- | ---- | ----
starter | String | T | 启动人id
processDefinitionKey | String | T | 流程定义的启动key
businessKey | String | T | 业务外键

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

activeTaskInfo

参数名 | 类型 | 描述
------ | ---- | ----
id | Number | 任务id
type | String | 任务类型 activeTask - 活动任务，historyTask - 历史任务
name | String | 任务名字
dueDate | String | 指定完成时间
localVariables | Object | 任务数据集
processVariables | Object | 流程数据集
assignee | String | 签收人id
owner | String | 任务归属人
description | String | 任务描述
createTime | String | 任务创建时间

### mock

#### SOA Gateway

```sbtshell
curl -X POST \
  http://10.10.1.103/bpm-server/v1/runtime \
  -H 'access-token: FDC77FC8A39AC10B984FE62C50F0B0319887EC370355A11E0EF4D1535021156A4E173EDE44761A4EA4AA6B215B2A8AD9' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/x-www-form-urlencoded' \
  -H 'postman-token: a22817e9-7b94-c673-ca95-6405bc4177de' \
  -d 'starter=afterloe&processDefinitionKey=supervisionIncident&businessKey=cb6c4b866ec644019478b0c270937969'
```

```json
{
    "data": {
        "type": "activeProcess",
        "businessKey": "cb6c4b866ec644019478b0c270937969",
        "name": null,
        "id": "17501",
        "version": null,
        "definitionName": null,
        "definitionId": "supervisionIncident:2:7504",
        "processId": "17501",
        "activeTaskId": "17505",
        "activeTaskInfo": {
            "id": "17505",
            "type": "activeTask",
            "name": "区级河长批准",
            "dueDate": null,
            "localVariables": {},
            "processVariables": {},
            "assignee": null,
            "owner": null,
            "description": null,
            "createTime": "2017-11-06 09:04:52"
        }
    },
    "code": 200,
    "msg": null
}
```
