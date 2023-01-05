package org.zerock.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect // 해당 클래스의 객체가 Aspect를 구현한 것임을 알림
@Log4j
@Component // 스프링에서 bean으로 인식하기 위해서 사용
public class LogAdvice {
	
	// execution -> 접근제한자와 특정 클래스의 메서드를 지정할 수 있다. 
	// 맨 앞의 *는 접근제한자를 의미하고, 맨 뒤의 *는 클래스의 이름과 메서드의 이름을 의미한다.
	// (..) -> 매개변수가 있든 없든 상관 없이 SampleService의 모든 메서드가 실행되기 전에 logBefore 메서드가 자동으로 동작함.( 타겟의 조인포인트를 호출하기 전에 실행되는 코드. )
	@Before ( "execution( * org.zerock.service.SampleService*.*(..))")
	public void logBefore() {
		log.info("====================");
	}
	
	// @Around는 조금 특별하게 동작을 한다. 직접 대상 메서드를 실행할 수 있는 권한을 가지고 있고, 메서드의 실행 전과 실행 후에 처리가 가능하다.
	// ProceedingJoinPoint는 @Around와 같이 결합해서 파라미터나 예외 등을 처리할 수 있다.
	@Around ( "execution( * org.zerock.service.SampleService*.*(..))")
	public Object logTime( ProceedingJoinPoint pjp) {
		long start = System.currentTimeMillis();
		
		log.info("Target: " + pjp.getTarget());
		log.info("Param: " + Arrays.toString(pjp.getArgs()));
		
		Object result = null;
		try {
			result = pjp.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		
		log.info("TIME: " + (end - start));
		
		return result;
	}
	
	@After ( "execution( * org.zerock.service.SampleService*.*(..))")
	public void after() {
		log.info("========after=======");
	}
}
