package com.github.errayeil.Tools;

import com.github.errayeil.utils.CompUtils;
import com.github.errayeil.utils.ToolsUtils;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Retrieves the records from a specified directory in a ready-to-paste format.<br>
 * E.G.: "records/moditems/modweapons/weapon_a01.dbr"
 *
 * @author Errayeil
 * @version 1.0
 * @since 1.0
 */
public class RecordListTool {

    /**
     * The directory the records are read from.
     */
    private File[] folderToRead;

    /**
     * The output txt file location.
     */
    private File outputLocation;

    /**
     * The file the record data is written to.
     */
    private File outputFile;

    /**
     * The name of the output file.
     */
    private String outputFileName;

    /**
     * Constructs this ModdingSuite app.
     */
    public RecordListTool () {

    }

    /**
     * Starts the process. This method will return if getDirectoriesToRead(), getOutputLocation(),
     * and getOutputFileName() returns false.
     */
    public void start() {
        boolean success0 = getDirectoriesToRead();
        if (!success0)
            return;

        boolean success1 = getOutputLocation();
        if (!success1)
            return;

        boolean success2 = getOutputFileName();
        if (!success2)
            return;

        createOutputFile();
        readAndWrite();

        JOptionPane.showMessageDialog(null, "Records successfully retrieve. You can find them here: " + "\n" +
                outputFile.getAbsolutePath());
    }


    /**
     * Allows the user to choose what directory(ies) that contains records to be exported.
     */
    private boolean getDirectoriesToRead() {
        JFileChooser chooser = CompUtils.getFileChooser("Choose location of the records", JFileChooser.DIRECTORIES_ONLY, true);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            folderToRead = chooser.getSelectedFiles();
            return true;
        }
        return false;
    }

    /**
     * Allows the user to choose the output files location.
     */
    private boolean getOutputLocation() {
        JFileChooser chooser = CompUtils.getFileChooser("Choose output file location", JFileChooser.DIRECTORIES_ONLY, false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            outputLocation = chooser.getSelectedFile();
            return true;
        }

        return false;
    }

    /**
     * Displays an input dialog to request the user to set the name of the output file.
     */
    private boolean getOutputFileName() {
        outputFileName = JOptionPane.showInputDialog(null, "What would you like the name of the output file to be?"
                + "\n" + "Type .txt at the end of the name for easiest access.", "Output file name", JOptionPane.QUESTION_MESSAGE);

        if (outputFileName.equals("") || outputFileName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "File name cannot be null.", "Invalid file name", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    /**
     * Creates the output file in the previously chosen directory.
     */
    private void createOutputFile() {
        File file = new File(outputLocation.getAbsolutePath() + File.separator + outputFileName);

        if (!file.exists()) {
            try {
                boolean success = file.createNewFile();

                if (!success) {
                    System.exit(0);
                } else {
                    outputFile = file;
                }
            } catch (IOException e) {
                //TODO log
                JOptionPane.showMessageDialog(null, "An error occurred when creating the output file.", "IOException", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "The specified output file already exists. The process will be aborted.");
        }
    }

    /**
     * Retrieves all the records within the chosen directory(ies) and trims the path
     * to be easily pasted within the DBREditor.
     */
    private void readAndWrite() {
        List<String> toWrite = new ArrayList<>();

        for (File folder : folderToRead) {
            if (folder.isDirectory()) {
                File[] files = folder.listFiles();

                if (files != null) {
                    for (File f : files) {
                        String path = f.getAbsolutePath();
                        toWrite.add( ToolsUtils.trimRecord(path)); //Substrings the string to start with records/ and replaces all \ with / (if windows)
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Files could not be retrieved.", "Null file list", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)));
        } catch (FileNotFoundException e) {
            //TODO log
            JOptionPane.showMessageDialog(null, "Runtime Exception occurred when attempting to write records.", "FileNotFoundException", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (String s : toWrite) {
            try {
                writer.write(s);
                writer.newLine();
            } catch (IOException e) {
                //TODO log
                JOptionPane.showMessageDialog(null, "The record could not be written to the output file.", "IOException", JOptionPane.ERROR_MESSAGE);
            }
        }

        try {
            writer.close();
        } catch (IOException e) {
            //TODO log
            JOptionPane.showMessageDialog(null, "Unable to close output stream. App will close.", "IOException", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
}
