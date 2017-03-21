package net.khe.j2ee;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by hyc on 2017/3/2.
 */
public class DbConfig {
    public final String driver;
    public final String url;
    public final String user;
    public final String passwd;
    public DbConfig(File configFile)throws IOException{
        Properties props = new Properties();
        FileInputStream is = new FileInputStream(configFile);
        props.load(is);
        this.driver = props.getProperty("driver");
        String urlPattern = "jdbc:mysql://%s:%s/%s";
        this.url = String.format(urlPattern,
                props.getProperty("host"),
                props.getProperty("port"),
                props.getProperty("db"));
        this.user = props.getProperty("user");
        this.passwd = props.getProperty("passwd");
    }
    public DbConfig(String filename) throws IOException {
        this(new File(filename));
    }
    @Override
    public String toString(){
        return "DbConfig("+
                this.driver+", "+
                this.url+", "+
                this.user+", "+
                this.passwd+")";
    }

    public static void main(String[] args) {
        try {
            DbConfig config = new DbConfig(new File("DbConfig.txt"));
            System.out.println(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
