package com.example.stringconverter;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * WARデプロイ用サーブレット初期化クラス
 * 
 * 外部のServletコンテナ(Tomcat等)にデプロイする場合に必要
 * Spring Bootアプリケーションを従来型のWAR形式でデプロイ可能にする
 */
public class ServletInitializer extends SpringBootServletInitializer {

    /**
     * アプリケーション設定メソッド
     * 
     * @param application Springアプリケーションビルダー
     * @return 設定済みアプリケーションビルダー
     * 
     * このメソッドはServletコンテナ起動時に呼び出され、
     * メインアプリケーションクラスを指定する役割を持つ
     * 
     * 処理内容:
     * 1. メインアプリケーションクラス(StringConvApiGatewayApplication)を指定
     * 2. 外部Servletコンテナ用の設定を適用
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        // メインアプリケーションクラスを登録
        return application.sources(StringConvApiGatewayApplication.class);
    }
}