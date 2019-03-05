# FROM openjdk:13-ea-7-jdk-alpine3.9
FROM maven:3.6.0-jdk-12-alpine

WORKDIR /validation_service
COPY . /validation_service
# ENV eureka.instance.hostname=eurekaserver:8761
# ENV eureka.instance.preferIpAddress=true
RUN mvn -Dmaven.test.skip=true clean package && rm -rf /root/.m2 && rm -rf /opt/openjdk-12/jmods
CMD ["java", "-jar", "target/business-1.0-SNAPSHOT.jar"]
