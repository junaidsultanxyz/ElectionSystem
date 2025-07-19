package com.junaid.client.util;

// @author junaidxyz

import javax.swing.*;
import java.awt.*;

/**
 * Utility class for displaying various types of popup dialogs using JOptionPane.
 * Provides static methods for common dialog operations.
 */
public class Popup {
    
    // Default parent component (null means centered on screen)
    private static Component defaultParent = null;
    
    /**
     * Set the default parent component for all popups
     * @param parent the parent component (null for screen center)
     */
    public static void setDefaultParent(Component parent) {
        defaultParent = parent;
    }
    
    // ==================== MESSAGE DIALOGS ====================
    
    /**
     * Shows an information message
     * @param message the message to display
     */
    public static void showInfo(String message) {
        showInfo(message, "Information");
    }
    
    /**
     * Shows an information message with custom title
     * @param message the message to display
     * @param title the dialog title
     */
    public static void showInfo(String message, String title) {
        JOptionPane.showMessageDialog(defaultParent, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Shows a warning message
     * @param message the message to display
     */
    public static void showWarning(String message) {
        showWarning(message, "Warning");
    }
    
    /**
     * Shows a warning message with custom title
     * @param message the message to display
     * @param title the dialog title
     */
    public static void showWarning(String message, String title) {
        JOptionPane.showMessageDialog(defaultParent, message, title, JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Shows an error message
     * @param message the message to display
     */
    public static void showError(String message) {
        showError(message, "Error");
    }
    
    /**
     * Shows an error message with custom title
     * @param message the message to display
     * @param title the dialog title
     */
    public static void showError(String message, String title) {
        JOptionPane.showMessageDialog(defaultParent, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Shows a plain message (no icon)
     * @param message the message to display
     */
    public static void showMessage(String message) {
        showMessage(message, "Message");
    }
    
    /**
     * Shows a plain message with custom title
     * @param message the message to display
     * @param title the dialog title
     */
    public static void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(defaultParent, message, title, JOptionPane.PLAIN_MESSAGE);
    }
    
    // ==================== CONFIRMATION DIALOGS ====================
    
    /**
     * Shows a Yes/No confirmation dialog
     * @param message the message to display
     * @return true if Yes is clicked, false otherwise
     */
    public static boolean showYesNo(String message) {
        return showYesNo(message, "Confirm");
    }
    
    /**
     * Shows a Yes/No confirmation dialog with custom title
     * @param message the message to display
     * @param title the dialog title
     * @return true if Yes is clicked, false otherwise
     */
    public static boolean showYesNo(String message, String title) {
        int result = JOptionPane.showConfirmDialog(defaultParent, message, title, 
                                                  JOptionPane.YES_NO_OPTION, 
                                                  JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }
    
    /**
     * Shows a Yes/No/Cancel confirmation dialog
     * @param message the message to display
     * @return 0 for Yes, 1 for No, 2 for Cancel, -1 for closed dialog
     */
    public static int showYesNoCancel(String message) {
        return showYesNoCancel(message, "Confirm");
    }
    
    /**
     * Shows a Yes/No/Cancel confirmation dialog with custom title
     * @param message the message to display
     * @param title the dialog title
     * @return 0 for Yes, 1 for No, 2 for Cancel, -1 for closed dialog
     */
    public static int showYesNoCancel(String message, String title) {
        return JOptionPane.showConfirmDialog(defaultParent, message, title, 
                                           JOptionPane.YES_NO_CANCEL_OPTION, 
                                           JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Shows an OK/Cancel confirmation dialog
     * @param message the message to display
     * @return true if OK is clicked, false otherwise
     */
    public static boolean showOkCancel(String message) {
        return showOkCancel(message, "Confirm");
    }
    
    /**
     * Shows an OK/Cancel confirmation dialog with custom title
     * @param message the message to display
     * @param title the dialog title
     * @return true if OK is clicked, false otherwise
     */
    public static boolean showOkCancel(String message, String title) {
        int result = JOptionPane.showConfirmDialog(defaultParent, message, title, 
                                                  JOptionPane.OK_CANCEL_OPTION, 
                                                  JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.OK_OPTION;
    }
    
    // ==================== INPUT DIALOGS ====================
    
    /**
     * Shows an input dialog for text input
     * @param message the message to display
     * @return the entered text, or null if cancelled
     */
    public static String showInput(String message) {
        return showInput(message, "Input");
    }
    
    /**
     * Shows an input dialog with custom title
     * @param message the message to display
     * @param title the dialog title
     * @return the entered text, or null if cancelled
     */
    public static String showInput(String message, String title) {
        return JOptionPane.showInputDialog(defaultParent, message, title, JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Shows an input dialog with default value
     * @param message the message to display
     * @param defaultValue the default input value
     * @return the entered text, or null if cancelled
     */
//    public static String showInput(String message, String defaultValue) {
//        return showInput(message, "Input", defaultValue);
//    }
    
    /**
     * Shows an input dialog with custom title and default value
     * @param message the message to display
     * @param title the dialog title
     * @param defaultValue the default input value
     * @return the entered text, or null if cancelled
     */
    public static String showInput(String message, String title, String defaultValue) {
        return (String) JOptionPane.showInputDialog(defaultParent, message, title, 
                                                   JOptionPane.QUESTION_MESSAGE, null, null, defaultValue);
    }
    
    /**
     * Shows a password input dialog
     * @param message the message to display
     * @return the entered password as char array, or null if cancelled
     */
    public static char[] showPasswordInput(String message) {
        return showPasswordInput(message, "Password");
    }
    
    /**
     * Shows a password input dialog with custom title
     * @param message the message to display
     * @param title the dialog title
     * @return the entered password as char array, or null if cancelled
     */
    public static char[] showPasswordInput(String message, String title) {
        JPasswordField passwordField = new JPasswordField();
        int result = JOptionPane.showConfirmDialog(defaultParent, 
                                                  new Object[]{message, passwordField}, 
                                                  title, 
                                                  JOptionPane.OK_CANCEL_OPTION, 
                                                  JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            return passwordField.getPassword();
        }
        return null;
    }
    
    // ==================== SELECTION DIALOGS ====================
    
    /**
     * Shows a selection dialog with dropdown options
     * @param message the message to display
     * @param options array of options to choose from
     * @return the selected option, or null if cancelled
     */
    public static String showSelection(String message, String[] options) {
        return showSelection(message, "Select", options);
    }
    
    /**
     * Shows a selection dialog with custom title and dropdown options
     * @param message the message to display
     * @param title the dialog title
     * @param options array of options to choose from
     * @return the selected option, or null if cancelled
     */
    public static String showSelection(String message, String title, String[] options) {
        return (String) JOptionPane.showInputDialog(defaultParent, message, title, 
                                                   JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }
    
    /**
     * Shows a selection dialog with generic objects
     * @param message the message to display
     * @param options array of objects to choose from
     * @return the selected object, or null if cancelled
     */
    public static Object showSelection(String message, Object[] options) {
        return showSelection(message, "Select", options);
    }
    
    /**
     * Shows a selection dialog with custom title and generic objects
     * @param message the message to display
     * @param title the dialog title
     * @param options array of objects to choose from
     * @return the selected object, or null if cancelled
     */
    public static Object showSelection(String message, String title, Object[] options) {
        return JOptionPane.showInputDialog(defaultParent, message, title, 
                                         JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }
    
    // ==================== CUSTOM DIALOGS ====================
    
    /**
     * Shows a custom dialog with specified buttons
     * @param message the message to display
     * @param title the dialog title
     * @param buttons array of button labels
     * @return the index of the selected button, or -1 if closed
     */
    public static int showCustomDialog(String message, String title, String[] buttons) {
        return JOptionPane.showOptionDialog(defaultParent, message, title, 
                                          JOptionPane.DEFAULT_OPTION, 
                                          JOptionPane.QUESTION_MESSAGE, 
                                          null, buttons, buttons[0]);
    }
    
    /**
     * Shows a custom dialog with icon and specified buttons
     * @param message the message to display
     * @param title the dialog title
     * @param messageType the message type (INFO, WARNING, ERROR, etc.)
     * @param buttons array of button labels
     * @return the index of the selected button, or -1 if closed
     */
    public static int showCustomDialog(String message, String title, int messageType, String[] buttons) {
        return JOptionPane.showOptionDialog(defaultParent, message, title, 
                                          JOptionPane.DEFAULT_OPTION, 
                                          messageType, 
                                          null, buttons, buttons[0]);
    }
    
    // ==================== UTILITY METHODS ====================
    
    /**
     * Shows a multiline message dialog
     * @param lines array of message lines
     * @param title the dialog title
     */
    public static void showMultilineMessage(String[] lines, String title) {
        JTextArea textArea = new JTextArea(String.join("\n", lines));
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setFont(UIManager.getFont("Label.font"));
        JOptionPane.showMessageDialog(defaultParent, textArea, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Shows a scrollable message dialog for long text
     * @param message the long message to display
     * @param title the dialog title
     */
    public static void showScrollableMessage(String message, String title) {
        JTextArea textArea = new JTextArea(message, 15, 50);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(UIManager.getFont("Label.font"));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        JOptionPane.showMessageDialog(defaultParent, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    // ==================== CONSTANTS FOR CONVENIENCE ====================
    
    public static final int YES = JOptionPane.YES_OPTION;
    public static final int NO = JOptionPane.NO_OPTION;
    public static final int CANCEL = JOptionPane.CANCEL_OPTION;
    public static final int OK = JOptionPane.OK_OPTION;
    public static final int CLOSED = JOptionPane.CLOSED_OPTION;
    
    public static final int INFO = JOptionPane.INFORMATION_MESSAGE;
    public static final int WARNING = JOptionPane.WARNING_MESSAGE;
    public static final int ERROR = JOptionPane.ERROR_MESSAGE;
    public static final int QUESTION = JOptionPane.QUESTION_MESSAGE;
    public static final int PLAIN = JOptionPane.PLAIN_MESSAGE;
}