package com.cricademy.controller;

import com.cricademy.model.PlayerModel;
import com.cricademy.services.RegisterService;
import com.cricademy.util.PasswordUtil;
import com.cricademy.util.ImageUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;

/**
 * Servlet implementation class Register
 * Handles user registration including form validation, password encryption,
 * profile image upload, and user creation.
 * Supports multipart file upload with max 5MB file size.
 * 
 * Author: Arpan Nepal
 * LMUID: 23048647
 */
@WebServlet("/register")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1MB threshold before writing to disk
    maxFileSize = 5 * 1024 * 1024,    // Max file size 5MB
    maxRequestSize = 10 * 1024 * 1024 // Max request size 10MB
)
public class Register extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final RegisterService registerService = new RegisterService();
    private final ImageUtil imageUtil = new ImageUtil();

    public Register() {
        super();
    }

    /**
     * Handles GET requests, forwards to registration JSP form.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for form submission.
     * Validates input fields, uploads profile image, encrypts password,
     * and registers new user.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set request encoding to UTF-8 for handling special characters
        request.setCharacterEncoding("UTF-8");

        // Retrieve form parameters
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String phone = request.getParameter("phone");
        Part profileImage = request.getPart("profileImage");

        boolean isValid = true; // Flag for overall validation status
        String savedFileName = null; // Will store uploaded image filename

        // Username validation: required, start with letter, length 3-20, alphanumeric and underscores allowed
        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("usernameError", "Username is required.");
            isValid = false;
        } else if (!username.matches("^[a-zA-Z][a-zA-Z0-9_]{2,19}$")) {
            request.setAttribute("usernameError", "Username must start with a letter and be 3-20 characters.");
            isValid = false;
        }

        // Email validation: required and regex format check
        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("emailError", "Email is required.");
            isValid = false;
        } else if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            request.setAttribute("emailError", "Invalid email format.");
            isValid = false;
        }

        // Password validation: required and minimum length 6
        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("passwordError", "Password is required.");
            isValid = false;
        } else if (password.length() < 6) {
            request.setAttribute("passwordError", "Password must be at least 6 characters long.");
            isValid = false;
        }

        // Confirm password validation: required and must match password
        if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
            request.setAttribute("confirmPasswordError", "Confirm password is required.");
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            request.setAttribute("confirmPasswordError", "Passwords do not match.");
            isValid = false;
        }

        // Phone validation: required and must start with '98' and be 10 digits total
        if (phone == null || phone.trim().isEmpty()) {
            request.setAttribute("phoneError", "Phone number is required.");
            isValid = false;
        } else if (!phone.matches("^98[0-9]{8}$")) {
            request.setAttribute("phoneError", "Phone must start with '98' and be 10 digits.");
            isValid = false;
        }

        // Profile image validation: required and allowed file types only
        if (profileImage == null || profileImage.getSize() == 0) {
            request.setAttribute("imageError", "Profile image is required.");
            isValid = false;
        } else {
            String originalFileName = imageUtil.getImageNameFromPart(profileImage);
            if (!isImageTypeAllowed(originalFileName)) {
                request.setAttribute("imageError", "Only JPG, JPEG, or PNG files are allowed.");
                isValid = false;
            } else {
                // Save the image on the server
                savedFileName = originalFileName;
                String rootPath = getServletContext().getRealPath("/");
                String saveFolder = "profile_images"; // Folder to save profile images

                boolean uploadSuccess = imageUtil.uploadImage(profileImage, rootPath, saveFolder);
                if (!uploadSuccess) {
                    request.setAttribute("imageError", "Failed to upload the image. Try again.");
                    isValid = false;
                }
            }
        }

        // If any validation failed, reload form with old data and error messages
        if (!isValid) {
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
            return;
        }

        // Encrypt password using PasswordUtil (username used as key)
        String encryptedPassword = PasswordUtil.encrypt(username, password);
        if (encryptedPassword == null) {
            request.setAttribute("error", "Failed to encrypt password. Try again.");
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
            return;
        }

        // Create player model with all user details
        String role = "player"; // Default role for new users
        String profileImagePath = "/resources/images/system/profile_images/" + savedFileName; // Stored profile image path
        PlayerModel player = new PlayerModel(username, email, encryptedPassword, phone, role, profileImagePath);

        // Add user via register service
        boolean registrationResult = registerService.addUser(player);

        // Redirect to login if success, else reload form with error
        if (registrationResult) {
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            request.setAttribute("error", "Registration failed. Please try again.");
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
        }
    }

    /**
     * Helper method to check if the image file type is allowed.
     * Only jpg, jpeg, and png extensions are accepted.
     * 
     * @param fileName the name of the file
     * @return true if allowed, false otherwise
     */
    private boolean isImageTypeAllowed(String fileName) {
        if (fileName == null) return false;
        String lowerCaseFileName = fileName.toLowerCase();
        return lowerCaseFileName.endsWith(".jpg") || 
               lowerCaseFileName.endsWith(".jpeg") || 
               lowerCaseFileName.endsWith(".png");
    }
}
