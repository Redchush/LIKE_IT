package by.epam.like_it.model.criteria_to.facade;

import by.epam.like_it.model.bean.*;
import by.epam.like_it.model.criteria_to.core.Criteria;
import by.epam.like_it.model.criteria_to.core.Order;
import by.epam.like_it.model.criteria_to.core.constriction.Constriction;
import by.epam.like_it.model.criteria_to.core.constriction.ConstrictionType;
import by.epam.like_it.model.criteria_to.core.constriction.EqConstriction;

import java.util.*;

/**
 * InitialPostCriteria contains data for following search. It wides the Criteria options by allowing adding
 * constrictions bound on field values of related bean classes and define the default values of order and
 * constrictions.
 */
public class InitialPostCriteria extends Criteria<Post> {

    /**
     * Define defaultOrder as count of related ReadedPost and constriction that post must not be banned.
     * That values is saved in instance and can by restored by calling appropriate set methods.
     */
    public InitialPostCriteria(){
        setOrderAsDefaultOrder();
        setConstrictionsAsDefaultConstrictions();
    }

    /**
     * restore default order as count of related ReadedPost
     */
    public void setOrderAsDefaultOrder(){
        this.order = new Order<>(ReadedPost.class, "userId",Order.OrderBase.COUNT, Order.OrderType.DESC);
    }

    public void setConstrictionsAsDefaultConstrictions(){
        Map<ConstrictionType, List<Constriction>> defaultConstrictions = new HashMap<>();
        Set<Boolean> set = new HashSet<>();
        set.add(false);
        EqConstriction<Post, Boolean> postEqConstriction= new EqConstriction<>(Post.class, "banned", set);
        List<Constriction> constrictions = new ArrayList<>();
        constrictions.add(postEqConstriction);
        defaultConstrictions.put(ConstrictionType.EQ, constrictions);
        this.constrictions = defaultConstrictions;
    }

    @Override
    public boolean putConstriction(Constriction<Post> constriction) {
        return super.putOtherConstriction(constriction);
    }

    public boolean putCatgoryConstriction(Constriction<Category> constriction){
        return super.putOtherConstriction(constriction);
    }

    public boolean putTagConstriction(Constriction<Tag> constriction){
        return super.putOtherConstriction(constriction);
    }

    public void setCategoryOrder(Order<Category> categoryOrder){
        this.order = categoryOrder;
    }

    public void setPredifinedOrder(Order.OrderType type, PredefinedOrders order){
        this.order = order.getOrder();
        this.order.setOrderType(type);
    }

    /**
     * Predefined orders that can be applied criteria_to this criteria without manual configuration.
     * Contains orders based on related classes, such as Answer, FavoriteUserPost, ReadedPost (Order.OrderBase.COUNT)
     */
    public enum PredefinedOrders {
        FAV_USERS_POSTS_ORDER(new Order<>(FavoriteUserPost.class, "userId", Order.OrderBase.COUNT)),
        ANSWERS_ORDER(new Order<>(Answer.class, "id", Order.OrderBase.COUNT)),
        READED_POST_ORDER_ORDER(new Order<ReadedPost>(ReadedPost.class, "userId",Order.OrderBase.COUNT)),
        CREATED_ORDER(new Order<>(Post.class, "createdDate")),
        UPDATED_ORDER(new Order<>(Post.class, "createdDate"));

        private Order<?> order;

        PredefinedOrders(Order order){
            this.order = order;
        }

        public Order<?> getOrder() {
            return order;
        }
    }
}
