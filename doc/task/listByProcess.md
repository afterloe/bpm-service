# 获取流程下的任务列表

## v1/task/process/{processId}/list
## v1/task/process/list
## GET

> 直接调用

### 请求参数

参数名 | 类型 | 必要 | 描述
------ | ---- | ---- | ----
processId | String | T | 流程id

### 返回参数

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
curl -X GET \
  http://10.10.1.103/bpm-server/v1/task/process/17501/list \
  -H 'access-token: FDC77FC8A39AC10B984FE62C50F0B0319887EC370355A11E0EF4D1535021156A4E173EDE44761A4EA4AA6B215B2A8AD9' \
  -H 'cache-control: no-cache' \
  -H 'postman-token: c259506b-9ac4-a6f1-4931-0e445c6c18aa'
```

```json
{
    "data": [
        {
            "id": "17508",
            "type": "activeTask",
            "name": "河长办公室处理",
            "dueDate": null,
            "localVariables": {},
            "processVariables": {},
            "assignee": "afterloe",
            "owner": null,
            "description": null,
            "createTime": "2017-11-06 09:26:38"
        }
    ],
    "code": 200,
    "msg": null
}
```
