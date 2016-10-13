package by.epam.like_it.common_util;


import java.sql.*;


public class Test {
    private static String path="jdbc:mysql://mysql38842-lovely-movies.mycloud.by/leon?autoReconnect=true&useSSL=false";



    @org.junit.Test
    public void connectToMe() throws ClassNotFoundException {
        Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");
        System.out.println(aClass);
        Connection connection = null;
        try {
            String ulla = "jdbc:mysql://mysql39230-env-1317477.mycloud.by/like_it";
            String oneMore =  "jdbc:mysql://mysql39230-env-1317477.mycloud.by";
            String name = "root";
            String password = "FXEpgn74526";


            String connectionString = "jdbc:mysql://localhost:3306/my_database?user=root&password=Pass&useUnicode" +
                    "=true&characterEncoding=UTF-8";
            String connectionString2 = "jdbc:mysql://mysql39230-env-1317477.mycloud.by/like_it?user=root&password=FXEpgn74526&useUnicode" +
                    "=true&characterEncoding=UTF-8";
//            connection = DriverManager.getConnection(connectionString2);
            String name2 = "root2";
            String password2 = "230004";

            String user_name = "user";
            String user_pass = "LZm7JBKhU9QS8PES";

            connection = DriverManager.getConnection(ulla, user_name, user_pass);
//            System.out.println("connection" + connection);
//            String selectById = QueryMaker.getSelectById(User.class);
            String testSelect = "SELECT * FORM users";
            try(Statement statement = connection.createStatement();
                ResultSet set = statement.executeQuery(testSelect)){
                long aLong = set.getLong(1);
                System.out.println(aLong);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
            System.out.println(e.getErrorCode());
            e.printStackTrace();
        } finally {
            System.out.println("connection : + " + connection);
            System.out.println("success");
//            connection.close();
        }
    }
    @org.junit.Test
    public void connectToOther() throws SQLException, ClassNotFoundException {
        String user = "root";
        String  password = "root";
        Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");
        Connection connection = null;
        try {
            DriverManager.setLoginTimeout(3);
            connection = DriverManager.getConnection(path, user, password);
        } catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
            System.out.println(e.getErrorCode());
            e.printStackTrace();
        }finally {
            System.out.println(connection);
            if (connection != null){
                connection.close();
            }
        }
    }

}
