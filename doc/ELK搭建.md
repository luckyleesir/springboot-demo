# kibana 7.2
## 简介
数据分析与可视化平台，对Elasticsearch存储的数据进行可视化分析，通过表格的形式展现出来。

## 下载
<a href="https://www.elastic.co/cn/downloads/kibana">kibana7.2.0</a>

## 搭建步骤
1. 上传安装包到linux服务器，目录为：/usr/local/elk
2. 解压安装包：`tar -xzvf kibana-7.2.0-linux-x86_64.tar.gz`
3. 进入到kibana根目录：`cd kibana-7.2.0-linux-x86_64` 
4. 配置kibana.yml：`vim config/kibana.yml`  
    ```
    server.port: 5601
    server.host: "服务器ip"
    elasticsearch.hosts: ["http://服务器ip:9200"]
    i18n.locale: "zh-CN"
    ```
5. 创建后台启动脚本startup.sh：`vim startup.sh`   
   脚本内容： `nohup bin/kibana --allow-root &`

6. 赋予startup.sh执行权限： `chmod 755 startup.sh`
7. 启动kibana： `./startup.sh`
8. 查看是否5601端口是否启动： `netstat -anp|grep 5601`
9. 浏览器打开kibana,进入界面： `http://服务器ip:5601`


# logstash 7.2
## 简介
数据收集引擎，它支持动态的的从各种数据源获取数据，并对数据进行过滤，分析，丰富，统一格式等操作，然后存储到用户指定的位置。

## 下载
<a href="https://www.elastic.co/cn/downloads/logstash">logstash7.2.0</a>

## 搭建步骤
