# 获取流程id下所有历史活动任务

## v1/history
## v1/history/{processId}
## GET

> 该接口有两个url，可根据喜好或需求自行选择

### 请求参数

参数名 | 类型 | 必要 | 描述
------ | ---- | ---- | ----
processId | String | t | 流程id

### 返回参数

参数名 | 类型 | 描述
------ | ---- | ----
startTime | String | 启动时间
endTime | String | 结束时间
assignee | String | 完成人id
workerTime | Number | 工作时常 - 秒
name | String | 任务名称
claimTime | String | 签收任务的时间

### mock

#### SOA Gateway

> 获取流程id 为 `` 的历史活动任务

**/v1/hisyory/{processId}**

```sbtshell
curl -X GET \
  http://10.10.1.103/bpm-server/v1/history/5007 \
  -H 'access-token: FDC77FC8A39AC10B984FE62C50F0B0319887EC370355A11E0EF4D1535021156A4E173EDE44761A4EA4AA6B215B2A8AD9' \
  -H 'cache-control: no-cache' \
  -H 'postman-token: 23c2bcc9-555f-e731-da0e-1146a81acd78'
```

```json
{
    "data": [
        {
            "startTime": "2017-10-19 14:18:06",
            "endTime": "2017-10-19 15:08:03",
            "assignee": "2c9eeea05e8481e5015e84d4414f0004",
            "workerTime": 306,
            "name": "区级河长批准",
            "claimTime": "2017-10-19 15:08:03"
        },
        {
            "startTime": "2017-10-19 15:08:03",
            "endTime": null,
            "assignee": null,
            "workerTime": null,
            "name": "河长办公室处理",
            "claimTime": null
        }
    ],
    "code": 200,
    "msg": null
}
```
