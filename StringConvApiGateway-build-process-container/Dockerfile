FROM tomcat:9.0
COPY webapps/*.war /usr/local/tomcat/webapps/
RUN ls -la /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]
