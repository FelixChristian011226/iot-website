@echo off

cd ./mysql
docker build -t mysql:v1.0.0 .
cd ..

cd ./redis
docker build -t redis:v1.0.0 .
cd ..

cd ./emqx
docker build -t emqx:v1.0.0 .
cd ..

cd ./iotclient
docker build -t iotclient:v1.0.0 .
cd ..

cd ./backend
docker build -t springboot:v1.0.0 .
cd ..

cd ./frontend
docker build -t vue:v1.0.0 .
cd ..

docker-compose up

pause