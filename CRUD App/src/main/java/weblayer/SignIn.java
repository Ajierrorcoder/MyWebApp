package weblayer;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class SignIn
 */
@WebServlet("/SignIn")
public class SignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();
		String OriginalPassword = password.replaceAll("\\s", "");
		ResultSet rs = null;
		boolean allowAccess = false;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/credentials", "root",
					"WJ28@krhps");
			String query = ("select count(*) from users where username = ? and password = ?; ");
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, username);
			pst.setString(2, OriginalPassword);
			rs = pst.executeQuery();//we get output from sql, so our resultset gets updated
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			
			if (rs.next() && rs.getInt(1)>0) {
				allowAccess = true;
			}if(allowAccess) {
				out.println("window.location.href='HomePage.html';");
			} else {
				out.println("alert('Invalid username or password!')");
				out.println("window.location.href = 'index.html';");
			}
			out.println("</script>");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

