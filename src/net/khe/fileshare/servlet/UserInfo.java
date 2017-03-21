package net.khe.fileshare.servlet;

import net.khe.db2.DBConfig;
import net.khe.db2.DataBase;
import net.khe.db2.annotations.*;

import java.io.IOException;


/**
 * Created by hyc on 2017/3/21.
 */
@DBTable({"user_info"})
public class UserInfo {
    @SqlString(20)
    @Constraints(primaryKey = true,alloNull = false)
    @Foreign("net.khe.fileshare.servlet.User")
    private String userName;
    @SqlString(20)
    private String realName;
    @SqlInt
    private int gender;
    @SqlInt
    private int grade;
    @SqlString
    private String phoneNum;
    @SqlString
    private String email;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public static void main(String[] args) {
        try {
            DBConfig config = new DBConfig("D:\\projects\\Hello_J2EE\\web\\users_dbConfig.txt");
            DataBase<UserInfo> db = new DataBase<>(config,UserInfo.class);
            db.connect();
            db.create();
            System.out.println("table created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
