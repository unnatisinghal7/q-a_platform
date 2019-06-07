import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class SearchOption extends HttpServlet
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
		/*String ques_id=req.getParameter("qid");
		int qid=Integer.parseInt(ques_id);*/
		
		String search=req.getParameter("s1");	//string to be searched
		
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
			rs1=st.executeQuery("select * from askquestion where mainquestion like %"+search+"%");
			if(rs1.next())
			{
				String sdata=rs1.getString(1);
				//if(search.equals(sdata.substring()))
				
			}
			else
				pw.println("Data not found");
			con.close();
			//res.sendRedirect("AnswersPage?qid="+qid);
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