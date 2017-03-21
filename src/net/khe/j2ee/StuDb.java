package net.khe.j2ee;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyc on 2017/3/7.
 */
class Student{
    public final String id;
    public final String name;
    public final int age;

    Student(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
    @Override
    public String toString(){
        return "Student("+
                id+", "+
                name+", "+
                age+")";
    }
}
public class StuDb extends DataBase<Student,String> {
    public StuDb(DbConfig config) throws SQLException {
        super(config);
    }

    @Override
    public void insert(Student data) throws SQLException {
        String sql = "INSERT student VALUES(?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1,data.id);
        stmt.setString(2,data.name);
        stmt.setInt(3,data.age);
        stmt.executeUpdate();
        stmt.close();
    }

    @Override
    public void remove(String key) throws SQLException {
        String sql = "DELETE FROM student WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1,key);
        stmt.executeUpdate();
        stmt.close();
    }

    @Override
    public List<Student> select(String key) throws SQLException {
        List<Student> results = new ArrayList<>();
        String sql = "SELECT * FROM student WHERE id = "+key;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            String id = rs.getString(1);
            String name = rs.getString(2);
            int age = rs.getInt(3);
            Student temp = new Student(id,name,age);
            results.add(temp);
        }
        stmt.close();
        return results;
    }

    @Override
    public List<Student> selectAll() throws SQLException {
        List<Student> results = new ArrayList<>();
        String sql = "SELECT * FROM student";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            String id = rs.getString(1);
            String name = rs.getString(2);
            int age = rs.getInt(3);
            Student temp = new Student(id,name,age);
            results.add(temp);
        }
        stmt.close();
        return results;
    }

    public static void main(String[] args) {
        try {
            DbConfig config = new DbConfig("DbConfig.txt");
            DataBase<Student,String> db = new StuDb(config);
            List<Student> stus = db.selectAll();
            for(Student s:stus) {
                System.out.println(s);
            }
            db.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
