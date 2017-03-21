FROM domblack/oracle-jre8
RUN mkdir -p /jet/controller
WORKDIR /jet/controller
COPY target/libs/ /jet/controller/libs
COPY target/*.jar /jet/controller/
