package com.github.errayeil.Actions.Menubar;

import com.github.errayeil.Persistence.Persistence;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @author Errayeil
 * @version 0.1
 * @since 0.1
 */
public class SetWorkspaceDirectoryAction implements ActionListener {

	/**
	 *
	 */
	private JCheckBoxMenuItem item;

	/**
	 *
	 * @param item
	 */
	public SetWorkspaceDirectoryAction ( JCheckBoxMenuItem item ) {
		this.item = item;
	}

	/**
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void actionPerformed ( ActionEvent e ) {
		File selected = null;
		JFileChooser chooser = new JFileChooser (  );
		chooser.setFileSelectionMode ( JFileChooser.DIRECTORIES_ONLY );
		chooser.setMultiSelectionEnabled ( false );
		Persistence persist = Persistence.getInstance ();

		if (chooser.showOpenDialog ( null ) == JFileChooser.APPROVE_OPTION) {
			selected = chooser.getSelectedFile ();
		}

		if (selected != null && selected.isDirectory ()) {
			if (selected.getAbsolutePath ().endsWith ( "Grim Dawn" )) {
				persist.registerDirectory ( persist.workspaceDirKey, selected.getAbsolutePath () + File.separator + "mods");
			} else {
				persist.registerDirectory ( persist.workspaceDirKey, selected.getAbsolutePath () );
			}

			item.setSelected ( true );
			item.setEnabled ( false );
		}
	}
}
