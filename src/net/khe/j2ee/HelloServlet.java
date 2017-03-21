package net.khe.j2ee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by hyc on 2017/3/8.
 */
@WebServlet(name = "HelloServlet",urlPatterns = "/HelloServlet")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String str = "some message";
        req.setAttribute("msg",str);
        req.getRequestDispatcher("Servlet2").forward(req,resp);
    }
    @Override
    public void destroy() {
        super.destroy();
    }
}
