package com.cricademy.util;

import java.io.File;
import java.io.IOException;
import jakarta.servlet.http.Part;

/**
 * Utility class for handling image file uploads.
 * This class provides methods for extracting the file name from a Part
 * object and uploading the image file to a specified directory on the server.
 */
public class ImageUtil {

    /**
     * Extracts the file name from the given Part object based on the
     * "content-disposition" header.
     *
     * @param part the Part object representing the uploaded file.
     * @return the extracted file name. If no filename is found, returns a default
     *         name "download.png".
     */
    public String getImageNameFromPart(Part part) {
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

        // Check if the filename was not found or is empty
        if (imageName == null || imageName.isEmpty()) {
            // Assign a default file name if none was provided
            imageName = "download.png";
        }

        // Return the extracted or default file name
        return imageName;
    }

    /**
     * Uploads the image file from the given Part object to a specified
     * directory on the server.
     *
     * @param part the Part object representing the uploaded image file.
     * @param rootPath the root path of the project (usually comes from the servlet context).
     * @param saveFolder the folder within the rootPath where the image will be saved.
     * @return true if the file was successfully uploaded, false otherwise.
     */
    public boolean uploadImage(Part part, String rootPath, String saveFolder) {
        String savePath = getSavePath(rootPath, saveFolder);
        File fileSaveDir = new File(savePath);

        // Ensure the directory exists
        if (!fileSaveDir.exists()) {
            if (!fileSaveDir.mkdirs()) {
                return false; // Failed to create the directory
            }
        }

        try {
            // Get the image name
            String imageName = getImageNameFromPart(part);
            // Create the file path
            String filePath = savePath + imageName;
            // Write the file to the server
            part.write(filePath);
            return true; // Upload successful
        } catch (IOException e) {
            e.printStackTrace(); // Log the exception
            return false; // Upload failed
        }
    }

    /**
     * Returns the path to save the image based on the provided folder name.
     *
     * @param rootPath the root path of the application.
     * @param saveFolder the folder where images will be saved (e.g., "customer").
     * @return the full save path.
     */
    public String getSavePath(String rootPath, String saveFolder) {
        // Construct the save path by combining the root path and the folder
		return "/Users/arpannepal/eclipse-workspace/Cricademy/src/main/webapp/resources/images/system/"+saveFolder+"/";
    }
}
