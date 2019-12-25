package com.sep.zuul.config;


import com.netflix.zuul.ZuulFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

import com.netflix.zuul.context.RequestContext;

public class ZuulPreFilter extends ZuulFilter {

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
	    HttpServletRequest request = ctx.getRequest();  
	    int port = request.getLocalPort();
	    System.out.println(port);
	    
	    if(port == 8672 && request.getRequestURI().contains("nc")) {
	    	ctx.setSendZuulResponse(false);
	        ctx.getResponse().setHeader("Content-Type", "text/plain;charset=UTF-8");
	        ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
	    }
	    
		return null;
	}

	@Override
	public int filterOrder() {
		return 1;
	}
};


