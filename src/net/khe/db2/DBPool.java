package net.khe.db2;

import net.khe.db2.annotations.KeyNotFoundException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by hyc on 2017/3/21.
 * ���ݿ����ӳ�
 */
public class DBPool {
    private Map<String,BlockingDeque<Connection>> freeConns =
            new HashMap<>();

    /**
     * ����һ����������ݿ���󣬸ö�������ʱ���ȴ����ӳ��л�ȡ���ӣ��ر�ʱ�������ͷŵ����ӳ�
     * @param config ���ݿ�������Ϣ
     * @param cls ӳ�䵽���ݿ����
     * @return �����ݿ�
     * @throws KeyNotFoundException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public <T>DataBase<T> newDataBase(DBConfig config,Class<T> cls) throws KeyNotFoundException, ClassNotFoundException, SQLException {
        if (!freeConns.containsKey(config.url)){
            freeConns.put(config.url,new LinkedBlockingDeque<>());
        }
        DataBase<T> db = new DataBaseInPool<T>(config,cls);
        db.connect();
        return db;
    }

    /**
     * �ر���������
     * @throws SQLException
     */
    public void close() throws SQLException {
        for(String url:freeConns.keySet()){
            while (!freeConns.get(url).isEmpty()){
                freeConns.get(url).pop().close();
            }
        }
    }
    @Override
    public void finalize (){
        try {
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    class DataBaseInPool<T> extends DataBase<T>{
        /**
         * @param config �����ļ�
         * @param cls    Ҫ���ص���
         * @throws ClassNotFoundException �಻�����쳣
         * @throws KeyNotFoundException   �����������쳣
         */
        public DataBaseInPool(DBConfig config, Class<T> cls) throws ClassNotFoundException, KeyNotFoundException {
            super(config, cls);
        }
        @Override
        public void connect() throws SQLException {
            if(conn!=null)return;
            if(freeConns.get(config.url).isEmpty()){
                conn = DriverManager.getConnection(config.url,config.user,config.passwd);
            }else{
                conn = freeConns.get(config.url).pop();
            }
        }
        @Override
        public void close(){
            freeConns.get(config.url).push(conn);
            conn = null;
        }
    }
}
