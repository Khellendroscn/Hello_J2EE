package net.khe.db2.annotations;

/**
 * Created by hyc on 2017/3/18.
 * �����������쳣
 */
public class KeyNotFoundException extends Exception {
    public KeyNotFoundException(){
        super("Must have a primary key");
    }
}
