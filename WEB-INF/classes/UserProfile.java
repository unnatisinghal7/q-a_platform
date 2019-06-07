import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class UserProfile extends HttpServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException , IOException
	{
		res.setContentType("text/html");
		PrintWriter pw=res.getWriter();
	
		Connection con =null;
		Statement st=null,st3=null;
		ResultSet rs1=null,rs2=null,rs3=null,rs4=null;
		HttpSession session=req.getSession();
		String email=(String)session.getAttribute("email");
		if(email==null)
			res.sendRedirect("login.html");
		
		
		String uname=req.getParameter("uname");
		//pw.println(uname);
		
pw.println("<html>");
pw.println("<head>");
pw.println("<title>Profile page</title>");
pw.println("<link rel='stylesheet' type='text/css' href='userprofilestyle.css'/>");

pw.println("</head>");
pw.println("<body>");
pw.println("<iframe class='_header' src='header.html' width='100%' height='85px' seamless>");
pw.println("</iframe>");

pw.println("<div class='container'>");
    pw.println("<div class='userinfo'>");
        pw.println("<div class='profilephoto'>");
            pw.println("<img src='' alt='No image selected'/>");
			pw.println("<div class='photooptions'>");
                pw.println("<ul>");
                    pw.println("<li>View photo</li>");
                    pw.println("<li>Change photo</li>");
                    pw.println("<li>Delete photo</li>");
                pw.println("</ul>");
            pw.println("</div>");
        pw.println("</div>");
        pw.println("<div class='userdetail'>");
            pw.println("<h2>Username</h2><br/>");
            pw.println("<span><input type='text' placeholder='About me'></span>");
        pw.println("</div>");
    pw.println("</div>");
    pw.println("<div class='activity'>");
        pw.println("<div class='answersgiven'>");
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
			rs4=st.executeQuery("select count(*) from ques_answers where email='"+uname+"'");
			rs4.next();
			int reqnum=rs4.getInt(1);
			
            pw.println("<div class='subheader'>");
                pw.println("<h3>Answers </h3><span>("+reqnum+")</span>");
            pw.println("</div>");
			
		
			rs1=st.executeQuery("select * from ques_answers where email='"+uname+"'");
			String ques="",dt="",time="",ans="";
			int qid,aid,vote;
			if(!rs1.next())
			{
				pw.println("No answers written yet.");
			}
			else{
			do
			{
				qid=rs1.getInt(1);
				aid=rs1.getInt(2);
				ans=rs1.getString(3);
				uname=rs1.getString(4);
				vote=rs1.getInt(5);
				dt=rs1.getString(6);
				time=rs1.getString(7);
				
				st3=con.createStatement();
				rs3=st3.executeQuery("select mainquestion from askquestion where ques_id="+qid);
				rs3.next();
				ques=rs3.getString(1);
			
            pw.println("<div class='subcontent'>");
                pw.println("<div class='thequestion'>");
                    pw.println("<a href='AnswersPage?qid="+qid+"'>"+ques+"</a>");
                pw.println("</div>");
				pw.println("<div class='anstoques'>");
                    pw.println(""+ans+"");
                pw.println("</div>");
                pw.println("<div class='started'>");
                    pw.println("<a href='' class='timetaken'>"+dt+" at "+time+"</a>");
                    pw.println("<a href='UserProfile?uname="+uname+"' class='userprofile'>"+uname+"</a>");
                pw.println("</div>");
                //pw.println("<div class='theanswer'></div>");
            pw.println("</div>");
			}while(rs1.next());
			}
        pw.println("</div>");
			
			
			ResultSet rs5=st.executeQuery("select count(*) from askquestion where email='"+uname+"'");
			rs5.next();
			int reqnumq=rs5.getInt(1);
			
			
        pw.println("<div class='questionasked'>");
            pw.println("<div class='subheader'>");
                pw.println("<h3>Questions </h3><span>("+reqnumq+")</span>");
            pw.println("</div>");
			
			rs2=st.executeQuery("select * from askquestion where email='"+uname+"'");
			String topic="";
			if(!rs2.next())
			{
				pw.println("No questions asked yet.");
			}
			else{
			do
			{
				qid=rs2.getInt(1);
				ques=rs2.getString(2);
				topic=rs2.getString(3);
				uname=rs2.getString(4);
				dt=rs2.getString(5);
				time=rs2.getString(6);
				
            pw.println("<div class='subcontent'>");
                pw.println("<div class='thequestion'>");
                    pw.println("<a href='AnswersPage?qid="+qid+"'>"+ques+"</a>");
                pw.println("</div>");
                pw.println("<div class='started'>");
                    pw.println("<a href='' class='timetaken'>"+dt+" at "+time+"</a>");
                    pw.println("<a href='UserProfile?uname="+uname+"' class='userprofile'>"+uname+"</a>");
                pw.println("</div>");
            pw.println("</div>");
			}while(rs2.next());
			}
        pw.println("</div>");
    pw.println("</div>");
pw.println("</div>");
pw.println("<iframe class='_footer' src='footer.html' width='100%' height='75px'>");
pw.println("</iframe>");

pw.println("</body>");
pw.println("</html>");
		
		
			
			
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