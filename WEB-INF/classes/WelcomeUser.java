import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class WelcomeUser extends HttpServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException , IOException
	{
		res.setContentType("text/html");
		PrintWriter pw=res.getWriter();
	
		Connection con =null;
		Statement st=null,st1=null;
		ResultSet rs=null,rs1=null, rs2=null;
		HttpSession session=req.getSession();
		String email=(String)session.getAttribute("email");
		
		
pw.println("<html>");
pw.println("<head>");
pw.println("<title>welcome page</title>");
pw.println("<link rel='stylesheet' type='text/css' href='welcomestyle.css'/>");
pw.println("</head>");
pw.println("<body>");

pw.println("<header>");
    pw.println("<div class='_title'><a href=''><h1>MyPage</h1></a></div>");
    pw.println("<div class='searchbar'>");
        pw.println("<input type='text' name='search' placeholder='Search or ask a question or article'/><button class='searchbarbutton' type='submit'></button>");
    pw.println("</div>");
    pw.println("<nav>");
        pw.println("<ul class='headeroptions'>");
            pw.println("<li><a href=''>Home</a></li>");
            pw.println("<li><a href=''>Notifications</a></li>");
            pw.println("<li class='profiledropdown'><a href='#'> Profile</a>");
                pw.println("<ul>");
                    pw.println("<li><a href=''>View profile</a></li>");
                    pw.println("<li><a href=''>Account settings</a></li>");
                    pw.println("<li><a href='LogOut'>Log out</a></li>");
                pw.println("</ul>");
            pw.println("</li>");
        pw.println("</ul>");
    pw.println("</nav>");
pw.println("</header>");

pw.println("<div class='container'>");
    pw.println("<div class='leftsec'>Welcome to my page and check out all the features of this site");
	pw.println("<div class='profilephoto'>");
            pw.println("<img src='' height='100px' width='75px' alt='no profile photo selected'><br>");
            pw.println("<div class='photooptions'>");
                pw.println("<ul>");
                    pw.println("<li>View photo</li>");
                    pw.println("<li>Change photo</li>");
                    pw.println("<li>Delete photo</li>");
                pw.println("</ul>");
            pw.println("</div>");
        pw.println("</div><br>");
        
    
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
			rs=st.executeQuery("select * from signinform where email='"+email+"'");
			String uname="";
			if(rs.next())
			{
				uname=rs.getString(1);
				pw.println("Email:  "+email+"<br/>");
				pw.println("Name:  "+uname+"<br/>");
			}
			pw.println("<form method='get' action='UserProfile?uname="+email+"'>");
			pw.println("<input type='submit' value='View Profile' name='profile'/>");
			pw.println("</form>");
			/*pw.println("<a href='UserProfile'>"+uname+"</a>");*/
			pw.println("</div>");
			
			pw.println("<div class='middlesec'>This is user's content and it will work only when user post something");
			pw.println("<div class='askquestion' >"); //yes
			pw.println("<form method='get' action='AskQuestion'>"); //yes
            pw.println("<textarea placeholder='Whats on your mind?' name='q1'></textarea><br/>");
            pw.println("<input type='list' list='quesoptions' name='o1' placeholder='Enter related topic'>");
                pw.println("<datalist id='quesoptions'>");
                    pw.println("<option value='CSS'></option>");
                    pw.println("<option value='HTML'></option>");
                    pw.println("<option value='javascript'></option>");
					pw.println("<option value='JAVA'></option>");
					pw.println("<option value='jQuery'></option>");
					pw.println("<option value='SQL'></option>");
					pw.println("<option value='C++'></option>");
					pw.println("<option value='haskell'></option>");
					pw.println("<option value='bootstrap'></option>");
					pw.println("<option value='wordpress'></option>");
					pw.println("<option value='C'></option>");
                pw.println("</datalist>");
            pw.println("<input type='submit' value='Post'/>");
			pw.println("</form>");
        pw.println("</div>");
		
		pw.println("<div class='allquestions'>");
		rs1=st.executeQuery("select * from askquestion order by dateasked and timeasked");
				String ques="",dt="",topic="",time="";
				int votes=0,views=0,nans=0,qid;
				while(rs1.next())
				{	
					qid=rs1.getInt(1);
					ques=rs1.getString(2);
					topic=rs1.getString(3);
					uname=rs1.getString(4);
					dt=rs1.getString(5);
					time=rs1.getString(6);
					st1=con.createStatement();
					rs2=st1.executeQuery("select * from questioncounters where ques_id='"+qid+"'");
					if(rs2.next())
					{
						votes=rs2.getInt(2);
						views=rs2.getInt(3);
						nans=rs2.getInt(4);
					}
				
        pw.println("<div class='contentbox'>");
            pw.println("<div class='thequestion'>");
                pw.println("<a href='AnswersPage?qid="+qid+"'>"+ques+"</a>");
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
            pw.println("<div class='peopleactivity'>");
                pw.println("<a href='' ><div class='vav'>votes </div><span>"+votes+"</span></a>");
                pw.println("<a href='' ><div class='vav'>answers </div><span>"+nans+"</span></a>");
                pw.println("<a href='' ><div class='vav'>views </div><span>"+views+"</span></a>");
            pw.println("</div>");
        pw.println("</div>");
				}
		pw.println("</div>");
	pw.println("</div>");
				
				
    pw.println("<div class='rightsec'>this is ad viewing area</div>");
pw.println("</div>");
pw.println("<footer>");
    pw.println("<ul>");
        pw.println("<li><a href=''>Privacy</a></li>");
        pw.println("<li><a href=''>terms</a></li>");
        pw.println("<li><a href=''>advertising</a></li>");
        pw.println("<li><a href=''>about us</a></li>");
        pw.println("<li><a href=''>feedback</a></li>");
    pw.println("</ul>");
pw.println("</footer>");
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