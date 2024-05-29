docker network create 1panel-network

sudo mkdir -p /home/nginx/conf
sudo mkdir -p /home/nginx/log
sudo mkdir -p /home/nginx/html

sudo docker run \
-p 80:80 \
-p 443:443 \
--name nginx \
--network=1panel-network \
--network-alias=nginx \
-d nginx:latest

sudo docker cp nginx:/etc/nginx/nginx.conf /home/nginx/conf/nginx.conf
sudo docker cp nginx:/etc/nginx/conf.d /home/nginx/conf/conf.d
sudo docker cp nginx:/usr/share/nginx/html /home/nginx/

sudo docker rm -f nginx

sudo docker run \
-p 80:80 \
-p 443:443 \
--name nginx \
--network=1panel-network \
--network-alias=nginx \
-v /home/nginx/conf/nginx.conf:/etc/nginx/nginx.conf \
-v /home/nginx/conf/conf.d:/etc/nginx/conf.d \
-v /home/nginx/log:/var/log/nginx \
-v /home/nginx/html:/usr/share/nginx/html \
-d nginx:latest

docker run --name redis \
-p 6379:6379 \
--network=1panel-network \
--network-alias=redis \
--restart=always \
-v redis-data:/data \
-d redis:7.2 redis-server \
--save 60 1 \
--loglevel warning

docker run -p 3306:3306 \
-d \
--name mysql8 \
-e MYSQL_ROOT_PASSWORD=123456 \
-e TZ=Asia/Shanghai  \
-e MYSQL_DATABASE=uni_ai \
-v mysql-data:/var/lib/mysql \
--network=1panel-network \
--network-alias=mysql \
--restart=always \
mysql:8.0.26 \
mysqld --character-set-server=utf8mb4 \
--collation-server=utf8mb4_unicode_ci  
```shell
curl --location 'https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions' \
--header "Authorization: Bearer sk-9846f3a9c6604c488650f0d7936565f0" \
--header 'Content-Type: application/json' \
--data '{
    "model": "qwen-plus",
    "messages": [
        {
            "role": "system",
            "content": "You are a helpful assistant."
        },
        {
            "role": "user", 
            "content": "你是谁？"
        }
    ]
}'
```