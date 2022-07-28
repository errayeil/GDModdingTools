package com.github.errayeil.Actions.Menubar;

import com.github.errayeil.Persistence.Persistence;
import org.apache.commons.codec.digest.DigestUtils;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Errayeil
 * @version 0.1
 * @since 0.1
 */
public class SetGDDirectoryAction implements ActionListener {

	private JCheckBoxMenuItem item;

	/**
	 *
	 */
	private final String md5checksum = "c38a51169794acfbc3a4fd11133f607d";

	public SetGDDirectoryAction( JCheckBoxMenuItem item) {
		this.item = item;
	}


	@Override
	public void actionPerformed ( ActionEvent e ) {
		JFileChooser chooser = new JFileChooser (  );
		chooser.setFileSelectionMode ( JFileChooser.DIRECTORIES_ONLY );
		chooser.setMultiSelectionEnabled ( false );
		Persistence persist = Persistence.getInstance ();

		File folder = null;

		if (chooser.showOpenDialog ( null ) == JFileChooser.APPROVE_OPTION) {
			folder = chooser.getSelectedFile ();
		}

		if ( folder != null ) {

			if ( folder.isDirectory ( ) && folder.getName ( ).contains ( "Grim Dawn" ) ) {
				File[] files = folder.listFiles ( );
				assert files != null;

				for (File f : files) {
					String name = f.getName ();

					if (name.equals ( "Grim Dawn.exe" )) {
						persist.registerDirectory ( persist.gdExeDirKey, f.getAbsolutePath () );
						String md5 = "";

						try ( InputStream stream = new FileInputStream ( f ) ) {
							md5 = DigestUtils.md5Hex ( stream );
						} catch ( IOException ex ) {
							throw new RuntimeException ( ex );
						}

						if (md5.equals ( md5checksum )) {
							persist.registerDirectory ( persist.gdDirKey, folder.getAbsolutePath () );
							item.setSelected ( true );
							item.setEnabled ( false );
							break;
						}
					}
				}
			}
		}
	}

	/**
	 *
	 * @param path
	 */
	private void validateChecksum(File path) {

	}
}
