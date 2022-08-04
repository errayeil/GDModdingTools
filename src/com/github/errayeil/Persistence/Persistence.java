package com.github.errayeil.Persistence;

import com.github.errayeil.utils.SystemUtils;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Persistence class is a specialized wrapper around Preferences, in which it stores all data that needs to
 * be remembered and persistent throughout the application and even when the app is closed and opened again. <br>
 * You'll find there are a lot of keys and variables and that's because I want this to be as fluid of an experience as
 * possible. All dialog sizes, positions, directories, and the list goes on is remembered and utilized via this instance
 * wrapper. <br>
 *
 * @TODO: File backup of Preferences node:  &#10060
 * @TODO: Code Refactoring:  &#10060
 * @TODO: Documentation:  &#10060
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
	 * If changes are allowed to be made to the preferences store.
	 * This is used for testing purposes.
	 */
	private boolean allowChanges = true;

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
	 * Static class that contains all the String keys used for the Perferences store.
	 */
	public static class Keys {

		/**
		 * Below you will find a ton of public static String keys used for
		 * registering preferences to the preferences store. The point is to make it easier
		 * to write to the preferences store and minimizing mistakes. I think the variable names
		 * are descriptive enough but I'm happy to refactor them as needed.
		 */

		/*
		 * Keys for the File Finder.
		 */
		public static final String currentDirKey = "Finder.currentDirectory";
		public static final String showHiddenKey = "Finder.List.showHidden";
		public static final String showFileStatsKey = "Finder.List.showFileStats";
		public static final String sortNameAscendKey = "Finder.sortByNameAscend";
		public static final String sortTypeAscendKey = "Finder.sortByTypeAscend";
		public static final String sortSizeAscendKey = "Finder.sortBySizeAscend";
		public static final String sortNameDescendKey = "Finder.sortByNameDescend";
		public static final String sortTypeDescendKey = "Finder.sortByTypeDescend";
		public static final String sortSizeDescendKey = "Finder.sortBySizeDescend";
		public static final String sortFilesKey = "Finder.sortByFilesFirst";
		public static final String sortFoldersKey = "Finder.sortByFoldersFirst";
		public static final String fileFilterKey = "Finder.fileFilter";

		/*
		 * Finder filter keys.
		 */
		public static final String allFilterKey = "FinderFilter.All";
		public static final String anmFilterKey = "FinderFilter.Anm";
		public static final String arcFilterKey = "FinderFilter.Arc\\z";
		public static final String cnvFilterKey = "FinderFilter.Cnv";
		public static final String dbrFilterKey = "FinderFilter.Dbr";
		public static final String fntFilterKey = "FinderFilter.Fnt";
		public static final String luaFilterKey = "FinderFilter.Lua";
		public static final String mshFilterKey = "FinderFilter.Mesh";
		public static final String pfxFilterKey = "FinderFilter.Pfx";
		public static final String qstFilterKey = "FinderFilter.Qst";
		public static final String sshFilterKey = "FinderFilter.Ssh";
		public static final String texFilterKey = "FinderFilter.Tex";
		public static final String tplFilterKey = "FinderFilter.Tpl";
		public static final String txtFilterKey = "FinderFilter.Txt";
		public static final String wavFilterKey = "FinderFilter.Wav";
		public static final String gdfFilterKey = "FinderFilter.gdFiles";
		public static final String lvlFilterKey = "FinderFilter.gdLvlFiles";
		public static final String ptfFilterKey = "FinderFilter.plainTxtFiles";


		/*
		 * Keys for registering dialog size and width.
		 */
		public static final String widthKey = "Dialog.width";
		public static final String heightKey = "Dialog.height";
		public static final String xKey = "Dialog.x";
		public static final String yKey = "Dialog.y";

		/*
		 * Keys for registering grim dawn modding directories.
		 */
		public static final String pathKey = "-path";
		public static final String gdDirKey = "Grim Dawn.installDirectory";
		public static final String gdBuildDirKey = "Grim Dawn.buildDirectory";
		public static final String gdWorkingDirKey = "Grim Dawn.workingDirectory";
		public static final String gdToolDirKey = "Grim Dawn.toolsDirectory";
		//public static final String workspaceDirKey = "workspaceDir";
		public static final String exportedTexDirKey = "Grim Dawn.exportedTexDirectory";
		public static final String exportedPakDirKey = "Grim Dawn.exportedPakDirectory";
		public static final String backupDirKey = "Grim Dawn.backupsDirectory";
		public static final String gdExeDirKey = "Grim Dawn.grimDawnExePath";

		/*
		 * Keys for GD modding tools.
		 */
		public static final String aifEditorKey = "aifeditor.exe-path";
		public static final String animCompKey = "animationcompiler.exe-path";
		public static final String archiveToolKey = "archivetool.exe-path";
		public static final String assetManageKey = "assetManager.exe-path";
		public static final String bitmapCreateKey = "bitmapcreator.exe-path";
		public static final String convoEditKey = "conversationeditor.exe-path";
		public static final String dbrEditorKey = "dbreditor.exe-path";
		public static final String worldEditorKey = "editor.exe-path";
		public static final String fontCompileKey = "fontcompiler.exe-path";
		public static final String mapCompileKey = "mapcompile.exe-path";
		public static final String modelCompileKey = "modelcompiler.exe-path";
		public static final String psEditorKey = "pseditor.exe-path";
		public static final String questEditKey = "questeditor.exe-path";
		public static final String shaderCompileKey = "shadercompiler.exe-path";
		public static final String sourceServerKey = "sourceserver.exe-path";
		public static final String texCompileKey = "texturecompiler.exe-path";
		public static final String texViewerKey = "texviewer.exe-path";
		public static final String meshViewerKey = "viewer.exe-path";

		/*
		 * Keys for registering preferred editors for lua, txt, and image type files.
		 */
		public static final String prefEditorKey = "prefEditorFor";
		public static final String luaKey = "lua";
		public static final String txtKey = "txt";
		public static final String imgKey = "img";
		public static final String builtInKey = "USE BUILT-IN";

		/*
		 * Key for registering setup completion.
		 */
		public static final String setupCompleteKey = "setup-completed";

		/*
		 * Keys for registering GDModdingSuite directories.
		 */
		public static final String suiteInstallDirKey = "Suite.installDirectory";
		public static final String suiteLogFileDirKey = "Suite.logFileDirectory";
		public static final String suiteUpdateDirKey = "Suite.updateFileDirectory";
		public static final String suiteLogFilePathKey = "Suite.logFilePath";
		public static final String suiteVersionKey = "Suite.version";


		private Keys() {}
	}

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
	 *
	 * @param key
	 */
	public void remove(String key) {
		store.remove ( key );
		push ();
	}

	/**
	 * Registers setup has been completed. Once this is registered, the app will load straight into
	 * AssetManager, skipping the setup dialog.
	 */
	public void registerSetupCompletion() {
		store.putBoolean ( Keys.setupCompleteKey, true );
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
		return store.getBoolean ( Keys.setupCompleteKey, false );
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
		if (key.contains ( Keys.widthKey ) || key.contains ( Keys.heightKey ) || key.contains ( Keys.xKey ) || key.contains ( Keys.yKey )) {
			return store.getInt ( key, -1 ) != -1;
		}  else {
			return !store.get ( key, "null" ).equals ( "null" );
		}
	}

	/**
	 *
	 * @param finderKey
	 * @return
	 */
	public boolean getFinderValue ( String finderKey) {
		return store.getBoolean ( finderKey, false );
	}

	/**
	 *
	 * @param finderKey
	 * @param finderValue
	 */
	public void setFinderValue (String finderKey, boolean finderValue) {
		store.putBoolean ( finderKey, finderValue );

		push ();
	}

	/**
	 * Sets the value of the specified key for Finder related data.
	 *
	 * @see Keys
	 * @param finderKey
	 * @param finderValue
	 */
	public void setFinderValue ( String finderKey, String finderValue) {
		store.put ( finderKey, finderValue );

		push ();
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
	public void loadAndRegister( JFileChooser chooser, String chooserKey) {
		if (hasBeenRegistered ( chooserKey )) {
			loadConfig ( chooser, chooserKey );
		} else {
			//chooser.setNullLocationRelative ( true );
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
	public void loadConfig ( JFileChooser chooser , String chooserKey ) {
		//chooser.setCurrentDirectory ( new File ( store.get ( chooserKey + Keys.currentDirKey , System.getProperty ( "user.home" ) ) ) );

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
	public void registerFileChooser ( JFileChooser chooser , String chooserKey ) {
//		chooser.addPropertyChangeListener ( evt -> {
//			String prop = evt.getPropertyName ( );
//
//			if ( prop.equals ( JFileChooser.DIRECTORY_CHANGED_PROPERTY ) ) {
//				File dir = chooser.getCurrentDirectory ( );
//
//				if ( dir != null ) {
//					store.put ( chooserKey + Keys.currentDirKey , dir.getAbsolutePath ( ) );
//
//					try {
//						store.flush ( );
//					} catch ( BackingStoreException e ) {
//						throw new RuntimeException ( e );
//					}
//				}
//			}
//		} );

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
		store.putInt ( dialogKey + Keys.widthKey , dialog.getWidth ( ) );
		store.putInt ( dialogKey + Keys.heightKey , dialog.getHeight ( ) );

		push ();
	}

	/**
	 *
	 * @param chooser
	 * @param chooserKey
	 */
	private void setonResizeValues (JFileChooser chooser, String chooserKey) {

	}

	/**
	 * When the dialog is moved the current x/y coordinate values are pushed to the preferences
	 * store.
	 *
	 * @param dialog
	 * @param dialogKey
	 */
	private void setonMovedValues ( JDialog dialog , String dialogKey ) {
		store.putInt ( dialogKey + Keys.xKey , dialog.getX () );
		store.putInt ( dialogKey + Keys.yKey , dialog.getY () );

		push ();
	}

	/**
	 *
	 * @param chooser
	 * @param chooserKey
	 */
	private void setonMovedValues (JFileChooser chooser, String chooserKey) {

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
	private void setonShownValues (JFileChooser chooser, String chooserKey) {

	}

	/**
	 * Gets dialog width. This is just for tidiness.
	 *
	 * @param dialogKey
	 * @return
	 */
	private int getKeyWidth ( JDialog dialog , String dialogKey ) {
		return store.getInt ( dialogKey + Keys.widthKey , dialog.getWidth ( ) );
	}

	/**
	 * Gets dialog height. This is just for tidiness.
	 *
	 * @param dialog
	 * @param dialogKey
	 * @return
	 */
	private int getKeyHeight ( JDialog dialog , String dialogKey ) {
		return store.getInt ( dialogKey + Keys.heightKey , dialog.getHeight ( ) );
	}

	/**
	 * Gets the dialog x coordinate. This is just for tidiness.
	 *
	 * @param dialog
	 * @param dialogKey
	 * @return
	 */
	private int getKeyX ( JDialog dialog , String dialogKey ) {
		return store.getInt ( dialogKey + Keys.xKey , SystemUtils.getCenteredCoordinateX () );
	}

	/**
	 * Gets the dialog y coordinate. This is just for tidiness.
	 *
	 * @param dialog
	 * @param dialogKey
	 * @return
	 */
	private int getKeyY ( JDialog dialog , String dialogKey ) {
		return store.getInt ( dialogKey + Keys.yKey , SystemUtils.getCenteredCoordinateY () );
	}
}
