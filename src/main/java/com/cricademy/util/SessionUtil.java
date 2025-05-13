package com.cricademy.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Utility class for managing HTTP sessions in a web application.
 */
public class SessionUtil {

    /**
     * Sets an attribute in the session.
     */
    public static void setAttribute(HttpServletRequest request, String key, Object value) {
        HttpSession session = request.getSession(true); // Always create a session if none exists
        session.setAttribute(key, value);
    }

    /**
     * Retrieves an attribute from the session.
     */
    public static Object getAttribute(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false); // Don't create a session if it doesn't exist
        if (session != null) {
            return session.getAttribute(key);
        }
        return null;
    }

    /**
     * Removes an attribute from the session.
     */
    public static void removeAttribute(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false); // Don't create a session if it doesn't exist
        if (session != null) {
            session.removeAttribute(key);
        }
    }

    /**
     * Invalidates the session.
     */
    public static void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Don't create a session if it doesn't exist
        if (session != null) {
            session.invalidate();
        }
    }

    /**
     * Checks if the session exists.
     */
    public static boolean sessionExists(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Don't create a session if it doesn't exist
        return session != null;
    }
}