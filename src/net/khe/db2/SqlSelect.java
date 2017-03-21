package net.khe.db2;

import net.khe.db2.annotations.KeyNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static java.util.stream.Collectors.*;

/**
 * Created by hyc on 2017/3/18.
 * ��ʾsql��ѯ����
 */
public class SqlSelect<T> {
    static Map<Class<?>,String> basicSqlMap = new HashMap<>();
    private Class<T> cls;
    private DataBase db;
    private Set<String> wheres = new HashSet<>();
    private Set<String> orders = new LinkedHashSet<>();
    SqlSelect(DataBase db, Class<T> cls) throws
            KeyNotFoundException,
            ClassNotFoundException {
        this.db = db;
        this.cls = cls;
        if(!basicSqlMap.containsKey(cls)){
            basicSqlMap.put(cls,prepareSql());
        }
    }

    /**
     * ����where������ɸѡ
     * @param filters where���
     * @return this
     */
    public SqlSelect where(String... filters){
        wheres.addAll(Arrays.asList(filters));
        return this;
    }

    /**
     * ����order by����������
     * @param order order by���
     * @return this
     */
    public SqlSelect orderBy(String... order){
        orders.addAll(Arrays.asList(order));
        return this;
    }

    /**
     * ִ�в�ѯ����
     * @return ���ݱ�
     * @throws SQLException sql�쳣
     * @throws NoSuchMethodException setter�������쳣�������Ƿ�ȱʧsetter
     * @throws IllegalAccessException ����Ȩ�޳�ͻ������setter��Ĭ�Ϲ������ķ���Ȩ��
     * @throws InstantiationException ʵ����ʼ��ʧ�ܣ�����Bean�Ƿ�ȱ��Ĭ�Ϲ��캯��
     * @throws InvocationTargetException ��������ʧ�ܣ�����setter����ǩ��
     * @throws KeyNotFoundException �����������쳣
     * @throws ClassNotFoundException �಻�����쳣
     */
    public Table<T> execute() throws
            SQLException,
            NoSuchMethodException,
            IllegalAccessException,
            InstantiationException,
            InvocationTargetException,
            KeyNotFoundException, ClassNotFoundException {
        String sql = basicSqlMap.get(cls);
        if(!wheres.isEmpty()){
            sql += "\nWHERE "+
                    wheres.stream()
                    .collect(joining(" AND "));
        }
        if(!orders.isEmpty()){
            sql += "\nORDER BY"+
                    orders.stream()
                    .collect(joining(", "));
        }
        Statement stmt = db.getConn().createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        return new Table<T>(db,rs,cls);
    }
    private List<String> prepareCols() throws
            KeyNotFoundException,
            ClassNotFoundException {
        TableMeta meta = DataBase.lookUp(cls);
        return meta.getFields().stream()
                .map(field->field.getName())
                .collect(toList());
    }
    private List<String> prepareFroms() throws
            KeyNotFoundException,
            ClassNotFoundException {
        TableMeta meta = DataBase.lookUp(cls);
        return meta.getTables().stream()
                .collect(toList());
    }
    private String prepareSql() throws
            KeyNotFoundException,
            ClassNotFoundException {
        List<String> cols = prepareCols();
        List<String> froms = prepareFroms();
        String sql = "SELECT "+
                cols.stream()
                .collect(joining(", "))+
                "\nFROM "+
                froms.stream()
                .collect(joining(", "));
        return sql;
    }
}
