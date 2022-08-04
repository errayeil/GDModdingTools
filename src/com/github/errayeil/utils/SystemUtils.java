package com.github.errayeil.utils;

import com.github.errayeil.Persistence.Persistence;
import com.sun.jna.platform.win32.COM.IShellFolder;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

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
	public static void setSystemUI ( final LookAndFeel laf ) {
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
	 * @see <a href="https://stackoverflow.com/a/3758880"> Method source </a>
	 * @param bytes
	 * @return
	 */
	public static String humanReadableByteCountSI ( long bytes ) {
		if ( -1000 < bytes && bytes < 1000 ) {
			return bytes + " B";
		}
		CharacterIterator ci = new StringCharacterIterator ( "kMGTPE" );
		while ( bytes <= -999_950 || bytes >= 999_950 ) {
			bytes /= 1000;
			ci.next ( );
		}
		return String.format ( "%.1f %cB" , bytes / 1000.0 , ci.current ( ) );
	}

	/**
	 * @param path
	 *
	 * @return
	 */
	public static Icon getSystemIcon ( final String path ) {
		Icon icon = FileSystemView.getFileSystemView ( ).getSystemIcon ( new File ( path ));
		//TODO stuff
		return icon;
	}

	/**
	 * TODO account for multi-monitor setup
	 *
	 * @return
	 */
	public static int getCenteredCoordinateX ( ) {
		return Toolkit.getDefaultToolkit ( ).getScreenSize ( ).width / 2;
	}

	/**
	 * //TODO account for multi-monitor setup
	 *
	 * @return
	 */
	public static int getCenteredCoordinateY ( ) {
		return Toolkit.getDefaultToolkit ( ).getScreenSize ( ).height / 2;
	}

}
