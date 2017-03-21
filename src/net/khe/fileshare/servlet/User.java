package net.khe.fileshare.servlet;

import net.khe.db2.DBConfig;
import net.khe.db2.DataBase;
import net.khe.db2.annotations.Constraints;
import net.khe.db2.annotations.DBTable;
import net.khe.db2.annotations.KeyNotFoundException;
import net.khe.db2.annotations.SqlString;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * Created by hyc on 2017/3/21.
 */
@DBTable({"user"})
public class User {
    @SqlString(20)
    @Constraints(alloNull = false,primaryKey = true)
    private String userName;
    @SqlString(20)
    private String passWd;
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWd() {
        return passWd;
    }

    public void setPassWd(String passWd) {
        this.passWd = passWd;
    }

    public static void main(String[] args) {
        try {
            DBConfig config = new DBConfig("web/users_dbConfig.txt");
            DataBase<User> db = new DataBase<>(config,User.class);
            db.connect();
            db.create();
            User admin = new User();
            admin.setUserName("admin");
            admin.setPassWd("88888888");
            db.put(admin);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
