package com.github.errayeil.ui.Custom;

import com.github.errayeil.ListApps.Software;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;

public class IALCellRenderer extends DefaultListCellRenderer {

	/**
	 *
	 */
	private JLabel appNameLabel;

	/**
	 *
	 */
	public IALCellRenderer() {
		appNameLabel = new JLabel (  );
		appNameLabel.setOpaque ( true );
	}

	@Override
	public Component getListCellRendererComponent ( JList<?> list , Object value , int index , boolean isSelected , boolean cellHasFocus ) {
		Software installedApp = (Software) value;
		Font font = UIManager.getDefaults ( ).getFont ( "Label.font" ).deriveFont ( 14 ).deriveFont ( Font.BOLD );
		String displayName = installedApp.getDisplayName ();

		if (installedApp.getIcon () != null) {
			Icon icon = FileSystemView.getFileSystemView ().getSystemIcon ( new File ( installedApp.getIcon ( ) ) );
			appNameLabel.setIcon ( icon );
		}

		appNameLabel.setFont ( font );
		appNameLabel.setText ( displayName );
		appNameLabel.setToolTipText ( installedApp.getInstallLocation () );

		if (isSelected) {
			appNameLabel.setBackground ( Color.DARK_GRAY);
			appNameLabel.setForeground ( Color.white );
		} else {
			appNameLabel.setBackground ( Color.WHITE );
			appNameLabel.setForeground ( Color.BLACK );
		}

		return appNameLabel;
	}
}
