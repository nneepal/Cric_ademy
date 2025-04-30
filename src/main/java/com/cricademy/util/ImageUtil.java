package com.cricademy.util;

import java.io.File;
import java.io.IOException;
import jakarta.servlet.http.Part;

public class ImageUtil {

    /**
     * Extracts the file name from the given {@link Part} object based on the
     * "content-disposition" header.
     */
    public String getFileName(Part part) {
        // Retrieve the content-disposition header from the part
        String contentDisp = part.getHeader("content-disposition");

        // Split the header by semicolons to isolate key-value pairs
        String[] items = contentDisp.split(";");

        // Initialize imageName variable to store the extracted file name
        String imageName = null;

        // Iterate through the items to find the filename
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                // Extract the file name from the header value
                imageName = s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }

        // Return the extracted or default file name
        return imageName != null && !imageName.isEmpty() ? imageName : "download.png";
    }

    /**
     * Checks if the given image file type is allowed.
     */
    public boolean isAllowedImageType(String fileName) {
        String lowerCaseFileName = fileName.toLowerCase();
        return lowerCaseFileName.endsWith(".jpg") || lowerCaseFileName.endsWith(".jpeg") || lowerCaseFileName.endsWith(".png");
    }

    /**
     * Uploads the image file from the given {@link Part} object to a specified
     * directory on the server.
     */
    public boolean uploadImage(Part part, String rootPath, String saveFolder) {
        String savePath = getSavePath(saveFolder);
        File fileSaveDir = new File(savePath);

        // Ensure the directory exists
        if (!fileSaveDir.exists() && !fileSaveDir.mkdir()) {
            return false; // Failed to create the directory
        }

        try {
            // Get the image name
            String imageName = getFileName(part);
            // Create the file path
            String filePath = savePath + "/" + imageName;
            // Write the file to the server
            part.write(filePath);
            return true; // Upload successful
        } catch (IOException e) {
            e.printStackTrace(); // Log the exception
            return false; // Upload failed
        }
    }

    public String getSavePath(String saveFolder) {
        return "/Users/arpannepal/eclipse-workspace/Cricademy/src/main/webapp/resources/images/system/" + saveFolder + "/";
    }
}
