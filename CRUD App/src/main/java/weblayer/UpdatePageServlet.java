package weblayer;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdatePageServlet
 */
@WebServlet("/UpdatePageServlet")
public class UpdatePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String strEmpId = request.getParameter("employeeId");
		int employeeId = 0;
		boolean isExisted = false;
		if (strEmpId != null && !strEmpId.isEmpty()) {
			try {
				employeeId = Integer.parseInt(strEmpId);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}		
		}	
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/credentials","root","WJ28@krhps");
			String findIdQuery = "Select count(*) from employeeinfo where EmpId = ?";
			PreparedStatement pst = conn.prepareStatement(findIdQuery);
			pst.setInt(1, employeeId);
			ResultSet rs = pst.executeQuery();
			if(rs.next() && rs.getInt(1)>0) {
				isExisted = true;
			}
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			if(isExisted) {
				isExisted = false;
				String employeeName = request.getParameter("employeeName");
				String employeeMail = request.getParameter("employeeMail");
				String employeePhno = request.getParameter("employeePhno");
				String employeeAddress = request.getParameter("employeeAddress");
				String employeeRole = request.getParameter("employeeRole");
				
				String updateEmpTable = "UPDATE employeeinfo SET  fname = ?, email = ?, phone = ?, address = ?, emprole = ? where EmpId = ?;";
				PreparedStatement pstmt = conn.prepareStatement(updateEmpTable);
				pstmt.setString(1, employeeName);
				pstmt.setString(2, employeeMail);
				pstmt.setString(3, employeePhno);
				pstmt.setString(4, employeeAddress);
				pstmt.setString(5, employeeRole);
				pstmt.setInt(6, employeeId);
				int rowsInserted = pstmt.executeUpdate();
					
				if(rowsInserted > 0) {
					
					out.println("alert('Record Updated!!')");					
				}else {
					out.println("alert('Unable to Update the Record')");
				}
				out.println("window.location.href = 'UpdatePage.html';");
				out.println("</script>");				
			}
			else {		
				out.println("alert('ID does not exist')");
				out.println("window.location.href = 'UpdatePage.html';");
				out.println("</script>");				
			}	
		} catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
	}
}
