package com.github.errayeil.ui.finder.List;

import javax.swing.*;
import java.awt.Color;
import java.awt.Component;
import java.io.File;

/**
 * @author Errayeil
 * @version 0.1
 * @since 0.1
 */
public class FinderListCellRenderer extends DefaultListCellRenderer {

	/**
	 *
	 */
	private FinderListCell cell;

	/**
	 *
	 */
	public FinderListCellRenderer ( ) {

	}

	@Override
	public Component getListCellRendererComponent ( JList<?> list , Object value , int index , boolean isSelected , boolean cellHasFocus ) {
		return this.getListCellRendererComponent ( ( FinderList ) list, (File) value, index, isSelected, cellHasFocus );
	}

	/**
	 *
	 *
	 * @TODO Dynamically alter the values depending on the LAF. This looks good on some LAF's, some it doesn't, some it has no effect.
	 *
	 * @param fileList
	 * @param file
	 * @param index
	 * @param isSelected
	 * @param cellHasFocus
	 * @return
	 */
	protected Component getListCellRendererComponent ( FinderList fileList , File file , int index , boolean isSelected , boolean cellHasFocus ) {
		cell = new FinderListCell ( file , fileList.isShowingStats (), true, true);
		configureFileCell ( index, isSelected );

		return cell;
	}

	private void configureFileCell ( int index, boolean isSelected) {
		UIDefaults defaults = UIManager.getDefaults ( );

		//Slight color change to add a line effect, similar to finder on macos. Just makes every even index a bit darker
		if ( isEvenIndex ( index ) ) {
			Color dg = defaults.getColor ( "List.background" );
			Color color = new Color ( dg.getRed ( ) - 15 , dg.getGreen ( ) - 15 , dg.getBlue ( ) - 15 );
			cell.setBackground ( color );
		}

		//Making the selection background and foreground a bit darker as well.
		if ( isSelected ) {
			if (isEvenIndex ( index )) {
				Color dgb = defaults.getColor ("List.selectionBackground");
				Color dgf = defaults.getColor ( "List.selectionForeground" );
				Color bc = new Color ( dgb.getRed () - 20, dgb.getGreen () - 20, dgb.getBlue () - 20 );
				Color fc = new Color (dgf.getRed () - 20, dgf.getGreen () - 20, dgf.getBlue () - 20);

				cell.setBackground ( bc );
				cell.setForeground ( fc );
			} else {
				cell.setBackground ( defaults.getColor ( "List.selectionBackground" ) );
				cell.setForeground ( defaults.getColor ( "List.selectionForeground" ) );
			}
		}
	}

	/**
	 * @param index
	 *
	 * @return
	 */
	public boolean isEvenIndex ( int index ) {
		return ( index & 1 ) == 0;
	}
}
