package by.epam.like_it.filter;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.common_util.ResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.*;
import java.io.IOException;

public class CharacterEncodingFilter implements Filter {

    private static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    private static final String ENCODING_KEY = "encoding";
    private static final String DEFAULT_CHARSET = "UTF-8";
    private String encoding;

    /**
     * Set default character encoding
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            this.encoding = ResourceManager.VIEW_CONFIG.getString(ENCODING_KEY);
        } catch (Exception e){
            LOGGER.info("Configurable charset is unavailable. Default charset is used.", e);
            this.encoding = DEFAULT_CHARSET;
        }
    }

    /**
     * Change the character encoding of request and response and send next the method parameters on the chain
     * @param servletRequest ServletRequest
     * @param servletResponse ServletResponse
     * @param filterChain FilterChain
     */

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        servletRequest.setCharacterEncoding(encoding);
        servletResponse.setCharacterEncoding(encoding);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * NOP
     */
    @Override
    public void destroy() {
         /*NOP*/
    }
}
