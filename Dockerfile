FROM openjdk:18-jdk AS builder
WORKDIR application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} cps-middleware-airline-strategic-dashboard-0.0.1-SNAPSHOT.jar
RUN java -Djarmode=layertools -jar cps-middleware-airline-strategic-dashboard-0.0.1-SNAPSHOT.jar extract

FROM openjdk:18-jdk
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java","-Dspring.profiles.active=dockerdev","org.springframework.boot.loader.JarLauncher"]