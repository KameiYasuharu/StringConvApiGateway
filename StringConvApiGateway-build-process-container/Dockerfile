FROM maven:3.9.9-amazoncorretto-17-al2023 AS builder
RUN mkdir -p /app
WORKDIR /app
COPY src src
COPY pom.xml pom.xml
RUN mvn clean package -DskipTests
FROM amazoncorretto:17.0.14-al2023
ENV max_ram_percentage="90"
RUN yum update -y && yum install -y shadow-utils
RUN mkdir -p /usr/app
COPY application_start.sh /usr/app/application_start.sh
COPY --from=builder /app/target/*.war /usr/app/
RUN useradd myuser && chown -R myuser /usr/app && chmod 755 /usr/app/application_start.sh
USER myuser
ENTRYPOINT ["/usr/app/application_start.sh"]
