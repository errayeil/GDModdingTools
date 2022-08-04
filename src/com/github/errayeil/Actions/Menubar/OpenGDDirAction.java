package com.github.errayeil.Actions.Menubar;

import com.github.errayeil.Persistence.Persistence.Keys;
import com.github.errayeil.ui.Dialogs.Dialogs;
import com.github.errayeil.Persistence.Persistence;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class OpenGDDirAction implements ActionListener {

	/**
	 *
	 */
	private final Persistence config = Persistence.getInstance ();

	@Override
	public void actionPerformed ( ActionEvent e ) {
		Desktop desktop = Desktop.getDesktop ();
		Persistence persist = Persistence.getInstance ();
		String path = config.getDirectory ( Keys.gdDirKey );

		if (path != null) {
			try {
				desktop.open ( new File (path) );
			} catch ( IOException ex ) {
				throw new RuntimeException ( ex );
			}
		} else {
			Dialogs.showMessageDialog ( "Could not open folder", "The Grim Dawn directory was not found.", 0);
		}
	}
}
