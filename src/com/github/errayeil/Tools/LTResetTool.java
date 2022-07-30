package com.github.errayeil.Tools;

import com.github.errayeil.utils.CompUtils;
import com.github.errayeil.utils.ToolsUtils;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Can clear a specified loot table(s) weight and name values.
 * @author Errayeil
 * @version 1.0
 * @since 1.0
 */
public class LTResetTool {

    /**
     * Constructor
     */
    public LTResetTool() {

    }

    /**
     * Starts the process to reset a loot table.
     */
    public void start() {
        JFileChooser chooser = CompUtils.getFileChooser("Select LT files to clear", JFileChooser.FILES_ONLY, true);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File[] files = chooser.getSelectedFiles();

            for (File f : files) {
                if ( ToolsUtils.isValidLTFile(f)) {
                    List<String> lines = null;
                    List<String> modifiedLines = new ArrayList<>();
                    try {
                        lines = ToolsUtils.readLinesFromRecord (f);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    for (int i = 0; i < lines.size(); i++) {
                        String line = lines.get(i);

                        if (line.contains("lootName") || line.contains("lootWeight")) {
                            modifiedLines.add(line.substring(0, line.indexOf(",") + 1) + ",");
                        } else {
                            modifiedLines.add(line);
                        }
                    }
                    try {
                        write(f, modifiedLines);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "One of the selected files is not a valid LootTable file.", "Invalid File", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     *
     * @param f
     * @param lines
     * @throws IOException
     */
    private void write(File f, List<String> lines) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));

        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }

        writer.close();
    }
}
