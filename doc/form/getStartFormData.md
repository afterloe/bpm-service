# 获取流程启动表单

## v1/form
## v1/form/{processId}
## GET

> 该接口有两个url，可根据喜好或需求自行选择

### 请求参数

参数名 | 类型 | 必要 | 描述
------ | ---- | ---- | ----
processId | String | t | 流程id

### 返回参数

参数名 | 类型 | 描述
------ | ---- | ----
type | String | formKey - 外置表单, properties - 内置表单
formData | String or Array | 如果是外置表单，则返回外置表单的表单名。内置表单返回表单的每一项详情

### mock

#### SOA Gateway

> 获取流程id 为`supervisionIncident:1:4`的启动表单

**/v1/form/{processId}**

```sbtshell
curl -X GET \
  http://10.10.1.103/bpm-server/v1/form/supervisionIncident:1:4 \
  -H 'access-token: FDC77FC8A39AC10B984FE62C50F0B0319887EC370355A11E0EF4D1535021156A4E173EDE44761A4EA4AA6B215B2A8AD9' \
  -H 'cache-control: no-cache' \
  -H 'postman-token: 7f696753-c0a0-e098-8052-8a8b45ec1b4b'
```

```json
{
    "data": {
        "type": "formKey",
        "formData": "SupervisionIncident.form"
    },
    "code": 200,
    "msg": null
}
```
