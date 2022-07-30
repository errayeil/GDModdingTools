package com.github.errayeil.Persistence;

import com.github.errayeil.ui.Custom.AMFileChooser;
import com.github.errayeil.utils.SystemUtils;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Persistence class is a specialized wrapper around Preferences, in which it stores all data that needs to
 * be remembered and persistent throughout the application and even when the app is closed and opened again. <br>
 * You'll find there are a lot of keys and variables and that's because I want this to be as fluid of an experience as
 * possible. All dialog sizes, positions, directories, and the list goes on is remembered and utilized via this instance
 * wrapper. <br>
 *
 *
 *
 * @// TODO: File backup of Preferences node:  &#10060;
 * @// TODO: Code Refactoring:  &#10060
 * @// TODO: Documentation:  &#10060;
 *
 *
 * @see Preferences
 * @author Errayeil
 * @version 0.1
 * @since 0.1
 */
public final class Persistence {

	/**
	 * The instance of the Persistence class.
	 */
	private static Persistence instance;

	/**
	 * The Preferences instance we're using to save data that needs to be persistent.
	 */
	private Preferences store;

	/**
	 *
	 */
	private boolean allowChanges = true;

	/**
	 * Below you will find a ton of public final String keys used for
	 * registering preferences to the preferences store. The point is to make it easier
	 * to write to the preferences store and minimizing mistakes. I think the variable names
	 * are descriptive enough but I'm happy to refactor them as needed.
	 */

	/*
	 * Keys for file choosers.
	 */
	public final String currentDirKey = "-currentDir";

	/*
	 * Keys for registering dialog size and width.
	 */
	public final String widthKey = "-width";
	public final String heightKey = "-height";
	public final String xKey = "-x";
	public final String yKey = "-y";

	/*
	 * Keys for registering grim dawn modding directories.
	 */
	public final String pathKey = "-path";
	public final String gdDirKey = "gdDir";
	public final String gdBuildDirKey = "gdBuildDir";
	public final String gdWorkingDirKey = "gdWorkingDir";
	public final String gdToolDirKey = "gdToolsDir";
	public final String workspaceDirKey = "workspaceDir";
	public final String exportedTexDirKey = "exportedTexDir";
	public final String exportedPakDirKey = "exportedPakDir";
	public final String backupDirKey = "backupDir";
	public final String gdExeDirKey = "grim dawn.exe-path";

	/*
	 * Keys for GD modding tools.
	 */
	public final String aifEditorKey = "aifeditor.exe-path";
	public final String animCompKey = "animationcompiler.exe-path";
	public final String archiveToolKey = "archivetool.exe-path";
	public final String assetManageKey = "assetManager.exe-path";
	public final String bitmapCreateKey = "bitmapcreator.exe-path";
	public final String convoEditKey = "conversationeditor.exe-path";
	public final String dbrEditorKey = "dbreditor.exe-path";
	public final String worldEditorKey = "editor.exe-path";
	public final String fontCompileKey = "fontcompiler.exe-path";
	public final String mapCompileKey = "mapcompile.exe-path";
	public final String modelCompileKey = "modelcompiler.exe-path";
	public final String psEditorKey = "pseditor.exe-path";
	public final String questEditKey = "questeditor.exe-path";
	public final String shaderCompileKey = "shadercompiler.exe-path";
	public final String sourceServerKey = "sourceserver.exe-path";
	public final String texCompileKey = "texturecompiler.exe-path";
	public final String texViewerKey = "texviewer.exe-path";
	public final String meshViewerKey = "viewer.exe-path";

	/*
	 * Keys for registering preferred editors for lua, txt, and image type files.
	 */
	public final String prefEditorKey = "prefEditorFor";
	public final String luaKey = "lua";
	public final String txtKey = "txt";
	public final String imgKey = "img";
	public final String builtInKey = "USE BUILT-IN";

	/*
	 * Key for registering setup completion.
	 */
	public final String setupCompleteKey = "setup-completed";

	/*
	 * Keys for registering GDModdingSuite directories.
	 */
	public final String suiteInstallDirKey = "suiteInstallDir";
	public final String suiteLogFileDirKey = "suiteLogFileDir";
	public final String suiteUpdateDirKey = "suiteUpdateDir";
	public final String suiteLogFilePathKey = "suiteLogFilePath";
	public final String suiteVersionKey = "suiteVersion";

	/**
	 * Private constructor to prevent out-of-scope initialization.
	 * This gets the Preferences' node for this class.
	 */
	private Persistence ( ) {

		store = Preferences.userNodeForPackage ( Persistence.class );
	}

	/**
	 * Applies changes made to the preferences store.
	 */
	private void push () {
		if (allowChanges) {
			try {
				store.flush ();
			} catch ( BackingStoreException e ) {
				//TODO logging
				throw new RuntimeException ( e );
			}
		} else {
			//TODO logging
		}
	}

	/**
	 * Returns the instance of Persistence.
	 * @return
	 */
	public static Persistence getInstance ( ) {
		if ( instance == null )
			instance = new Persistence ( );

		return instance;
	}

