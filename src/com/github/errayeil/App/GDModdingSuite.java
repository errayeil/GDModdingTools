package com.github.errayeil.App;

import com.formdev.flatlaf.FlatDarkLaf;
import com.github.errayeil.Persistence.Persistence;
import com.github.errayeil.ui.Dialogs.Dialogs;
import com.github.errayeil.utils.SystemUtils;

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
		SystemUtils.setSystemUI ( new FlatDarkLaf () );

		Persistence.getInstance ().clear ();

		Dialogs.showSetupDialog ( "Setup now?" );
	}
}
