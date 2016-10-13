package by.epam.like_it.controller;


import by.epam.like_it.common_util.PageNavigator;
import by.epam.like_it.controller.command.Command;
import by.epam.like_it.exception.command.CommandException;
import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.model.vo.system_vo.prev_query.PrevQuery;
import by.epam.like_it.service.PrevQueryService;
import by.epam.like_it.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.epam.like_it.controller.command.util.CommandConstants.*;


public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
	private static final String COMMAND_ATTRIBUTE = "command";

	private static final String RETURN_PAGE_ATTRIBUTE = "return_page";
	private static final String ERROR_PAGE = "/error";

	public FrontController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        if(     ( (PrevQuery) request.getSession().getAttribute(PREV_QUERY_SESS_ATTR)).getUri().contains("personal/")) {
//            LOGGER.debug("personal from GET ");
//            response.sendRedirect(request.getContextPath() + "/log_in");
//            return;
//        } else {
//
//        }
        doRequest(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doRequest(request, response);

	}

    private void doRequest(HttpServletRequest request,
						   HttpServletResponse response) throws ServletException, IOException {

		String page = PageNavigator.getInstance().getPage(PageNavigator.Pages.INDEX);

        String commandName = request.getParameter(COMMAND_ATTRIBUTE);
        if (commandName == null){
            commandName = (String) request.getAttribute(COMMAND_ATTRIBUTE);
            if (commandName == null) {
                processEmptyCommand(request, response);
                return;
            }
        }
        LOGGER.trace("Doing request by command " + commandName);
        Command command  = CommandHelper.getInstance().getCommand(commandName);
	    if(command != null) {
            try {
				page = command.execute(request, response);
                LOGGER.trace("Trying to do something " + page);
				Object action = request.getAttribute(ACTION_ATTRIBUTE);
				if(action != null && action.equals(SEND_REDIRECT_VALUE)){
                    processRedirect(request, response, page);
				} else {
					processForward(request, response, page);
				}
			} catch (CommandException e) {
				LOGGER.error("Unexpected commandException", e);
				processRedirect(request, response, ERROR_PAGE);
			}
		}
	}

    private void processForward(HttpServletRequest request, HttpServletResponse response, String page)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        if (dispatcher != null) {
            LOGGER.trace("in forward  " + page);
            dispatcher.forward(request, response);                //inner redirect
        }
    }

    private void processRedirect(HttpServletRequest request, HttpServletResponse response, String page) throws
                                                                                                       IOException {

        String prefix = getServletContext().getContextPath();
        LOGGER.debug("SendRedirect new page " + prefix + " " + page);
        String fullPage = page.contains(prefix) ? page : prefix + page;
        response.sendRedirect(fullPage);                              //outer redirect
        LOGGER.debug("total : " + fullPage);

//        response.reset();
//        String fullPage = request.getContextPath() + page;
////        response.setStatus(303);
//        response.sendRedirect(fullPage);                              //outer redirect
//        LOGGER.debug("SendRedirect page " + fullPage);
    }


    private void processEmptyCommand(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        LOGGER.debug("Process empty command");
        HttpSession session = request.getSession();

        PrevQuery attribute = (PrevQuery) session.getAttribute(PREV_QUERY_SESS_ATTR);
        PrevQueryService prevQueryService = ServiceFactory.getInstance().getPrevQueryService();
        String prevQueryURI = prevQueryService.getFullPrevQuery(attribute);

        if (!prevQueryURI.contains(COMMAND_ATTRIBUTE)){
            processRedirect(request, response, PageNavigator.getInstance().getPage(PageNavigator.Pages.INDEX));
        } else {
            processForward(request, response, prevQueryURI);
        }
    }

}
