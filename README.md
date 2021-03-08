# amazon-listing-helper
跨境电商 商品listing 助手


## 功能说明

 - 根据关键字从amazon网站查询商品列表
 - 对用户选择的商品给出营收成本的计算公式
 
 
## 代码结构说明

[web/demo](web/demo)   Demo 页面

[web/demo_fba](web/demo_fba)  Demo 页面

[web/server](web/server) 是Java开发的web应用



[web/server/src/main/resources/templates/index.ftl](web/server/src/main/resources/templates/index.ftl) 

该文件是用到的html页面


[web/server/src/main/resources/static/js/network.js](web/server/src/main/resources/static/js/network.js)

javascript 文件中用到了两个接口， 分别去查询产品列表和 获取单个商品的详细信息




[web/server/src/main/java/cn/nwcdcloud/samples/listingHelper/service/impl/ProductServiceImpl.java](web/server/src/main/java/cn/nwcdcloud/samples/listingHelper/service/impl/ProductServiceImpl.java)

Java 文件实现了从Amazon 网站抓取商品的详细信息。 


