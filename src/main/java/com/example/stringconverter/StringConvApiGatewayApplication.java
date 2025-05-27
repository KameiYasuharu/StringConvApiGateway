package com.example.stringconverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * StringConverterアプリケーションのメインクラス
 * 
 * <p>Spring Bootアプリケーションの起動と基本設定を管理します。</p>
 */
@SpringBootApplication // (1) Spring Bootの自動設定を有効化（コンポーネントスキャン、自動設定など）
public class StringConvApiGatewayApplication {

    /**
     * アプリケーションのエントリーポイント
     * 
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        // (2) Spring Bootアプリケーションを起動
        // - 内蔵Tomcatサーバーの起動
        // - アプリケーションコンテキストの初期化
        // - 自動設定の適用
        SpringApplication.run(StringConvApiGatewayApplication.class, args);
    }

    /**
     * RestTemplateのBean定義
     * 
     * <p>REST API呼び出し用のテンプレートを提供します。</p>
     * 
     * @return 新しいRestTemplateインスタンス
     */
    @Bean
    public RestTemplate restTemplate() {
        // (3) HTTP通信を行うためのRestTemplateを生成
        // - API Gatewayとの通信などに使用
        return new RestTemplate();
    }
}