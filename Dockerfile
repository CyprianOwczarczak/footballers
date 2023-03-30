FROM ubuntu
WORKDIR /myapp

RUN echo "hello"
CMD [ "/bin/sh" ]
# CMD java -version
