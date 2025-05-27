package com.example.stringconverter.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.example.stringconverter.dto.ConversionRequest;
import com.example.stringconverter.dto.ConversionResponse;

@Controller
@RequestMapping
public class ApiGatewayController {

	// AWS API GatewayのURLをapplication.propertiesから注入
	@Value("${aws.api.gateway.url}")
	private String awsApiGatewayUrl;

	// REST API呼び出し用のテンプレート
	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 変換フォームを表示する
	 * @return 変換画面のテンプレート名
	 */
	@GetMapping("/")
	public String showLoginForm() {
		return "conversion";
	}

	/**
	 * API Gateway経由で文字列変換を行う
	 * @param request 変換リクエストDTO
	 * @return 変換結果を含むレスポンスエンティティ
	 */
	@GetMapping("/ApiGateway")
	@ResponseBody
	public ResponseEntity<?> gatewayConvert(@ModelAttribute ConversionRequest request) {
		try {
			String url = awsApiGatewayUrl + "?inputString="
					+ URLEncoder.encode(request.getInputString(), StandardCharsets.UTF_8);
			System.out.println("Forwarding to: " + url + " (Method: GET)");
			// AWS API Gatewayにリクエストを送信
			ConversionResponse response = restTemplate.getForObject(
					url,
					ConversionResponse.class);

			// 結果を返す
			return ResponseEntity.ok().body(response.getResultText());

		} catch (Exception e) {
			// エラー発生時の処理
			return ResponseEntity.internalServerError()
					.body(new ConversionResponse("error: " + e.getMessage()));
		}
	}
}
