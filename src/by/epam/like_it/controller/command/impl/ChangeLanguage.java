package by.epam.like_it.controller.command.impl;


import by.epam.like_it.controller.command.Command;
import by.epam.like_it.exception.command.CommandException;
import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.controller.command.util.CommandConstants;
import by.epam.like_it.model.vo.system_vo.prev_query.PrevQuery;
import by.epam.like_it.service.PrevQueryService;
import by.epam.like_it.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangeLanguage implements Command {

	private static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
     private static final String LANG_PARAM = "language";
	private static ChangeLanguage instance;

	private ChangeLanguage(){}

	public static ChangeLanguage getInstance(){

		if (instance == null)
			synchronized (ChangeLanguage.class){
				if (instance == null)
					instance = new ChangeLanguage();
			}
		return instance;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException{

		LOGGER.trace("Proceed vo execute ChangeLanguage command");
		HttpSession session = request.getSession(true);

		String language = request.getParameter(LANG_PARAM);
		session.setAttribute("language", language);

		PrevQuery prev_query = (PrevQuery) session.getAttribute(CommandConstants.PREV_QUERY_SESS_ATTR);

		LOGGER.debug(prev_query);
		PrevQueryService prevQueryService = ServiceFactory.getInstance().getPrevQueryService();
		String newURL = prevQueryService.makeQueryByAddingParams(prev_query, LANG_PARAM, language);
		request.setAttribute(CommandConstants.ACTION_ATTRIBUTE, CommandConstants.SEND_REDIRECT_VALUE);
		LOGGER.trace("Change language " + language +" PrevQuery: " + prev_query + " .Redirect back to page " +
				newURL);
		return newURL;
	}
}
