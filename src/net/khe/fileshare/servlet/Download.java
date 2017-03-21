package net.khe.fileshare.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by hyc on 2017/3/21.
 */
@WebServlet(name = "Download",urlPatterns = "/download")
public class Download extends HttpServlet {
    private final static String FileRoot = "d:/sharefile/files";
    private final static int BufferSize = 4096;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("loginUser")==null){
            response.setContentType("text/html;charset=gb2312");
            PrintWriter writer = new PrintWriter(response.getOutputStream());
            writer.println("<font color='red'>请先登录</font>");
            writer.close();
            return;
        }
        String fileId = request.getParameter("fileId");
        String fileName = fileId+".txt";
        File file = new File(FileRoot,fileName);
        openfile(file,response);
    }
    private void openfile(File file,HttpServletResponse res) throws IOException {
        if(file==null||!file.exists()){
            res.setContentType("text/html;charset=gb2312");
            PrintWriter out = new PrintWriter(res.getOutputStream());
            out.println("<font color=\"red\">文件不存在</font>");
            out.close();
            return;
        }
        res.setContentType("application/octet-stream");
        res.setContentLength((int)file.length());
        String encodeName =
                new String(file.getName().getBytes(),"ISO-8859-1");
        res.setHeader("content-disposition","attachment;filename="+encodeName);
        try{
        BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[BufferSize];
            int read = 0;
            BufferedOutputStream out = new BufferedOutputStream(res.getOutputStream());
            while ((read = input.read(buffer))!=-1){
                out.write(buffer,0,read);
            }
            input.close();
            out.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
