package by.epam.like_it.filter;


import by.epam.like_it.common_util.ClassName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DebugFilter implements Filter{
    private static final Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    private static final String COMMAND_ATTRIBUTE = "command";
    private static final String FRONTCONTROLLER_NAME = "FrontController";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession(true);
        if (!request.getRequestURL().toString().contains("resources")) {

            String url = request.getHeader("referer");
            String queryString = request.getQueryString();
            System.out.println("_______________");
            LOGGER.debug("doing " + request.getMethod() + ", referrer " + url +
                    ", requestedURL " + request.getRequestURL() + ", requestedURI "
                    + request.getRequestURI() + " pathinfo " + request.getPathInfo() + "\n Context path: "  + request
                    .getContextPath() + " local adress: " + request.getLocalAddr() + " svlt context" + request
                    .getServletContext() + " remoteHost: " + request.getRemoteHost());

            String params = request.getParameterMap().entrySet().stream()
                                   .map(s -> s.getKey() + " " + Arrays.toString(s.getValue()))
                                   .collect(Collectors.joining(", "));

            LOGGER.debug("queryString " + queryString + ", parameters: " + params);
            LOGGER.debug("attributes: " + Collections.list(request.getAttributeNames()));

            ArrayList<String> list1 = Collections.list(request.getHeaderNames());
            List<String> collect1 =
                    list1.stream().map(s -> s + " " + request.getHeader(s)).collect(Collectors.toList());
            LOGGER.debug("HEADER NAMES: " + collect1);
            ArrayList<String> list = Collections.list(session.getAttributeNames());

            Map<String, String> collect = list.stream().sorted().collect(Collectors.toMap(Function.identity(), s ->
                    session.getAttribute(s).toString()));


            String finalAttrs = collect.entrySet().stream().map(s -> s.getKey() + "=" + s.getValue()).collect(Collectors
                    .joining(" \n "));

            LOGGER.debug("IN SESSION names : " + list);
            LOGGER.debug("IN SESSION attr: " + finalAttrs);
            String parameter = request.getParameter(COMMAND_ATTRIBUTE);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void processForward(HttpServletRequest request, HttpServletResponse response, String page)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        if (dispatcher != null) {
            LOGGER.trace("in forward  " + page);
            dispatcher.forward(request, response);                //inner redirect
        }
    }
    @Override
    public void destroy() {

    }
}
