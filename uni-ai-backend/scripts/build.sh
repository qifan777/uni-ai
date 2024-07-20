cd ../../uni-ai-admin
npm run build-only
cp -r ./dist ../uni-ai-backend/server/src/main/resources
cd ../uni-ai-backend
mvn -DskipTests=true package
cd scripts
docker build -t qifan7/uni-ai:0.1.7 -f Dockerfile ../server
docker push qifan7/uni-ai:0.1.7