package net.khe.j2ee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by hyc on 2017/3/7.
 */
public abstract class DataBase<T,K> {
    private DbConfig config;
    protected Connection conn;
    public DataBase(DbConfig config) throws SQLException {
        this.config = config;
        try{
            Class.forName(config.driver);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        this.conn = DriverManager.getConnection(
                config.url,
                config.user,
                config.passwd);
    }
    public void close() throws SQLException {
        conn.close();
    }
    public Connection getConn(){return conn;}
    public abstract void insert(T data)throws SQLException;
    public abstract void remove(K key)throws SQLException;
    public abstract List<T> select(K key)throws SQLException;
    public abstract List<T> selectAll()throws SQLException;
}
