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
RUN mkdir -p /usr/webapps/
COPY application_start.sh /usr/webapps/application_start.sh

### warファイルを配置
COPY --from=builder /webapps/target/*.war /usr/webapps/

### ログ格納フォルダを作成
RUN mkdir -p /usr/logs/
RUN chmod 777 /usr/logs

### 非root権限
RUN useradd myuser && chown -R myuser /usr/webapps/ && chmod 755 /usr/webapps/application_start.sh
USER myuser

### 起動
ENTRYPOINT ["/usr/webapps/application_start.sh"]
