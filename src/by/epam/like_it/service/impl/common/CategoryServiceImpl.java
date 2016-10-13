package by.epam.like_it.service.impl.common;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.dao.CategoryDao;
import by.epam.like_it.dao.MySqlDaoFactory;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.model.vo.db_vo.SimpleCategoryVO;
import by.epam.like_it.service.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Deprecated
public class CategoryServiceImpl implements CategoryService {

    private static Logger logger = LogManager.getLogger(ClassName.getClassName());

    private static CategoryServiceImpl instance;

    private CategoryServiceImpl(){}

    public static CategoryServiceImpl getInstance(){

        if (instance == null)
            synchronized (PostServiceImpl.class){
                if (instance == null)
                    instance = new CategoryServiceImpl();
            }
        return instance;
    }


    @Override
    public List<SimpleCategoryVO> findCategoryTree() throws ServiceSystemException {
        CategoryDao DAO = MySqlDaoFactory.getInstance().getCategoryDao();
        List<SimpleCategoryVO> categories = null;
        try {
            categories = DAO.getShortCategoriesTree();
            if (categories != null) {
                setHasChilds(categories);
            }
        } catch (PersistenceException e) {
            throw new ServiceSystemException("Can't access initial files");
        }
        return categories;
    }

    private void setHasChilds(List<SimpleCategoryVO> categoryVOs){
        for(SimpleCategoryVO vo : categoryVOs){
            if (vo.getSubCategories().isEmpty()){
                vo.setHasChildren(false);
            } else {
                vo.setHasChildren(true);
                setHasChilds(vo.getSubCategories());
            }
        }
    }
}
