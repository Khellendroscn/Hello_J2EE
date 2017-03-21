package net.khe.db2;

import net.khe.db2.annotations.KeyNotFoundException;

import java.util.*;

/**
 * Created by hyc on 2017/3/18.
 * ��ʾ���ݱ�Ԫ����
 */
public class TableMeta {
    private List<String> tables;
    private Map<String,TableField> fields = new LinkedHashMap<>();
    private TableField key;
    private Map<Class<?>,TableField> foreignMap;

    /**
     * @param tables �����б�
     * @param fields �ֶ��б�
     */
    public TableMeta(List<String> tables, List<TableField> fields) {
        this.tables = tables;
        for(TableField field:fields){
            this.fields.put(field.getNameInClass(),field);
        }
    }

    /**
     * Ĭ�Ϲ��캯��
     */
    public TableMeta(){
        tables = new ArrayList<>();
        fields = new HashMap<>();
    }

    /**
     * ��ȡ�����б�
     * @return �����б�
     */
    public List<String> getTables() {
        return tables;
    }

    /**
     * �����ֶ������еı�������ȡ�ֶ�
     * @param name �ֶ������еı�����
     * @return �ֶ�
     */
    public TableField getField(String name) {
        return fields.get(name);
    }

    /**
     * ��ȡ�ֶ��б�
     * @return �ֶ��б�
     */
    public List<TableField> getFields(){
        return new ArrayList<>(fields.values());
    }

    /**
     * ��ȡ�����ֶ���Ϣ
     * @return �����ֶ�
     * @throws KeyNotFoundException �����������쳣
     */
    public TableField getKey() throws KeyNotFoundException {
        if(key==null)throw new KeyNotFoundException();
        return key;
    }

    /**
     * ��������
     * @param key �����ֶ�
     */
    public void setKey(TableField key) {
        this.key = key;
    }

    /**
     * ���ݹ���������ȡ����ֶ�
     * @param cls ����
     * @return ����ֶ���Ϣ
     */
    public TableField getForeigns(Class<?> cls) {
        return foreignMap.get(cls);
    }

    /**
     * �������ӳ���
     * @param foreignMap ���ӳ���
     */
    public void setForeignMap(Map<Class<?>, TableField> foreignMap) {
        this.foreignMap = foreignMap;
    }
}
