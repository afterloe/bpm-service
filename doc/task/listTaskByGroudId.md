# 查询某组的任务列表

## v1/task/group/{groupId}/list
## v1/task/group/list
## GET
 
> 直接调用, 两个url都有用，根据开发习惯选择url

### 请求参数

参数名 | 类型 | 必要 | 描述
------ | ---- | ---- | ----
groupId | String | T | 被查询组的用户唯一编码
page | Number | F | 请求页码 - default 0
number | Number | F | 每页条数 - default 50

### 返回参数

参数名 | 类型 | 描述
------ | ---- | ----
id | Number | 任务id
name | String | 任务名字
processInstanceId | String | 流程实例id
assignee | String | 签收人id
owner | String | 任务归属人
description | String | 任务描述
createTime | String | 任务创建时间

### mock

#### SOA Gateway

```sbtshell
curl -X GET \
  http://10.10.1.103/bpm-server/v1/task/group/afterloe/list \
  -H 'access-token: FDC77FC8A39AC10B984FE62C50F0B0319887EC370355A11E0EF4D1535021156A4E173EDE44761A4EA4AA6B215B2A8AD9' \
  -H 'cache-control: no-cache' \
  -H 'postman-token: c2af72ad-496d-bf94-967e-e06d480534e3'
```

```json
{
    "data": [
        {
            "name": "河长办公室处理",
            "id": "17508",
            "processInstanceId": "17501",
            "assignee": "afterloe",
            "createTime": "2017-11-06 09:26:38",
            "description": null,
            "owner": null
        }
    ],
    "code": 200,
    "msg": null
}
```
