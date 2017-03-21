package net.khe.fileshare.servlet;

import com.sun.deploy.net.HttpResponse;
import net.khe.db2.DBConfig;
import net.khe.db2.DataBase;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by hyc on 2017/3/21.
 */
@WebServlet(name = "Regist",urlPatterns = "/regist")
public class Regist extends HttpServlet {
    private DataBase<User> userDb;
    private DataBase<UserInfo>infoDb;
    public Regist(){
        super();
        try {
            DBConfig config = new DBConfig("D:\\projects\\Hello_J2EE\\web\\users_dbConfig.txt");
            userDb = new DataBase<>(config,User.class);
            infoDb = new DataBase<>(config,UserInfo.class);
            userDb.connect();
            infoDb.connect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void destroy(){
        super.destroy();
        try {
            userDb.close();
            infoDb.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            String userName = request.getParameter("userId");
            if(userName==null||userName.equals("")){
                errorMessage("账号不能为空",response);
                return;
            }
            if(userDb.getInstance(userName)!=null){
                //检测账号是否存在
                errorMessage("账号已存在",response);
                return;
            }
            String realName = request.getParameter("userName");
            int gender = new Integer(request.getParameter("sex"));
            String passwd = request.getParameter("password");
            if(passwd==null||passwd.equals("")){
                errorMessage("密码不能为空",response);
                return;
            }
            if(!passwd.equals(request.getParameter("retype_password"))){
                errorMessage("密码与确认不一致",response);
                return;
            }
            if(passwd.length()<6){
                errorMessage("密码太短，只是6位",response);
            }
            if(request.getParameter("grade").equals("")){
                errorMessage("请选择班级",response);
                return;
            }
            int grade = new Integer(request.getParameter("grade"));
            String phoneNum = request.getParameter("M_PhoneNumber");
            String email = request.getParameter("email");
            User newUser = new User();
            newUser.setUserName(userName);
            newUser.setPassWd(passwd);
            UserInfo userInfo = new UserInfo();
            userInfo.setUserName(userName);
            userInfo.setRealName(realName);
            userInfo.setGender(gender);
            userInfo.setGrade(grade);
            userInfo.setPhoneNum(phoneNum);
            userInfo.setEmail(email);
            userDb.put(newUser);
            infoDb.put(userInfo);
            HttpSession session = request.getSession();
            session.setAttribute("loginUser",newUser);
            ServletContext context = getServletContext();
            RequestDispatcher disp = context.getRequestDispatcher("/filelist.htm");
            disp.forward(request,response);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    private void errorMessage(String what, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=gb2312");
        PrintWriter writer = new PrintWriter(response.getOutputStream());
        writer.println("<font color=\"red\">"+what+"</font>");
        writer.close();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
