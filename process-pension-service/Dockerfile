FROM openjdk:11
EXPOSE 8082
ADD "target/process-pension-service.jar" "process-pension-service.jar"
ENTRYPOINT [ "java", "-jar", "/process-pension-service.jar" ]