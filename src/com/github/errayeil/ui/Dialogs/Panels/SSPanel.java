package com.github.errayeil.ui.Dialogs.Panels;

import com.alexandriasoftware.swing.JInputValidator;
import com.alexandriasoftware.swing.JInputValidatorPreferences;
import com.alexandriasoftware.swing.Validation;
import com.alexandriasoftware.swing.Validation.Type;
import com.github.errayeil.Persistence.Persistence;
import com.github.errayeil.Persistence.Persistence.Keys;
import com.github.errayeil.utils.ToolsUtils;
import io.codeworth.panelmatic.PanelMatic;

import javax.swing.*;
import java.awt.Font;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.errayeil.utils.CompUtils.fill;
import static io.codeworth.panelmatic.componentbehavior.Modifiers.L_CENTER;
import static io.codeworth.panelmatic.componentbehavior.Modifiers.L_START;
import static io.codeworth.panelmatic.util.Groupings.lineGroup;

/**
 * Custom JComponent class that guides the user through the AssetManager setup process.
 *
 * @//TODO: Code Refactoring
 * @//TODO: Documentation
 *
 * @author Errayeil
 * @version 0.1
 * @since 0.1
 */
public class SSPanel extends JComponent {

	/**
	 *
	 */
	public SSPanel ( JDialog parentDialog ) {
		JLabel askLabel = new JLabel ( "The modding suite has not been set up. Would you like to do this now?" );
		JLabel notifLabel = new JLabel ( "This can be done later, though functionality will be limited." );
		Font font1 = UIManager.getDefaults ( ).getFont ( "Label.font" ).deriveFont ( 12 );
		Font font2 = UIManager.getDefaults ( ).getFont ( "Label.font" ).deriveFont ( 14 ).deriveFont ( Font.BOLD );
		JButton okButton = new JButton ( "Start Setup" );
		JButton cancelButton = new JButton ( "I'll do it later" );
		SetupPanel panel = new SetupPanel ( );

		askLabel.setFont ( font1 );
		notifLabel.setFont ( font2 );

		okButton.addActionListener ( ( a ) -> {
			parentDialog.setContentPane ( panel );
			parentDialog.setTitle ( "Choose directories" );
			parentDialog.pack ( );
			parentDialog.validate ( );
		} );

		cancelButton.addActionListener ( ( a ) -> {
			parentDialog.dispose ( );
		} );

		PanelMatic.begin ( this )
				.add ( lineGroup ( fill ( 5 , 0 ) , askLabel , fill ( 5 , 0 ) ) , L_CENTER )
				.add ( lineGroup ( fill ( 5 , 0 ) , notifLabel , fill ( 5 , 0 ) ) , L_CENTER )
				.addFlexibleSpace ( )
				.add ( lineGroup ( okButton , fill ( 5 , 0 ) , cancelButton ) , L_CENTER );
	}
}

/**
 * The SetupPanel component provides the user the ability to input the various Grim Dawn modding
 * directories needed and their preferred editor tool paths for txt, lua, and various img formats.
 *
 * @//TODO: Code Refactoring
 * @//TODO: Documentation
 *
 * @author Errayeil
 * @version 0.1
 * @since 0.1
 */
class SetupPanel extends JComponent {

	/**
	 * Preferences wrapper class that makes it easier to contain data we need to be persistent
	 * between instances.
	 */
	private final Persistence persist = Persistence.getInstance ( );

	/**
	 * The path to tools (DBREditor or AifEditor, for example) when the tool path is set.
	 */
	private final Map<String, String> toolPaths = new HashMap<> ( );

	/**
	 * Complete Button for the setup panel.
	 */
	private final JButton completeButton;

	/**
	 * The various paths set by the user.
	 */
	private String gdDirPath = "";
	private String gdToolDirPath = "";
	private String gdBuildDirPath = "";
	private String gdWorkingDirPath = "";
	private String prefLuaDirPath = "";
	private String prefTxtDirPath = "";
	private String prefImgDirPath = "";


