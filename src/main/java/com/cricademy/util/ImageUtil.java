package com.cricademy.util;

import java.io.File;
import java.io.IOException;
import jakarta.servlet.http.Part;

/**
 * Utility class for handling image file uploads.
 * Provides methods to extract the filename from a Part object
 * and to upload image files to a specified directory on the server.
 * 
 * Author: Arpan Nepal
 * LMUID: 23048647
 */
public class ImageUtil {

    /**
     * Extracts the filename from the given Part object based on the
     * "content-disposition" header.
     * 
     * This method handles different browser behaviors including IE
     * which may include full path in the filename.
     * 
     * @param part the Part object representing the uploaded file.
     * @return the extracted filename; returns "download.png" if none found.
     */
    public String getImageNameFromPart(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        String imageName = null;

        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                imageName = s.substring(s.indexOf('=') + 1).trim().replace("\"", "");
                // Strip path if any (handles IE full path filename)
                imageName = imageName.substring(imageName.lastIndexOf(File.separator) + 1);
            }
        }

        if (imageName == null || imageName.isEmpty()) {
            imageName = "download.png";
        }

        return imageName;
    }

    /**
     * Uploads the image file from the Part object to a directory on the server.
     * The directory path is constructed from rootPath and saveFolder.
     * If the directory doesn't exist, it attempts to create it.
     * 
     * @param part the Part object containing the uploaded image file.
     * @param rootPath the root directory path of the web application.
     * @param saveFolder the folder name inside the root path to save the image.
     * @return true if the upload succeeds, false otherwise.
     */
    public boolean uploadImage(Part part, String rootPath, String saveFolder) {
        String savePath = getSavePath(rootPath, saveFolder);
        File fileSaveDir = new File(savePath);

        // Ensure the directory exists
        if (!fileSaveDir.exists()) {
            if (!fileSaveDir.mkdirs()) {
                return false; // Failed to create directory
            }
        }

        try {
            // Extract the image filename
            String imageName = getImageNameFromPart(part);
            // Construct the full file path
            String filePath = savePath + imageName;
            // Save the file to disk
            part.write(filePath);
            return true; // Upload succeeded
        } catch (IOException e) {
            e.printStackTrace(); // Log exception details
            return false; // Upload failed
        }
    }

    /**
     * Constructs the full directory path where images should be saved.
     * 
     * @param rootPath the root path of the application (e.g., servlet context real path).
     * @param saveFolder the subfolder inside the images directory (e.g., "customer").
     * @return the full path where the image should be saved.
     */
    public String getSavePath(String rootPath, String saveFolder) {
        // Customize this path as per deployment environment
        return "/Users/arpannepal/eclipse-workspace/Cricademy/src/main/webapp/resources/images/system/" + saveFolder + "/";
    }
}
