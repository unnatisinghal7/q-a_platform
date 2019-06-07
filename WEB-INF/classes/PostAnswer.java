import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class PostAnswer extends HttpServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException , IOException
	{
		res.setContentType("text/html");
		PrintWriter pw=res.getWriter();
		Connection con =null;
		Statement st=null;
		ResultSet rs=null,rs1=null;
		HttpSession session=req.getSession();
		String email=(String)session.getAttribute("email");
		if(email==null)
			res.sendRedirect("login.html");
		String answer=req.getParameter("a1");
		String ques_id=req.getParameter("qid");
		pw.println("ndskskdj1");
		int qid=Integer.parseInt(ques_id);
		
		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con= DriverManager.getConnection("jdbc:odbc:xyz");
		}
		catch(ClassNotFoundException err1)
		{
			pw.println(err1);
		}
		catch(SQLException err2)
		{
			pw.println(err2);
		}
		try{
			pw.println("ndskskdj");
			st=con.createStatement();
			st.executeUpdate("insert into ques_answers (ques_id,answer,email,votes,date,time) values("+qid+",'"+answer+"','"+email+"',0,curdate(),curtime())");
			
			
			
			
			pw.println("jhfkjd2");
			rs1=st.executeQuery("select nanswers from questioncounters where ques_id="+qid);
			rs1.next();
			int l=rs1.getInt(1);
			l=l+1;
			st.executeUpdate("update questioncounters set nanswers="+l+" where ques_id="+qid);
			
			con.close();
			res.sendRedirect("AnswersPage?qid="+qid);
		}
		catch(SQLException err3)
		{
			pw.println(err3);
		}
		catch(Exception err4)
		{
			pw.println(err4);
		}
		
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException , IOException
	{
		doGet(req,res);
	}
}