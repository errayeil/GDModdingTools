package com.github.errayeil.utils;

import com.formdev.flatlaf.FlatDarkLaf;
import com.github.errayeil.Persistence.Persistence;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Errayeil
 * @version 1.0
 * @since 1.0
 */
public class Utils {

    /**
     *
     */
    private static String[] validLootTableClasses = {
            "lootitemtable_dynweighted_dynaffix.tpl", "lootitemtable_dynweight.tpl",
    "lootitemtable_fixedweight.tpl" , "lootitemtable_dynitemlist.tpl"};

    /**
     * File chooser used for various processes in the GDModdingSuite.
     */
    private static JFileChooser chooser;

    /**
     *
     */
    private static Persistence config = Persistence.getInstance();

    /**
     *
     */
    private Utils() {

    }

    /**
     *
     */
    public static void setSystemUI() {
        /**
         * FlatMaterialDesignDarkIJTheme
         * FlatDarkLAF
         * FlatArcDarkIJTheme
         *
         */
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf () );
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
    }

    public static Icon getSystemIcon(final String path) {
        return FileSystemView.getFileSystemView ( ).getSystemIcon ( new File (path) );
    }

    /**
     *
     * @return
     */
    public static int getCenteredCoordinateX() {
        return Toolkit.getDefaultToolkit ().getScreenSize ().width / 2;
    }

    /**
     *
     * @return
     */
    public static int getCenteredCoordinateY() {
        return Toolkit.getDefaultToolkit ().getScreenSize ().height / 2;
    }

    /**
     * Returns a list of lines from the specified file.
     *
     * @return
     */
    public static List<String> readLinesFromFile(final File fileToRead) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileToRead));
        Stream<String> lines = reader.lines();
        List<String> list = lines.toList();
        reader.close();

        return list;
    }

    /**
     * Replaces the comma separators in loot table records with the specified string.
     *
     * @param toModify The String that is being modified.
     * @param replacement The String that replaces the ",," or ",0," segments in toModify.
     * @param replace0 Set to true if ",0," is needing replacement instead.
     * @return
     */
    public static String replaceCommasWith(String toModify, String replacement, boolean replace0) {
        if (!replace0) {
            return toModify.replace(",,", "," + replacement + ","); //For lootWeight without 0's or lootName variables
        } else {
            return toModify.replace(",0,", "," + replacement + ","); //For lootWeight with the value set to 0.
        }
    }

    /**
     *
     * @param recordFilePath
     * @return
     */
    public static String trimRecord(String recordFilePath) {
        int index = recordFilePath.indexOf("\\records");
        String ss = recordFilePath.replace("\\", "/" );
        return ss.substring(index + 1);
    }

    /**
     * Gets a JFileChooser configured with the specified paramaters.
     *
     * @param dialogTitle The title of the file chooser dialog.
     * @param selectionMode The selection mode of the chooser
     * @param multipleSelection If multiple directory/file selection is allowed.
     * @return The new file chooser
     */
    public static JFileChooser getFileChooser(String dialogTitle, int selectionMode, boolean multipleSelection) {
        if (chooser == null)
            chooser = new JFileChooser();

        config.registerFileChooser(chooser, "utilsChooser");

        chooser.setDialogTitle(dialogTitle);
        chooser.setFileSelectionMode(selectionMode);
        chooser.setMultiSelectionEnabled(multipleSelection);

        return chooser;
    }

    /**
     *
     * @param ltFile The loot table we're validating
     * @return
     */
    public static boolean isValidLTFile(File ltFile) {
        List<String> lines = null;
        try {
            lines = readLinesFromFile(ltFile);
        } catch (IOException e) {
            //TODO log
            JOptionPane.showMessageDialog(null, "Could not validate selected file.", "Validation failed", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        for (String line : lines) {
            if (line.contains(validLootTableClasses[0]) || line.contains(validLootTableClasses[1])) {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @param f
     * @return
     */
    public static boolean isLTListTable(File f) {
        List<String> lines = null;
        try {
            lines = readLinesFromFile(f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (String line : lines)
            if (line.contains("lootitemtable_dynweight.tpl")) {
                return true;
            }

        return false;
    }

    /**
     *
     * @param path
     * @return
     */
    public static int countDBRFiles(File path) {
        if (!path.isDirectory())
            return -1;

        File[] files = path.listFiles();

        int invalid = 0;
        for (File f : files) {
            if (!f.getAbsolutePath().endsWith("dbr")) {
                invalid++;
            }
        }
        return invalid;
    }

    /**
     * Creates a space filling JComponent to add some padding in between components
     * in the panel.
     * @param width The width the filler should have.
     * @param height The height the filler should have.
     * @return The newly create Box.Filler JComponent.
     */
    public static Box.Filler createFiller(int width, int height) {
        Dimension dim = new Dimension(width,height);
        return new Box.Filler(dim, dim, dim);
    }

    public static File getFileTreeDirectory() {

        return null;
    }
}
