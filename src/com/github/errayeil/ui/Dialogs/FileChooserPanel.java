package com.github.errayeil.ui.Dialogs;

import com.github.errayeil.ui.Dialogs.EntryData.FileChooserEntryData;
import io.codeworth.panelmatic.PanelMatic;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import java.awt.event.ActionListener;
import java.io.File;

import static com.github.errayeil.utils.Utils.createFiller;
import static io.codeworth.panelmatic.componentbehavior.Modifiers.*;
import static io.codeworth.panelmatic.util.Groupings.lineGroup;

public class FileChooserPanel extends JComponent {

	/**
	 *
	 */
	private final JFileChooser chooser;

	/**
	 *
	 */
	private final JButton okButton;

	/**
	 *
	 */
	private FileChooserEntryData data;

	/**
	 *
	 */
	public FileChooserPanel ( ) {
		chooser = new JFileChooser ( );
		okButton = new JButton ( "Okay" );
		data = new FileChooserEntryData ( );
		JButton cancelButton = new JButton ( "Cancel" );

		//TODO custom popup menu

		ActionListener al = e -> {
			if ( e.getSource ( ) == okButton ) {
				if ( chooser.isMultiSelectionEnabled ( ) ) {
					data.selectedFiles = chooser.getSelectedFiles ( );
				} else {
					File[] file = new File[ 1 ];
					file[ 0 ] = chooser.getSelectedFile ( );
					data.selectedFiles = file;
				}
			} else {
				data.selectedFiles = new File[ 0 ];
			}

			SwingUtilities.windowForComponent ( FileChooserPanel.this ).dispose ( );
		};

		okButton.setEnabled ( false );
		okButton.addActionListener ( al );
		cancelButton.addActionListener ( al );

		chooser.setControlButtonsAreShown ( false );
		chooser.addPropertyChangeListener ( evt -> {
			String prop = evt.getPropertyName ( );
			if ( prop.equals ( JFileChooser.SELECTED_FILE_CHANGED_PROPERTY ) || prop.equals ( JFileChooser.SELECTED_FILES_CHANGED_PROPERTY ) ) {
				if ( chooser.getSelectedFile ( ) != null || chooser.getSelectedFiles ( ) != null ) {
					okButton.setEnabled ( true );
				} else {
					okButton.setEnabled ( false );
				}
			}
		} );

		PanelMatic.begin ( this )
				.add ( chooser , GROW )
				.add ( lineGroup ( okButton , createFiller ( 5 , 0 ) , cancelButton ) , L_CENTER , P_FEET )
				.add ( createFiller ( 0 , 5 ) , L_CENTER )
				.get ( );
	}

	/**
	 * @param optionType
	 */
	public void setFileSelectionMode ( int optionType ) {
		chooser.setFileSelectionMode ( optionType );
	}

	/**
	 * @param multipleSelection
	 */
	public void setMultipleSelection ( boolean multipleSelection ) {
		chooser.setMultiSelectionEnabled ( multipleSelection );
	}

	/**
	 * @return
	 */
	public FileChooserEntryData getReturnData ( ) {
		return data;
	}
}
