#!/bin/sh

echo `/bin/date "+%Y-%m-%d %H:%M:%S"` "INFO application_start.sh 実行"

echo `/bin/date "+%Y-%m-%d %H:%M:%S"` "INFO Spring Boot 起動"
java -jar /usr/webapps/*.war -XX:MaxRAMPercentage=80.0

