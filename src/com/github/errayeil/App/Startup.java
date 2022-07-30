package com.github.errayeil.App;

import com.github.errayeil.Persistence.Persistence;
import com.github.errayeil.ui.Dialogs.Dialogs;

public class Startup {

	/**
	 *
	 */
	private final Persistence persist = Persistence.getInstance ();

	/**
	 *
	 */
	private Startup() {
		checkSetup ();
	}

	/**
	 *
	 * @param args
	 */
	public static void main (String[] args) {

	}

	private void checkSetup() {
		if ( !persist.isSetupCompleted ( ) ) {
			Dialogs.showSetupDialog ( "Start setup?");
		}
	}

	/**
	 *
	 */
	private void checkResources() {
		if ( !persist.hasBeenRegistered ( persist.suiteInstallDirKey) ) {
			//TODO
		}

		if ( !persist.hasBeenRegistered ( persist.suiteUpdateDirKey)) {
			//TODO
		}

		if ( !persist.hasBeenRegistered ( persist.suiteLogFileDirKey)) {
			//TODO
		}

		if (!persist.hasBeenRegistered ( persist.suiteLogFilePathKey)) {
			//TODO
		}
	}

	/**
	 *
	 */
	private void checkUpdates() {

	}

	/**
	 *
	 */
	private void start() {

	}
}
