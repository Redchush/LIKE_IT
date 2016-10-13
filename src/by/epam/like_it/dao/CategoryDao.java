package by.epam.like_it.dao;


import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.model.bean.Category;
import by.epam.like_it.model.vo.db_vo.SimpleCategoryVO;

import java.util.List;

@Deprecated
public abstract class CategoryDao extends AbstractRealEntityDao<Category> {

    public abstract List<SimpleCategoryVO> getShortCategoriesTree() throws PersistenceException;

}
