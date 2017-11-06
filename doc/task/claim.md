# 签收任务

## v1/task/claim/{taskId}
## PUT

> 直接调用

### 请求参数

参数名 | 类型 | 必要 | 描述
------ | ---- | ---- | ----
taskId | String | T | 任务id
uid | String | T | 用户id

### 返回参数

参数名 | 类型 | 描述
------ | ---- | ----
true | Boolean | 签收成功

### mock

#### SOA Gateway

```sbtshell
curl -X PUT \
  http://10.10.1.103/bpm-server/v1/task/claim/17505 \
  -H 'access-token: FDC77FC8A39AC10B984FE62C50F0B0319887EC370355A11E0EF4D1535021156A4E173EDE44761A4EA4AA6B215B2A8AD9' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/x-www-form-urlencoded' \
  -H 'postman-token: a71ec5d8-2009-ffdc-8d22-5a2cf3c7db64' \
  -d uid=afterloe
```

```json
{
    "data": true,
    "code": 200,
    "msg": null
}
```
