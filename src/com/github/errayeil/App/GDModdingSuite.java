package com.github.errayeil.App;

import com.github.errayeil.Persistence.PBackup;
import com.github.errayeil.ui.Window.AMWindow;
import com.github.errayeil.utils.Utils;

import java.io.IOException;
import java.util.prefs.BackingStoreException;

/**
 * @author Errayeil
 * @version 1.0
 * @since 1.0
 */
public class GDModdingSuite {

	/**
	 *
	 */
	public GDModdingSuite ( ) {

	}

	/**
	 * @param args
	 */
	public static void main ( String[] args ) {
		Utils.setSystemUI ( );

		PBackup backup = new PBackup ();
		try {
			backup.backupStore ();
		} catch ( BackingStoreException | IOException e ) {
			throw new RuntimeException ( e );
		}
	}
}
