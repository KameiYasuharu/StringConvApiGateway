FROM tomcat:9.0
COPY webapps/StringConvApiGateway /usr/local/tomcat/webapps/StringConvApiGateway
EXPOSE 8080
CMD ["catalina.sh", "run"]
