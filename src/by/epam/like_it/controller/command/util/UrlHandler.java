package by.epam.like_it.controller.command.util;

import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.model.vo.page_vo.BeanPair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

public final class UrlHandler {

	private static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
	private static UrlHandler instance;

	private UrlHandler(){}

	public static UrlHandler getInstance(){

		if (instance == null)
			synchronized (UrlHandler.class){
				if (instance == null)
					instance = new UrlHandler();
			}
		return instance;
	}

	public String getUrl(HttpServletRequest req) {
		String reqUrl = req.getRequestURL().toString();
		String queryString = req.getQueryString();   // d=789
		if (queryString != null) {
			reqUrl += "?"+queryString;
		}
		return reqUrl;
	}

	public String createHttpQueryStringFromPost(HttpServletRequest request){
		Enumeration<String> params = request.getParameterNames();
		StringBuilder query = new StringBuilder(request.getRequestURI());
		query.append("?");
		String key;
		String value;
		int counter = 1;
		while(params.hasMoreElements()){
			key = params.nextElement();
			value  = request.getParameter(key);
			if (counter != 1){
				query.append("&");
			}
			query.append(key).append("=").append(value);
			counter++;
			LOGGER.debug("in query process " + query);
		}
		return query.toString();
	}

	public void changeRequestURL(StringBuffer requestedURL, String replacement, String target){
		int i = requestedURL.indexOf(replacement);
		int last = i + replacement.length();
		requestedURL.replace(i, last, target);
	}

	public String copyParameterFromGet(HttpServletRequest request, String target, String[] key, String[] value){
		StringBuilder builder = new StringBuilder(target);
		String queryString = request.getQueryString();
		if (queryString != null){
			builder.append("?").append(queryString).append("&");
		} else {
			builder.append("?");
		}
		appendHttpParameterToNewUrl(builder, key, value);
		return builder.toString();
	}
	public String appendHttpParameterToNewUrl(String ulr, Map<String, String> paramMap){
		if (paramMap == null || paramMap.isEmpty()){
			return ulr;
		}
		StringBuilder result = new StringBuilder(ulr);
		if (ulr.contains("?")){
			result.append("&");
		} else {
			result.append("?");
		}
		String paramCollected =
				paramMap.entrySet().stream().map(s -> s.getKey() + "=" + s.getValue()).collect(Collectors.joining("="));

		return result.append(paramCollected).toString();
	}

	public String appendHttpParameterToNewUrl(String ulr, BeanPair[] pairs){
		if (pairs == null || pairs.length == 0){
			return ulr;
		}

		StringBuilder result = new StringBuilder(ulr);
		if (ulr.contains("?")){
			result.append("&");
		} else {
			result.append("?");
		}
		String paramCollected =
				Arrays.stream(pairs).map(s -> s.getKey() + "=" + s.getValue())
					  .collect(Collectors.joining("="));
		return result.append(paramCollected).toString();
	}

	private String appendHttpParameterToNewUrl(StringBuilder ulr, String[] key, String[] value){
		if (key == null || value == null || key.length < 1 || value.length < 1){
			return ulr.toString();
		}
		for (int i = 0; i < key.length; i++) {
			ulr.append(key[i]).append("=").append(value[i]);
			if (i != key.length - 1){
				ulr.append("&");
			}
		}
		return ulr.toString();
	}

}
