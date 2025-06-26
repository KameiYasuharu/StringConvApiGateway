package com.example.stringconverter.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.stringconverter.dto.ConversionRequest;
import com.example.stringconverter.dto.ConversionResponse;

@Service
public class GatewayConvertService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	// AWS API GatewayのURLをapplication.propertiesから注入
	@Value("${aws.api.gateway.url}")
	private String awsApiGatewayUrl;

	// REST API呼び出し用のテンプレート
	@Resource
	private RestTemplate restTemplate;

	public ConversionResponse convertService(ConversionRequest request) {

		String url = awsApiGatewayUrl + "?inputString="
				+ URLEncoder.encode(request.getInputString(), StandardCharsets.UTF_8);

		log.info("Forwarding to: " + url + " (Method: GET)");

		// AWS API Gatewayにリクエストを送信
		ConversionResponse response = restTemplate.getForObject(
				url,
				ConversionResponse.class);

		return response;

	}

}
