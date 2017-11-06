# 获取已有流程图列表

## v1/repository/list
## GET

> 直接调用

### 请求参数

参数名 | 类型 | 必要 | 描述
------ | ---- | ---- | ----
page | Number | F | 请求页码 - default 0
number | Number | F | 每页条数 - default 50

### 返回参数

参数名 | 类型 | 描述
------ | ---- | ----
id | String | 流程部署id
name | String | 流程部署名
category | String | 流程图
key | String | 流程key
description | String | 描述
version | Number | 流程版本
resourceName | String | 流程bpm图名
deploymentId | String | 部署人id
diagramResourceName | String | 资源图位置

### mock

#### SOA Gateway

```sbtshell
curl -X GET \
  http://10.10.1.103/bpm-server/v1/repository/list \
  -H 'access-token: FDC77FC8A39AC10B984FE62C50F0B0319887EC370355A11E0EF4D1535021156A4E173EDE44761A4EA4AA6B215B2A8AD9' \
  -H 'cache-control: no-cache' \
  -H 'postman-token: 977e69ee-b656-1015-6b83-cf59836ef9ba'
```

```json
{
    "data": [
        {
            "id": "supervisionIncident:1:4",
            "name": null,
            "category": "http://www.activiti.org/processdef",
            "key": "supervisionIncident",
            "description": null,
            "version": 1,
            "resourceName": "/project/java/bpm-engine/activiti-demo/out/production/resources/processes/supervisionIncident.bpmn20.xml",
            "deploymentId": "1",
            "diagramResourceName": "/project/java/bpm-engine/activiti-demo/out/production/resources/processes/supervisionIncident.supervisionIncident.png",
            "tenantId": ""
        },
        {
            "id": "supervisionIncident:2:7504",
            "name": null,
            "category": "http://www.activiti.org/processdef",
            "key": "supervisionIncident",
            "description": null,
            "version": 2,
            "resourceName": "supervisionIncident.bpmn20.xml",
            "deploymentId": "7501",
            "diagramResourceName": "supervisionIncident.supervisionIncident.png",
            "tenantId": ""
        }
    ],
    "code": 200,
    "msg": null
}
```
