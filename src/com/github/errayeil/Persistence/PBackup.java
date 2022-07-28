package com.github.errayeil.Persistence;

import java.io.*;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class PBackup {

	/**
	 *
	 */
	public PBackup() {

	}

	public void backupStore() throws BackingStoreException, IOException {
		Persistence persist = Persistence.getInstance ();
		Preferences store = persist.getStore ();

		File file = new File("C:\\Users\\Steven\\Desktop\\test.txt");
		FileOutputStream stream = new FileOutputStream ( file );

		store.exportSubtree ( stream );

		stream.flush ();
		stream.close ();
	}
}
