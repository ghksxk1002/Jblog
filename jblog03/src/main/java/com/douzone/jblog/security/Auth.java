package com.douzone.jblog.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// @를 붙이면 인터페이스를 어노테이션으로 쓸수 있다.
// 어노테이션 붙일 곧 정하기 : ElementType 이 메소드다
@Target({ElementType.METHOD, ElementType.TYPE})
// 종속기간
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
	public String role() default "USER";
//	public String value() default "USER";
//	public boolean test() default false;
}
