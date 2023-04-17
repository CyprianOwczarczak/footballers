FROM openjdk:17

RUN mkdir /app

COPY target/footballers-0.0.1-SNAPSHOT.jar /app

WORKDIR /app

CMD ["java", "-jar", "footballers-0.0.1-SNAPSHOT.jar"]

#CMD ["java", "-version"]
#Open in interactive mode -it
#We have change the application settings to open postgres on my machine, not on localhost