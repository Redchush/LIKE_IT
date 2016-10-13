package by.epam.like_it.controller;


import by.epam.like_it.common_util.CommandName;
import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.command.impl.ChangeLanguage;
import by.epam.like_it.controller.command.impl.command_with_restriction.auth.Logination;
import by.epam.like_it.controller.command.impl.command_with_restriction.auth.Logout;
import by.epam.like_it.controller.command.impl.command_with_restriction.auth.Registration;
import by.epam.like_it.controller.command.impl.command_with_restriction.delete.*;
import by.epam.like_it.controller.command.impl.command_with_restriction.create.auth_opt.CreateAnswer;
import by.epam.like_it.controller.command.impl.command_with_restriction.create.auth_opt.CreateComment;
import by.epam.like_it.controller.command.impl.command_with_restriction.create.auth_opt.CreatePost;
import by.epam.like_it.controller.command.impl.command_with_restriction.create.client_opt.CreateFavorite;
import by.epam.like_it.controller.command.impl.command_with_restriction.create.client_opt.DeleteFavorite;
import by.epam.like_it.controller.command.impl.command_with_restriction.edit.*;
import by.epam.like_it.controller.command.impl.command_with_restriction.edit.edit_content.EditAnswer;
import by.epam.like_it.controller.command.impl.command_with_restriction.edit.edit_content.EditComment;
import by.epam.like_it.controller.command.impl.command_with_restriction.edit.edit_content.EditPost;
import by.epam.like_it.controller.command.impl.command_with_restriction.load.LoadEntity;
import by.epam.like_it.controller.command.impl.command_with_restriction.load.LoadFavoritePost;
import by.epam.like_it.controller.command.impl.command_with_restriction.load.LoadSubscribing;
import by.epam.like_it.controller.command.impl.search.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static by.epam.like_it.common_util.CommandName.*;


public final class CommandHelper {

	private static CommandHelper instance;
	private Map<CommandName, Supplier<Command>> commandsFactions = new HashMap<>();

	public static CommandHelper getInstance(){

		if (instance == null)
			synchronized (CommandHelper.class){
				if (instance == null)
					instance = new CommandHelper();
			}
		return instance;
	}

	private CommandHelper() {
		commandsFactions.put(CHANGE_LANGUAGE, ChangeLanguage::getInstance);
		commandsFactions.put(LOGINATION, Logination::getInstance);
		commandsFactions.put(REGISTRATION, Registration::getInstance);

		commandsFactions.put(LOAD_MAIN, LoadMain::getInstance);
		commandsFactions.put(VIEW_POSTS_LIST, ViewPostsList::getInstance);
		commandsFactions.put(VIEW_SINGLE_POST, ViewSinglePost::getInstance);

		commandsFactions.put(SEARCH_POSTS_BY_ORDER, SearchPostByOrder::getInstance);
		commandsFactions.put(SEARCH_POSTS_BY_ADD_CONSTRICTION, SearchPostByAddConstriction::getInstance);
		commandsFactions.put(SEARCH_POSTS_BY_CONSTRICTION, SearchPostByConstriction::getInstance);

		commandsFactions.put(VIEW_SINGLE_USER, ViewSingleUser::getInstance);
		commandsFactions.put(LOG_OUT, Logout::getInstance);

		commandsFactions.put(EDIT_COMMENT_OWNER, EditComment::getInstance);
		commandsFactions.put(EDIT_POST_OWNER, EditPost::getInstance);
		commandsFactions.put(EDIT_ANSWER_OWNER, EditAnswer::getInstance);

		commandsFactions.put(EDIT_PROFILE_AUTH, EditProfile::getInstance);
		commandsFactions.put(EDIT_SETTINGS_AUTH, EditSettings::getInstance);
		commandsFactions.put(EDIT_PHOTO_AUTH, SaveNewPhoto::getInstance);
		commandsFactions.put(EDIT_SUBSCRIBING_CLIENT, EditSubscribing::getInstance);

		commandsFactions.put(DELETE_PHOTO_AUTH, DeletePhoto::getInstance);

		commandsFactions.put(TOGGLE_RATING_CLIENT, ToggleRating::getInstance);

		commandsFactions.put(BAN_COMMENT_RESPONSIBLE, BanComment::getInstance);
		commandsFactions.put(BAN_ANSWER_RESPONSIBLE, BanAnswer::getInstance);
		commandsFactions.put(BAN_POST_RESPONSIBLE, BanPost::getInstance);
		commandsFactions.put(BAN_USER_RESPONSIBLE, BanUser::getInstance);
		commandsFactions.put(BAN_RATING_RESPONSIBLE, BanRating::getInstance);

		commandsFactions.put(CREATE_COMMENT_AUTH, CreateComment::getInstance);
		commandsFactions.put(CREATE_POST_AUTH, CreatePost::getInstance);
		commandsFactions.put(CREATE_ANSWER_AUTH, CreateAnswer::getInstance);

		commandsFactions.put(CREATE_FAVORITE_CLIENT, CreateFavorite::getInstance);
		commandsFactions.put(DELETE_FAVORITE_CLIENT, DeleteFavorite::getInstance);

		commandsFactions.put(LOAD_ENTITY_AUTH, LoadEntity::getInstance);
		commandsFactions.put(LOAD_SUBSCRIBING_CLIENT, LoadSubscribing::getInstance);
		commandsFactions.put(LOAD_FAVORITE_POSTS_CLIENT, LoadFavoritePost::getInstance);

	}

	public Command getCommand(String name) {
		name = normalizeCommand(name).toUpperCase();
		CommandName commandName = valueOf(name.toUpperCase());
		return commandsFactions.get(commandName).get();
	}

	private String normalizeCommand(String name){
		return name.replace('-', '_');
	}
}
