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
import java.util.UUID;

@WebServlet("/register")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1MB
    maxFileSize = 5 * 1024 * 1024,    // 5MB
    maxRequestSize = 10 * 1024 * 1024 // 10MB
)
public class Register extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final RegisterService registerService = new RegisterService();
    private final ImageUtil imageUtil = new ImageUtil();

    public Register() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String phone = request.getParameter("phone");
        Part profileImage = request.getPart("profileImage");

        boolean isValid = true;
        String savedFileName = null;

        // Username validation
        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("usernameError", "Username is required.");
            isValid = false;
        } else if (!username.matches("^[a-zA-Z][a-zA-Z0-9_]{2,19}$")) {
            request.setAttribute("usernameError", "Username must start with a letter and be 3-20 characters.");
            isValid = false;
        }

        // Email validation
        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("emailError", "Email is required.");
            isValid = false;
        } else if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            request.setAttribute("emailError", "Invalid email format.");
            isValid = false;
        }

        // Password validation
        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("passwordError", "Password is required.");
            isValid = false;
        } else if (password.length() < 6) {
            request.setAttribute("passwordError", "Password must be at least 6 characters long.");
            isValid = false;
        }

        // Confirm Password validation
        if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
            request.setAttribute("confirmPasswordError", "Confirm password is required.");
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            request.setAttribute("confirmPasswordError", "Passwords do not match.");
            isValid = false;
        }

        // Phone validation
        if (phone == null || phone.trim().isEmpty()) {
            request.setAttribute("phoneError", "Phone number is required.");
            isValid = false;
        } else if (!phone.matches("^98[0-9]{8}$")) {
            request.setAttribute("phoneError", "Phone must start with '98' and be 10 digits.");
            isValid = false;
        }

        // Image validation
        if (profileImage == null || profileImage.getSize() == 0) {
            request.setAttribute("imageError", "Profile image is required.");
            isValid = false;
        } else {
            String originalFileName = imageUtil.getImageNameFromPart(profileImage);  // Changed to getImageNameFromPart
            if (!isImageTypeAllowed(originalFileName)) {  // Using local method for type checking
                request.setAttribute("imageError", "Only JPG, JPEG, or PNG files are allowed.");
                isValid = false;
            } else {
                // Generate unique filename
                savedFileName =originalFileName;
                // Get root path and save folder
                String rootPath = getServletContext().getRealPath("/");
                String saveFolder = "profile_images"; // or whatever folder name you want to use
                boolean uploadSuccess = imageUtil.uploadImage(profileImage, rootPath, saveFolder);
                if (!uploadSuccess) {
                    request.setAttribute("imageError", "Failed to upload the image. Try again.");
                    isValid = false;
                }
            }
        }

        // If validation fails, reload form with old data
        if (!isValid) {
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
            return;
        }

        // Encrypt password before saving
        String encryptedPassword = PasswordUtil.encrypt(username, password);
        if (encryptedPassword == null) {
            request.setAttribute("error", "Failed to encrypt password. Try again.");
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
            return;
        }

        // Create PlayerModel with encrypted password
        String role = "player";
        String profileImagePath = "/resources/images/system/profile_images/" + savedFileName; // Updated path to match ImageUtil
        PlayerModel player = new PlayerModel(username, email, encryptedPassword, phone, role, profileImagePath);

        boolean registrationResult = registerService.addUser(player);

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
     * Helper method to check if the image type is allowed
     */
    private boolean isImageTypeAllowed(String fileName) {
        if (fileName == null) return false;
        String lowerCaseFileName = fileName.toLowerCase();
        return lowerCaseFileName.endsWith(".jpg") || 
               lowerCaseFileName.endsWith(".jpeg") || 
               lowerCaseFileName.endsWith(".png");
    }
}