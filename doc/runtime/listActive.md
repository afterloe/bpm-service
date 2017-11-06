# 获取所有活动中的流程

## v1/runtime/list/active
## POST

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
processDefinitionName | String | 流程部署名
processInstanceId | String | 流程实例id
name | String | 流程名

### mock

#### SOA Gateway

```sbtshell
curl -X GET \
  http://10.10.1.103/bpm-server/v1/runtime/list/active \
  -H 'access-token: FDC77FC8A39AC10B984FE62C50F0B0319887EC370355A11E0EF4D1535021156A4E173EDE44761A4EA4AA6B215B2A8AD9' \
  -H 'cache-control: no-cache' \
  -H 'postman-token: b5df3a7b-c3d3-574b-ae27-b10550419994'
```

```json
    "data": [
				{
            "id": "supervisionIncident:2:7504",
            "processDefinitionName": null,
            "processInstanceId": "10001",
            "name": null
        },
        {
            "id": "supervisionIncident:2:7504",
            "processDefinitionName": null,
            "processInstanceId": "10007",
            "name": null
        }
    ],
    "code": 200,
    "msg": null
}
```
