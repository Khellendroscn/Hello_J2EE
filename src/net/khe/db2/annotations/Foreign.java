package net.khe.db2.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hyc on 2017/3/18.
 * ��ע���ʾ������������ֶ�
 * �������ʾBean֮���ӳ���ϵ���������ݿ��е��������
 * ��˶�Bean��������������ݱ�����κ�Ӱ��
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Foreign {
    /**
     * ������󶨵�һ��Bean�ϣ�ע��Ĳ���Ϊ���ȫ��
     * �����֮�󣬱����ע����ֶλ�ӳ�䵽���󶨵�Bean�������ֶ�
     * @return ���󶨵����ȫ��
     */
    String value();
}
