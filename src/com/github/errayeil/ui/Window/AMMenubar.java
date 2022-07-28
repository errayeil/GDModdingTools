package com.github.errayeil.ui.Window;

import com.github.errayeil.Actions.Menubar.*;
import com.github.errayeil.GD.GDTProcessBuilder;
import com.github.errayeil.Persistence.Persistence;
import com.github.errayeil.utils.Utils;

import javax.swing.*;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.File;

public class AMMenubar {

    /**
     *
     */
    private JMenuBar menubar;

    /**
     *
     */
    private Persistence config = Persistence.getInstance ();

    /**
     *
     */
    public AMMenubar() {
        menubar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu viewMenu = new JMenu("View");
        JMenu modsMenu = new JMenu("Mods");
        JMenu buildMenu = new JMenu("Build");
        JMenu archiveMenu = new JMenu("Archive");
        JMenu dbMenu = new JMenu("Database");
        JMenu resourceMenu = new JMenu("Resources");
        JMenu toolsMenu = new JMenu("Tools");
        JMenu gdToolsMenu = new JMenu("GD Tools");
        JMenu gdCmdToolsMenu = new JMenu("GD command line tools");
        JMenu settingsMenu = new JMenu("Settings");

        JMenuItem runGDItem = new JMenuItem ( "Run Grim Dawn"  );
        JMenuItem newModItem = new JMenuItem("Create new mod");
        JMenuItem openGDDirItem = new JMenuItem("Open Grim Dawn directory");
        JMenuItem exitItem = new JMenuItem("Close AssetManager");

        JMenu docksMenu = new JMenu ( "Tool windows" );
        JCheckBoxMenuItem workspaceItem = new JCheckBoxMenuItem ( "Workspace" );
        JCheckBoxMenuItem exportedItem = new JCheckBoxMenuItem ( "Exported Textures" );
        JCheckBoxMenuItem soundPakItem = new JCheckBoxMenuItem( "Exported sound paks");
        JCheckBoxMenuItem pendingItem = new JCheckBoxMenuItem ( "Pending changes" );
        JCheckBoxMenuItem bookmarkItem = new JCheckBoxMenuItem ( "Bookmarks" );
        JCheckBoxMenuItem findItem = new JCheckBoxMenuItem("Find");
        JCheckBoxMenuItem statusBarItem = new JCheckBoxMenuItem ( "Status bar" );
        JCheckBoxMenuItem statusBoxItem = new JCheckBoxMenuItem ( "Status box" );

        JMenuItem buildarzItem = new JMenuItem("Build database");
        JMenuItem stopItem = new JMenuItem("Stop build");

        JMenuItem buildarcItem = new JMenuItem("Build resources");
        JMenuItem compactItem = new JMenuItem("Compact resources");

        JMenuItem importDBRItem = new JMenuItem("Import records");
        JMenuItem checkItem = new JMenuItem("Check records");
        JMenuItem moveDBRItem = new JMenuItem("Move records");

        JMenuItem importResItem = new JMenuItem("Import resources");
        JMenuItem moveResItem = new JMenuItem("Move resources");

        JCheckBoxMenuItem prefLuaItem = new JCheckBoxMenuItem("Set preferred lua editor");
        JCheckBoxMenuItem prefTxtItem = new JCheckBoxMenuItem("Set preferred text editor");
        JCheckBoxMenuItem prefImgItem = new JCheckBoxMenuItem("Set preferred image editor");

        JCheckBoxMenuItem setGDDirItem = new JCheckBoxMenuItem("Set Grim Dawn directory");
        JCheckBoxMenuItem setToolDirItem = new JCheckBoxMenuItem("Set tools directory");
        JCheckBoxMenuItem setWorkingDirItem = new JCheckBoxMenuItem("Set workspace directory");
        JCheckBoxMenuItem setModsDirItem = new JCheckBoxMenuItem("Set Build directory");
        JMenuItem settingsItem = new JMenuItem("Settings");

        fileMenu.add ( runGDItem );
        fileMenu.addSeparator ();
        fileMenu.add(newModItem);
        fileMenu.addSeparator();
        fileMenu.add(openGDDirItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        viewMenu.add ( docksMenu );
        viewMenu.addSeparator ();
        viewMenu.add ( statusBarItem );
        viewMenu.add ( statusBoxItem );

        docksMenu.add ( findItem );
        docksMenu.addSeparator ();
        docksMenu.add ( bookmarkItem );
        docksMenu.addSeparator ();
        docksMenu.add ( workspaceItem );
        docksMenu.add ( exportedItem );
        docksMenu.add ( soundPakItem );
        docksMenu.addSeparator ();
        docksMenu.add ( pendingItem );

        addModMenuItems(modsMenu);

        buildMenu.add(buildarzItem);
        buildMenu.addSeparator();
        buildMenu.add(stopItem);

        archiveMenu.add(buildarcItem);
        archiveMenu.addSeparator();
        archiveMenu.add(compactItem);

        dbMenu.add(importDBRItem);
        dbMenu.add(moveDBRItem);
        dbMenu.addSeparator();
        dbMenu.add(checkItem);

        resourceMenu.add(importResItem);
        resourceMenu.add(moveResItem);

        toolsMenu.add(gdToolsMenu);
        toolsMenu.add(gdCmdToolsMenu);
        toolsMenu.addSeparator();
        toolsMenu.add(prefLuaItem);
        toolsMenu.add(prefTxtItem);
        toolsMenu.add(prefImgItem);

        settingsMenu.add(setGDDirItem);
        settingsMenu.add(setToolDirItem);
        settingsMenu.add(setWorkingDirItem);
        settingsMenu.add(setModsDirItem);
        settingsMenu.addSeparator();
        settingsMenu.add(settingsItem);

        menubar.add(fileMenu);
        menubar.add(viewMenu);
        menubar.add ( modsMenu );
        menubar.add(buildMenu);
        menubar.add(archiveMenu);
        menubar.add(dbMenu);
        menubar.add(resourceMenu);
        menubar.add(toolsMenu);
        menubar.add(settingsMenu);

        runGDItem.addActionListener ( new RunGDAction () );
        openGDDirItem.addActionListener ( new OpenGDDirAction () );
        exitItem.addActionListener ( new CloseAMAction ( menubar ) );

        prefImgItem.addActionListener ( new SetPrefEditorAction ( prefImgItem, "img" ) );
        prefLuaItem.addActionListener ( new SetPrefEditorAction ( prefLuaItem, "lua" ) );
        prefTxtItem.addActionListener ( new SetPrefEditorAction ( prefTxtItem, "txt" ) );

        setGDDirItem.addActionListener ( new SetGDDirectoryAction (setGDDirItem) );
        setToolDirItem.addActionListener ( new SetToolDirectoryAction ( setToolDirItem ) );
        setWorkingDirItem.addActionListener ( new SetWorkspaceDirectoryAction ( setWorkingDirItem ) );
        setModsDirItem.addActionListener ( new SetBuildDirectoryAction (setModsDirItem) );

        if (config.hasBeenRegistered ( config.gdDirKey )) {
            setGDDirItem.setSelected ( true );
            setGDDirItem.setEnabled ( false );
        }

        if (config.hasBeenRegistered ( config.gdToolDirKey )) {
            setToolDirItem.setSelected ( true );
            setToolDirItem.setEnabled ( false );
            addUIToolsItems(gdToolsMenu);
            addCmdToolsItems(gdCmdToolsMenu);
        }

        if (config.hasBeenRegistered ( config.gdWorkingDirKey )) {
            setWorkingDirItem.setSelected ( true );
            setWorkingDirItem.setEnabled ( false );
        }

        if (config.hasBeenRegistered ( config.gdBuildDirKey )) {
            setModsDirItem.setSelected ( true );
            setModsDirItem.setEnabled ( false );
        }

        if (config.hasBeenRegistered ( config.prefEditorKey + "lua" )) {
            prefLuaItem.setSelected ( true );
            prefLuaItem.setEnabled ( false );
        }

        if (config.hasBeenRegistered ( config.prefEditorKey + "txt" )) {
            prefTxtItem.setSelected ( true );
            prefTxtItem.setEnabled ( false );
        }

        if (config.hasBeenRegistered ( config.prefEditorKey + "img")) {
            prefImgItem.setSelected ( true );
            prefImgItem.setEnabled ( false );
        }
    }

    /**
     *
     * @return
     */
    public JMenuBar getMenubar() {
        return menubar;
    }

    /**
     *TODO
     * @param modsMenu
     */
    private void addModMenuItems(JMenu modsMenu) {
        if (config.hasBeenRegistered ( config.gdBuildDirKey )) {
            String path = config.getDirectory ( config.gdBuildDirKey ) + File.separator + "mods";
            File[] files = new File(path).listFiles ();

            for (File f : files) {
                if (f.isDirectory ()) {
                    JMenuItem item = new JMenuItem(f.getName ());
                    item.setToolTipText ( f.getAbsolutePath () );
                    //TODO actionlistener
                    modsMenu.add ( item );
                }
            }
        }
    }

    /**
     *
     * @param toolsMenu
     */
    private void addUIToolsItems(JMenu toolsMenu) {
        String[] tools = {"Animation Editor", "Bitmap Creator", "Conversation Editor", "DBR Editor", "World Editor",
        "Particle Effect Editor", "Quest Editor", "Texture Viewer", "Mesh Editor"};

        for (String s : tools) {
            JMenuItem item = new JMenuItem(s);
            toolsMenu.add(item);
        }

        configureItems ( toolsMenu.getMenuComponents());
    }

    /**
     *
     */
    private void addCmdToolsItems(JMenu cmdToolsMenu ) {
        String[] tools = {"Animation Compiler", "Archive Tool", "Font compiler", "Map compiler", "Model Compiler",
        "Shader compiler", "Texture Compiler"};

        for ( String tool : tools ) {
            JMenuItem item = new JMenuItem ( tool );
            String path = "";

            switch ( tool ) {
                case "Animation Compiler" -> path = config.getDirectory ( config.animCompKey );
                case "Archive Tool" -> path = config.getDirectory ( config.archiveToolKey );
                case "Font Compiler" -> path = config.getDirectory ( config.fontCompileKey );
                case "Map Compiler" -> path = config.getDirectory ( config.modelCompileKey );
                case "Shader Compiler" -> path = config.getDirectory ( config.shaderCompileKey );
                case "Texture Compiler" -> path = config.getDirectory ( config.texCompileKey );
            }

            ActionListener al = e -> {
                GDTProcessBuilder builder = new GDTProcessBuilder ();
                //TODO
            };

            item.setIcon ( Utils.getSystemIcon ( path ) );
            item.setToolTipText ( path );
            item.addActionListener ( al );
            cmdToolsMenu.add ( item );
        }
    }

    /**
     * Gets the icon for the GD tool menu items and creates their action listeners.
     * @param items The list of JMenuItems attached to the GD Tools menu, as a component array.
     */
    private void configureItems ( Component[] items) {
        for (Component component : items) {
            JMenuItem item = (JMenuItem) component;

            String path = "";
            switch (item.getText()) {
                case "Animation Editor" ->
                        path = config.getDirectory ( config.aifEditorKey );
                case "Bitmap Creator" ->
                        path = config.getDirectory ( config.bitmapCreateKey );
                case "Conversation Editor" ->
                        path = config.getDirectory ( config.convoEditKey );
                case "DBR Editor" ->
                        path = config.getDirectory ( config.dbrEditorKey );
                case "World Editor" ->
                        path = config.getDirectory ( config.worldEditorKey );
                case "Particle Effect Editor" ->
                        path = config.getDirectory ( config.psEditorKey );
                case "Quest Editor" ->
                        path = config.getDirectory ( config.questEditKey );
                case "Texture Viewer" ->
                        path = config.getDirectory ( config.texViewerKey );
                case "Mesh Editor" ->
                        path = config.getDirectory ( config.meshViewerKey );
            }

            ActionListener al = e -> {
                GDTProcessBuilder builder = new GDTProcessBuilder ();
                builder.runToolAt ( new File (item.getToolTipText ()) );
            };

            item.setIcon ( Utils.getSystemIcon ( path) );
            item.setToolTipText ( path );
            item.addActionListener ( al );
        }
    }
}
