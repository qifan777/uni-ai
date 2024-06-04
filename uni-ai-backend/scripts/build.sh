cd ../../uni-ai-admin
npm run build-only
cp -r ./dist ../uni-ai-backend/server/src/main/resources
cd ../uni-ai-backend
gradle server:bootJar
cd scripts
docker build -t qifan7/uni-ai:0.1.0-snapshot -f Dockerfile ../server
docker push qifan7/uni-ai:0.1.0-snapshot