package com.github.errayeil.App;

import com.formdev.flatlaf.FlatDarkLaf;
import com.github.errayeil.Persistence.Persistence;
import com.github.errayeil.ui.finder.Filters.TPLFileFilter;
import com.github.errayeil.ui.finder.List.FinderListPane;
import com.github.errayeil.utils.SystemUtils;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Errayeil
 * @version 1.0
 * @since 1.0
 */
public class GDModdingSuite {

	private static FinderListPane pane;

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

		Persistence.getInstance ( ).clear ( );

		String path = "C:\\Applications\\Gaming Apps\\Steam\\steamapps\\common\\Grim Dawn\\database\\templates";

		try {
			SwingUtilities.invokeAndWait ( () -> {
				pane = new FinderListPane ( new File ( path ) );
				pane.setShowFileStats ( true );

				JDialog dialog = new JDialog ( );
				dialog.setContentPane ( pane );
				dialog.setLocationRelativeTo ( null );
				dialog.pack ( );
				dialog.setVisible ( true );
			});
		} catch ( InterruptedException | InvocationTargetException e ) {
			throw new RuntimeException ( e );
		}

	}
}
