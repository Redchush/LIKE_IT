package by.epam.like_it.dao.mysql.util.criteriaChain.constriction_sub_chain.prepared;


import by.epam.like_it.dao.mysql.util.criteriaChain.constriction_sub_chain.simple.EqChain;

public class EqPreparedChain extends EqChain{

    private static final String PATTERN = "%s.%s = %s";

    protected EqPreparedChain() {
        next = LikePreparedChain.getInstance();
    }

    private static EqPreparedChain instance;


    public static EqPreparedChain getInstance(){

        if (instance == null)
            synchronized (EqPreparedChain.class){
                if (instance == null)
                    instance = new EqPreparedChain();
            }
        return instance;
    }

    @Override
    protected String getPattern() {
        return PATTERN;
    }
}
