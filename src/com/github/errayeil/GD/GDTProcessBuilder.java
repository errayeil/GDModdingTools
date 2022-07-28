package com.github.errayeil.GD;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * GDTProcessBuilder provides the functionality needed to open files in the respective mod tools right from the
 * AssetManagerTool. The AssetManagerTool is set up similarly to Crate's AssetManager but with the ability to
 * open most respective mod files in their default application. Tex files can be opened right into TexViewer from
 * the AssetManagerTool.
 * <p></p>
 * Certain files (such as pfx) cannot be opened directly into their editors.
 *
 * @author Errayeil
 * @version 1.0
 * @since 1.0
 */
public class GDTProcessBuilder {

	/**
	 *
	 */
	public GDTProcessBuilder ( ) {

	}

	/**
	 * @param arzPath
	 * @param destination
	 */
	public void extractDatabase ( File arzPath , File destination ) {

	}

	/**
	 * @param arcPath
	 * @param destination
	 */
	public void extractResources ( File arcPath , File destination ) {

	}

	/**
	 * @param path
	 */
	public void runToolAt ( File path ) {
		Desktop desktop = Desktop.getDesktop ( );

		try {
			desktop.open ( path );
		} catch ( IOException e ) {
			throw new RuntimeException ( e );
		}
	}

	public void openFileWith ( String fileToOpenPath , String programToRunPath ) {
		ProcessBuilder builder = new ProcessBuilder ( );
		List<String> list = new ArrayList<> ( );

		list.add ( programToRunPath );
		list.add ( fileToOpenPath );

		builder.command ( list );

		try {
			builder.start ( );
		} catch ( IOException e ) {
			throw new RuntimeException ( e );
		}
	}

	public void runCmdPrompt ( ) throws IOException {
		ProcessBuilder builder = new ProcessBuilder ( );

		builder.command ( "cmd.exe" );
		Process process = builder.start ( );

		BufferedReader stdInput
				= new BufferedReader ( new InputStreamReader (
				process.getInputStream ( ) ) );
		String s = null;
		while ( ( s = stdInput.readLine ( ) ) != null ) {
			System.out.println ( s );
		}
	}

	/**
	 * @param path
	 */
	public void runCMDt ( File path ) {

	}

	/**
	 * @param path
	 */
	public void openTexFile ( File path ) {

	}

	/**
	 * @param path
	 */
	public void openQuestFile ( File path ) {

	}

	/**
	 * @param path
	 */
	public void openConversationFile ( File path ) {

	}

	/**
	 * @param path
	 */
	public void openLuaFile ( File path ) {

	}

	/**
	 * @param path
	 */
	public void openTextFile ( File path ) {

	}

	/**
	 * @param path
	 */
	public void openDBRFile ( File path ) {

	}
}
