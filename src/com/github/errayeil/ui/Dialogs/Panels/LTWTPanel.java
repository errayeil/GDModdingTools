package com.github.errayeil.ui.Dialogs.Panels;

import com.alexandriasoftware.swing.JInputValidator;
import com.alexandriasoftware.swing.JInputValidatorPreferences;
import com.alexandriasoftware.swing.Validation;
import com.github.errayeil.ui.Dialogs.EntryData.WTEData;
import com.github.errayeil.utils.CompUtils;
import com.github.errayeil.utils.ToolsUtils;
import io.codeworth.panelmatic.PanelMatic;
import org.apache.commons.lang3.math.NumberUtils;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;

import static com.github.errayeil.utils.CompUtils.fill;
import static io.codeworth.panelmatic.componentbehavior.Modifiers.L_CENTER;
import static io.codeworth.panelmatic.componentbehavior.Modifiers.L_START;
import static io.codeworth.panelmatic.util.Groupings.lineGroup;

/**
 * @author Errayeil
 * @version 1.0
 * @since 1.0
 */
public class LTWTPanel extends JComponent {

	/**
	 *
	 */
	private final JButton okButton;

	/**
	 *
	 */
	private final JTextField lootTableFileField;

	/**
	 *
	 */
	private final JTextField lootWeightField;

	/**
	 *
	 */
	private final WTEData data;

	/**
	 *
	 */
	private boolean fileValid = false;

	/**
	 *
	 */
	private boolean weightValid = false;

	/**
	 *
	 */
	public LTWTPanel ( ) {
		okButton = new JButton ( "Okay" );
		lootTableFileField = new JTextField ( 30 );
		lootWeightField = new JTextField ( 30 );
		data = new WTEData ( );
		JButton cancelButton = new JButton ( "Cancel" );
		JButton lootTableFCButton = new JButton ( "..." );
		Font font = UIManager.getDefaults ( ).getFont ( "Label.font" ).deriveFont ( 14 ).deriveFont ( Font.BOLD );
		JLabel ltHeader = new JLabel ( "Select loot table file to write weights to:" );
		JLabel wHeader = new JLabel ( "Enter in loot weight:" );

		okButton.setEnabled ( false );
		lootTableFileField.setEditable ( false );
		lootTableFileField.setFocusable ( false );
		ltHeader.setFont ( font );
		wHeader.setFont ( font );

		ActionListener al = e -> {
			SwingUtilities.windowForComponent ( LTWTPanel.this ).dispose ( );
		};

		okButton.addActionListener ( al );
		cancelButton.addActionListener ( al );

		lootTableFCButton.addActionListener ( ( a ) -> {
			JFileChooser chooser = CompUtils.getFileChooser ( "Choose loot table file" , 0 , false );

			if ( chooser.showOpenDialog ( null ) == JFileChooser.APPROVE_OPTION ) {
				File selected = chooser.getSelectedFile ( );
				data.ltFilePath = selected.getAbsolutePath ( );
				lootTableFileField.setText ( selected.getName ( ) );
			}
		} );

		lootTableFileField.setInputVerifier ( new JInputValidator ( lootTableFileField ) {
			@Override
			protected Validation getValidation ( JComponent component , JInputValidatorPreferences preferences ) {
				String message = "";
				Validation.Type type = Validation.Type.NONE;

				if ( !ToolsUtils.isValidLTFile ( new File ( data.ltFilePath ) ) ) {
					message = "The selected loot table file is not a valid loot table.";
					type = Validation.Type.WARNING;
					fileValid = false;
				} else {
					fileValid = true;
				}

				enableOkButton ( );
				return new Validation ( type , message , preferences );
			}
		} );

		lootWeightField.setInputVerifier ( new JInputValidator ( lootWeightField ) {
			@Override
			protected Validation getValidation ( JComponent component , JInputValidatorPreferences preferences ) {
				String message = "";
				Validation.Type type = Validation.Type.NONE;

				if ( !NumberUtils.isDigits ( lootWeightField.getText ( ) ) ) {
					message = "The weight is not a number";
					type = Validation.Type.WARNING;
					weightValid = false;
				} else {
					data.weight = lootWeightField.getText ( );
					weightValid = true;
				}

				enableOkButton ( );
				return new Validation ( type , message , preferences );
			}
		} );

		PanelMatic.begin ( this )
				.add ( fill ( 1 , 0 ) , ltHeader , L_START )
				.add ( lineGroup ( fill ( 5 , 5 ) , lootTableFileField , fill ( 5 , 5 ) , lootTableFCButton , fill ( 5 , 5 ) ) )
				.add ( fill ( 1 , 0 ) , wHeader , L_START )
				.add ( lineGroup ( fill ( 5 , 0 ) , lootWeightField , fill ( 5 , 0 ) ) )
				.addFlexibleSpace ( )
				.add ( lineGroup ( okButton , fill ( 5 , 5 ) , cancelButton ) , L_CENTER )
				.addFlexibleSpace ( )
				.get ( );
	}

	/**
	 * @return
	 */
	public WTEData getReturnData ( ) {
		return data;
	}

	/**
	 *
	 */
	private void enableOkButton ( ) {
		okButton.setEnabled ( fileValid && weightValid );
	}

}