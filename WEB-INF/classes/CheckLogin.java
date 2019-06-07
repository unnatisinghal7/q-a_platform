//add feature if incorrect password is written
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class CheckLogin extends HttpServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException , IOException
	{
		res.setContentType("text/html");
		PrintWriter pw=res.getWriter();
		String email, password;
		Connection con =null;
		Statement st=null;
		ResultSet rs=null;
		HttpSession session=req.getSession();
		email=req.getParameter("t1");
		password=req.getParameter("t2");
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
			rs=st.executeQuery("select * from signinform where email='"+email+"' and password='"+password+"'");
			String type="";
			if(rs.next())
			{
				session.setAttribute("email",email);
				type=rs.getString(4);
				session.setAttribute("type",type);
				if(type.equals("user"))
					res.sendRedirect("WelcomeUser");
				else if(type.equals("admin"))
					res.sendRedirect("login.html");
			}
			else
			{
				res.sendRedirect("");
			}
			con.close();
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