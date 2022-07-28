package com.github.errayeil.Persistence;

import com.github.errayeil.utils.Utils;

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
	 *
	 */
	private static Persistence persist;

	/**
	 *
	 */
	private Preferences store;

	/**
	 * Key strings used in addition to component names.
	 */
	public final String currentDirKey = "-currentDir";

	public final String widthKey = "-width";
	public final String heightKey = "-height";
	public final String xKey = "-x";
	public final String yKey = "-y";

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

	public final String prefEditorKey = "prefEditorFor";


	/**
	 *
	 */
	private Persistence ( ) {
		store = Preferences.userNodeForPackage ( Persistence.class );
	}

	/**
	 * @return
	 */
	public static Persistence getInstance ( ) {
		if ( persist == null )
			persist = new Persistence ( );

		return persist;
	}

	/**
	 *
	 * @return
	 */
	public Preferences getStore() {
		return store;
	}

	/**
	 * Only used for testing purposes.
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
	 * Applies changes made to the preferences store.
	 */
	private void push () {
		try {
			store.flush ();
		} catch ( BackingStoreException e ) {
			throw new RuntimeException ( e );
		}
	}

	/**
	 * Checks if the provided key has a value stored. If not, int prefs will return -1,
	 * thus the method returning false and string prefs will return "null", thus the method
	 * returning false.
	 *
	 * @param key The key to see if a value has been paired with.
	 * @return
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
	 * @param chooserName
	 */
	public void loadConfig ( JFileChooser chooser , String chooserName ) {
		chooser.setCurrentDirectory ( new File ( store.get ( chooserName + currentDirKey , System.getProperty ( "user.home" ) ) ) );
	}

	/**
	 * @param directoryName
	 * @param directoryPath
	 */
	public void registerDirectory ( String directoryName , String directoryPath ) {
		store.put ( directoryName , directoryPath );

		push ();
	}

	/**
	 * @param chooser
	 * @param chooserName
	 */
	public void registerFileChooser ( JFileChooser chooser , String chooserName ) {
		chooser.addPropertyChangeListener ( evt -> {
			String prop = evt.getPropertyName ( );

			if ( prop.equals ( JFileChooser.DIRECTORY_CHANGED_PROPERTY ) ) {
				File dir = chooser.getCurrentDirectory ( );

				if ( dir != null ) {
					store.put ( chooserName + currentDirKey , dir.getAbsolutePath ( ) );

					try {
						store.flush ( );
					} catch ( BackingStoreException e ) {
						throw new RuntimeException ( e );
					}
				}
			}
		} );
	}

	/**
	 * @param dialog     The component we are registering to listen for component changes.
	 * @param dialogName The name is used as a key to retrieve previous values or set new ones.
	 */
	public void registerDialog ( JDialog dialog , String dialogName ) {
		store.put ( dialogName, "registered" );
		push ();

		dialog.addComponentListener ( new ComponentAdapter ( ) {
			@Override
			public void componentResized ( ComponentEvent e ) {
				setonResizeValues ( dialog , dialogName );
			}

			@Override
			public void componentMoved ( ComponentEvent e ) {
				setonMovedValues ( dialog , dialogName );
			}

			@Override
			public void componentShown ( ComponentEvent e ) {
				setonShownValues ( dialog , dialogName );
			}
		} );
	}

	/**
	 * When the dialog is resized the current size values are pushed to the preferences
	 * store.
	 *
	 * @param dialog
	 * @param dialogName
	 */
	private void setonResizeValues ( JDialog dialog , String dialogName ) {
		store.putInt ( dialogName + widthKey , dialog.getWidth ( ) );
		store.putInt ( dialogName + heightKey , dialog.getHeight ( ) );

		push ();
	}

	/**
	 * When the dialog is moved the current x/y coordinate values are pushed to the preferences
	 * store.
	 *
	 * @param dialog
	 * @param dialogName
	 */
	private void setonMovedValues ( JDialog dialog , String dialogName ) {
		store.putInt ( dialogName + xKey , dialog.getX () );
		store.putInt ( dialogName + yKey , dialog.getY () );

		push ();
	}

	/**
	 * When the dialog is displayed the width/height and x/y coordinate values
	 * are applied to the dialog. These values are always dialog-independent,
	 * provided unique dialogName keys are supplied when registered.
	 *
	 * @param dialog
	 * @param dialogName
	 */
	private void setonShownValues ( JDialog dialog , String dialogName ) {
		int width = getKeyWidth ( dialog , dialogName );
		int height = getKeyHeight ( dialog , dialogName );
		int x = getKeyX ( dialog , dialogName );
		int y = getKeyY ( dialog , dialogName );

		dialog.setSize ( width , height );
		dialog.setLocation ( x , y );
	}

	/**
	 * Gets dialog width. This is just for tidiness.
	 *
	 * @param dialogName
	 * @return
	 */
	private int getKeyWidth ( JDialog dialog , String dialogName ) {
		return store.getInt ( dialogName + widthKey , dialog.getWidth ( ) );
	}

	/**
	 * Gets dialog height. This is just for tidiness.
	 *
	 * @param dialog
	 * @param dialogName
	 * @return
	 */
	private int getKeyHeight ( JDialog dialog , String dialogName ) {
		return store.getInt ( dialogName + heightKey , dialog.getHeight ( ) );
	}

	/**
	 * Gets the dialog x coordinate. This is just for tidiness.
	 *
	 * @param dialog
	 * @param dialogName
	 * @return
	 */
	private int getKeyX ( JDialog dialog , String dialogName ) {
		return store.getInt ( dialogName + xKey , Utils.getCenteredCoordinateX () );
	}

	/**
	 * Gets the dialog y coordinate. This is just for tidiness.
	 *
	 * @param dialog
	 * @param dialogName
	 * @return
	 */
	private int getKeyY ( JDialog dialog , String dialogName ) {
		return store.getInt ( dialogName + yKey , Utils.getCenteredCoordinateY () );
	}
}
