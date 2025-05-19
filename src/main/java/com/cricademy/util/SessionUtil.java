package com.cricademy.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Utility class for managing HTTP sessions in a web application.
 * 
 * Provides helper methods to set, get, remove session attributes,
 * invalidate sessions, and check session existence.
 * 
 * Author: Arpan Nepal
 * LMUID: 23048647
 */
public class SessionUtil {

    /**
     * Sets an attribute in the session associated with the request.
     * If no session exists, creates a new one.
     * 
     * @param request the HttpServletRequest
     * @param key the attribute name
     * @param value the attribute value
     */
    public static void setAttribute(HttpServletRequest request, String key, Object value) {
        HttpSession session = request.getSession(true); // Create session if none exists
        session.setAttribute(key, value);
    }

    /**
     * Retrieves an attribute from the session associated with the request.
     * Returns null if no session exists or attribute not found.
     * 
     * @param request the HttpServletRequest
     * @param key the attribute name
     * @return the attribute value or null if not found
     */
    public static Object getAttribute(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false); // Do not create session if none exists
        if (session != null) {
            return session.getAttribute(key);
        }
        return null;
    }

    /**
     * Removes an attribute from the session associated with the request.
     * Does nothing if no session exists.
     * 
     * @param request the HttpServletRequest
     * @param key the attribute name
     */
    public static void removeAttribute(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false); // Do not create session if none exists
        if (session != null) {
            session.removeAttribute(key);
        }
    }

    /**
     * Invalidates the session associated with the request.
     * Does nothing if no session exists.
     * 
     * @param request the HttpServletRequest
     */
    public static void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Do not create session if none exists
        if (session != null) {
            session.invalidate();
        }
    }

    /**
     * Checks if a session exists for the given request.
     * 
     * @param request the HttpServletRequest
     * @return true if session exists, false otherwise
     */
    public static boolean sessionExists(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Do not create session if none exists
        return session != null;
    }
}
