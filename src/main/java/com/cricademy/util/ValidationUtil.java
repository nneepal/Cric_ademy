package com.cricademy.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;
import jakarta.servlet.http.Part;

/**
 * Utility class for common input validations used in the application.
 * 
 * This class provides static methods to validate strings, emails, phone numbers,
 * passwords, file extensions, and age criteria.
 * 
 * Author: Arpan Nepal
 * LMUID: 23048647
 */
public class ValidationUtil {

    /**
     * Checks if the given string is null or empty after trimming.
     * 
     * @param value the string to check
     * @return true if null or empty, false otherwise
     */
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Validates if the string contains only alphabetic letters (a-z, A-Z).
     * 
     * @param value the string to validate
     * @return true if only letters, false otherwise
     */
    public static boolean isAlphabetic(String value) {
        return value != null && value.matches("^[a-zA-Z]+$");
    }

    /**
     * Validates if a string starts with a letter and contains only letters and digits.
     * 
     * @param value the string to validate
     * @return true if starts with a letter and alphanumeric, false otherwise
     */
    public static boolean isAlphanumericStartingWithLetter(String value) {
        return value != null && value.matches("^[a-zA-Z][a-zA-Z0-9]*$");
    }

    /**
     * Validates if the string equals "male" or "female", case-insensitive.
     * 
     * @param value the gender string
     * @return true if valid gender, false otherwise
     */
    public static boolean isValidGender(String value) {
        return value != null && (value.equalsIgnoreCase("male") || value.equalsIgnoreCase("female"));
    }

    /**
     * Validates if the given email string matches a common email pattern.
     * 
     * @param email the email to validate
     * @return true if valid email, false otherwise
     */
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email != null && Pattern.matches(emailRegex, email);
    }

    /**
     * Validates if the phone number is exactly 10 digits and starts with '98'.
     * 
     * @param number the phone number string
     * @return true if valid phone number, false otherwise
     */
    public static boolean isValidPhoneNumber(String number) {
        return number != null && number.matches("^98\\d{8}$");
    }

    /**
     * Validates if the password contains at least 1 uppercase letter, 1 digit,
     * 1 special symbol, and is at least 8 characters long.
     * 
     * @param password the password string
     * @return true if valid password, false otherwise
     */
    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password != null && password.matches(passwordRegex);
    }

    /**
     * Validates if the file extension of the uploaded Part matches allowed image formats.
     * 
     * @param imagePart the uploaded file Part
     * @return true if file extension is jpg, jpeg, png, or gif; false otherwise
     */
    public static boolean isValidImageExtension(Part imagePart) {
        if (imagePart == null || isNullOrEmpty(imagePart.getSubmittedFileName())) {
            return false;
        }
        String fileName = imagePart.getSubmittedFileName().toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".gif");
    }

    /**
     * Checks if two password strings match exactly.
     * 
     * @param password the first password
     * @param retypePassword the second password to compare
     * @return true if both match, false otherwise
     */
    public static boolean doPasswordsMatch(String password, String retypePassword) {
        return password != null && password.equals(retypePassword);
    }

    /**
     * Validates if the provided date of birth corresponds to an age of at least 16 years.
     * 
     * @param dob the date of birth as LocalDate
     * @return true if age is 16 or older, false otherwise
     */
    public static boolean isAgeAtLeast16(LocalDate dob) {
        if (dob == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        return Period.between(dob, today).getYears() >= 16;
    }
}
