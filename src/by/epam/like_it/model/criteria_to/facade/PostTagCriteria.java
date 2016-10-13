package by.epam.like_it.model.criteria_to.facade;


import by.epam.like_it.model.bean.PostTag;
import by.epam.like_it.model.bean.Tag;
import by.epam.like_it.model.criteria_to.core.Criteria;
import by.epam.like_it.model.criteria_to.core.Order;
import by.epam.like_it.model.criteria_to.core.constriction.LikeConstriction;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 * initially has predefined order as count of tag usage in post
 */
public class PostTagCriteria extends Criteria<Tag> {
    /**
     * initially has predefined order as count of tag usage in post. DESC
     */
    public PostTagCriteria() {
        setOrderAsDefaultOrder();
    }

    public void setOrderAsDefaultOrder(){
        this.order = new Order<PostTag>(PostTag.class, "postId", Order.OrderBase.COUNT, Order.OrderType.DESC);
    }

    public void setNameStartWith(String letter){
        HashSet<String> set = new HashSet<>();
        set.add(letter);
        set.add(letter.toUpperCase());

        LikeConstriction<Tag> tagLikeConstriction = new LikeConstriction<>
                (Tag.class, "name", set);
        tagLikeConstriction.setLikeType(LikeConstriction.LikeType.STARTS_WITH);
        this.putConstriction(tagLikeConstriction);
    }

    public void toggleOrder(){
        Order.OrderType orderType = this.order.getOrderType();
        switch (orderType){
            case ASC:
                this.order.setOrderType(Order.OrderType.DESC);
                break;
            case DESC:
                this.order.setOrderType(Order.OrderType.ASC);
                break;
        }
    }

}
