package by.epam.like_it.service;


import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.model.vo.db_vo.SimpleCategoryVO;

import java.util.List;

@Deprecated
public interface CategoryService {

    List<SimpleCategoryVO> findCategoryTree() throws ServiceSystemException;
}
