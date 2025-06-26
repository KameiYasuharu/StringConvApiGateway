package com.example.stringconverter.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	// 処理開始ログ
	private final String startLogFormat = "START,%s,%s";
	// 他機能呼び出しログ
	private final String callLogFormat = "CALL,%s,%s";
	// 他機能呼び出し後、受信ログ
	private final String receiveLogFormat = "RECEIVE,%s,%s";
	// 処理正常終了ログ
	private final String normalEndLogFormat = "NORMAL_END,%s,%s";
	// 処理異常終了ログ
	private final String errorEndLogFormat = "ERROR_END,%s,%s";

	// コントロール
	@Pointcut("execution(public * com.example.stringconverter..*.*(..)) && within(@org.springframework.stereotype.Controller *) ")
	public void controllerPointcut() {
	}

	// サービス
	@Pointcut("execution(public * com.example.stringconverter..*.*(..)) && within(@org.springframework.stereotype.Service *) ")
	public void servicePointcut() {
	}

	// 共通処理
	@Pointcut("execution(public * org.springframework.web.client..*.*(..))")
	public void commonPointcut() {
	}

	@Around(" servicePointcut() || commonPointcut()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		String className = joinPoint.getTarget().getClass().getSimpleName();
		String methodName = joinPoint.getSignature().getName();

		log.info(String.format(callLogFormat, className, methodName));

		Object result = joinPoint.proceed();

		log.info(String.format(receiveLogFormat, className, methodName));

		return result;
	}

	// 処理開始
	@Before("controllerPointcut() || servicePointcut() || commonPointcut()")
	public void logCommonEntry(JoinPoint jp) {
		String className = jp.getTarget().getClass().getSimpleName();
		String methodName = jp.getSignature().getName();
		log.info(String.format(startLogFormat, className, methodName));
	}

	// 処理正常終了
	@After("controllerPointcut() || servicePointcut() || commonPointcut()")
	public void logCommonExit(JoinPoint jp) {
		String className = jp.getTarget().getClass().getSimpleName();
		String methodName = jp.getSignature().getName();
		log.info(String.format(normalEndLogFormat, className, methodName));
	}

	// 処理異常終了
	@AfterThrowing(pointcut = "controllerPointcut() || servicePointcut() || commonPointcut()", throwing = "ex")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
		String className = joinPoint.getTarget().getClass().getSimpleName();
		String methodName = joinPoint.getSignature().getName();
		log.info(String.format(errorEndLogFormat, className, methodName));
	}

}
