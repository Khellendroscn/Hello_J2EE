package net.khe.j2ee;

import net.khe.util.Generator;
import net.khe.util.RandomGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by hyc on 2017/3/14.
 */
public class CheckCodeGenerator implements Generator<CheckCode> {
    public static int width = 100;
    public static int height = 36;
    public static Color bgColor = Color.LIGHT_GRAY;
    public static Font font = new Font("DIALOG",Font.BOLD,25);
    private final static String charset =
            "123456789ABCDEFGHIJKLMNPQRSTUVWXYZ";
    private final static Image bgImg;
    private Random r = new Random();
    static {
        bgImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics g = bgImg.getGraphics();
        g.setColor(bgColor);
        g.fillRect(0,0,width,height);
        g.setColor(Color.BLACK);
        g.drawRect(0,0,width,height);
    }
    @Override
    public CheckCode next() {
        String checkCode = makeCheckCode();
        Image checkCodeImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics g = checkCodeImg.getGraphics();
        g.drawImage(bgImg,0,0,width,height,null);
        drawCode(g,checkCode);
        drawPoints(g,Color.WHITE);
        return new CheckCode(checkCode,checkCodeImg);
    }
    private String makeCheckCode(){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<4;++i){
            int index = r.nextInt(charset.length());
            sb.append(charset.charAt(index));
        }
        return sb.toString();
    }
    private void drawPoints(Graphics g, Color c){
        Color oldColor = g.getColor();
        g.setColor(c);
        Generator<Integer> xgen = new RandomGenerator.Integer(width);
        Generator<Integer> ygen = new RandomGenerator.Integer(height);
        for(int i=0;i<Math.sqrt(width*height)*10;++i){
            int x = xgen.next();
            int y = ygen.next();
            g.fillRect(x,y,1,1);
        }
        g.setColor(oldColor);
    }
    private void drawCode(Graphics g,String code){
        Color oldColor = g.getColor();
        g.setFont(font);
        Generator<Color> colorGen = new RandomGenerator.PureColor();
        Generator<Integer> xgen = new RandomGenerator.Integer(5);
        Generator<Integer> ygen = new RandomGenerator.Integer(10);
        int x = xgen.next();
        for(Character ch:code.toCharArray()){
            g.setColor(colorGen.next());
            g.drawString(ch.toString(),x,height-ygen.next());
            x+=font.getSize();
        }
        g.setColor(oldColor);
    }
    public static void main(String[] args) {
        CheckCodeGenerator gen = new CheckCodeGenerator();
        CheckCode code = gen.next();
        JFrame frame = new JFrame(){
            {
                add(BorderLayout.CENTER,new JLabel(new ImageIcon(code.getImage())));
                add(BorderLayout.NORTH,new JLabel(code.getCheckCode()));
            }
        };
        frame.setSize(100,100);
        frame.setVisible(true);
    }
}
