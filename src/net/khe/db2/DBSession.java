package net.khe.db2;

import net.khe.db2.annotations.KeyNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyc on 2017/3/21.
 * ���ݿ��������
 * ���Խ�һ��д��������������б�
 * ֻҪ��һ�������׳��쳣������ͻ���лع�
 */
public class DBSession<T> {
    private DataBase<T> db;
    private Class<T> cls;
    private List<SqlWriteOperator> operators = new ArrayList<>();
    DBSession(DataBase<T> db, Class<T> cls){
        this.db = db;
        this.cls = cls;
    }

    /**
     * �����������һ��put����
     * @param obj Ҫ�������ݿ�Ķ���
     * @see SqlPut
     */
    public void put(T obj){
        SqlPut<T> put = new SqlPut<T>(db,cls);
        put.setObject(obj);
        operators.add(put);
    }

    /**
     * �����������һ��ɾ������
     * @param key ��Ҫɾ���Ķ���ļ�ֵ
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws KeyNotFoundException
     */
    public void delete(Object key) throws
            ClassNotFoundException,
            NoSuchFieldException,
            KeyNotFoundException {
        SqlDelete<T> del = new SqlDelete<T>(db,cls);
        del.setKey(key);
        operators.add(del);
    }

    /**
     * ִ�����񣬸ò�����ͬ����
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws KeyNotFoundException
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws NoSuchFieldException
     */
    public synchronized void execute() throws
            SQLException,
            IllegalAccessException,
            InstantiationException,
            KeyNotFoundException,
            ClassNotFoundException,
            NoSuchMethodException,
            InvocationTargetException,
            NoSuchFieldException {
        Connection conn = db.getConn();
        Savepoint savepoint = conn.setSavepoint("session");
        try{
            for(SqlWriteOperator operator:operators){
                operator.execute();
            }
            conn.releaseSavepoint(savepoint);
        }catch (Exception e){
            conn.rollback(savepoint);
            throw e;
        }
    }
}
