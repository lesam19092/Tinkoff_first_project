FROM openjdk:21
LABEL authors="lesam19092"

COPY ./target/scrapper.jar scrapper.jar

ENTRYPOINT ["java","-jar","/scrapper.jar"]
