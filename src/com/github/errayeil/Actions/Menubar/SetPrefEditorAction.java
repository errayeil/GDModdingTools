package com.github.errayeil.Actions.Menubar;

import com.github.errayeil.ui.Dialogs.Dialogs;
import com.github.errayeil.Persistence.Persistence;

import javax.swing.JCheckBoxMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 */
public class SetPrefEditorAction implements ActionListener {

	/**
	 *
	 */
	private JCheckBoxMenuItem item;

	/**
	 *
	 */
	private String forEditor;

	/**
	 *
	 * @param item
	 */
	public SetPrefEditorAction ( JCheckBoxMenuItem item, final String forEditor) {
		this.item = item;
		this.forEditor = forEditor;
	}

	/**
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void actionPerformed ( ActionEvent e ) {
		Dialogs.showChooseEditorDialog ( "Choose preferred editor", forEditor);
		Persistence persist = Persistence.getInstance ();

		if (persist.hasBeenRegistered ( persist.prefEditorKey + forEditor )) {
			item.setSelected ( true );
			item.setEnabled ( false );
		}
	}
}
