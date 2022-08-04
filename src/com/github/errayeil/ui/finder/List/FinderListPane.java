package com.github.errayeil.ui.finder.List;

import com.github.errayeil.ui.finder.Filters.FinderFilter;
import io.codeworth.panelmatic.PanelMatic;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import static io.codeworth.panelmatic.componentbehavior.Modifiers.*;
import static io.codeworth.panelmatic.util.Groupings.pageGroup;

/**
 * @author Errayeil
 * @version 0.1
 * @since 0.1
 */
public class FinderListPane extends JPanel {

	/**
	 *
	 */
	private FinderListHeader header;

	/**
	 *
	 */
	private FinderList list;

	/**
	 *
	 */
	private JScrollPane scroller;

	/**
	 *
	 */
	private JPopupMenu listPopup;


	/**
	 *
	 * @param rootDirectory
	 */
	public FinderListPane (File rootDirectory) {
		list = new FinderList ( rootDirectory );
		header = new FinderListHeader ( list , 10 );
		scroller = new JScrollPane ( list );

		list.setShowFileStats ( true );

		//Shows the list popup if one is available
		list.addMouseListener ( new MouseAdapter ( ) {
			@Override
			public void mouseClicked ( MouseEvent e ) {
				if ( SwingUtilities.isRightMouseButton ( e ) && e.getClickCount () == 1 ) {
					if (listPopup != null) {

					}
				}
			}
		} );

		scroller.setFocusable ( false );

		PanelMatic.begin ( this)
				.add ( header )
				.add ( scroller, GROW_MORE )
				.get (  );

	}

	/**
	 *
	 * @return
	 */
	public boolean isShowingFileStats() {
		return list.isShowingStats ();
	}

	/**
	 *
	 * @return
	 */
	public boolean isShowingHiddenFiles() {
		return list.isShowingHidden ();
	}

	/**
	 *
	 * @param newRootDirectory
	 */
	public void setRootDirectory(File newRootDirectory) {
		list.setRootDirectory ( newRootDirectory );
	}

	/**
	 *
	 * @param filter
	 */
	public void setFinderFilter ( FinderFilter filter) {
		list.setFinderFilter ( filter );
	}

	/**
	 * Sets the FinderList JPopupMenu. If one was previously assigned the new
	 * one will take its place.
	 * @param popup
	 */
	public void setListPopupMenu(JPopupMenu popup) {
		this.listPopup = popup;
	}

	/**
	 *
	 * @param showFileStats
	 */
	public void setShowFileStats(boolean showFileStats) {
		list.setShowFileStats ( showFileStats );
		header.setShowFileStats ( showFileStats );
	}

	/**
	 *
	 * @param showHiddenFiles
	 */
	public void setShowHiddenFiles(boolean showHiddenFiles) {
		list.setShowHidden ( showHiddenFiles );
	}
}
