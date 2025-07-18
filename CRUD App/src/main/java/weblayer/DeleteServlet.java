package weblayer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.PrintWriter;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String employeeIdStr = request.getParameter("empID");
		int employeeId = 0;
		boolean idExisted = false;
		if (employeeIdStr != null && !employeeIdStr.isEmpty()) {
			try {
				employeeId = Integer.parseInt(employeeIdStr);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/credentials", "root",
					"WJ28@krhps");
			String checkIdQuery = "select count(*) FROM employeeinfo where empId = ?;";
			PreparedStatement pst = conn.prepareStatement(checkIdQuery);
			pst.setInt(1,employeeId);
			ResultSet rs = pst.executeQuery();
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<script type=\'text/javascript'\">");
			if (rs.next() && rs.getInt(1) > 0) {
				idExisted = true;
			}
			if (idExisted) {
				idExisted = false;
				String deleteQuery = "DELETE FROM employeeinfo where empId = ?;";
				PreparedStatement dpst = conn.prepareStatement(deleteQuery);
				dpst.setInt(1, employeeId);
				int rowsInserted = dpst.executeUpdate();
				if (rowsInserted > 0) {
					out.println("alert('Record Deleted!')");
				}
			}else {
				out.println("alert('ID Not Found')");	
			}
			out.println("window.location.href = 'DeletePage.html'");
			out.println("</script>");	
		} catch (SQLException | ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

}