	/**
	 * Constructs the panel.
	 * <br>
	 * TODO: Look into refactoring this for better code organization and readability.
	 */
	public SetupPanel ( ) {
		JLabel gdDirLabel = new JLabel ( "Choose Grim Dawn install directory:" );
		JLabel gdToolDirLabel = new JLabel ( "Choose the location of the tools directory:" );
		JLabel gdBuildDirLabel = new JLabel ( "Choose the location of your build directory:" );
		JLabel gdWorkingDirLabel = new JLabel ( "Choose the location of your working directory:" );
		JLabel prefLuaEditorLabel = new JLabel ( "Choose your preferred editor for lua files:" );
		JLabel prefTxtEditorLabel = new JLabel ( "Choose your preferred editor for txt files:" );
		JLabel prefImgEditorLabel = new JLabel ( "Choose your preferred editor for img files:" );
		JCheckBox builtInLuaItem = new JCheckBox ( "Use built-in editor for lua files?" );
		JCheckBox builtInTxtItem = new JCheckBox ( "Use built-in editor for txt files?" );
		JTextField gdDirPathField = new JTextField ( 50 );
		JTextField gdToolDirPathField = new JTextField ( 50 );
		JTextField gdBuildDirPathField = new JTextField ( 50 );
		JTextField gdWorkingDirPathField = new JTextField ( 50 );
		JTextField prefLuaEditorPathField = new JTextField ( 50 );
		JTextField prefTxtEditorPathField = new JTextField ( 50 );
		JTextField prefImgEditorPathField = new JTextField ( 50 );
		JButton gdFCButton = new JButton ( "..." );
		JButton gdToolFCButton = new JButton ( "..." );
		JButton gdBuildFCButton = new JButton ( "..." );
		JButton gdWorkingFCButton = new JButton ( "..." );
		JButton prefLuaFCButton = new JButton ( "..." );
		JButton prefTxtFCButton = new JButton ( "..." );
		JButton prefImgFCButton = new JButton ( "..." );
		completeButton = new JButton ( "Complete Setup" );
		JButton cancelButton = new JButton ( "Cancel Setup" );
		JFileChooser chooser = new JFileChooser ( );

		//TODO: FileChooser persistence does not work at the moment. A custom FileChooser class with custom components will be needed.
		//persist.loadAndRegister ( chooser, "setup-chooser" );

		completeButton.setEnabled ( false ); //We don't want the complete button enabled until every path field is filled.
		gdDirPathField.setEditable ( false );
		gdDirPathField.setFocusable ( false );
		gdToolDirPathField.setEditable ( false );
		gdToolDirPathField.setFocusable ( false );
		gdBuildDirPathField.setEditable ( false );
		gdBuildDirPathField.setFocusable ( false );
		gdWorkingDirPathField.setEditable ( false );
		gdWorkingDirPathField.setFocusable ( false );
		prefLuaEditorPathField.setEditable ( false );
		prefLuaEditorPathField.setFocusable ( false );
		prefTxtEditorPathField.setEditable ( false );
		prefTxtEditorPathField.setFocusable ( false );
		prefImgEditorPathField.setEditable ( false );
		prefImgEditorPathField.setFocusable ( false );

		//Input verifiers do the validation work.
		gdDirPathField.setInputVerifier ( createGDDirValidator ( gdDirPathField ) );
		gdToolDirPathField.setInputVerifier ( createGDToolDirValidator ( gdToolDirPathField ) );
		gdBuildDirPathField.setInputVerifier ( createGDBuildDirValidator ( gdBuildDirPathField ) );
		gdWorkingDirPathField.setInputVerifier ( createGDWorkingDirValidator ( gdWorkingDirPathField ) );
		prefLuaEditorPathField.setInputVerifier ( createPrefEditorValidator ( prefLuaEditorPathField , "lua" ) );
		prefTxtEditorPathField.setInputVerifier ( createPrefEditorValidator ( prefTxtEditorPathField , "txt" ) );
		prefImgEditorPathField.setInputVerifier ( createPrefEditorValidator ( prefImgEditorPathField , "img" ) );

		//chooser.setMultiSelectionEnabled ( false );

		builtInLuaItem.addActionListener ( ( a ) -> {
			if ( builtInLuaItem.isSelected ( ) ) {
				prefLuaFCButton.setEnabled ( false );
				prefLuaEditorPathField.setText ( Keys.builtInKey );
			} else {
				prefLuaFCButton.setEnabled ( true );
				prefLuaEditorPathField.setText ( "" );
			}

		} );

		builtInTxtItem.addActionListener ( ( a ) -> {
			if ( builtInTxtItem.isSelected ( ) ) {
				prefTxtFCButton.setEnabled ( false );
				/**
				 * @see Persistence
				 */
				prefTxtEditorPathField.setText ( Keys.builtInKey );
			} else {
				prefTxtFCButton.setEnabled ( true );
				prefTxtEditorPathField.setText ( "" );
			}
		} );

		gdFCButton.addActionListener ( ( a ) -> {
			chooser.setFileSelectionMode ( JFileChooser.DIRECTORIES_ONLY );

			if ( chooser.showOpenDialog ( SetupPanel.this ) == JFileChooser.APPROVE_OPTION ) {
				gdDirPathField.setText ( chooser.getSelectedFile ( ).getAbsolutePath ( ) );
			}
		} );

		gdToolFCButton.addActionListener ( ( a ) -> {
			chooser.setFileSelectionMode ( JFileChooser.DIRECTORIES_ONLY );

			if ( chooser.showOpenDialog ( SetupPanel.this ) == JFileChooser.APPROVE_OPTION ) {
				gdToolDirPathField.setText ( chooser.getSelectedFile ( ).getAbsolutePath ( ) );
			}
		} );

		gdBuildFCButton.addActionListener ( ( a ) -> {
			chooser.setFileSelectionMode ( JFileChooser.DIRECTORIES_ONLY );

			if ( chooser.showOpenDialog ( SetupPanel.this ) == JFileChooser.APPROVE_OPTION ) {
				gdBuildDirPathField.setText ( chooser.getSelectedFile ( ).getAbsolutePath ( ) );
			}
		} );

		gdWorkingFCButton.addActionListener ( ( a ) -> {
			chooser.setFileSelectionMode ( JFileChooser.DIRECTORIES_ONLY );

			if ( chooser.showOpenDialog ( SetupPanel.this ) == JFileChooser.APPROVE_OPTION ) {
				gdWorkingDirPathField.setText ( chooser.getSelectedFile ( ).getAbsolutePath ( ) );
			}
		} );

		prefLuaFCButton.addActionListener ( ( a ) -> {
			chooser.setFileSelectionMode ( JFileChooser.FILES_ONLY );

			if ( chooser.showOpenDialog ( SetupPanel.this ) == JFileChooser.APPROVE_OPTION ) {
				prefLuaEditorPathField.setText ( chooser.getSelectedFile ( ).getAbsolutePath ( ) );
			}
		} );

		prefTxtFCButton.addActionListener ( ( a ) -> {
			chooser.setFileSelectionMode ( JFileChooser.FILES_ONLY );

			if ( chooser.showOpenDialog ( SetupPanel.this ) == JFileChooser.APPROVE_OPTION ) {
				prefTxtEditorPathField.setText ( chooser.getSelectedFile ( ).getAbsolutePath ( ) );
			}
		} );

		prefImgFCButton.addActionListener ( ( a ) -> {
			chooser.setFileSelectionMode ( JFileChooser.FILES_ONLY );

			if ( chooser.showOpenDialog ( SetupPanel.this ) == JFileChooser.APPROVE_OPTION ) {
				prefImgEditorPathField.setText ( chooser.getSelectedFile ( ).getAbsolutePath ( ) );
			}
		} );

		completeButton.addActionListener ( ( a ) -> {
			persist.registerDirectory ( Keys.gdDirKey, gdDirPath );
			persist.registerDirectory ( Keys.gdBuildDirKey, gdBuildDirPath );
			persist.registerDirectory ( Keys.gdToolDirKey, gdToolDirPath );
			persist.registerDirectory ( Keys.gdWorkingDirKey, gdWorkingDirPath );
			persist.registerDirectory ( Keys.prefEditorKey + Keys.luaKey, prefLuaDirPath );
			persist.registerDirectory ( Keys.prefEditorKey + Keys.txtKey, prefTxtDirPath );
			persist.registerDirectory ( Keys.prefEditorKey + Keys.imgKey, prefImgDirPath );

			for (String toolNameKey : toolPaths.keySet ()) {
				String toolPath = toolPaths.get ( toolNameKey );
				persist.registerDirectory ( toolNameKey.toLowerCase () + Keys.pathKey, toolPath );
			}

			persist.registerSetupCompletion ();
		} );

		cancelButton.addActionListener ( ( a ) -> {
			SwingUtilities.windowForComponent ( SetupPanel.this ).dispose ( );
		} );

		PanelMatic.begin ( this ) //I hate GUI work and PanelMatic is the greatest thing I've ever come across. I wish I could kiss it. I mean, I could, but it would just be my screen.
				.add ( fill ( 1 , 0 ) , gdDirLabel , L_START )
				.add ( lineGroup ( fill ( 5 , 0 ) , gdDirPathField , fill ( 5 , 0 ) , gdFCButton , fill ( 5 , 0 ) ) )
				.add ( fill ( 1 , 0 ) , gdToolDirLabel , L_START )
				.add ( lineGroup ( fill ( 5 , 0 ) , gdToolDirPathField , fill ( 5 , 0 ) , gdToolFCButton , fill ( 5 , 0 ) ) )
				.add ( fill ( 1 , 0 ) , gdBuildDirLabel , L_START )
				.add ( lineGroup ( fill ( 5 , 0 ) , gdBuildDirPathField , fill ( 5 , 0 ) , gdBuildFCButton , fill ( 5 , 0 ) ) )
				.add ( fill ( 1 , 0 ) , gdWorkingDirLabel , L_START )
				.add ( lineGroup ( fill ( 5 , 0 ) , gdWorkingDirPathField , fill ( 5 , 0 ) , gdWorkingFCButton , fill ( 5 , 0 ) ) )
				.add ( lineGroup ( fill ( 5 , 0 ) , prefLuaEditorLabel , fill ( 30 , 0 ) , builtInLuaItem ) , L_START )
				.add ( lineGroup ( fill ( 5 , 0 ) , prefLuaEditorPathField , fill ( 5 , 0 ) , prefLuaFCButton , fill ( 5 , 0 ) ) )
				.add ( lineGroup ( fill ( 5 , 0 ) , prefTxtEditorLabel , fill ( 30 , 0 ) , builtInTxtItem ) , L_START )
				.add ( lineGroup ( fill ( 5 , 0 ) , prefTxtEditorPathField , fill ( 5 , 0 ) , prefTxtFCButton , fill ( 5 , 0 ) ) )
				.add ( fill ( 1 , 0 ) , prefImgEditorLabel , L_START )
				.add ( lineGroup ( fill ( 5 , 0 ) , prefImgEditorPathField , fill ( 5 , 0 ) , prefImgFCButton , fill ( 5 , 0 ) ) )
				.addFlexibleSpace ( )
				.add ( lineGroup ( completeButton , fill ( 5 , 0 ) , cancelButton ) , L_CENTER )
				.get ( );
	}

