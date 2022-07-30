package com.github.errayeil.Tools;

import com.github.errayeil.utils.CompUtils;
import com.github.errayeil.utils.ToolsUtils;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.*;

/**
 * Makes it much easier to write lootable records to loottable.dbr files instead of manually doing it
 * via DBREditor.<br>
 * This starts from the start of the file and will work its way to the bottom, moving onto the next
 * provided LT file when lootName variables (up to 100, depending on the LT type) are used up.
 *
 * TODO Read records from multiple directories (I'll get to this at some point, unless someone beats me to the punch)
 * <b></b>
 * @author Errayeil
 * @version 1.0
 * @since 1.0
 */
public class LTNameTool {

    /**
     * List of loot table files we will be writing to.
     */
    private List<File> lootTableFiles;

    /**
     * List of records that need to be written to the loot table files.
     */
    private List<String> records;

    /**
     * The index we are currently pulling from the records list.
     */
    private int recordsIndex;

    /**
     * Constructor.
     */
    public LTNameTool() {
        lootTableFiles = new ArrayList<>();
        records = new ArrayList<>();
        recordsIndex = 0;
    }

    /**
     * Starts the process.
     */
    public void start() {
        if (!lootTableFiles.isEmpty())
            lootTableFiles.clear();
        if (!records.isEmpty())
            records.clear();

        if (!getLootTableFiles())
            return;
        if (!getRecordsToWrite())
            return;

        try {
            writeRecords();
        } catch (IOException e) {
            throw new RuntimeException(e); //Why is IntelliJ filling in RuntimeExceptions instead of printStackTrace()????
            //I'll get to logging
        }
    }

    /**
     * Gets the LT files we will end up writing to.
     * The algorithm will utilize all LT files provided if the amount of records surpasses the capacity of the LT's. <br>
     * E.G. 30, 30, and 100
     * @return True or false if this process was completed
     */
    private boolean getLootTableFiles() {
        JFileChooser chooser = CompUtils.getFileChooser("Select loot table files", JFileChooser.FILES_ONLY, true);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File[] files = chooser.getSelectedFiles();
            lootTableFiles.addAll(Arrays.asList(files));

            for (File f : lootTableFiles) {
                if (!ToolsUtils.isValidLTFile(f)) {
                    JOptionPane.showMessageDialog(null, "The selected file(s) is not a valid loot table.", "Invalid File", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        } else {
            return false;
        }

        return true;
    }

    /**
     * Retrieves the records from the directory specified.
     * @return True or false if this process was completed.
     */
    private boolean getRecordsToWrite() {
        JFileChooser chooser = CompUtils.getFileChooser("Choose directory of records", JFileChooser.DIRECTORIES_ONLY, false);

        File directory = null;

        if ( chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { //In all my years I JUST NOW thought of throwing open dialog into the if statement instead of int option == blah blah
            directory = chooser.getSelectedFile();
        } else {
            return false;
        }

        if (directory != null && directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            assert files != null; //Change from assert to different null check, assert doesn't work outside of dev , right??
            for (File f : files) {
                String path = f.getAbsolutePath();
                records.add( ToolsUtils.trimRecord(path)); //Utility call to trim off the parts of the path we don't need and replace all backslash with forward slash
            }
        } else {
            return false;
        }

        return true;
    }

    /**
     * Starts and handles the record-to-LT.dbr file writing process.
     * @throws IOException Thrown when the BufferedWriter encounters an error.
     */
    private void writeRecords() throws IOException {
        Map<File, List<String>> originalLines = new HashMap<>(); //TODO Maybe condense this into 1 list? May be worse for readability purposes.
        Map<File, List<String>> modifiedLines = new HashMap<>();
        recordsIndex = 0;

        for (File f : lootTableFiles) {
            List<String> lines = ToolsUtils.readLinesFromRecord (f);
            originalLines.put(f, lines);
        }

        for (File key : originalLines.keySet()) {
            if ( ToolsUtils.isLTListTable(key)) { //Checks for a certain type of loot table file
                writeList(key, originalLines.get(key), modifiedLines);
            } else {
                writeNonList(key, originalLines.get(key), modifiedLines);
            }
        }

        //Time to write the modified lines to the file. *THIS OVERWRITES ALL FILE DATA*
        for (File key : modifiedLines.keySet()) {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(key)));
            List<String> lines = modifiedLines.get(key);

            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }

            writer.close();
        }
    }

    /**
     * Method used when the provided LT file is based off the template "lootitemtable_dynweight.tpl".
     * This LT type only has one line for item records, so I had to process that in separate method.
     * @param lines
     * @param modifiedLines
     */
    private void writeList(File key, List<String> lines, Map<File, List<String>> modifiedLines) {
        List<String> modified = new ArrayList<>();
        boolean recordsDone = false;

        for (String line : lines) {
            if (line.contains("itemNames") && !recordsDone) {
                String mod = "";
                for (String record : records) {
                    int index = line.lastIndexOf(",");
                    String sub = line.substring(0, index);

                    if (line.endsWith(",,")) {
                        mod = sub + record + ",";
                    } else {
                        mod = sub + ";" + record + ",";
                    }
                    line = mod;
                    recordsIndex++;

                    if (recordsIndex == records.size()) {
                        recordsDone = true;
                    }
                }

                modified.add(mod);

            } else {
                modified.add(line);
            }
        }

        modifiedLines.put(key, modified);
    }

    /**
     * Writes records to individual "lootName" lines in LT.dbr files.
     *
     * @param key The key to access the modifiedLines map.
     * @param lines A list containing the original lines read from the loot table file.
     * @param modifiedLines Map that contains the files and associated lists of modified lines
     */
    private void writeNonList(File key, List<String> lines, Map<File, List<String>> modifiedLines) {
        List<String> modified = new ArrayList<>();
        boolean recordsDone = false;

        for (String line : lines) {
            if (line.contains("lootName") && !line.contains("Dyn") && !recordsDone) {
                if (line.contains(",,")) {
                    modified.add( ToolsUtils.replaceCommasWith(line, records.get(recordsIndex), false));
                    recordsIndex++;

                    if (recordsIndex == records.size()) {
                        recordsDone = true;
                    }
                }
            } else {
                modified.add(line);
            }
        }

        modifiedLines.put(key, modified);
    }
}
