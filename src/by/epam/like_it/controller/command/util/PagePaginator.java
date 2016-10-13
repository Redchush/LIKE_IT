package by.epam.like_it.controller.command.util;

import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.model.criteria_to.core.Criteria;
import by.epam.like_it.model.criteria_to.core.Limit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * class responsible criteria_to view pages pagination
 */

public class PagePaginator {

    private static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());

    private static final int ACCESSIBLE_PAGES = 5;
    private static final int SHIFT = 2;
    protected static final int DEFAULT_PER_PAGE = 10;

    private static PagePaginator instance;

    protected PagePaginator(){}

    public static PagePaginator getInstance(){

        if (instance == null)
            synchronized (PagePaginator.class){
                if (instance == null)
                    instance = new PagePaginator();
            }
        return instance;
    }

    /**
     *
     * @param request
     * @return int value of CommandConstants.CURRENT_PAGE attribute. If there no such attribute - return 1;
     */

    public int getCurrentPage(HttpServletRequest request){
        String currentPageAttr =  request.getParameter(CommandConstants.CURRENT_PAGE);
        int currentPage;
        if(currentPageAttr != null) {
            currentPage = Integer.parseInt(currentPageAttr);
        } else {
            currentPage = 1;
        }
        return currentPage;
    }

    /**
     * create limit parameter criteria_to criteria.
     * Get limParam as parameter of request, parse it and set it criteria_to Criteria as Limit count
     * and perPage field
     *
     * @param request - HttpServletRequest
     * @param limParam - parameter of request responsible for setting per page count of units
     * @param criteria - Criteria criteria_to be processed
     */
    public void processInitialLimit(HttpServletRequest request, String limParam, Criteria criteria){
        int perPageInt = DEFAULT_PER_PAGE;
        try{
            String perPage = request.getParameter(limParam);
            perPageInt = Integer.parseInt(perPage);
        } catch (NumberFormatException e){
            LOGGER.info("Starting page use illegal argument. Default setting " + DEFAULT_PER_PAGE + " applied.");
        }
        Limit limitObject = new Limit(0, perPageInt);
        criteria.setLimit(limitObject);
        criteria.setPerPage(perPageInt);
    }


    /**
     * produce array of accessible pages and set it as CommandConstants.ACCESSIBLE_PAGES_ARRAY
     * @param request
     * @param total
     * @param perPage
     */

    public void setAccessiblePages(HttpServletRequest request, Long total, int perPage, int currentPage) {

        int lastPage = (total == null || total == 0) ? 1
                                                     : (int) Math.ceil(total/((double) perPage));

        int[] accessiblePages = makeAccessiblePagesArray(lastPage, currentPage);
        if (accessiblePages[0] > 1){
            request.setAttribute(CommandConstants.FIRST_DOT, "true");
        }
        int lastAccessible = accessiblePages[accessiblePages.length - 1];

        if (lastAccessible != lastPage) {
            if (lastAccessible != (lastPage - 1)) {
                request.setAttribute(CommandConstants.LAST_DOT, "true");
            }
            request.setAttribute(CommandConstants.LAST_PAGE, lastPage);
        }

        request.setAttribute(CommandConstants.CURRENT_PAGE, currentPage);
        request.setAttribute(CommandConstants.ACCESSIBLE_PAGES_ARRAY, accessiblePages);
    }

    /**
     *
     * @param lastPage - last page in possible pagination array
     * @param currentPage - requested page
     * @return array, that contains all possible numbers
     */

    private int[] makeAccessiblePagesArray(int lastPage, int currentPage){
        int pageCount = lastPage <= ACCESSIBLE_PAGES ?
                        lastPage : ACCESSIBLE_PAGES;

        int[] accessiblePages = new int[pageCount];
        int from = currentPage - SHIFT;
        int to = currentPage + SHIFT;

        if (from < 1 && to > lastPage){
            from = 1;
            to = lastPage;
        } else {
            if (from < 1){
                to = to - (from - 1);//from is negative or 0
                from = 1;
            }
            if (to > lastPage){
                from = from - (to - lastPage);  // shift from on overflowed numbers of page
            }
        }
        fillAcessible(accessiblePages, from);
        return accessiblePages;
    }

    private void fillAcessible(int[] accessible, int from){
        int counter = from;
        for (int i = 0; i < accessible.length; i++) {
            accessible[i] = counter++;
        }
    }
}
