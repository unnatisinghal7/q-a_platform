import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class QuestionDownvotes extends HttpServlet
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
		String ques_id=req.getParameter("qid");
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
			st=con.createStatement();
			rs1=st.executeQuery("select email from askquestion where ques_id="+qid);
			rs1.next();
			String qask=rs1.getString(1);
			if(qask.equals(email))
			{
			}
			else
			{
			rs=st.executeQuery("select votes from questioncounters where ques_id="+qid);
			rs.next();
			int l=rs.getInt(1);
			l=l-1;
			st.executeUpdate("update questioncounters set votes="+l+" where ques_id="+qid);
			}
			
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