	/**
	 * Trys to enable the complete button by checking if all variables are ready and valid for registering to
	 * the persistent store.
	 */
	private void tryEnable ( ) {
		if ( !gdDirPath.isBlank ( ) &&
				!gdBuildDirPath.isBlank ( ) && !gdToolDirPath.isBlank ( ) &&
				!gdWorkingDirPath.isBlank ( ) && !prefImgDirPath.isBlank ( ) &&
				!prefLuaDirPath.isBlank ( ) && !prefTxtDirPath.isBlank ( ) ) { //ugh so long and unreadable

			// make sure the map keys set size is the same as the amount of tools we search for
			completeButton.setEnabled ( toolPaths.keySet ( ).size ( ) == ToolsUtils.getGDToolsAsList ( ).size ( ) );
		} else {
			completeButton.setEnabled ( false );
		}
	}


	/**
	 * @return
	 */
	private JInputValidator createGDDirValidator ( JTextField field ) {

		return new JInputValidator ( field ) {
			@Override
			protected Validation getValidation ( JComponent jComponent , JInputValidatorPreferences preferences ) {
				String message = "This is a valid path.";
				Validation.Type type = Type.NONE;

				String text = field.getText ( );
				if ( !text.endsWith ( "Grim Dawn" ) || !Files.isDirectory ( Paths.get ( text ) ) ) {
					message = "The selected path is not a valid Grim Dawn installation folder.";
					type = Type.DANGER;
					gdDirPath = "";
				} else {
					type = Type.SUCCESS;
					gdDirPath = text;
				}

				tryEnable ( );
				return new Validation ( type , message , preferences );
			}
		};
	}

