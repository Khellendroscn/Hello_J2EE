package net.khe.db2.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hyc on 2017/3/17.
 * ��ע���ʾsqlԼ�����������ֶ�
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Constraints {
    /**
     * ����Լ��
     * @return true-���ֶ�Ϊ����
     */
    boolean primaryKey() default  false;

    /**
     * ��ֵԼ��
     * @return false-���ֶβ�����Ϊnull
     */
    boolean alloNull() default true;

    /**
     * uniqueԼ��
     * @return true-���ֶβ����ظ�
     */
    boolean unique() default false;
}
