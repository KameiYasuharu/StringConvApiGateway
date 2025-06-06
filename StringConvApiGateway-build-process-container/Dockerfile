FROM tomcat:9.0
COPY StringConvApiGateway-build-process-container/webapps/*.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]
