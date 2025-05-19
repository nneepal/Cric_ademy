package com.cricademy.controller;

import com.cricademy.model.PlayerModel;
import com.cricademy.services.ProfileService;
import com.cricademy.util.CookieUtil;
import com.cricademy.util.ImageUtil;
import com.cricademy.util.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.IOException;

/**
 * Servlet implementation class Profile
 * Handles profile viewing and updating.
 * Supports updating profile details including profile image upload.
 * Uses session to track logged-in user and cookies to get role.
 * 
 * Author: Arpan Nepal
 * LMUID: 23048647
 */
@WebServlet("/profile")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024) // Max 5MB upload size
public class Profile extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ProfileService profileService = new ProfileService();
    private final ImageUtil imageUtil = new ImageUtil();

    public Profile() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String username = (String) session.getAttribute("username");
        PlayerModel user = profileService.getUserDetails(username);

        if (user != null) {
            request.setAttribute("user", user);
        }

        // Retrieve user role from cookie
        Cookie roleCookie = CookieUtil.getCookie(request, "role");
        String role = (roleCookie != null) ? roleCookie.getValue() : null;
        request.setAttribute("role", role);

        request.getRequestDispatcher("WEB-INF/pages/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String oldUsername = (String) session.getAttribute("username");
        String newUsername = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");

        // Encrypt password with new username as key
        String encryptedPassword = PasswordUtil.encrypt(newUsername, password);

        // Handle profile image upload
        Part imagePart = request.getPart("profileImage");
        String profileImage = null;

        if (imagePart != null && imagePart.getSize() > 0) {
            String imageName = imageUtil.getImageNameFromPart(imagePart);
            profileImage = "resources/images/system/profile_images/" + imageName;

            // Upload the image to server path
            String rootPath = getServletContext().getRealPath("/");
            boolean uploadSuccess = imageUtil.uploadImage(imagePart, rootPath, "profile_images");
            if (!uploadSuccess) {
                profileImage = null;
            }
        }

        // Use existing profile image if no new image uploaded
        if (profileImage == null || profileImage.isEmpty()) {
            PlayerModel existingUser = profileService.getUserDetails(oldUsername);
            if (existingUser != null) {
                profileImage = existingUser.getProfileImage();
            }
        }

        // Update profile in database
        boolean updated = profileService.updateUserProfile(oldUsername, newUsername, email,
                encryptedPassword, phone, profileImage);

        if (updated) {
            session.setAttribute("username", newUsername);
            request.setAttribute("successMessage", "Profile updated successfully.");
        } else {
            request.setAttribute("errorMessage", "Failed to update profile. Please try again.");
        }

        doGet(request, response);
    }
}
