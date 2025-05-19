package com.cricademy.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;

/**
 * Utility class for managing HTTP cookies in the web application.
 * Provides methods to add, retrieve, and delete cookies.
 * 
 * Author: Arpan Nepal
 * LMUID: 23048647
 */
public class CookieUtil {

    /**
     * Adds a cookie with the specified name, value, and max age to the response.
     * The cookie path is set to "/" so that it is accessible throughout the application.
     *
     * @param response HttpServletResponse to add the cookie to
     * @param name     Name of the cookie
     * @param value    Value of the cookie
     * @param maxAge   Maximum age of the cookie in seconds
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/"); // Make cookie accessible across the entire application
        response.addCookie(cookie);
    }

    /**
     * Retrieves a cookie by its name from the HttpServletRequest.
     *
     * @param request HttpServletRequest to search cookies in
     * @param name    Name of the cookie to retrieve
     * @return The Cookie object if found, or null if not present
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> name.equals(cookie.getName()))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    /**
     * Deletes a cookie by setting its max age to 0 and adding it to the response.
     * The cookie path is set to "/" to match the path of the original cookie.
     *
     * @param response HttpServletResponse to add the deletion cookie to
     * @param name     Name of the cookie to delete
     */
    public static void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0); // Deletes the cookie
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
