package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateImage {
    public static void main(String[] args) {
        // Path to the image file
        String filePath = "D://MIC/eic.jpg";

        // Database credentials
        String jdbcURL = "jdbc:mysql://localhost:3306/mi_db";
        String dbUser = "root";
        String dbPassword = "root";

        Connection connection = null;
        FileInputStream inputStream = null;

        try {
            // Establish the database connection
            connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

            // Path to the image file
            File imageFile = new File(filePath);
            inputStream = new FileInputStream(imageFile);

            // Prepare the SQL query to update the image
            String sql = "UPDATE tbl_organization SET logo_image = ? WHERE ID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBinaryStream(1, inputStream, (int) imageFile.length());
            statement.setString(2, "5");  // Adjust this line as needed

            // Execute the update
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("The image was updated successfully.");
            }

        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        } finally {
            // Close the resources
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (IOException | SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
