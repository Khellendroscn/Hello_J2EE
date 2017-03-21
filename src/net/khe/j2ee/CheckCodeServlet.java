package net.khe.j2ee;

import net.khe.util.Generator;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by hyc on 2017/3/14.
 */
@WebServlet(name = "CheckCodeServlet",urlPatterns = "/checkcode")
public class CheckCodeServlet extends HttpServlet {
    private Generator<CheckCode> gen = new CheckCodeGenerator();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/jpeg");
        HttpSession session = request.getSession();
        CheckCode checkCode = gen.next();
        session.setAttribute("checkcode",checkCode.getCheckCode());
        OutputStream os = response.getOutputStream();
        ImageIO.write((RenderedImage) checkCode.getImage(),"jpeg",os);
        os.flush();
        os.close();
    }
}
