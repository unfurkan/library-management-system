package database;


import java.sql.*;

public class DbConnection {

    private static Connection conn;

    public static void  connectDatabase(){
        String url ="jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC";
        String username ="root";
        String password ="";

        try {
            conn =DriverManager.getConnection(url,username,password);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Db Connected Succesfully;");
    }

    public static void disconnectDatabase(){
        try{
            if(conn !=null && ! (conn.isClosed()) ){

                conn.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean updateQuery(String sqlCommand) throws SQLException {
        Statement stmt =null;
        try {
            connectDatabase();
            stmt =conn.createStatement();
            stmt.executeUpdate(sqlCommand);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        }
        finally {
            if(stmt != null) {
                stmt.close();
            }
            disconnectDatabase();
        }
        return true;

    }

    public static ResultSet readFromDatabase(String sqlCommand) throws SQLException {
        Statement stmt =null;
        ResultSet rs =null;
        try{
            connectDatabase();
            stmt =conn.createStatement();
            rs =stmt.executeQuery(sqlCommand);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;

    }

}