	/**
	 * Sets if changes made to the store should actually be written.
	 */
	public void setAllowChanges(boolean allowChanges) {
		this.allowChanges = allowChanges;
	}

	/**
	 * Returns the actual Preferences node.
	 * @return
	 */
	public Preferences getStore() {
		return store;
	}

	/**
	 * Only used for testing purposes.
	 * Calling this clears all registered keys and will have to be set again by the user.
	 * Probably would be a good idea to leave this for user functionality.
	 */
	public void clear() {
		try {
			store.removeNode ();
			store.flush ();
		} catch ( BackingStoreException e ) {
			throw new RuntimeException ( e );
		}

		store = Preferences.userNodeForPackage ( Persistence.class );
	}

	/**
	 * Registers setup has been completed. Once this is registered, the app will load straight into
	 * AssetManager, skipping the setup dialog.
	 */
	public void registerSetupCompletion() {
		store.putBoolean ( setupCompleteKey, true );
		push();
	}

	/**
	 * Returns if changes are being pushed to the store.
	 * @return True or false
	 */
	public boolean isAllowingChanges() {
		return allowChanges;
	}

	/**
	 * Returns true or false if the setup process has been completed.
	 * @return Boolean, ?what am I supposed to put here?
	 */
	public boolean isSetupCompleted() {
		return store.getBoolean ( setupCompleteKey, false );
	}

	/**
	 * Checks if the provided key has a value stored. If not, int prefs will return -1,
	 * thus the method returning false and string prefs will return "null", thus the method
	 * returning false.
	 *
	 * @param key The key to see if a value has been paired with.
	 * @return true or false if the key is registered.
	 */
	public boolean hasBeenRegistered ( String key ) {
		if (key.contains ( widthKey ) || key.contains ( heightKey ) || key.contains ( xKey ) || key.contains ( yKey )) {
			return store.getInt ( key, -1 ) != -1;
		}  else {
			return !store.get ( key, "null" ).equals ( "null" );
		}
	}

	/**
	 * Returns the directory absolute path from the provided key. If there is no value associate
	 * with the key the returned String will be "null".
	 * @param key
	 * @return
	 */
	public String getDirectory(String key) {
		return store.get ( key, "null" );
	}

	/**
	 *
	 * @param chooser
	 * @param chooserKey
	 */
	public void loadAndRegister( AMFileChooser chooser, String chooserKey) {
		if (hasBeenRegistered ( chooserKey )) {
			loadConfig ( chooser, chooserKey );
		} else {
			chooser.setNullLocationRelative ( true );
		}
		registerFileChooser ( chooser, chooserKey );
	}

	/**
	 * Loads the previous dimension (width & height) and x and y coordinates of the dialog,
	 * so the dialog can always be in the most recent and familiar position.
	 *
	 * @param dialog The dialog that needs to have its size and position configured.
	 * @param dialogName The name of the dialogs key.
	 */
	public void loadConfig ( JDialog dialog , String dialogName ) {
		setonShownValues ( dialog , dialogName );
	}

	/**
	 * Loads the most recent current directory, view UI model, selection mode, multi-selection, and file filter
	 * parameters saved to the preference store.
	 *
	 * @// TODO: View UI Model config
	 * @// TODO: Selection mode config
	 * @// TODO: Multi-selection config
	 * @// TODO: File filter config
	 *
	 * @param chooser
	 * @param chooserKey
	 */
	public void loadConfig ( AMFileChooser chooser , String chooserKey ) {
		chooser.setCurrentDirectory ( new File ( store.get ( chooserKey + currentDirKey , System.getProperty ( "user.home" ) ) ) );

		setonShownValues ( chooser, chooserKey );
	}

	/**
	 * Registers the specified directory.
	 *
	 * @param directoryKey The key for the registry.
	 * @param directoryPath The path of the directory.
	 */
	public void registerDirectory ( String directoryKey , String directoryPath ) {
		store.put ( directoryKey , directoryPath );
		push ();
	}

	/**
	 * @param chooser
	 * @param chooserKey
	 */
	public void registerFileChooser ( AMFileChooser chooser , String chooserKey ) {
		chooser.addPropertyChangeListener ( evt -> {
			String prop = evt.getPropertyName ( );

			if ( prop.equals ( JFileChooser.DIRECTORY_CHANGED_PROPERTY ) ) {
				File dir = chooser.getCurrentDirectory ( );

				if ( dir != null ) {
					store.put ( chooserKey + currentDirKey , dir.getAbsolutePath ( ) );

					try {
						store.flush ( );
					} catch ( BackingStoreException e ) {
						throw new RuntimeException ( e );
					}
				}
			}
		} );

		ComponentAdapter adapter = new ComponentAdapter ( ) {
			@Override
			public void componentResized ( ComponentEvent e ) {
				setonResizeValues ( chooser, chooserKey );
			}

			@Override
			public void componentMoved ( ComponentEvent e ) {
				setonMovedValues ( chooser, chooserKey );
			}

			@Override
			public void componentShown ( ComponentEvent e ) {
				setonShownValues ( chooser, chooserKey );
			}
		};

		chooser.setComponentAdapter ( adapter );
	}

