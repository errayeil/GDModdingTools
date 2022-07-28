package com.github.errayeil.ui.Window;


import com.github.errayeil.Persistence.Persistence;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuBar;
import java.awt.Dimension;

/**
 * @author Errayeil
 * @version 0.1
 * @since 0.1
 */
public class AMWindow {

    /**
     *
     */
    private JMenuBar frameMenuBar;

    /**
     *
     */
    private JFrame managerFrame;


    /**
     *
     */
    private JList<String> outputList;

    /**
     *
     */
    private Persistence config = Persistence.getInstance ();

    /**
     *
     */
    public AMWindow() {
        AMMenubar bar = new AMMenubar();
        frameMenuBar = bar.getMenubar();
        managerFrame = new JFrame("GDModdingTools AssetManager");
        outputList = new JList<>();

        managerFrame.setJMenuBar(frameMenuBar);

        if ( config.hasBeenRegistered ( "amWindow") ) {
            System.out.println("Registered");
            //TODO
        } else {
            System.out.println("Not Registered");
            managerFrame.setMinimumSize(new Dimension(1200, 600));
            managerFrame.pack();
            managerFrame.setLocationRelativeTo(null);
        }
    }

    /**
     *
     */
    public void openAssetManager() {
        managerFrame.setVisible(true);
    }

    /**
     *
     */
    public void closeAssetManager() {

    }
}
