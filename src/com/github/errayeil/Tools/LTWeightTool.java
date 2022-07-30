package com.github.errayeil.Tools;

import com.github.errayeil.utils.CompUtils;
import com.github.errayeil.utils.ToolsUtils;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes a specified weight to a provided loot table.
 * Currently, this only works on blank LTs.
 *
 * //TODO work with multiple weights, LT's, and LT's with written weights.
 *
 * @author Errayeil
 * @version 1.0
 * @since 1.0
 */
public class LTWeightTool {

    /**
     * The Loot table file the new weight is being written to.
     */
    private File fileToWrite;

    /**
     * The weight to write.
     */
    private String weightToWrite;

    /**
     * Constructor.
     */
    public LTWeightTool() {

    }

    /**
     * Starts the process. This will return if getFile or getWeightToWrite returns false.
     */
    public void start() {
        if (!getFile())
            return;
        if (!getWeightToWrite())
            return;

        try {
            write();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     */
    private boolean getFile() {
        JFileChooser chooser = CompUtils.getFileChooser("Choose LT file to write to", JFileChooser.FILES_ONLY, false);

        int option = chooser.showOpenDialog(null);

        if (option == JFileChooser.APPROVE_OPTION) {
            fileToWrite = chooser.getSelectedFile();

            if (!ToolsUtils.isValidLTFile(fileToWrite))
                return false;

        } else {
           return false;
        }

        return true;
    }

    /**
     *
     */
    private boolean getWeightToWrite() {
        weightToWrite = JOptionPane.showInputDialog("Enter weight");

        return !weightToWrite.equals("") && !weightToWrite.isEmpty();
    }

    /**
     *
     * @throws IOException
     */
    private void write() throws IOException {
        List<String> lines = ToolsUtils.readLinesFromRecord (fileToWrite); //TODO Can make this only one list
        List<String> modified = new ArrayList<>();

        for (String line : lines) {
            if (line.contains("lootWeight") && !line.contains("Dyn")) {
               if (line.contains(",,")) {
                  modified.add( ToolsUtils.replaceCommasWith(line, weightToWrite, false));
               } else if (line.contains(",0,")) {
                   modified.add( ToolsUtils.replaceCommasWith(line, weightToWrite, true));
               }
            } else {
                modified.add(line);
            }
        }

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileToWrite)));

        for (String s : modified) {
            writer.write(s);
            writer.newLine();
        }

        writer.close();
    }
}
