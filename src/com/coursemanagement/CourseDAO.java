package com.coursemanagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    public void addCourse(Course course) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String query = "INSERT INTO Course(course_name, course_description, course_duration, course_fee) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, course.getName());
        stmt.setString(2, course.getDescription());
        stmt.setInt(3, course.getDuration());
        stmt.setDouble(4, course.getFee());
        stmt.executeUpdate();
        conn.close();
    }

    public List<Course> getAllCourses() throws SQLException {
        Connection conn = DBConnection.getConnection();
        String query = "SELECT * FROM Course";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        List<Course> courseList = new ArrayList<>();
        while (rs.next()) {
            Course course = new Course(rs.getInt("id"), rs.getString("course_name"), rs.getString("course_description"),
                    rs.getInt("course_duration"), rs.getDouble("course_fee"));
            courseList.add(course);
        }
        conn.close();
        return courseList;
    }

    public void updateCourse(Course course) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String query = "UPDATE Course SET course_name = ?, course_description = ?, course_duration = ?, course_fee = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, course.getName());
        stmt.setString(2, course.getDescription());
        stmt.setInt(3, course.getDuration());
        stmt.setDouble(4, course.getFee());
        stmt.setInt(5, course.getId());
        stmt.executeUpdate();
        conn.close();
    }

    public void deleteCourse(int id) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String query = "DELETE FROM Course WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        conn.close();
    }

    public List<Course> searchCourses(String keyword) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String query = "SELECT * FROM Course WHERE course_name LIKE ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, "%" + keyword + "%");
        ResultSet rs = stmt.executeQuery();

        List<Course> courseList = new ArrayList<>();
        while (rs.next()) {
            Course course = new Course(rs.getInt("id"), rs.getString("course_name"), rs.getString("course_description"),
                    rs.getInt("course_duration"), rs.getDouble("course_fee"));
            courseList.add(course);
        }
        conn.close();
        return courseList;
    }
}
