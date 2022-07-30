package com.github.errayeil.utils;

import com.formdev.flatlaf.FlatDarkLaf;
import com.github.errayeil.Persistence.Persistence;
import com.github.errayeil.ui.Custom.AMFileChooser;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.Toolkit;
import java.io.File;

/**
 * @author Errayeil
 * @version 1.0
 * @since 1.0
 */
public class SystemUtils {


	/**
	 *
	 */
	private static Persistence config = Persistence.getInstance ( );

	/**
	 *
	 */
	private SystemUtils ( ) {

	}

	/**
	 *
	 */
	public static void setSystemUI ( final LookAndFeel laf) {
		/**
		 * FlatMaterialDesignDarkIJTheme
		 * FlatDarkLAF
		 * FlatArcDarkIJTheme
		 *
		 */
		try {
			UIManager.setLookAndFeel ( laf );
		} catch ( UnsupportedLookAndFeelException e ) {
			throw new RuntimeException ( e );
		}
	}

	/**
	 *
	 * @param path
	 * @return
	 */
	public static Icon getSystemIcon ( final String path ) {
		return FileSystemView.getFileSystemView ( ).getSystemIcon ( new File ( path ) );
	}

	/**
	 *
	 * TODO account for multi-monitor setup
	 * @return
	 */
	public static int getCenteredCoordinateX ( ) {
		return Toolkit.getDefaultToolkit ( ).getScreenSize ( ).width / 2;
	}

	/**
	 * //TODO account for multi-monitor setup
	 * @return
	 */
	public static int getCenteredCoordinateY ( ) {
		return Toolkit.getDefaultToolkit ( ).getScreenSize ( ).height / 2;
	}

}
