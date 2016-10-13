package by.epam.like_it.controller.command.util;


public class CommandConstants {

    public static final String MSG_SERVICE = "msgService";

    public static final String PREV_QUERY_SESS_ATTR = "prevQuery";

    public static final String IS_LOADED = "isLoaded";

    public static final String COMMAND_ATTR = "command";
    public static final String COMMAND_OPT_DELIMITER = "-";


    public static final String ACTION_ATTRIBUTE = "action";
    public static final String SEND_REDIRECT_VALUE = "redirect";

    public static final String MAIN_POST_CRITERIA = "post_criteria";
    public static final String MAIN_TAG_CRITERIA = "tag_criteria";

//    public static final String SPARED_POSTS_LIST = "spared_posts" ;
    public static final String SPARED_TAGS_LIST = "spared_tags";

    public static final String CURRENT_POSTS_ATTR = "posts";
    public static final String CURRENT_CATEGORIES_ATTR = "categories";

    public static final String POSSIBLE_TAGS_MAP = "tags";
    public static final String CHOSEN_TAGS_MAP = "chosen_tags";
    public static final String ALL_TAGS_LIST = "all_tags";

    public static final String TOTAL_TAGS_COUNT = "total_tags";
    public static final String TOTAL_POSTS_COUNT = "total_posts";

    public static final String CURRENT_PAGE = "current_page";
    public static final String ACCESSIBLE_PAGES_ARRAY = "accessible_pages";
    public static final String LAST_PAGE = "last_page";
    public static final String FIRST_PAGE = "first_page";

    public static final String FIRST_DOT = "first_dot";
    public static final String LAST_DOT = "last_dot";

    public static final String IS_SORT_TYPE_ASC = "isSortOrderTypeChanged";
    public static final String CURRENT_ORDER_CLASS = "currentOrderClass";

    public static final String SORT_TAG_PARAM = "sort_tag";

    public static final String SORT_BY_CREATION_VALUE = "create";
    public static final String SORT_BY_UPDATE_VALUE = "update";
    public static final String SORT_BY_ANSWERS_VALUE = "answers";
    public static final String SORT_BY_VIEWS_VALUE = "views";
    public static final String SORT_BY_FAVORITE_VALUE = "favorite";

    public static final String SORT_DIRECTION = "direction";
    public static final String SORT_DIRECTION_UP = "up";
    public static final String SORT_DIRECTION_DOWN = "down";

    public static final String SORT_BY_NAME = "name";
    public static final String SORT_BY_POSTS_COUNT = "popularity";
    public static final String SORT_BY_LETTER= "letter";

    public static final String DEFAULT_ORDER_CLASS = SORT_BY_VIEWS_VALUE;
    public static final boolean DEFAULT_IS_SORT_TYPE_ASC = false;

    public static final String ID_FROM_GET_PARAM = "id";

    public static final String ID_FROM_POST_PARAM = "entity_id";
    public static final String POST_PARENT_ID_PARAM = "parent_id";

    public static final String ID_TAG = "tag_id";
    public static final String NEW_TAG_ID_PARAM = "tag_new";
    public static final String OLD_TAG_ID_PARAM = "tag_old";

    public static final String TAG_LIMIT = "tag_lim";

    public static final String ENTITY_INDEX_PARAM = "index";

    public static final String CURRENT_POST_VO = "postVO";
    public static final String CURRENT_POST_VO_RESPONSIBLE = "postVoResponsible";
    public static final String CURRENT_ANSWERS_LIST_ATTR = "current_answers";


    public static final String RELATED_POST = "related";


    public static final String BANNED_ENTITY_ATTR = "bannedResponsible";

    /*for error messages */

    public static final String ERROR_FLAG_ATTR = "error";

//    use locale.invalid.entity.${failed_fields}. Define all failed_field
    public static final int VALIDATION_FAIL_VAL = 1;

//    use locale.error.${action}.${failed_field}. Define only one failed_field
    public static final int ACTION_FAIL_VAL = 2;


    public static final String MSG_BEAN = "msg_bean";
    public static final String ERROR_BEAN = "error_bean";

    public static final String FAILED_FIELD = "failed_field";
    /*for profile*/
    public static final String USER_VO = "userVO";

    public static final String POPULAR_TAG_RESPONSIBLE = "popular_tag";

    public static final String ENTITY_BEAN_LOADED = "entity_bean_loaded";
    public static final String ENTITY_BEAN_TO_LOAD = "entity_bean";
    public static final String PREV_ENTITY_BEAN = "prev_bean";
    public static final String PREV_IDS = "prev_ids";


    public static final String TOTAL_CHOSEN = "chosen_tag";
}
