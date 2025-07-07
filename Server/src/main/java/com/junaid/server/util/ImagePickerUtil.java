package com.junaid.server.util;

// @author junaidxyz


import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImagePickerUtil {
    public static String baseImageDir   = "../shared_images/";
    
    public static String pickAndSaveFlag(Component parent, String filename) {
        return pickAndSaveImage(parent, "flag", filename);
    }

    public static String pickAndSaveSymbol(Component parent, String filename) {
        return pickAndSaveImage(parent, "symbol", filename);
    }

    private static String pickAndSaveImage(Component parent, String subdir, String filenamePrefix) {
        JFileChooser chooser = new JFileChooser();
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif"));
        chooser.setDialogTitle("Select " + filenamePrefix + " image");

        int result = chooser.showOpenDialog(parent);
        if (result != JFileChooser.APPROVE_OPTION) {
            return null;
        }

        File selected = chooser.getSelectedFile();
        String extension = getFileExtension(selected);
        if (extension == null) {
            JOptionPane.showMessageDialog(parent, "Invalid file type.");
            return null;
        }

        String relativePath = subdir + "/" + filenamePrefix + "." + extension;
        File dest = new File(baseImageDir + relativePath);

        try {
            dest.getParentFile().mkdirs(); // Ensure directory exists
            Files.copy(selected.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            JOptionPane.showMessageDialog(parent, "Saved to: " + dest.getPath());
            return relativePath;
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parent, "Error saving image.");
            return null;
        }
    }

    private static String getFileExtension(File file) {
        String name = file.getName();
        int dot = name.lastIndexOf('.');
        return (dot > 0 && dot < name.length() - 1) ? name.substring(dot + 1).toLowerCase() : null;
    }
}
