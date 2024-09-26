package com.coursemanagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.EventQueue;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;
import java.awt.Color;
import java.awt.Font;
import java.awt.Canvas;

public class CourseManagementApp {
    private JFrame frame;
    private JTextField txtName, txtDuration, txtFee, txtSearch;
    private JTextArea txtDescription;
    private JTable table;
    private CourseDAO courseDAO;

    public CourseManagementApp() {
        courseDAO = new CourseDAO();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Course Management");
        frame.getContentPane().setBackground(new Color(192, 192, 192));
        frame.setBounds(100, 100, 883, 693);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblName = new JLabel("Course Name:");
        lblName.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblName.setBounds(21, 67, 111, 30);
        frame.getContentPane().add(lblName);

        txtName = new JTextField();
        txtName.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtName.setBounds(21, 107, 235, 31);
        frame.getContentPane().add(txtName);

        JLabel lblDescription = new JLabel("Description:");
        lblDescription.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblDescription.setBounds(293, 72, 100, 20);
        frame.getContentPane().add(lblDescription);

        txtDescription = new JTextArea();
        txtDescription.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtDescription.setBounds(293, 107, 235, 31);
        frame.getContentPane().add(txtDescription);

        JLabel lblDuration = new JLabel("Duration:");
        lblDuration.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblDuration.setBounds(21, 148, 100, 20);
        frame.getContentPane().add(lblDuration);

        txtDuration = new JTextField();
        txtDuration.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtDuration.setBounds(21, 178, 235, 38);
        frame.getContentPane().add(txtDuration);

        JLabel lblFee = new JLabel("Fee:");
        lblFee.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblFee.setBounds(293, 148, 100, 20);
        frame.getContentPane().add(lblFee);

        txtFee = new JTextField();
        txtFee.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtFee.setBounds(293, 176, 235, 41);
        frame.getContentPane().add(txtFee);

        JButton btnAdd = new JButton("Add Course");
        btnAdd.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnAdd.setBounds(21, 244, 507, 54);
        frame.getContentPane().add(btnAdd);

        JButton btnUpdate = new JButton("Update Course");
        btnUpdate.setBackground(new Color(0, 255, 64));
        btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnUpdate.setBounds(21, 319, 235, 31);
        frame.getContentPane().add(btnUpdate);

        JButton btnDelete = new JButton("Delete Course");
        btnDelete.setBackground(new Color(242, 32, 37));
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnDelete.setBounds(293, 319, 235, 31);
        frame.getContentPane().add(btnDelete);

        txtSearch = new JTextField();
        txtSearch.setBounds(21, 360, 728, 21);
        frame.getContentPane().add(txtSearch);

        JButton btnSearch = new JButton("Search");
        btnSearch.setBackground(new Color(255, 128, 0));
        btnSearch.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnSearch.setBounds(759, 358, 100, 20);
        frame.getContentPane().add(btnSearch);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(21, 388, 838, 244);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);
        
        JLabel lblNewLabel = new JLabel("Add Your Courses From Here!");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setBounds(21, 21, 361, 22);
        frame.getContentPane().add(lblNewLabel);
        
        Canvas canvas = new Canvas();
        canvas.setBackground(new Color(255, 255, 0));
        canvas.setBounds(577, 107, 235, 217);
        frame.getContentPane().add(canvas);

        btnAdd.addActionListener(e -> addCourse());
        btnUpdate.addActionListener(e -> updateCourse());
        btnDelete.addActionListener(e -> deleteCourse());
        btnSearch.addActionListener(e -> searchCourses());

        displayCourses();
    }

    private void addCourse() {
        try {
            Course course = new Course(0, txtName.getText(), txtDescription.getText(),
                    Integer.parseInt(txtDuration.getText()), Double.parseDouble(txtFee.getText()));
            courseDAO.addCourse(course);
            displayCourses();
            JOptionPane.showMessageDialog(frame, "Course added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateCourse() {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) table.getValueAt(selectedRow, 0);
                Course course = new Course(id, txtName.getText(), txtDescription.getText(),
                        Integer.parseInt(txtDuration.getText()), Double.parseDouble(txtFee.getText()));
                courseDAO.updateCourse(course);
                displayCourses();
                JOptionPane.showMessageDialog(frame, "Course updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteCourse() {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) table.getValueAt(selectedRow, 0);
                courseDAO.deleteCourse(id);
                displayCourses();
                JOptionPane.showMessageDialog(frame, "Course deleted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchCourses() {
        try {
            String keyword = txtSearch.getText();
            List<Course> courses = courseDAO.searchCourses(keyword);
            updateTable(courses);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayCourses() {
        try {
            List<Course> courses = courseDAO.getAllCourses();
            updateTable(courses);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateTable(List<Course> courses) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Name", "Description", "Duration", "Fee"}, 0);
        for (Course course : courses) {
            model.addRow(new Object[]{course.getId(), course.getName(), course.getDescription(), course.getDuration(), course.getFee()});
        }
        table.setModel(model);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CourseManagementApp window = new CourseManagementApp();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
