package com.github.errayeil.Actions.Menubar;

import com.github.errayeil.Persistence.Persistence;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 *
 */
public class SetToolDirectoryAction implements ActionListener {

	/**
	 *
	 */
	private JCheckBoxMenuItem item;

	private final String[] tools = { "AifEditor.exe" , "AnimationCompiler.exe" , "ArchiveTool.exe" , "AssetManager.exe" ,
			"BitmapCreator.exe" , "ConversationEditor.exe" , "DBREditor.exe" , "Editor.exe" , "FontCompiler.exe" , "MapCompiler.exe" ,
			"ModelCompiler.exe" , "PSEditor.exe" , "QuestEditor.exe" , "ShaderCompiler.exe" , "SourceServer.exe" , "TextureCompiler.exe" ,
			"TexViewer.exe" , "Viewer.exe" };

	/**
	 * @param item
	 */
	public SetToolDirectoryAction ( JCheckBoxMenuItem item ) {
		this.item = item;
	}

	@Override
	public void actionPerformed ( ActionEvent e ) {
		File selected = null;
		JFileChooser chooser = new JFileChooser ( );
		chooser.setFileSelectionMode ( JFileChooser.DIRECTORIES_ONLY );
		chooser.setMultiSelectionEnabled ( false );

		Persistence persist = Persistence.getInstance ();

		if ( chooser.showOpenDialog ( null ) == JFileChooser.APPROVE_OPTION ) {
			selected = chooser.getSelectedFile ( );
		}

		if ( selected != null ) {
			if ( selected.isDirectory ( ) && selected.getName ( ).contains ( "Grim Dawn" ) ) {
				persist.registerDirectory ( persist.gdToolDirKey, selected.getAbsolutePath () );
				File[] files = selected.listFiles ( );

				assert files != null;
				for ( File f : files ) {
					String toolName = f.getName ();

					for (String tool : tools) {
						if (toolName.equals ( tool )) {
							System.out.println(toolName);
							persist.registerDirectory ( tool.toLowerCase () + persist.pathKey, f.getAbsolutePath ());
						}
					}
				}

				item.setSelected ( true );
				item.setEnabled ( false );
			}
		}
	}
}
