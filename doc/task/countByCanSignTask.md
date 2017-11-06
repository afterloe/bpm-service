# 查询某人的可签收任务数

## v1/task/count/{userId}
## v1/task/count
## GET

> 直接调用

### 请求参数

参数名 | 类型 | 必要 | 描述
------ | ---- | ---- | ----
userId | String | T | 查询人唯一标识

### 返回参数

参数名 | 类型 | 描述
------ | ---- | ----
data | Number | 可获取的任务数量

### mock

#### SOA Gateway

```sbtshell
curl -X GET \
  http://10.10.1.103/bpm-server/v1/task/count/afterloe \
  -H 'access-token: FDC77FC8A39AC10B984FE62C50F0B0319887EC370355A11E0EF4D1535021156A4E173EDE44761A4EA4AA6B215B2A8AD9' \
  -H 'cache-control: no-cache' \
  -H 'postman-token: b22df54f-896a-bacc-1590-930cc83d3c05'
```

```json
{
    "data": 0,
    "code": 200,
    "msg": null
}
```
