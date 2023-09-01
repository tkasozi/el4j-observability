package com.github.tkasozi.help_config;

import lombok.NonNull;

import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.DefaultAopProxyFactory;

@lombok.experimental.UtilityClass
public class UtilityClass {
	public static AopProxy getAopProxy(
			final @NonNull Object aspect, final @NonNull Object object) {
		final AspectJProxyFactory aspectJProxyFactory = new AspectJProxyFactory(object);
		aspectJProxyFactory.addAspect(aspect);

		final DefaultAopProxyFactory proxyFactory = new DefaultAopProxyFactory();
		return proxyFactory.createAopProxy(aspectJProxyFactory);
	}
}
