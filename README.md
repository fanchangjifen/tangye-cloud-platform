# tangye-cloud-platform
唐冶码农的碎碎念...

Spring cloud H + Spring cloud alibaba + Spring boot技术架构，脱胎于日常工作中使用的技术，中间件集成方式，计划提供生产级别对各种常用中间件集成的样例项目。项目起步，用Mysql祭天！
最近一直在纠结每个样例要不要默认集成注册中心，最终还是觉得如果定位成生产级别的demo项目的话，还是集成吧！
现在正式宣布，本项目默认使用Alibaba Nacos作为注册&&配置中心，注册中心每个模块默认集成，配置中心的使用单独样例提供。

# tangye-angel-common
公共项目模块，公共方法，公共常量等。移除了默认的Tomcat容器，选择了Undertow。

# tangye-angel-mysql
基于dynamic-datasource，mybatis-plus，druid实现对mysql数据库操作的样例项目，支持Mysql5.7.x,Mysql8.0.x版本

# tangye-angel-elasticsearch6
基于elasticsearch-rest-high-level-client实现对Elasticsearch6.x版本的集成样例

# tangye-angel-elasticsearch7
基于elasticsearch-rest-high-level-client实现对Elasticsearch7.x版本的集成样例

# tangye-angel-rabbitmq
基于rabbitmq3.6.5实现对rabbitmq客户端的集成样例

# tangye-angel-presto
基于presto实现对异构数据源操作

# tangye-angel-dataway
依托 DataQL 服务聚合能力，为应用提供一个 UI 界面。并以 jar 包的方式集成到应用中。 通过 Dataway 可以直接在界面上配置和发布接口