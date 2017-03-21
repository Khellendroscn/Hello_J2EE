package net.khe.db2.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hyc on 2017/3/17.
 * ��ʾ���ݿ��еĶ����ַ���CHAR
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SqlChars {
    /**
     * �ַ�������
     * @return �ַ�������
     */
    int value();
    /**
     * �����ݱ��е��ֶ���
     * ���Bean��Ҫ�Ӷ��ű��ȡ���ݣ�����д�����ֶ�������������.�ֶ�������
     * Ĭ����Bean�еı�������ͬ
     * @return �ֶ���
     */
    String name() default "";
}
