package net.khe.fileshare.servlet;

import net.khe.db2.DBConfig;
import net.khe.db2.DataBase;
import net.khe.db2.annotations.KeyNotFoundException;
import net.khe.util.Directory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import com.mysql.jdbc.Driver;
import net.khe.util.Print;

/**
 * Created by hyc on 2017/3/21.
 */
@WebServlet(name = "Login",urlPatterns = "/login")
public class Login extends HttpServlet {
    private DataBase<User> db;
    public Login(){
        super();
        try {
            DBConfig config = new DBConfig("D:\\projects\\Hello_J2EE\\web\\users_dbConfig.txt");
            db = new DataBase<>(config,User.class);
            db.connect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void destroy(){
        try {
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void verify(User user)throws LoginException{
        User u = null;
        try{
            u = db.getInstance(user.getUserName());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        if(u==null) throw new LoginException("’À∫≈≤ª¥Ê‘⁄");
        if(!u.getPassWd().equals(user.getPassWd()))
            throw new LoginException("√‹¬Î¥ÌŒÛ");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("username");
        String passwd = request.getParameter("passwd");
        ServletContext context = getServletContext();
        if(userName==null||userName.equals("")){
            RequestDispatcher disp = context.getRequestDispatcher("/login.htm");
            disp.forward(request,response);
        }else{
            User user = new User();
            user.setUserName(userName);
            user.setPassWd(passwd);
            try{
                verify(user);
                HttpSession session = request.getSession();
                session.setAttribute("loginUser",user);
                RequestDispatcher disp = context.getRequestDispatcher("/filelist.htm");
                disp.forward(request,response);
            } catch (LoginException e) {
                response.setContentType("text/html;charset=gb2312");
                PrintWriter os = new PrintWriter(response.getOutputStream());
                os.println("<font color=\"red\">"+e.getMessage()+"</font>");
                os.close();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
