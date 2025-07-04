package com.example.stringconverter.controller;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.stringconverter.dto.ConversionRequest;
import com.example.stringconverter.dto.ConversionResponse;
import com.example.stringconverter.service.GatewayConvertService;

@Controller
@RequestMapping
public class ApiGatewayController {

	@Resource
	private GatewayConvertService gatewayConvertService;

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

			// サービスを呼び出す。
			ConversionResponse response = gatewayConvertService.convertService(request);

			// 結果を返す
			return ResponseEntity.ok().body(response.getResultText());

		} catch (Exception e) {
			// エラー発生時の処理
			return ResponseEntity.internalServerError()
					.body(new ConversionResponse("error: " + e.getMessage()));
		}
	}

}
