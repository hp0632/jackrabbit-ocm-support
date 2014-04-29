package org.mplus.jcr.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibsProxy implements InitProxy,MethodInterceptor{
	
	private Object target;
	

	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		return proxy.invokeSuper(obj, args);
	}


	@Override
	public Object bind(Object targetImpl) {
		this.target = targetImpl;  
        Enhancer enhancer = new Enhancer();  
        enhancer.setSuperclass(this.target.getClass());  
        // 回调方法  
        enhancer.setCallback(this);  
        // 创建代理对象  
        return enhancer.create();  
	}

	
}
