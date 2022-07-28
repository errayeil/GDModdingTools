package com.github.errayeil.ui.Dialogs;

import com.alexandriasoftware.swing.JInputValidator;
import com.alexandriasoftware.swing.JInputValidatorPreferences;
import com.alexandriasoftware.swing.Validation;
import com.github.errayeil.ui.Dialogs.EntryData.NameToolEntryData;
import com.github.errayeil.utils.Utils;
import io.codeworth.panelmatic.PanelMatic;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import static com.github.errayeil.utils.Utils.createFiller;
import static io.codeworth.panelmatic.componentbehavior.Modifiers.*;
import static io.codeworth.panelmatic.util.Groupings.lineGroup;

/**
 * @author Errayeil
 * @version 1.0
 * @since 1.0
 */
class LTNameToolInputPanel extends JComponent {

	/**
	 *
	 */
	private final JButton okButton;

	/**
	 * Shows selected loot table files
	 */
	private final JTextField lootTableFilesField;

	/**
	 * Shows selected directory containing records.
	 */
	private final JTextField recordsDirectoryField;

	/**
	 *
	 */
	private final NameToolEntryData data;

	/**
	 *
	 */
	private boolean ltFilesValid = false;

	/**
	 *
	 */
	private boolean recordFolderValid = false;

	/**
	 *
	 */
	public LTNameToolInputPanel ( ) {
		okButton = new JButton ( "Okay" );
		lootTableFilesField = new JTextField ( 50 );
		recordsDirectoryField = new JTextField ( 50 );
		data = new NameToolEntryData ( );
		JButton cancelButton = new JButton ( "Cancel" );
		JButton lootTableFCButton = new JButton ( "..." );
		JButton recordDirectoryFCButton = new JButton ( "..." );
		JLabel ltHeader = new JLabel ( "Select loot table files" );
		JLabel rHeader = new JLabel ( "Select records directory" );
		Font font = UIManager.getDefaults ( ).getFont ( "Label.font" ).deriveFont ( 14 ).deriveFont ( Font.BOLD );

		lootTableFilesField.setEditable ( false );
		recordsDirectoryField.setEditable ( false );
		lootTableFilesField.setFocusable ( false );
		recordsDirectoryField.setFocusable ( false );

		okButton.setEnabled ( false );
		ltHeader.setFont ( font );
		rHeader.setFont ( font );

		ActionListener al = e -> {
			SwingUtilities.windowForComponent ( LTNameToolInputPanel.this ).dispose ( );
		};

		okButton.addActionListener ( al );
		cancelButton.addActionListener ( al );

		lootTableFCButton.addActionListener ( ( a ) -> {
			JFileChooser chooser = Utils.getFileChooser ( "Choose Loot Table files" , JFileChooser.FILES_ONLY , true );

			if ( chooser.showOpenDialog ( null ) == JFileChooser.APPROVE_OPTION ) {
				File[] files = chooser.getSelectedFiles ( );
				iterateAndSet ( files );
			}
		} );

		recordDirectoryFCButton.addActionListener ( ( a ) -> {
			JFileChooser chooser = Utils.getFileChooser ( "Choose directory records reside in" , JFileChooser.DIRECTORIES_ONLY , false );

			if ( chooser.showOpenDialog ( null ) == JFileChooser.APPROVE_OPTION ) {
				String path = chooser.getSelectedFile ( ).getAbsolutePath ( );
				recordsDirectoryField.setText ( path );
				data.recordPath = path;
			}
		} );

		lootTableFilesField.setInputVerifier ( new JInputValidator ( lootTableFilesField ) {
			@Override
			protected Validation getValidation ( JComponent component , JInputValidatorPreferences preferences ) {
				String message = "";
				String determine;
				Validation.Type type = Validation.Type.NONE;

				if ( data.lootTableFiles.size ( ) == 1 ) {
					determine = "The selected file ";
				} else {
					determine = "One of the selected files ";
				}

				boolean valid = true;
				for ( String s : data.lootTableFiles ) {
					if ( !Utils.isValidLTFile ( new File ( s ) ) ) {
						message = determine + "is not a valid loot table file.";
						type = Validation.Type.WARNING;
						valid = false;
						break;
					}
				}
				ltFilesValid = valid;

				enableOkButton ( );
				return new Validation ( type , message , preferences );
			}
		} );

		recordsDirectoryField.setInputVerifier ( new JInputValidator ( recordsDirectoryField ) {
			@Override
			protected Validation getValidation ( JComponent component , JInputValidatorPreferences preferences ) {
				int invalid = Utils.countDBRFiles ( new File ( data.recordPath ) );
				String message = "";
				Validation.Type type = Validation.Type.NONE;

				if ( invalid == -1 ) {
					message = "The file selected is not a folder."; //This should never pop up but why not account for it just in case?
					type = Validation.Type.DANGER;
					recordFolderValid = false;
				} else if ( invalid >= 1 ) {
					message = "One or more files within the folder does not end with the .dbr extension.";
					type = Validation.Type.WARNING;
					recordFolderValid = false;
				} else {
					recordFolderValid = true;
				}

				enableOkButton ( );
				return new Validation ( type , message , preferences );
			}
		} );

		PanelMatic.begin ( this )
				.add ( createFiller ( 1 , 0 ) , ltHeader , L_START )
				.add ( lineGroup ( createFiller ( 5 , 5 ) , lootTableFilesField , createFiller ( 5 , 5 ) , lootTableFCButton , createFiller ( 5 , 5 ) ) ,
						L_START , L_END , GROW )
				.add ( createFiller ( 1 , 0 ) , rHeader , L_START )
				.add ( lineGroup ( createFiller ( 5 , 5 ) , recordsDirectoryField , createFiller ( 5 , 5 ) , recordDirectoryFCButton , createFiller ( 5 , 5 ) ) ,
						L_START , L_END )
				.addFlexibleSpace ( )
				.add ( lineGroup ( okButton , createFiller ( 5 , 5 ) , cancelButton ) , L_CENTER )
				.addFlexibleSpace ( )
				.get ( );
	}

	/**
	 * Returns the Data class containing the user entered information.
	 *
	 * @return LTNameToolData class
	 */
	public NameToolEntryData getReturnData ( ) {
		return data;
	}

	/**
	 * Iterates through the provided loot table files
	 * and sets the text for the ltfiles text field.
	 *
	 * @param ltFiles An array of loot table files
	 */
	private void iterateAndSet ( File[] ltFiles ) {
		String concat = "";
		data.lootTableFiles = new ArrayList<> ( );

		for ( File f : ltFiles ) {
			String name = f.getName ( );
			data.lootTableFiles.add ( f.getAbsolutePath ( ) );

			if ( concat.isEmpty ( ) ) {
				if ( ltFiles.length == 1 ) {
					concat = name;
				} else {
					concat = name + ",";
				}
			} else {
				concat = concat + name + ",";
			}
		}

		lootTableFilesField.setText ( concat );
	}

	/**
	 * Made into a method in case I need to add further functionality in the future.
	 */
	private void enableOkButton ( ) {
		okButton.setEnabled ( ltFilesValid && recordFolderValid );
	}
}
