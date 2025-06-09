FROM maven:3.9.9-amazoncorretto-17-al2023 AS builder
RUN mkdir -p /webapps
WORKDIR /webapps
RUN ls -l
COPY src src
COPY pom.xml pom.xml
RUN mvn clean package -DskipTests
FROM amazoncorretto:17.0.14-al2023
ENV max_ram_percentage="90"
RUN yum update -y && yum install -y shadow-utils
RUN mkdir -p /usr/local/tomcat/webapps/
COPY application_start.sh /usr/local/tomcat/webapps/application_start.sh
COPY --from=builder /webapps/target/*.war /usr/local/tomcat/webapps/
RUN useradd myuser && chown -R myuser /usr/local/tomcat/webapps/ && chmod 755 /usr/local/tomcat/webapps/application_start.sh
USER myuser
ENTRYPOINT ["/usr/local/tomcat/webapps/application_start.sh"]
