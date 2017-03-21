package net.khe.db2.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hyc on 2017/3/17.
 * ��ʾSql�еĿɱ��ַ���VARCHAR
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SqlString {
    /**
     * �ַ�����󳤶ȣ�Ĭ��Ϊ255
     * @return �ַ�����󳤶�
     */
    int value() default 0;
    /**
     * �����ݱ��е��ֶ���
     * ���Bean��Ҫ�Ӷ��ű��ȡ���ݣ�����д�����ֶ�������������.�ֶ�������
     * Ĭ����Bean�еı�������ͬ
     * @return �ֶ���
     */
    String name() default "";
}
