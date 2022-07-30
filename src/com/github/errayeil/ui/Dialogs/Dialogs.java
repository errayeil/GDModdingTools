package com.github.errayeil.ui.Dialogs;

import com.github.errayeil.ui.Dialogs.EntryData.FileChooserEntryData;
import com.github.errayeil.ui.Dialogs.EntryData.NameToolEntryData;
import com.github.errayeil.ui.Dialogs.EntryData.WeightToolEntryData;
import com.github.errayeil.Persistence.Persistence;
import com.github.errayeil.ui.Dialogs.Panels.*;

import javax.swing.JDialog;
import java.io.InputStream;

/**
 * @author ErraYeil
 * @version 1.0
 * @since 1.0
 */
public class Dialogs {

	/**
	 *
	 */
	private static Persistence config = Persistence.getInstance ( );


	/**
	 * Private constructor to prevent initialization.
	 */
	private Dialogs ( ) {

	}

	/**
	 * Custom method for displaying a JFileChooser because I plan on creating a custom JFileChooser at some point with
	 * expanded features. For now, JFileChooser will do.
	 *
	 * @param dialogTitle    The title for the Dialog
	 * @param selectionMode  Whether only files or directories can be selected, or both.
	 * @param multiSelection If multiple files or directories can be selected.
	 *
	 * @return
	 */
	public static FileChooserEntryData showFileChooserDialog ( String dialogTitle , int selectionMode , boolean multiSelection ) {
		JDialog dialog = new JDialog ( );
		FCPanel panel = new FCPanel ( );

		dialog.setTitle ( dialogTitle );
		dialog.setContentPane ( panel );

		panel.setFileSelectionMode ( selectionMode );
		panel.setMultipleSelection ( multiSelection );

		//Load persistence values so the dialog is always the same place and size as before
		if ( config.hasBeenRegistered ( "fcDialog" ) ) {
			config.loadConfig ( dialog , "fcDialog" ); //fc = file chooser
		} else {
			dialog.setLocationRelativeTo ( null );
			dialog.pack ( );
		}

		config.registerDialog ( dialog , "fcDialog" );

		dialog.setModal ( true );
		dialog.setVisible ( true );

		return panel.getReturnData ( );
	}

	/**
	 * @param dialogTitle The title that should be displayed in the title bar of the dialog.
	 *
	 * @return
	 */
	public static NameToolEntryData showNTInputDialog ( String dialogTitle ) {
		JDialog dialog = new JDialog ( );
		LTNTPanel panel = new LTNTPanel ( );

		dialog.setResizable ( false );
		dialog.setTitle ( dialogTitle );
		dialog.setContentPane ( panel );

		//Load persistence values so the dialog is always the same place and size as before
		if ( config.hasBeenRegistered ( "ntDialog" ) ) {
			config.loadConfig ( dialog , "ntDialog" ); //nt = name tool
		} else {
			dialog.setLocationRelativeTo ( null );
			dialog.pack ( );
		}

		config.registerDialog ( dialog , "ntDialog" );

		dialog.setModal ( true );
		dialog.setVisible ( true );

		return panel.getReturnData ( );
	}

	/**
	 * @param dialogTitle
	 *
	 * @return
	 */
	public static WeightToolEntryData showWTInputDialog ( String dialogTitle ) {
		JDialog dialog = new JDialog ( );
		LTWTPanel panel = new LTWTPanel ( );

		dialog.setResizable ( false );
		dialog.setTitle ( dialogTitle );
		dialog.setContentPane ( panel );

		//Load persistence values so the dialog is always the same place and size as before
		if ( config.hasBeenRegistered ( "wtDialog" ) ) {
			config.loadConfig ( dialog , "wtDialog" ); //wt = weight tool
		} else {
			dialog.setLocationRelativeTo ( null );
			dialog.pack ( );
		}

		config.registerDialog ( dialog , "wtDialog" );

		dialog.setModal ( true );
		dialog.setVisible ( true );

		return panel.getReturnData ( );
	}

	/**
	 * @param dialogTitle
	 * @param forEditor   Either lua,txt, or img
	 */
	public static void showChooseEditorDialog ( String dialogTitle , String forEditor ) {
		JDialog dialog = new JDialog ( );
		CEPanel panel = new CEPanel ( forEditor );

		dialog.setResizable ( false );
		dialog.setTitle ( dialogTitle );
		dialog.setContentPane ( panel );

		if ( config.hasBeenRegistered ( "ceDialog" ) ) {
			config.loadConfig ( dialog , "ceDialog" );
		} else {
			dialog.setLocationRelativeTo ( null );
			dialog.pack ( );
		}

		config.registerDialog ( dialog , "ceDialog" );

		dialog.setModal ( true );
		dialog.setVisible ( true );

	}

	/**
	 * @param dialogTitle
	 * @param message
	 * @param messageType
	 *
	 * @return
	 */
	public static void showMessageDialog ( String dialogTitle , String message , int messageType ) {

	}

	/**
	 * @param dialogTitle
	 * @param message
	 * @param progressStream
	 */
	public static void showProgressDialog ( String dialogTitle , String message , InputStream progressStream ) {
		JDialog dialog = new JDialog ( );
	}

	/**
	 *
	 * @param dialogTitle
	 * @param updateMessage The update message returned when the built-in update checker receives a message.
	 */
	public static void showUpdateDialog( String dialogTitle, String updateMessage) {

	}

	/**
	 *
	 * @param dialogTitle The title of the dialog
	 */
	public static void showSetupDialog( String dialogTitle) {
		JDialog dialog = new JDialog();
		SSPanel panel = new SSPanel (dialog);

		dialog.setResizable ( false );
		dialog.setTitle ( dialogTitle );
		dialog.setContentPane ( panel );

		if ( config.hasBeenRegistered ( "suDialog" ) ) {
			System.out.println ( "Registered" );
			config.loadConfig ( dialog , "suDialog" );
		} else {
			System.out.println("Not registered");
			dialog.setLocationRelativeTo ( null );
			dialog.pack ( );
		}

		config.registerDialog ( dialog , "suDialog" );

		dialog.setModal ( true );
		dialog.setVisible ( true );
	}

	/**
	 *
	 * @param dialogTitle
	 * @return
	 */
	public static String showInstalledAppListDialog(String dialogTitle) {
		JDialog dialog = new JDialog();
		IALPanel panel = new IALPanel ();

		dialog.setResizable ( false );
		dialog.setTitle ( dialogTitle );
		dialog.setContentPane ( panel );

		if ( config.hasBeenRegistered ( "ialDialog" ) ) {
			System.out.println ( "Registered" );
			config.loadConfig ( dialog , "ialDialog" );
		} else {
			System.out.println("Not registered");
			dialog.setLocationRelativeTo ( null );
			dialog.pack ( );
		}

		config.registerDialog ( dialog , "ialDialog" );

		dialog.setModal ( true );
		dialog.setVisible ( true );

		return panel.getSelectedApp ();
	}
}

