package com.github.errayeil.Actions.Menubar;

import com.github.errayeil.ui.Dialogs.Dialogs;
import com.github.errayeil.Persistence.Persistence;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * @author Errayeil
 * @version 0.1
 * @since 0.1
 */
public class RunGDAction implements ActionListener {

	/**
	 *
	 */
	public RunGDAction() {

	}

	@Override
	public void actionPerformed ( ActionEvent e ) {
		Persistence persist = Persistence.getInstance ();
		Desktop desktop = Desktop.getDesktop ();
		String path = persist.getDirectory ( persist.gdExeDirKey );

		if (path != null) {
			try {
				desktop.open ( new File (path) );
			} catch ( IOException ex ) {
				throw new RuntimeException ( ex );
			}
		} else {
			Dialogs.showMessageDialog ( "Could not run Grim Dawn.exe", "The Grim Dawn executable was not found.", 0);
		}
	}
}
