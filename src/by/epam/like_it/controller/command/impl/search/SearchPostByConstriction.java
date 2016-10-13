package by.epam.like_it.controller.command.impl.search;


import by.epam.like_it.controller.command.Command;
import by.epam.like_it.exception.command.CommandException;
import by.epam.like_it.model.bean.Post;
import by.epam.like_it.model.bean.Tag;
import by.epam.like_it.model.criteria_to.core.constriction.LikeConstriction;
import by.epam.like_it.model.criteria_to.facade.InitialPostCriteria;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Set;

public class SearchPostByConstriction extends SearchPost implements Command {

    private static final String TEXT_ATTR = "text";
    private static final String CATEGORY_ATTR = "cat";
    private static final String CATEGORY_ALL_VALUE = "all";

    private static final String SEACH_BY_ATTR = "by";

    private static final String BY_TAG_VALUE = "tag";
    private static final String BY_TITLE_VALUE = "title";
    private static final String BY_CONTENT_ATTR = "content";


    private static SearchPostByConstriction instance;

    private SearchPostByConstriction(){}

    public static SearchPostByConstriction getInstance(){

        if (instance == null)
            synchronized (SearchPostByConstriction.class){
                if (instance == null)
                    instance = new SearchPostByConstriction();
            }
        return instance;
    }

    @Override
    protected boolean refactorCriteria(HttpServletRequest request, InitialPostCriteria criteria)
            throws CommandException {
        HttpSession session = request.getSession();
        super.clearSessionAndCriteria(session, criteria);

        String text = request.getParameter(TEXT_ATTR);
//        String cat = request.getParameter(CATEGORY_ATTR);
        String by = request.getParameter(SEACH_BY_ATTR);
//
//        if (!cat.equals(CATEGORY_ALL_VALUE)){
//            Set<String> orVals = Collections.singleton(cat);
//            EqConstriction<Category, String> catEq = new EqConstriction<Category, String>(Category.class, "id", orVals);
//            criteria.putCatgoryConstriction(catEq);
//        }

        Set<String> singleton = Collections.singleton(text);
        switch (by){
            case BY_TAG_VALUE :
                LikeConstriction<Tag> tagLike = new LikeConstriction<Tag>(Tag.class, "name", singleton);
                criteria.putTagConstriction(tagLike);
                break;
            case BY_TITLE_VALUE:
                LikeConstriction<Post> titleLike = new LikeConstriction<Post>(Post.class, "title", singleton);
                criteria.putConstriction(titleLike);
                break;
            case BY_CONTENT_ATTR:
                LikeConstriction<Post> contentLike = new LikeConstriction<Post>(Post.class, "title", singleton);
                criteria.putConstriction(contentLike);
                break;
        }
        return true;
    }
}
