package net.khe.db2.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hyc on 2017/3/18.
 * ��ע����������(java bean)����ʾ�����ʵ���ɱ�ӳ�䵽���ݿ���
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBTable {
    /**
     * ���ݱ������
     * ���ԴӶ��ű��л�ȡ���ݣ����ǽ������ݱ�����Ϊ1ʱ�ſ��Զ����ݱ����д�����
     * д���������insert,update,delete
     * ����Ĭ��Ϊ��ע�������������(Сд)�������е�"."�ᱻ�滻Ϊ"_"
     * @return
     */
    String[] value() default {};
}
