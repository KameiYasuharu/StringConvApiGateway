# ビルドステージ
## ベースイメージの指定
FROM maven:3.9.9-amazoncorretto-17-al2023 AS builder

# 資材をダウンロード
RUN mkdir -p /webapps
WORKDIR /webapps
RUN ls -l
COPY src src
COPY pom.xml pom.xml

# ビルドコマンドの実行
RUN mvn clean package -DskipTests

# 実行ステージ
## ベースイメージの指定
FROM amazoncorretto:17.0.14-al2023

## 環境変数の設定
ENV max_ram_percentage="90"

## 実行変数に必要なSWインストール
RUN yum update -y && yum install -y shadow-utils

## デプロイ
### 実行用シェルの配置
RUN mkdir -p /usr/local/tomcat/webapps/
COPY application_start.sh /usr/local/tomcat/webapps/application_start.sh

### warファイルを配置
COPY --from=builder /webapps/target/*.war /usr/local/tomcat/webapps/

### 非root権限
RUN useradd myuser && chown -R myuser /usr/local/tomcat/webapps/ && chmod 755 /usr/local/tomcat/webapps/application_start.sh
USER myuser

### 起動
ENTRYPOINT ["/usr/local/tomcat/webapps/application_start.sh"]
