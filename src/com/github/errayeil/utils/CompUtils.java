package com.github.errayeil.utils;

import com.github.errayeil.Persistence.Persistence;

import javax.swing.Box;
import javax.swing.JFileChooser;
import java.awt.Dimension;

/**
 * @author Errayeil
 * @version 0.1
 * @since 0.1
 */
public class CompUtils {

	/**
	 * File chooser used for various processes in the GDModdingSuite.
	 */
	private static JFileChooser chooser;

	/**
	 *
	 */
	private final static Persistence config = Persistence.getInstance ();

	/**
	 *
	 */
	private CompUtils() {}

	/**
	 * Gets a JFileChooser configured with the specified parameters.
	 * TODO: custom file chooser, AMFileChooser is not final
	 *
	 * @param dialogTitle       The title of the file chooser dialog.
	 * @param selectionMode     The selection mode of the chooser
	 * @param multipleSelection If multiple directory/file selection is allowed.
	 *
	 * @return The new file chooser
	 */
	public static JFileChooser getFileChooser ( String dialogTitle , int selectionMode , boolean multipleSelection ) {
		if ( chooser == null )
			chooser = new JFileChooser ( );

		config.registerFileChooser ( chooser , "utilsChooser" );

		chooser.setDialogTitle ( dialogTitle );
		chooser.setFileSelectionMode ( selectionMode );
		chooser.setMultiSelectionEnabled ( multipleSelection );

		return chooser;
	}

	/**
	 * Creates a space filling JComponent to add some padding in between components
	 * in panels when building with PanelMatic.
	 *
	 * @see io.codeworth.panelmatic.PanelMatic
	 * @param width  The width the filler should have.
	 * @param height The height the filler should have.
	 *
	 * @return The newly create Box.Filler JComponent.
	 */
	public static Box.Filler fill ( int width , int height ) {
		Dimension dim = new Dimension ( width , height );
		return new Box.Filler ( dim , dim , dim );
	}
}
