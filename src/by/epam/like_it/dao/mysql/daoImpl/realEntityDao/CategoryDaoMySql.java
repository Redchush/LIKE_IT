package by.epam.like_it.dao.mysql.daoImpl.realEntityDao;

import by.epam.like_it.dao.CategoryDao;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.exception.persistence.system.PersistenceCollectorException;
import by.epam.like_it.dao.mysql.collector.FullStackCollector;
import by.epam.like_it.model.bean.Category;
import by.epam.like_it.model.vo.db_vo.SimpleCategoryVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Deprecated
public class CategoryDaoMySql extends CategoryDao {

    private static CategoryDaoMySql instance;

    private CategoryDaoMySql(){}

    public static CategoryDaoMySql getInstance(){

        if (instance == null)
            synchronized (CategoryDaoMySql.class){
                if (instance == null)
                    instance = new CategoryDaoMySql();
            }
        return instance;
    }

    private static final String CATEGORY_TREE_SHORT = "SELECT  FIRST.id, FIRST.title, \n" +
            "SECOND.id, SECOND.title, \n" +
            "THIRD.id, THIRD.title,\n" +
            "FORTH.id, FORTH.title\n" +
            "FROM categories AS FIRST\n" +
            "LEFT JOIN categories AS SECOND \n" +
            "ON FIRST.id = SECOND.parent_category AND SECOND.published = TRUE\n" +
            "LEFT JOIN categories AS THIRD\n" +
            "ON SECOND.id = THIRD.parent_category AND THIRD.published = TRUE\n" +
            "LEFT JOIN categories AS FORTH\n" +
            "ON THIRD.id = FORTH.parent_category AND FORTH.published = TRUE \n" +
            "WHERE FIRST.parent_category IS NULL AND FIRST.published = TRUE\n" +
            "ORDER BY FIRST.title, SECOND.title, THIRD.title, FORTH.title";

    private static final String CATEGORY_TREE_LONG = "SELECT FIRST.id, FIRST.title, FIRST.language_id, FIRST.description, FIRST.parent_category, FIRST.created_date, \n" +
            "\tFIRST.published,\n" +
            "\tSECOND.id, SECOND.title, SECOND.language_id, SECOND.description, SECOND.parent_category, SECOND.created_date, \n" +
            "\tSECOND.published,\n" +
            "\tTHIRD.id, THIRD.title, THIRD.language_id, THIRD.description, THIRD.parent_category, THIRD.created_date, \n" +
            "\tTHIRD.published,\n" +
            "        FORTH.id, FORTH.title, FORTH.language_id, FORTH.description, FORTH.parent_category, FORTH.created_date, \n" +
            "\tFORTH.published\n" +
            "  FROM categories AS FIRST\n" +
            "LEFT JOIN categories AS SECOND \n" +
            "ON FIRST.id = SECOND.parent_category AND SECOND.published = TRUE\n" +
            "LEFT JOIN categories AS THIRD\n" +
            "ON SECOND.id = THIRD.parent_category AND THIRD.published = TRUE\n" +
            "LEFT JOIN categories AS FORTH\n" +
            "ON THIRD.id = FORTH.parent_category AND FORTH.published = TRUE";


    protected Category getExampleObject(){
        return new Category();
    }

    /**
     * Collect maximum levels of categories from db
     * @return List<CategoryVO> of first level(there are not parent), in which each CategoryVO contains only this
     * CategoryContent with id  and title and objects
     * CategoryVO, that represents their subcategory;
     */
    @Override
    public List<SimpleCategoryVO> getShortCategoriesTree() throws PersistenceException {

        return UTIL_HOLDER.getUtilMySql().findAll(CATEGORY_TREE_SHORT, new SimpleCategoryVO(), new FullStackCollector<SimpleCategoryVO>() {

            private static final int fieldsForCategory = 2;
            private static final int depth = 4;

            private void recursion(ResultSet set, int shift, List<SimpleCategoryVO> sub) throws
                                                                                         SQLException {
                if (shift > fieldsForCategory*depth){              //return if reach the end of row
                    return;
                }
                int counter = shift;                               //counter of column position
                Long id = set.getLong(counter++);
                if (set.wasNull()){
                    return;
                }
                String title = set.getString(counter++);
                SimpleCategoryVO vo = new SimpleCategoryVO(id, title);
                sub.add(vo);                                                     //create collected entity in parent
                // list
                List<SimpleCategoryVO> thisSubs = new LinkedList<SimpleCategoryVO>();  //create list for
                // subcategories of this entity
                vo.setSubCategories(thisSubs);
                Long next = id;
                boolean hasNext = false;
                do {
                    recursion(set,counter, thisSubs);  //collect subcategories
                    hasNext = set.next();               //move cursor
                    if (!hasNext){                      //check the method not reach the end of set
                        return;
                    }
                    next = set.getLong(shift);
                } while (id.equals(next));           //check if id is the same (what means current entity is same)
                if (shift == 1){                     //get next first level element
                    recursion(set, shift, sub);      //repeat action for first level next element
                }
                set.previous();                      //return cursor position in initial state in case of change
                // entity
            }

            @Override
            public List<SimpleCategoryVO> collectEntityList(ResultSet set, String aliasPrefix, SimpleCategoryVO instance)
                    throws SQLException, PersistenceCollectorException {
                List<SimpleCategoryVO> result = new LinkedList<SimpleCategoryVO>();
                set.next();
                recursion(set, 1, result);
                return result;
            }

            @Override
            public SimpleCategoryVO collectEntity(ResultSet set, int shift, String aliasPrefix,
                                                  SimpleCategoryVO instance) throws SQLException,
                                                                                    PersistenceCollectorException {
                /*NOP*/
                return null;
            }
        }, null, true);

    }


//    @Override
//    public Criteria<CategoryContent> getCriteria() {
//        return new CriteriaMySql<CategoryContent>(CategoryContent.class);
//    }

}
//
//        categories.num = 6;
//        categories.1 = id
//        categories.2 = title
//        categories.3 = created_date
//        categories.4 = description
//        categories.5 = parent_category
//        categories.6 = published
