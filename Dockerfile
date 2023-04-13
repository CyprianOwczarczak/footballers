FROM amazoncorretto
WORKDIR /myapp

COPY D:/PROGRAMMING/JAVA/footballers/src /

RUN mvn package

EXPOSE 80

# RUN echo "hello"
# CMD [ "/bin/sh" ]
