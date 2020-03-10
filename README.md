## Phoenix

采用RPC实现业务check服务

- producer为business service为主要业务逻辑线，通过pingback调用check服务进行校验
- registry采用zookeeper+dubbo完成服务注册和发现
- checker为check service负责对已有业务进行数据校验

![RPC](./image/rpc.png)

