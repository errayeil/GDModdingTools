package com.github.errayeil.Actions.Menubar;

import com.github.errayeil.ui.Window.AMMenubar;
import com.github.errayeil.ui.Window.AMWindow;

import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseAMAction implements ActionListener {

	/**
	 *
	 */
	private JMenuBar menubar;

	/**
	 *
	 * @param window
	 */
	public CloseAMAction( JMenuBar menubar ) {
		this.menubar = menubar;
	}

	@Override
	public void actionPerformed ( ActionEvent e ) {
		SwingUtilities.windowForComponent ( menubar ).dispose ();;
	}
}
