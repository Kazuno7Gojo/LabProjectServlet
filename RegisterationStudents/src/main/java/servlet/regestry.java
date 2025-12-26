package servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/register")
public class regestry extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String year = request.getParameter("year");

        if (year == null || !year.matches("[A-Za-z0-9]+")) {
            response.getWriter().println("Invalid year format");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/studentdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                    "root",
                    "suzuno"
            );

            String sql = "INSERT INTO students(name, email, year) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, year);

            ps.executeUpdate();
            con.close();

            response.sendRedirect("show_all");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println(e.getMessage());
        }
    }
}
