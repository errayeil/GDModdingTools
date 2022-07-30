package com.github.errayeil.utils;

import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ToolsUtils {

	/**
	 *
	 */
	private final static String[] validLootTableClasses = {
			"lootitemtable_dynweighted_dynaffix.tpl" , "lootitemtable_dynweight.tpl" ,
			"lootitemtable_fixedweight.tpl" , "lootitemtable_dynitemlist.tpl" };


	private ToolsUtils() {}

	/**
	 * Returns a list of lines from the specified file.
	 *
	 * @return
	 */
	public static List<String> readLinesFromRecord ( final File fileToRead ) throws IOException {
		BufferedReader reader = new BufferedReader ( new FileReader ( fileToRead ) );
		Stream<String> lines = reader.lines ( );
		List<String> list = lines.toList ( );
		reader.close ( );

		return list;
	}

	/**
	 * Replaces the comma separators in loot table records with the specified string.
	 *
	 * @param toModify    The String that is being modified.
	 * @param replacement The String that replaces the ",," or ",0," segments in toModify.
	 * @param replace0    Set to true if ",0," is needing replacement instead.
	 *
	 * @return
	 */
	public static String replaceCommasWith ( String toModify , String replacement , boolean replace0 ) {
		if ( !replace0 ) {
			return toModify.replace ( ",," , "," + replacement + "," ); //For lootWeight without 0's or lootName variables
		} else {
			return toModify.replace ( ",0," , "," + replacement + "," ); //For lootWeight with the value set to 0.
		}
	}

	/**
	 * Trims the file path of a record file and replaces the backslashes with forward slashes
	 * so the path can be ready to write or paste to a record line.
	 * @param recordFilePath
	 *
	 * @return
	 */
	public static String trimRecord ( String recordFilePath ) {
		int index = recordFilePath.indexOf ( "\\records" );
		String ss = recordFilePath.replace ( "\\" , "/" );
		return ss.substring ( index + 1 );
	}

	/**
	 * Checks to see if the provided loot table file is actually a loot table file. This is based
	 * off the template set to the file.
	 *
	 * @param ltFile The loot table we're validating
	 *
	 * @return
	 */
	public static boolean isValidLTFile ( File ltFile ) {
		List<String> lines = null;
		try {
			lines = readLinesFromRecord ( ltFile );
		} catch ( IOException e ) {
			//TODO log
			JOptionPane.showMessageDialog ( null , "Could not validate selected file." , "Validation failed" , JOptionPane.ERROR_MESSAGE );
			return false;
		}

		for ( String line : lines ) {
			if ( line.contains ( validLootTableClasses[ 0 ] ) || line.contains ( validLootTableClasses[ 1 ] ) ) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks to see if the provided loot table file is set from the template 'lootitemtable_dynweight.tpl',
	 * since that type of loot table has one variable for loot.
	 *
	 * @param ltFile The loot table file we are checking.
	 *
	 * @return
	 */
	public static boolean isLTListTable ( File ltFile ) {
		List<String> lines = null;
		try {
			lines = readLinesFromRecord ( ltFile );
		} catch ( IOException e ) {
			throw new RuntimeException ( e );
		}

		for ( String line : lines )
			if ( line.contains ( "lootitemtable_dynweight.tpl" ) ) {
				return true;
			}

		return false;
	}

	/**
	 * Checks to see how many files in the provided path are not dbr files.
	 * @param path
	 *
	 * @return
	 */
	public static int countDBRFiles ( File path ) {
		if ( !path.isDirectory ( ) )
			return -1;

		File[] files = path.listFiles ( );

		int invalid = 0;
		for ( File f : files ) {
			if ( !f.getAbsolutePath ( ).endsWith ( "dbr" ) ) {
				invalid++;
			}
		}
		return invalid;
	}

	/**
	 * Returns a list of all GD tools in the Grim Dawn installation directory.
	 *
	 * @return
	 */
	public static List<String> getGDToolsAsList ( ) {
		final String[] tools = { "AifEditor.exe" , "AnimationCompiler.exe" , "ArchiveTool.exe" , "AssetManager.exe" ,
				"BitmapCreator.exe" , "ConversationEditor.exe" , "DBREditor.exe" , "Editor.exe" , "FontCompiler.exe" , "MapCompiler.exe" ,
				"ModelCompiler.exe" , "PSEditor.exe" , "QuestEditor.exe" , "ShaderCompiler.exe" , "SourceServer.exe" , "TextureCompiler.exe" ,
				"TexViewer.exe" , "Viewer.exe" };

		return Arrays.asList ( tools );
	}
}
