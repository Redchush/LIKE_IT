package by.epam.like_it.dao.mysql.util.criteriaChain;


import by.epam.like_it.dao.mysql.util.ResourceNavigator;
import by.epam.like_it.model.criteria_to.core.Criteria;
import by.epam.like_it.model.criteria_to.core.Order;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderChain extends AbstractCriteriaChain {

    /**
     * variables : table name, table field, order
     */
    private static final String PATTERN_SIMPLE = "\nORDER BY %s.%s %s";
    /**
     * need agreement between select part and this pattern on alias naming
     * current agreement - counter__tableName__tableField
     * @todo make this field configurable
     */
    private static final String PATTERN_COUNT = "\nORDER BY counter__%s__%s %s";
    private static final Pattern HAS_ORDER = Pattern.compile("[oO][dD][eE][rR][ ]{0,5}[bB][yY]");

    private static OrderChain instance;

    private OrderChain() {
        next = LimitChain.getInstance();
    }

    public static OrderChain getInstance(){

        if (instance == null)
            synchronized (OrderChain.class){
                if (instance == null)
                    instance = new OrderChain();
            }
        return instance;
    }


    @Override
    public StringBuilder doChain(StringBuilder mainPart, Criteria criteria) {
        Order order = criteria.getOrder();
        processSingleOnePart(mainPart, order);
        return next.doChain(mainPart, criteria);
    }

    public StringBuilder processSingleOnePart(StringBuilder mainPart, Order order){

        if (order != null) {
            Matcher m = HAS_ORDER.matcher(mainPart);
            boolean matches = m.matches();
            if (!matches) {
                String orderPart = getPattern(order);
                mainPart.append(orderPart);
            }
        }
        return mainPart;
    }

    private String getPattern(Order order){
        Order.OrderType orderType = order.getOrderType();
        Order.OrderBase base = order.getOrderBase();
        Class bean = order.getBeanToOrder();
        String fieldToOrder = order.getFieldToOrder();

        String refTable = ResourceNavigator.getRefTable(bean);
        String refField = ResourceNavigator.getRefTableField(bean.getSimpleName(), fieldToOrder);
        if (base == Order.OrderBase.COUNT){
            return String.format(PATTERN_COUNT, refTable, refField, orderType.toString());
        } else {
            return String.format(PATTERN_SIMPLE, refTable, refField, orderType.toString());
        }
    }
}
