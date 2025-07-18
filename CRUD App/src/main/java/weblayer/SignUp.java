package weblayer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
/**
 * Servlet implementation class Validation
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	boolean isExisted = false;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username").trim();		
		String p = request.getParameter("password").trim();
		String password = p.replaceAll("\\s", "");
		String c = request.getParameter("confirm").trim();
		String confirm = c.replaceAll("\\s", "");
		String email = request.getParameter("email").trim();
		String role = request.getParameter("role").trim();
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/credentials", "root",
					"WJ28@krhps");
			//Validation to Check Username is unique or not 
			String checkQuery = "Select count(*) from users where username = ?";
			PreparedStatement checkuserobj = con.prepareStatement(checkQuery);
			checkuserobj.setString(1,username);
			rs = checkuserobj.executeQuery();
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			if(rs.next() && rs.getInt(1)>0) {
				isExisted = true;
				}
			if(isExisted){
				isExisted = false;
				out.println("alert('Username already exists!')");
				out.println("window.location.href = 'SignUp.html';");			
			}else {			
			String query = "insert into users(username, password, Email, confirmpassword, role) values(?,?,?,?,?);";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, username);
			pst.setString(2, password);
			pst.setString(3, confirm);
			pst.setString(4, email);
			pst.setString(5, role);
			int rowsInserted = pst.executeUpdate();
			if(rowsInserted > 0) {
				out.println("alert('Account Created Succefully')");
				out.println("window.location.href='index.html';");
			} else {
				out.println("alert('Account Creation Failed')");
				out.println("window.location.href='SignUp.html'");
			}
			}
			out.println("</script>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
