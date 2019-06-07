import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class AnswersPage extends HttpServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException , IOException
	{
		res.setContentType("text/html");
		PrintWriter pw=res.getWriter();
		Connection con =null;
		Statement st=null,st3=null;
		ResultSet rs=null,rs1=null,rs2=null,rs3=null,rs4=null;
		HttpSession session=req.getSession();
		String email=(String)session.getAttribute("email");
		if(email==null)
			res.sendRedirect("login.html");
		
		String ques_id=req.getParameter("qid");
		int qid=Integer.parseInt(ques_id);
		
pw.println("<html>");
pw.println("<head>");
pw.println("<title>Answer's page</title>");
pw.println("<link rel='stylesheet' type='text/css' href='answerstyle.css'/>");
pw.println("</head>");
pw.println("<body>");
pw.println("<iframe class='_header' src='header.html' width='100%' height='85px'>");
pw.println("</iframe>");

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
			rs2=st.executeQuery("select * from askquestion where ques_id='"+qid+"'");
			String ques="",topic="",dt="",time="",uname="";
				int votes=0;
				st3=con.createStatement();
			while(rs2.next())
			{
				qid=rs2.getInt(1);
				ques=rs2.getString(2);
				topic=rs2.getString(3);
				uname=rs2.getString(4);
				dt=rs2.getString(5);
				time=rs2.getString(6);
				
				rs3=st3.executeQuery("select votes from questioncounters where ques_id='"+qid+"'");
				if(rs3.next())
				{
				votes=rs3.getInt(1);
				}
				
pw.println("<div class='container'>");
pw.println("<div class='questioncontainer'>");
    pw.println("<div class='votecount'>");
        pw.println("<a class='voteq' href='QuestionUpvotes?qid="+qid+"'>upvote</a>");
        pw.println("<span>"+votes+"</span>");
        pw.println("<a class='voteq' href='QuestionDownvotes?qid="+qid+"'>downvote</a>");
    pw.println("</div>");
				
			
    pw.println("<div class='questionpart'>");
        pw.println("<div class='thequestion'>");
                pw.println("<a href=''>"+ques+"</a>");
        pw.println("</div>");
        pw.println("<div class='relatedtopics'>");
            pw.println("<ul>");
                pw.println("<li><a href=''>"+topic+"</a></li>");
                //pw.println("<li><a href=''>topic2</a></li>");
            pw.println("</ul>");
        pw.println("</div>");
        pw.println("<div class='started'>");
            pw.println("<a href='' class='timetaken'>"+dt+" at "+time+"</a>");
            pw.println("<a href='UserProfile?uname="+uname+"' class='userprofile'>"+uname+"</a>");
        pw.println("</div>");
    pw.println("</div>");
pw.println("</div>");
			}

			rs4=st.executeQuery("select email from askquestion where ques_id="+qid);
			rs4.next();
			String name=rs4.getString(1);
			if(name.equals(email))
			{}
			else
			{
			rs=st.executeQuery("select views from questioncounters where ques_id="+qid);
			rs.next();
			int l=rs.getInt(1);
			l=l+1;
			st.executeUpdate("update questioncounters set views="+l+" where ques_id="+qid);
			}
			
			rs=st.executeQuery("select nanswers from questioncounters where ques_id="+qid);
			rs.next();
			int l=rs.getInt(1);
			
			pw.println("<div class='subheader'>");
				pw.println("<h2>Answers</h2><span> ("+l+")</span>");
			pw.println("</div>");
			
			//st1=con.createStatement();
			rs1=st.executeQuery("select * from ques_answers where ques_id="+qid+" order by date");
				String ans="";
				int aid;
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
					votes=rs1.getInt(5);
					dt=rs1.getString(6);
					time=rs1.getString(7);
				
				
pw.println("<div class='answercontainer'>");
    pw.println("<div class='theanswer'>");
        pw.println("<div class='votecount'>");
            pw.println("<a class='voteq' href='AnswerVotes?qid="+qid+"&aid="+aid+"&uord=u'>upvote</a>");
            pw.println("<span>"+votes+"</span>");
            pw.println("<a class='voteq' href='AnswerVotes?qid="+qid+"&aid="+aid+"&uord=d'>downvote</a>");
        pw.println("</div>");
		pw.println("<div class='wholeanswer'>");
			pw.println("<div class='answer'>"+ans+"</div>");
			pw.println("<div class='started'>");
				pw.println("<a href='' class='timetaken'>"+dt+" at " +time+"</a>");
				pw.println("<a href='UserProfile?uname="+uname+"' class='userprofile'>"+uname+"</a>");
			pw.println("</div>");
			pw.println("<div class='extrabuttons'>");
				pw.println("<a href='DeleteAnswer?qid="+qid+"&aid="+aid+"'><input type='button' name='deleteans' value='Delete'/></a>");
			pw.println("</div>");
		pw.println("</div>");
    pw.println("</div>");
pw.println("</div>");
				}while(rs1.next());
				}
				
				//pw.println("jhj"+qid);
pw.println("<div class='writeanswers'>");
    pw.println("<form method='get' action='PostAnswer'>");
	pw.println("<input type='hidden' name='qid' value='"+qid+"'>");
        pw.println("<textarea placeholder='Write your answer.' name='a1'></textarea><br>");
        pw.println("<input type='submit' value='Submit' name='submit'/>");
    pw.println("</form>");
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