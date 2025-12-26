package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet("/show_all")
public class showall extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/studentdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                "root",
                "suzuno")) {

            var st = con.createStatement();
            var rs = st.executeQuery("SELECT * FROM students");

            var out = response.getWriter();
            out.println("<h2 style='text-align:center;'>Registered Students</h2>");
            out.println("""
<html>
<head>
<style>
    body {
        background-color: white;
        font-family: Arial, sans-serif;
    }

table {
    margin: auto;
    border-collapse: collapse;
    width: 70%;
}
th, td {
    padding: 10px;
    text-align: left;
}
</style>
</head>
<body>

<table>
<tr>
    <th>Name</th>
    <th>Email</th>
    <th>Year</th>
</tr>
""");

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("email") + "</td>");
                out.println("<td>" + rs.getString("year") + "</td>");
                out.println("</tr>");
            }

            out.println("</table></body></html>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
