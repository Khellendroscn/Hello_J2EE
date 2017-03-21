package net.khe.db2;

import net.khe.db2.annotations.DBAnnotationsProcesser;
import net.khe.db2.annotations.KeyNotFoundException;
import net.khe.util.ClassVisitor;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hyc on 2017/3/17.
 * ���ݿ���
 */
public class DataBase<T> {
    protected DBConfig config;
    protected Connection conn;
    protected Class<T> cls;
    static Map<Class<?>,TableMeta> metaMap = new HashMap<>();

    /**
     * @param config �����ļ�
     * @param cls Ҫ���ص���
     * @throws ClassNotFoundException �಻�����쳣
     * @throws KeyNotFoundException �����������쳣
     */
    public DataBase(DBConfig config, Class<T> cls) throws ClassNotFoundException, KeyNotFoundException {
        Class.forName(config.driver);
        this.config = config;
        this.cls = cls;
        if(!metaMap.containsKey(cls)){
            DBAnnotationsProcesser<T> processer =
                    new DBAnnotationsProcesser<T>(cls);
            metaMap.put(cls,processer.getMeta());
        }
    }

    /**
     * ���ӵ����ݿ�
     * @throws SQLException sql�쳣������ʧ��
     */
    public void connect() throws SQLException {
        if(conn==null)
            conn = DriverManager.getConnection(config.url,config.user,config.passwd);
    }

    /**
     * �Ͽ�����
     * @throws SQLException sql�쳣���Ͽ�ʧ��
     */
    public void close() throws SQLException {
        conn.close();
    }
    @Override
    public void finalize(){
        try {
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    Connection getConn(){return conn;}
    public static <T>TableMeta lookUp(Class<T> cls) throws KeyNotFoundException, ClassNotFoundException {
        if(!metaMap.containsKey(cls)){
            DBAnnotationsProcesser<T> processer =
                    new DBAnnotationsProcesser<T>(cls);
            metaMap.put(cls,processer.getMeta());
        }
        return metaMap.get(cls);
    }

    /**
     * @return ���ݿ�������Ϣ
     */
    public DBConfig getConfig(){return config;}
    /**
     * ִ��Select����
     * @return SqlSelect����
     * @see SqlSelect
     * @throws KeyNotFoundException �����������쳣
     * @throws ClassNotFoundException �಻�����쳣
     */
    public SqlSelect select() throws KeyNotFoundException, ClassNotFoundException {
        return new SqlSelect(this,cls);
    }

    /**
     * �������������ݿ��л�ȡʵ��
     * @param primaryKey ����ֵ
     * @return ʵ��
     * @throws KeyNotFoundException �����������쳣
     * @throws InvocationTargetException setter��������ʧ��
     * @throws SQLException sql�쳣
     * @throws InstantiationException ʵ��������ʧ�ܣ�����Bean�Ƿ����Ĭ�Ϲ�����
     * @throws IllegalAccessException �Ƿ������쳣������Ĭ�Ϲ������Ƿ�ɷ���
     * @throws NoSuchMethodException �����������쳣������Bean�Ƿ����setter����
     * @throws ClassNotFoundException �಻�����쳣
     */
    public T getInstance(Object primaryKey) throws
            KeyNotFoundException,
            InvocationTargetException,
            SQLException,
            InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            ClassNotFoundException {
        String key1 = lookUp(cls).getKey().getName();
        String key2 = primaryKey.toString();
        if(primaryKey instanceof CharSequence){
            key2 = String.format("\'%s\'",key2);
        }
        SqlSelect<T> select = new SqlSelect<T>(this,cls);
        select.where(key1+" = "+key2);
        Table<T> tb = select.execute();
        if(tb.getList().size()>0)return tb.getList().get(0);
        else return null;
    }

    /**
     * ��ȡ���ݱ�
     * @return ���ݱ�
     * @throws KeyNotFoundException �����������쳣
     * @throws InvocationTargetException setter��������ʧ��
     * @throws SQLException sql�쳣
     * @throws InstantiationException ʵ��������ʧ�ܣ�����Bean�Ƿ����Ĭ�Ϲ�����
     * @throws IllegalAccessException �Ƿ������쳣������Ĭ�Ϲ������Ƿ�ɷ���
     * @throws NoSuchMethodException �����������쳣������Bean�Ƿ����setter����
     * @throws ClassNotFoundException �಻�����쳣
     */
    public Table<T> getTable() throws
            KeyNotFoundException,
            InvocationTargetException,
            SQLException,
            InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            ClassNotFoundException {
        return select().execute();
    }

    /**
     * �������ݱ�
     * @throws KeyNotFoundException �����������쳣
     * @throws ClassNotFoundException �಻�����쳣
     * @throws SQLException sql�쳣
     */
    public void create() throws
            KeyNotFoundException,
            ClassNotFoundException,
            SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute(makeCreateSql());
    }

    /**
     * �����ݱ���ɾ��һ������
     * @param key
     * @throws SQLException
     * @throws KeyNotFoundException
     * @throws ClassNotFoundException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public void delete(Object key) throws
            SQLException,
            KeyNotFoundException,
            ClassNotFoundException,
            InvocationTargetException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException,
            NoSuchFieldException {
        if(metaMap.get(cls).getTables().size()!=1)
            throw new UnsupportedOperationException("Delete operation only be supported when the class maped to one table");
        SqlDelete<T> delete = new SqlDelete<T>(this,cls);
        delete.execute(key);
    }

    /**
     * ������������ݿ⣬�������key��������insert������Ѵ�����update
     * @param obj
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws KeyNotFoundException
     * @throws SQLException
     * @throws NoSuchFieldException
     * @throws NoSuchMethodException
     * @throws ClassNotFoundException
     */
    public void put(T obj) throws
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException,
            KeyNotFoundException,
            SQLException,
            NoSuchFieldException,
            NoSuchMethodException,
            ClassNotFoundException {
        if(metaMap.get(cls).getTables().size()!=1)
            throw new UnsupportedOperationException("Write operation only be supported when the class maped to one table");
        SqlPut<T> put = new SqlPut<T>(this,cls);
        put.execute(obj);
    }

    /**
     * �½�һ���������
     * @return ���ݿ��������
     */
    public DBSession<T> createSession(){
        return new DBSession<T>(this,cls);
    }
    private String makeCreateSql() throws KeyNotFoundException, ClassNotFoundException {
        TableMeta meta = lookUp(cls);
        String sql = "CREATE TABLE "+meta.getTables().get(0)+"(\n"+
                meta.getFields().stream()
                .map(field->field.toSql())
                .collect(Collectors.joining(",\n"))+
                "\n)";
        return sql;
    }
}
