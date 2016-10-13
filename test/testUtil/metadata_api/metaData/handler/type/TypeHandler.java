package testUtil.metadata_api.metaData.handler.type;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class TypeHandler<T> implements Cloneable {

   public abstract void applySetToStatement(PreparedStatement statement, int pos, Object value) throws SQLException;

   /**
    * extract data from result set by position
    * @param set - source
    * @param pos - position in row
    * @return extracted data
    * @throws SQLException - in case of fail action
     */
   public abstract T getDataFromResultSet(ResultSet set, int pos) throws SQLException;

   /**
    * extract data from result set by field name
    * @param set - source
    * @param fieldName -name of field to be extracted
    * @return extracted data
    * @throws SQLException - in case of fail action
     */
   public abstract T getDataFromResultSet(ResultSet set, String fieldName) throws SQLException;

   public abstract T getExample();

   public abstract List<T> castFromString(List<String> values);

}
