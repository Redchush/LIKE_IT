package by.epam.like_it.exception.persistence.util;

import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.dao.mysql.util.ResourceNavigator;

import by.epam.like_it.model.vo.system_vo.error_bean.ErrorInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.epam.like_it.common_util.ResourceManager.DB;

/**
 * The class parse data from SQL exception, throw correct PersistenceException with information about failed
 * constriction
 */
public class ExceptionParser {

    private static final Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    private static final String UNIQUE_VIOLATION_STATE = "23000";
    private static ExceptionParser instance;

    private ExceptionParser(){}

    public static ExceptionParser getInstance(){

        if (instance == null)
            synchronized (ExceptionParser.class){
                if (instance == null)
                    instance = new ExceptionParser();
            }
        return instance;
    }

    public boolean isUniqueConstraintBroken(SQLException e, String uniquePattern){
        return e.getSQLState() != null && e.getSQLState().equals(UNIQUE_VIOLATION_STATE) &&
                e.getMessage().matches(".*" + uniquePattern + ".*");
    }

    /**
     * Parse SQLException if it is has state UNIQUE_VIOLATION_STATE("23000");
     * @param e
     * @return
     *   ErrorInfo - bean, that contains information about reason of failing operation.
     *   if current SQLException don't connected with unique violation, returns null;
     *
     */
    public ErrorInfo parseSQLException(SQLException e)  {
        String uniquePattern = DB.getString("database.marker.unique");
        if (!isUniqueConstraintBroken(e, uniquePattern)){
            String primary = DB.getString("database.marker.primary");
            if (!isUniqueConstraintBroken(e, primary)){
                return null;
            }
            ErrorInfo info = new ErrorInfo();
            info.setFailedField(ErrorInfo.PRIMARY_VALUE);
            return info;
        }

        ErrorInfo result = new ErrorInfo();

        String message = e.getMessage();
        Pattern fieldViolatedPat = Pattern.compile("'[a-zA-z._-]*" + uniquePattern  + "'");
        Pattern valueViolatedPat = Pattern.compile("'.*?'");

        Matcher fieldViolatedMatcher = fieldViolatedPat.matcher(message);
        Matcher valueViolatedMatcher = valueViolatedPat.matcher(message);
        String filed = "";
        String value = "";


        if (valueViolatedMatcher.find()){
            int start = valueViolatedMatcher.start();
            int end = valueViolatedMatcher.end();
            value = message.substring(start + 1, end - 1);
        }

        if (fieldViolatedMatcher.find()) {
            int start = fieldViolatedMatcher.start();
            int end = fieldViolatedMatcher.end();
            filed = message.substring(start + 1, end - 1);
        }
        String[] split = filed.split("\\.");
        if (split.length > 1) {
            String table = split[0];
            int lastInd = split[1].lastIndexOf('-');
            String field = split[1].substring(0, lastInd);

            String referencedClass = ResourceNavigator.getReferencedClass(table);
            String refField = ResourceNavigator.getReferencedBeanField(table, field);

            result.setFailedBean(referencedClass);
            result.setFailedField(refField);
            result.setValueViolated(value);
        }
        return result;

    }

}
