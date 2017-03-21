package net.khe.j2ee;

import java.io.IOException;
import java.sql.*;

/**
 * Created by hyc on 2017/3/18.
 */
public class DBTest {
    public static void main(String[] args) {
        try {
            DbConfig config = new DbConfig("DbConfig.txt");
            Class.forName(config.driver);
            Connection conn =
                    DriverManager.getConnection(config.url,config.user,config.passwd);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM test";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                String id = (String) rs.getObject("id");
                String name = (String) rs.getObject("name");
                rs.getObject("id");
                System.out.println(id+" : "+name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
