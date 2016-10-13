package by.epam.like_it.service.impl;

import by.epam.like_it.model.vo.db_vo.SimpleCategoryVO;
import by.epam.like_it.service.impl.common.CategoryServiceImpl;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;



public class CategoryServiceImplTest {

    public static final CategoryServiceImpl SERVICE = CategoryServiceImpl.getInstance();

    @Test
    public void findCategoryTree() throws Exception {
        List<SimpleCategoryVO> categoryTree = SERVICE.findCategoryTree();
        checkMathHasChilds(categoryTree);
    }

    private void checkMathHasChilds(List<SimpleCategoryVO> categoryTree) {
        for(SimpleCategoryVO instanse : categoryTree){
            boolean hasChilds = instanse.isHasChildren();
            List<SimpleCategoryVO> subCategories = instanse.getSubCategories();
            assertEquals(hasChilds, !subCategories.isEmpty());
            checkMathHasChilds(subCategories);
        }
    }

}