	/**
	 * @param field The JTextField we're pulling the file path from.
	 *
	 * @return Returns the JInputValidator that validates the file path set to the JTextField.
	 */
	private JInputValidator createGDToolDirValidator ( JTextField field ) {
		List<String> tools = new ArrayList<> ( ToolsUtils.getGDToolsAsList ( ) );
		List<String> toolsToRemove = new ArrayList<> ( ); //Separate list as tools list is immutable during loop
		List<String> missingTools = new ArrayList<> ( ); // Tools not found in the directory


		return new JInputValidator ( field ) {
			@Override
			protected Validation getValidation ( JComponent jComponent , JInputValidatorPreferences preferences ) {
				String message = "The path provided is a valid tools location.";
				Validation.Type type = Type.SUCCESS;
				;

				File selected = new File ( field.getText ( ) );

				if ( selected.isDirectory ( ) && selected.getName ( ).contains ( "Grim Dawn" ) ) {
					File[] files = selected.listFiles ( );

					assert files != null;
					for ( File f : files ) {
						String toolName = f.getName ( );

						for ( String tool : tools ) {
							if ( toolName.equals ( tool ) ) {
								System.out.println ( toolName );
								toolPaths.put ( tool , f.getAbsolutePath ( ) );
								toolsToRemove.add ( tool );
							}
						}
					}

					tools.removeAll ( toolsToRemove );

					if ( tools.size ( ) > 0 ) {
						missingTools.addAll ( tools );
						message = "The path provided is a valid tools location, however these tools are missing:" + "\n";
						type = Type.WARNING;
						gdToolDirPath = "";

						for ( String tool : missingTools ) {
							message = message + "\n" + '\u2022' + " " + tool;
						}
					} else {
						gdToolDirPath = field.getText ( );
					}

				}

				tryEnable ( );
				return new Validation ( type , message , preferences );
			}
		};
	}

