package by.epam.like_it.service.impl.msg;

import by.epam.like_it.model.vo.page_vo.MsgBean;
import by.epam.like_it.model.vo.system_vo.error_bean.InvalidInfo;
import by.epam.like_it.service.MessageService;
import by.epam.like_it.service.impl.system.MessageServiceImpl;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;


public class MessageServiceImplTest {

    public static MessageService service =   MessageServiceImpl.getInstance();

    @Test
    public void getBeanForInvalid() throws Exception {
        InvalidInfo info = new InvalidInfo();
        info.setFailedBean("Comment");
        info.setFailedFields(Collections.singletonList("unnamed"));
        MsgBean beanForInvalid = service.getBeanForInvalid(info);
        System.out.println(beanForInvalid);
        assertEquals(beanForInvalid.getMsgKeys(), Collections.singletonList("locale.invalid.Comment"));

    }

    @Test
    public void getBeanForInvalid1() throws Exception {

    }

}