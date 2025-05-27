package com.example.stringconverter.dto;

/**
 * 文字列変換リクエスト用DTOクラス
 */
public class ConversionRequest {
	// 変換対象の入力文字列
	private String inputString;

	/**
	 * デフォルトコンストラクタ
	 */
	public ConversionRequest() {
	}

	/**
	 * 入力文字列を指定するコンストラクタ
	 * @param inputString 変換対象の文字列
	 */
	public ConversionRequest(String inputString) {
		this.inputString = inputString;
	}

	/**
	 * 入力文字列を取得
	 * @return 入力文字列
	 */
	public String getInputString() {
		return inputString;
	}

	/**
	 * 入力文字列を設定
	 * @param inputString 設定する文字列
	 */
	public void setInputString(String inputString) {
		this.inputString = inputString;
	}
}