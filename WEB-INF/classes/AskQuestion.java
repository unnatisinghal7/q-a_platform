import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class AskQuestion extends HttpServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException , IOException
	{
		res.setContentType("text/html");
		PrintWriter pw=res.getWriter();
	
		Connection con =null;
		Statement st=null;
		ResultSet rs=null;
		HttpSession session=req.getSession();
		String email=(String)session.getAttribute("email");
		if(email==null)
			res.sendRedirect("login.html");
		
		// int ques_id=100;
		String question=req.getParameter("q1");
		String topic=req.getParameter("o1");
		
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
			st=con.createStatement();
			
			st.executeUpdate("insert into askquestion (mainquestion, relatedtopics, email, dateasked, timeasked) values('"+question+"','"+topic+"','"+email+"', curdate(), curtime())");
			rs=st.executeQuery("select ques_id from askquestion where timeasked=curtime()");
			rs.next();
			int l=rs.getInt(1);
			
			
			st.executeUpdate("insert into questioncounters (ques_id,votes,views,nanswers) values("+l+",0,0,0)");
	
			con.close();
			res.sendRedirect("WelcomeUser");
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