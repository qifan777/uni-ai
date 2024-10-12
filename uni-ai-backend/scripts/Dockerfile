FROM openjdk:17
# 作者的信息，可以不填
MAINTAINER http://www.jarcheng.top/
# 注意这里，这个地方访问的是build时指定的资源路径。而不是当前文件夹下的文件。
# 将资源路径内的的jar包夹复制到镜像内并且重命名为app.jar。
COPY ./target/server-0.1.11.jar app.jar
# 镜像实例化，即启动容器后的运行命令，我们这边就是启动服务
ENTRYPOINT ["java","-jar", "/app.jar"]