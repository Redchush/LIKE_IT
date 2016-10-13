package by.epam.like_it.controller.command;

import by.epam.like_it.exception.command.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {

	String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
}
