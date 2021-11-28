package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;



public class JdbcUtils1 {
	 
	private static String driverName = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://47.114.109.242:3306/db_from_fr_temp";
    private static String user = "root";
    private static String password = "%JGY,password%";
    /**
     * 链接数据库
     */
    static {
        try {
            Class.forName(JdbcUtils1.driverName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取链接对象connection
     * @return
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(JdbcUtils1.url, JdbcUtils1.user, JdbcUtils1.password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 关闭资源
     * @param conn
     * @param st
     * @param rs
     */
    public static void close(Connection conn,Statement st,ResultSet rs) {
        if(rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(st != null) {
            try {
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}