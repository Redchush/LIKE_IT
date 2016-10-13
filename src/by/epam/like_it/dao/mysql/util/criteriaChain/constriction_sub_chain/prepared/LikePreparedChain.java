package by.epam.like_it.dao.mysql.util.criteriaChain.constriction_sub_chain.prepared;


import by.epam.like_it.dao.mysql.util.criteriaChain.constriction_sub_chain.simple.LikeChain;

public class LikePreparedChain extends LikeChain{

    private static final String PATTERN = "%s.%s LIKE %s%s%s";

    protected LikePreparedChain() {}

    private static LikePreparedChain instance;


    public static LikePreparedChain getInstance(){

        if (instance == null)
            synchronized (LikePreparedChain.class){
                if (instance == null)
                    instance = new LikePreparedChain();
            }
        return instance;
    }

    @Override
    protected String getPattern() {
        return PATTERN;
    }
}
