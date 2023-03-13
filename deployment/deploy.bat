@ECHO OFF

SET "SERVER_IP=207.148.71.252"
SET "SERVER_DIR=/app/auth"
SET "SERVICE_NAME=formlaez-auth-server"
SET "VERSION=0.0.1-SNAPSHOT"

ECHO start deployment to server %SERVER_IP%

ECHO building jar file...
call ..\gradlew.bat clean build -p ..\

ECHO uploading jar file...
scp ../Dockerfile root@%SERVER_IP%:%SERVER_DIR%/Dockerfile
scp ../docker-compose.dev.yml root@%SERVER_IP%:%SERVER_DIR%/docker-compose.yml
scp ../build/libs/%SERVICE_NAME%-%VERSION%.jar root@%SERVER_IP%:%SERVER_DIR%/target/app.jar

ECHO restarting app on server...
ssh root@%SERVER_IP% "cd %SERVER_DIR% && docker compose up -d --build"

ECHO completed!