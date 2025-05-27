package com.example.stringconverter.dto;

/**
 * 文字列変換レスポンス用DTOクラス
 */
public class ConversionResponse {
	// 変換結果テキスト
	private String resultText;

	/**
	 * デフォルトコンストラクタ
	 */
	public ConversionResponse() {
	}

	/**
	 * 変換結果を指定するコンストラクタ
	 * @param resultText 変換結果文字列
	 */
	public ConversionResponse(String resultText) {
		this.resultText = resultText;
	}

	/**
	 * 変換結果を取得
	 * @return 変換結果文字列
	 */
	public String getResultText() {
		return resultText;
	}

	/**
	 * 変換結果を設定
	 * @param resultText 設定する変換結果文字列
	 */
	public void setResultText(String resultText) {
		this.resultText = resultText;
	}
}