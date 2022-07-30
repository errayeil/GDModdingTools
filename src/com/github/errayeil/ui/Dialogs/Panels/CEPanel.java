package com.github.errayeil.ui.Dialogs.Panels;

import com.alexandriasoftware.swing.JInputValidator;
import com.alexandriasoftware.swing.JInputValidatorPreferences;
import com.alexandriasoftware.swing.Validation;
import com.alexandriasoftware.swing.Validation.Type;
import com.github.errayeil.Persistence.Persistence;
import io.codeworth.panelmatic.PanelMatic;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.github.errayeil.utils.CompUtils.fill;
import static io.codeworth.panelmatic.componentbehavior.Modifiers.GROW;
import static io.codeworth.panelmatic.componentbehavior.Modifiers.L_CENTER;
import static io.codeworth.panelmatic.util.Groupings.lineGroup;

/**
 * @author Errayeil
 * @version 0.1
 * @since 0.1
 */
public class CEPanel extends JComponent {

	/**
	 *
	 */
	private final JButton okButton;

	/**
	 * @param forEditor Which file type we are registering the preference for .
	 */
	public CEPanel ( String forEditor ) {
		JFileChooser chooser = new JFileChooser ( );
		okButton = new JButton ( "Okay" );
		JButton cancelButton = new JButton ( "Cancel" );
		JButton fcButton = new JButton ( "..." );
		JTextField filePathField = new JTextField ( 30 );
		JLabel dHeader = new JLabel ( "Set preferred file editor for: " +  "." + forEditor + " files." );
		Font font = UIManager.getDefaults ( ).getFont ( "Label.font" ).deriveFont ( 14 ).deriveFont ( Font.BOLD );

		String text = "";
		boolean enable = true;
		if ( forEditor.equalsIgnoreCase ( "lua" ) || forEditor.equalsIgnoreCase ( "txt" )) {
			text = "Use built-in editor?";
		} else if (forEditor.equalsIgnoreCase ( "img" )) {
			text = "There is not a built-in editor for image files. (yet)";
			enable = false;
		} else {
			text = "File type is not supported.";
			enable = false;
		}

		JCheckBox useBIEditorCheck = new JCheckBox ( text );
		useBIEditorCheck.setEnabled ( enable );

		ActionListener al1 = e -> {
			if ( e.getSource ( ) == okButton ) {
				Persistence persist = Persistence.getInstance ();
				String path = "";
				if (useBIEditorCheck.isSelected ()) {
					path = "builtIn";
				} else {
					if (!filePathField.getText ().isEmpty ()) {
						path = filePathField.getText ();
					}
				}

				persist.registerDirectory ( persist.prefEditorKey + forEditor, path );
			}

			SwingUtilities.windowForComponent ( CEPanel.this ).dispose ( );
		};

		ActionListener al2 = e -> {
			if ( e.getSource ( ) == fcButton ) {
				if ( chooser.showOpenDialog ( null ) == JFileChooser.APPROVE_OPTION ) {
					File selectedFile = chooser.getSelectedFile ( );

					if ( selectedFile != null && selectedFile.isFile ( ) ) {
						filePathField.setText ( selectedFile.getAbsolutePath ( ) );
						useBIEditorCheck.setSelected ( false );
					}
				}
			}
		};

		ActionListener al3 = e -> {
			if (e.getSource () == useBIEditorCheck) {
				if (useBIEditorCheck.isSelected ()) {
					filePathField.setText ( "" );
					okButton.setEnabled ( true );
					fcButton.setEnabled ( false );
				} else {
					okButton.setEnabled ( false );
					fcButton.setEnabled ( true );
				}
			}
		};

		filePathField.setInputVerifier ( new JInputValidator ( filePathField ) {
			@Override
			protected Validation getValidation ( JComponent comp , JInputValidatorPreferences preferences ) {
				String message = "";
				Validation.Type type = Type.NONE;

				if (!filePathField.getText ().isEmpty ()) {
					if ( !Files.isExecutable ( Path.of ( filePathField.getText ()))) {
						message = "The file selected is not an executable file.";
						type = Type.DANGER;
						okButton.setEnabled ( false );
					} else {
						okButton.setEnabled ( true );
					}
				}

				return new Validation ( type, message, preferences );
			}
		} );

		dHeader.setFont ( font );
		filePathField.setEditable ( false );
		filePathField.setFocusable ( false );

		okButton.setEnabled ( false );
		okButton.addActionListener ( al1 );
		cancelButton.addActionListener ( al1 );
		fcButton.addActionListener ( al2 );
		useBIEditorCheck.addActionListener ( al3 );

		PanelMatic.begin ( this )
				.add ( lineGroup ( fill ( 1, 0 ), useBIEditorCheck ) , L_CENTER, GROW )
				.addFlexibleSpace ()
				.add ( lineGroup( fill ( 5, 0 ), filePathField, fill ( 8, 0 ), fcButton, fill ( 5,0  )) )
				.addFlexibleSpace ()
				.add ( lineGroup ( okButton, fill ( 5,0  ), cancelButton ), L_CENTER )
				.get (  );
	}
}