	/**
	 * @return
	 */
	private JInputValidator createGDBuildDirValidator ( JTextField field ) {
		return new JInputValidator ( field ) {
			@Override
			protected Validation getValidation ( JComponent jComponent , JInputValidatorPreferences preferences ) {
				String message = "The path selected is valid.";
				Validation.Type type = Type.SUCCESS;

				String text = field.getText ( );
				if ( !text.endsWith ( "Grim Dawn" ) || !Files.isDirectory ( Paths.get ( text ) ) ) {
					message = "The path selected does not contain the Grim Dawn installation folder.";
					type = Type.WARNING;
					gdBuildDirPath = "";
				} else {
					gdBuildDirPath = text + File.separator + "mods";
				}

				tryEnable ( );
				return new Validation ( type , message , preferences );
			}
		};
	}

	/**
	 * @return
	 */
	private JInputValidator createGDWorkingDirValidator ( JTextField field ) {
		return new JInputValidator ( field ) {
			@Override
			protected Validation getValidation ( JComponent jComponent , JInputValidatorPreferences preferences ) {
				String message = "The path selected is valid.";
				Validation.Type type = Type.SUCCESS;

				String text = field.getText ( );

				if ( !text.contains ( "Grim Dawn" ) || !Files.isDirectory ( Paths.get ( text ) ) ) {
					message = "The path selected does not contain the Grim Dawn installation folder";
					type = Type.WARNING;
					gdWorkingDirPath = "";
				} else {
					gdWorkingDirPath = text;
				}

				tryEnable ( );
				return new Validation ( type , message , preferences );
			}
		};
	}

	/**
	 * @return
	 */
	private JInputValidator createPrefEditorValidator ( JTextField field , String forWhat ) {
		return new JInputValidator ( field ) {
			@Override
			protected Validation getValidation ( JComponent jComponent , JInputValidatorPreferences preferences ) {
				String message = "The path selected is valid.";
				Validation.Type type = Type.SUCCESS;

				String text = field.getText ( );

				if ( text.isBlank ( ) || text.isEmpty ( ) ) {
					clear ( forWhat );
					tryEnable ( );
					return new Validation ( Type.NONE , "" , preferences );
				}

				if ( !text.equals ( Keys.builtInKey ) ) {
					if ( Files.isDirectory ( Paths.get ( text ) ) && !new File ( text ).getName ( ).endsWith ( "exe" ) ) {
						message = "The path provided does not point to a valid executable.";
						type = Type.WARNING;
						clear ( forWhat );
					} else {
						switch ( forWhat ) {
							case "lua" -> prefLuaDirPath = field.getText ( );
							case "txt" -> prefTxtDirPath = field.getText ( );
							case "img" -> prefImgDirPath = field.getText ( );
						}
					}
				} else {
					message = "The built-in editor will be the preferred app.";
					type = Type.SUCCESS;
					switch ( forWhat ) {
						case "lua" -> prefLuaDirPath = Keys.builtInKey;
						case "txt" -> prefTxtDirPath = Keys.builtInKey;
					}
				}

				tryEnable ( );
				return new Validation ( type , message , preferences );
			}
		};
	}

	private void clear ( String forWhat ) {
		switch ( forWhat ) {
			case "lua" -> prefLuaDirPath = "";
			case "txt" -> prefTxtDirPath = "";
			case "img" -> prefImgDirPath = "";
		}
	}
}