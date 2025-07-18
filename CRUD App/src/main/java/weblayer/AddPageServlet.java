package weblayer;

import java.io.IOException;
import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.PrintWriter;

/**
 * Servlet implementation class AddPageServlet
 */
@WebServlet("/Add")
public class AddPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	boolean isExisted = false;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idIsNumber = request.getParameter("employeeId");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<script type=\"text/javascript\">");
		int employeeId = 0;
		if (idIsNumber != null && !idIsNumber.isEmpty()) {
			try {
				employeeId = Integer.parseInt(idIsNumber);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				out.println("alert('Enter Valid Employee ID!')");
				out.println("location='Add.html';");	
				out.println("</script>");	
			}
		}
		String employeeName = request.getParameter("employeeName");
		String employeeMail = request.getParameter("employeeMail");
		String employeePhno = request.getParameter("employeePhno");
		String employeeAddress = request.getParameter("employeeAddress");
		String employeeRole = request.getParameter("employeeRole");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/credentials", "root",
					"WJ28@krhps");
			String checkQuery = "select count(*) from employeeinfo where EmpId=?";
			PreparedStatement checkUniqueId = con.prepareStatement(checkQuery);
			checkUniqueId.setInt(1, employeeId);
			ResultSet rs = checkUniqueId.executeQuery();

			if (rs.next() && rs.getInt(1) > 0) {
				isExisted = true;
			}
			if (isExisted) {
				isExisted = false;				
				out.println("alert('Employee ID already exists!')");
	     		out.println("location='Add.html';");
	     		out.println("</script>");
				return;
			}
			String query = "Insert Into employeeinfo(EmpId,fname,email,phone,address,emprole) values(?,?,?,?,?,?)";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, employeeId);
			pst.setString(2, employeeName);
			pst.setString(3, employeeMail);
			pst.setString(4, employeePhno);
			pst.setString(5, employeeAddress);
			pst.setString(6, employeeRole);
			pst.executeUpdate();
			out.println("alert('Record Created')");
			out.println("location='Add.html';"); 
			out.println("</script>");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
