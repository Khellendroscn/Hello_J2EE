package net.khe.j2ee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by hyc on 2017/3/14.
 */
@WebServlet("/Servlet2")
public class Servlet2 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter os = response.getWriter();
        os.println("<html>");
        os.println("<head><title>Servlet2</title></head>");
        os.println("<body><h1>");
        String msg = (String) request.getAttribute("msg");
        os.println(msg);
        os.println("</h1></body></html>");
    }
}
