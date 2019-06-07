import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class LogOut extends HttpServlet
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
		/*if(email==null)
			res.sendRedirect("login.html"); */
		
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
			pw.println("<script language='javascript'");
			pw.println("var rand=confirm('Do you want to log out')");
			pw.println("if(rand == true)");
			pw.println("{");
			session.invalidate();
			res.sendRedirect("index.html");
			pw.println("}");
			pw.println("</script>");
		}
		/*catch(SQLException err3)
		{
			pw.println(err3);
		}*/
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