	/**
	 * @param dialog     The component we are registering to listen for component changes.
	 * @param dialogKey The name is used as a key to retrieve previous values or set new ones.
	 */
	public void registerDialog ( JDialog dialog , String dialogKey ) {
		store.put ( dialogKey, "registered" );
		push ();

		dialog.addComponentListener ( new ComponentAdapter ( ) {
			@Override
			public void componentResized ( ComponentEvent e ) {
				setonResizeValues ( dialog , dialogKey );
			}

			@Override
			public void componentMoved ( ComponentEvent e ) {
				setonMovedValues ( dialog , dialogKey );
			}

			@Override
			public void componentShown ( ComponentEvent e ) {
				setonShownValues ( dialog , dialogKey );
			}
		} );
	}

	/**
	 * When the dialog is resized the current size values are pushed to the preferences
	 * store.
	 *
	 * @param dialog
	 * @param dialogKey
	 */
	private void setonResizeValues ( JDialog dialog , String dialogKey ) {
		store.putInt ( dialogKey + widthKey , dialog.getWidth ( ) );
		store.putInt ( dialogKey + heightKey , dialog.getHeight ( ) );

		push ();
	}

	/**
	 *
	 * @param chooser
	 * @param chooserKey
	 */
	private void setonResizeValues (AMFileChooser chooser, String chooserKey) {
		if (chooser.getDialog () != null) {
			System.out.println("Resized" + chooserKey);
			JDialog dialog = chooser.getDialog ();

			store.putInt ( chooserKey + widthKey, dialog.getWidth () );
			store.putInt ( chooserKey + heightKey, dialog.getHeight () );

			push ();
		}
	}

	/**
	 * When the dialog is moved the current x/y coordinate values are pushed to the preferences
	 * store.
	 *
	 * @param dialog
	 * @param dialogKey
	 */
	private void setonMovedValues ( JDialog dialog , String dialogKey ) {
		store.putInt ( dialogKey + xKey , dialog.getX () );
		store.putInt ( dialogKey + yKey , dialog.getY () );

		push ();
	}

	/**
	 *
	 * @param chooser
	 * @param chooserKey
	 */
	private void setonMovedValues (AMFileChooser chooser, String chooserKey) {
		if (chooser.getDialog () != null) {
			System.out.println("Moved" + chooserKey);
			JDialog dialog = chooser.getDialog ();

			store.putInt ( chooserKey + xKey, dialog.getX ()  );
			store.putInt ( chooserKey + yKey, dialog.getY () );

			push ();
		}
	}

	/**
	 * When the dialog is displayed the width/height and x/y coordinate values
	 * are applied to the dialog. These values are always dialog-independent,
	 * provided unique dialogName keys are supplied when registered.
	 *
	 * @param dialog
	 * @param dialogKey
	 */
	private void setonShownValues ( JDialog dialog , String dialogKey ) {
		int newWidth = getKeyWidth ( dialog , dialogKey );
		int newHeight = getKeyHeight ( dialog , dialogKey );
		int newX = getKeyX ( dialog , dialogKey );
		int newY = getKeyY ( dialog , dialogKey );

		dialog.setSize ( newWidth , newHeight );
		dialog.setLocation ( newX , newY );
	}

	/**
	 *
	 * @param chooser
	 * @param chooserKey
	 */
	private void setonShownValues (AMFileChooser chooser, String chooserKey) {
		if (chooser.getDialog () != null) {
			System.out.println("Shown + " + chooserKey);
			JDialog dialog = chooser.getDialog ();

			int newWidth = getKeyWidth ( dialog , chooserKey );
			int newHeight = getKeyHeight ( dialog , chooserKey );
			int newX = getKeyX ( dialog , chooserKey);
			int newY = getKeyY ( dialog , chooserKey );

			chooser.setDialogSize ( newWidth, newHeight );
			chooser.setDialogCoordinates ( newX, newY );
		}
	}

	/**
	 * Gets dialog width. This is just for tidiness.
	 *
	 * @param dialogKey
	 * @return
	 */
	private int getKeyWidth ( JDialog dialog , String dialogKey ) {
		return store.getInt ( dialogKey + widthKey , dialog.getWidth ( ) );
	}

	/**
	 * Gets dialog height. This is just for tidiness.
	 *
	 * @param dialog
	 * @param dialogKey
	 * @return
	 */
	private int getKeyHeight ( JDialog dialog , String dialogKey ) {
		return store.getInt ( dialogKey + heightKey , dialog.getHeight ( ) );
	}

	/**
	 * Gets the dialog x coordinate. This is just for tidiness.
	 *
	 * @param dialog
	 * @param dialogKey
	 * @return
	 */
	private int getKeyX ( JDialog dialog , String dialogKey ) {
		return store.getInt ( dialogKey + xKey , SystemUtils.getCenteredCoordinateX () );
	}

	/**
	 * Gets the dialog y coordinate. This is just for tidiness.
	 *
	 * @param dialog
	 * @param dialogKey
	 * @return
	 */
	private int getKeyY ( JDialog dialog , String dialogKey ) {
		return store.getInt ( dialogKey + yKey , SystemUtils.getCenteredCoordinateY () );
	}
}
