package com.innoq.sample;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


public class IDFilter implements Filter {

	private FilterConfig filterConfig = null;
	private static final Pattern REWRITE_PATTERN = Pattern.compile("(^[1-9]\\d*)$");
	
    /**
     * Default constructor. 
     */
    public IDFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		this.filterConfig = null;
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String url = null;
	      if (request instanceof HttpServletRequest) {
	         url = ((HttpServletRequest)request).getRequestURL().toString();
	      }
	      String number = url.substring(url.lastIndexOf("/")).replace("/", "");
	      Matcher m = REWRITE_PATTERN.matcher(number);
	      if(m.find()) {
	          RequestDispatcher dispatcher = request.getRequestDispatcher("event?id=" + m.group(1));
	          dispatcher.forward(request, response);
	      } else {
	  		chain.doFilter(request, response);
	      }
	      
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.filterConfig = fConfig;
	}

}
