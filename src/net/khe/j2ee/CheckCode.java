package net.khe.j2ee;

import java.awt.*;

/**
 * Created by hyc on 2017/3/14.
 */
public class CheckCode {
    private String checkCode;
    private Image image;
    public CheckCode(String checkWd, Image image){
        this.checkCode = checkWd;
        this.image = image;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
