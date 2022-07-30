package com.github.errayeil.Actions.Menubar;

import com.github.errayeil.ui.Window.AMMenubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 */
public class CreateModAction implements ActionListener {

	/**
	 *
	 */
	private AMMenubar menubar;

	/**
	 *
	 */
	public CreateModAction( AMMenubar menubar ) {
		this.menubar = menubar;
	}

	/**
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void actionPerformed ( ActionEvent e ) {




		menubar.reloadModMenu ();
	}
}
