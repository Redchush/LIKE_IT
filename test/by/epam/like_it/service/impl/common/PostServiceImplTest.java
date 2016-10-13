package by.epam.like_it.service.impl.common;

import by.epam.like_it.model.bean.Answer;
import by.epam.like_it.model.bean.Post;
import by.epam.like_it.model.criteria_to.core.Limit;
import by.epam.like_it.model.vo.db_vo.PostVO;
import by.epam.like_it.service.PostService;
import by.epam.like_it.service.ServiceFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class PostServiceImplTest {

    private static PostService postService;


    @BeforeClass
    public static void login(){
        postService = ServiceFactory.getInstance().getPostService();
    }

    @Test
    public void getSimilarPost() throws Exception {
        String title = "блиндировать кристаллография подщепать футлярщик ретикулиновый дисквалификация хвостище птичка вероисповедание разномастный командировочные переудить подрядничать насадной перескакать нагнёт виктория испохабиться послезавтра демографический арнаут древнеп";
        List<String> expected = Arrays.asList(title.split(" "));
        PostVO vo = new PostVO();
        Post post = new Post();
        post.setTitle(title);
        vo.setPost(post);
        PostService postService = ServiceFactory.getInstance().getPostService();
        List<Post> similarPostsList = postService.findSimilarPostsList(vo, new Limit(0, 20));

        similarPostsList.forEach(post1 -> {
            String wordInActual = null;
            for (String s : expected){
                boolean contains = post.getTitle().contains(s);
                if (contains){
                    wordInActual = s;
                    break;
                }
            }
            assertNotNull(wordInActual);

        });

        System.out.println(similarPostsList);
    }

    @Test
    public void getSinglePost() throws Exception {
        Long postTested = 3L;
        PostVO actual = postService.findSinglePostVOWithInfo(postTested);
        System.out.println(actual);
        List<Answer> answers = actual.getAnswers();
        for (int i = 0; i < answers.size(); i++) {
            Answer answer = answers.get(i);
            System.out.println(answer);

        }
    }

    @Test
    public void createPostTags() throws Exception {
        List<Long> postTags = postService.createPostTags(5L, new String[]{"1", "2", "3"});

    }

}