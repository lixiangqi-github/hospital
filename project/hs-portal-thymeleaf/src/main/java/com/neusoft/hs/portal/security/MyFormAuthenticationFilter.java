package com.neusoft.hs.portal.security;

import javax.servlet.ServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

public class MyFormAuthenticationFilter extends FormAuthenticationFilter {

	public final static String LoginFailureExeception = "LoginFailureExeception";

	@Override
	protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
		request.setAttribute(LoginFailureExeception, ae);
	}

}
