package by.epam.like_it.controller.command.impl.search;


import by.epam.like_it.controller.command.Command;
import by.epam.like_it.exception.command.CommandException;
import by.epam.like_it.model.criteria_to.core.Order;
import by.epam.like_it.model.criteria_to.facade.InitialPostCriteria;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.epam.like_it.controller.command.util.CommandConstants.*;


public class SearchPostByOrder extends SearchPost implements Command {

    protected static final String SEARCH_BY_ORDER_VALUE = "sort_value";

    private static SearchPostByOrder instance;

    private SearchPostByOrder(){}

    public static SearchPostByOrder getInstance(){

        if (instance == null)
            synchronized (SearchPostByOrder.class){
                if (instance == null)
                    instance = new SearchPostByOrder();
            }
        return instance;
    }

    /**
     * set criteria_to InitialPostCriteria the requested order. If order is't defined -> set default order.
     * @param request
     * @param criteria
     * @throws CommandException
     */

    @Override
    protected boolean refactorCriteria(HttpServletRequest request, InitialPostCriteria criteria) throws CommandException {
        String parameter = request.getParameter(SEARCH_BY_ORDER_VALUE);
        String orderClass = "";
        InitialPostCriteria.PredefinedOrders currentOrder = null;
        switch (parameter){
            case SORT_BY_CREATION_VALUE:
                currentOrder = InitialPostCriteria.PredefinedOrders.CREATED_ORDER;
                break;
            case SORT_BY_UPDATE_VALUE:
                currentOrder = InitialPostCriteria.PredefinedOrders.UPDATED_ORDER;
                break;
            case SORT_BY_ANSWERS_VALUE:
                currentOrder = InitialPostCriteria.PredefinedOrders.ANSWERS_ORDER;
                break;
            case SORT_BY_VIEWS_VALUE:
               currentOrder = InitialPostCriteria.PredefinedOrders.READED_POST_ORDER_ORDER;
               break;
            case SORT_BY_FAVORITE_VALUE:
               currentOrder = InitialPostCriteria.PredefinedOrders.FAV_USERS_POSTS_ORDER;
               break;
            default:
               criteria.setOrderAsDefaultOrder();
        }
        HttpSession session = request.getSession();

        Class prevOrderBase = criteria.getOrder().getBeanToOrder();
        Class currentOrderBase = currentOrder.getOrder().getBeanToOrder();
        if (prevOrderBase.equals(currentOrderBase)){
            boolean isOrderTypeChanged = toggleOrder(criteria.getOrder());
            session.setAttribute(IS_SORT_TYPE_ASC, isOrderTypeChanged);
        } else {
            criteria.setPredifinedOrder(Order.OrderType.DESC, currentOrder);
        }
        session.setAttribute(CURRENT_ORDER_CLASS, orderClass);
        return true;
    }

    private boolean toggleOrder(Order order){
        Order.OrderType orderType = order.getOrderType();
        if (orderType == Order.OrderType.DESC){
            order.setOrderType(Order.OrderType.ASC);
            return true;
        } else {
            order.setOrderType(Order.OrderType.DESC);
            return false;
        }
    }
}
