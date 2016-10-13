package by.epam.like_it.dao.mysql.util.criteriaChain;

import by.epam.like_it.model.bean.Post;
import by.epam.like_it.model.criteria_to.facade.InitialPostCriteria;
import by.epam.like_it.model.criteria_to.core.Order;
import org.junit.BeforeClass;
import org.junit.Test;


public class OrderChainTest {
    private static InitialPostCriteria criteria;
    private static OrderChain instance;
    private static Order<Post> postOrders;

    @BeforeClass
    public static void logIn(){
        instance = OrderChain.getInstance();
        criteria = new InitialPostCriteria();
        criteria.setPredifinedOrder(Order.OrderType.DESC, InitialPostCriteria.PredefinedOrders.ANSWERS_ORDER);
    }

    @Test
    public void name() throws Exception {

    }
}