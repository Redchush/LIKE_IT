package by.epam.like_it.model.criteria_to.core;

import java.io.Serializable;

/**
 * Class that represent wanted order of entities in result
 */
public class Order<T> implements Serializable {

    public enum OrderType {
        ASC, DESC
    }

    public OrderType getASC(){
        return OrderType.ASC;
    }

    public OrderType getDESC(){
        return OrderType.DESC;
    }


    public enum OrderBase{
        FIELD, COUNT
    }

    private Class<T> beanToOrder;
    private String fieldToOrder;

    private OrderBase orderBase;
    private OrderType orderType;

    public Order() {
        orderType = OrderType.ASC;
    }

    public Order(Class<T> beanToOrder, String fieldToOrder, OrderBase orderBase) {
        this.beanToOrder = beanToOrder;
        this.fieldToOrder = fieldToOrder;
        this.orderBase = orderBase;
    }

    public Order(Class<T> beanToOrder, String fieldToOrder) {
        this();
        this.beanToOrder = beanToOrder;
        this.fieldToOrder = fieldToOrder;
        orderBase = OrderBase.FIELD;
    }

    public Order(Class<T> beanToOrder, String fieldToOrder, OrderBase orderBase,
                 OrderType orderType) {
        this.beanToOrder = beanToOrder;
        this.fieldToOrder = fieldToOrder;
        this.orderBase = orderBase;
        this.orderType = orderType;
    }

    public Class<T> getBeanToOrder() {
        return beanToOrder;
    }

    public void setBeanToOrder(Class<T> beanToOrder) {
        this.beanToOrder = beanToOrder;
    }

    public String getFieldToOrder() {
        return fieldToOrder;
    }

    public void setFieldToOrder(String fieldToOrder) {
        this.fieldToOrder = fieldToOrder;
    }

    public OrderBase getOrderBase() {
        return orderBase;
    }

    public void setOrderBase(OrderBase orderBase) {
        this.orderBase = orderBase;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Order<?> order = (Order<?>) o;

        if (beanToOrder != null ? !beanToOrder.equals(order.beanToOrder) : order.beanToOrder != null) {
            return false;
        }
        if (fieldToOrder != null ? !fieldToOrder.equals(order.fieldToOrder) : order.fieldToOrder != null) {
            return false;
        }
        //noinspection SimplifiableIfStatement
        if (orderBase != order.orderBase) {
            return false;
        }
        return orderType == order.orderType;

    }

    @Override
    public int hashCode() {
        int result = beanToOrder != null ? beanToOrder.hashCode() : 0;
        result = 31 * result + (fieldToOrder != null ? fieldToOrder.hashCode() : 0);
        result = 31 * result + (orderBase != null ? orderBase.hashCode() : 0);
        result = 31 * result + (orderType != null ? orderType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");

        sb.append("beanToOrder=").append(beanToOrder);
        sb.append(", fieldToOrder='").append(fieldToOrder).append('\'');
        sb.append(", orderBase=").append(orderBase);
        sb.append(", orderType=").append(orderType);
        sb.append('}');
        return sb.toString();
    }
}
