package weblayer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ReadServlet")
public class ReadPage extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/credentials", "root", "WJ28@krhps");
            String query = "SELECT * FROM employeeinfo";
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            StringBuilder json = new StringBuilder();
            json.append("[");
            
            boolean first = true;
            while(rs.next()) {
            	if(!first) {
            		json.append(",");
            	}
            	json.append("{");
            	json.append("\"EmpId\":").append(rs.getInt("EmpId")).append(",");
            	json.append("\"fname\":\"").append(rs.getString("fname")).append("\",");
            	json.append("\"email\":\"").append(rs.getString("email")).append("\",");
            	json.append("\"phone\":").append(rs.getInt("phone")).append(",");
            	json.append("\"address\":\"").append(rs.getString("address")).append("\",");
            	json.append("\"emprole\":\"").append(rs.getString("emprole")).append("\"");      
            	json.append("}");
            	first = false;
            }
            json.append("]");
            out.println(json.toString());
       
            
//            while (rs.next()) {
//                int readId = rs.getInt("EmpId");
//                String readName = rs.getString("fname");
//                String readEmail = rs.getString("email");
//                int readPhone = rs.getInt("phone");
//                String readAddress = rs.getString("address");
//                String readEmpRole = rs.getString("emprole");
//                EmployeeInfo pojo=new EmployeeInfo(readId, readEmpRole, readAddress, readPhone, readEmail, readName);
//                first = false;
//                out.print(pojo.toString());
//            }
//            json.append("]");
           

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (pst != null) pst.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }
    }
}
