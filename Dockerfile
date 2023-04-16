FROM openjdk:17

RUN mkdir /app

ADD target/footballers-0.0.1-SNAPSHOT.jar /app

WORKDIR /app

CMD ["java", "-jar", "footballers-0.0.1-SNAPSHOT.jar"]
