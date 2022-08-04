package com.github.errayeil.ui.finder.List;

import com.github.errayeil.Persistence.Persistence.Keys;
import com.github.errayeil.ui.finder.Filters.FinderFilter;
import com.github.errayeil.ui.finder.Filters.GDFFileFilter;
import com.github.errayeil.ui.finder.Filters.TextFileFilter;
import com.github.errayeil.ui.finder.Sort.FileNameSort;
import com.github.errayeil.ui.finder.Sort.FileSizeSort;
import com.github.errayeil.ui.finder.Sort.FileTypeSort;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.ActionListener;

/**
 * @author Errayeil
 * @version 0.1
 * @since 0.1
 */
public class FinderListHeaderPopup {

	/**
	 *
	 */
	private final FinderListHeader header;

	/**
	 *
	 */
	private JPopupMenu menu;

	/**
	 *
	 */
	private JMenu filterMenu;

	/**
	 *
	 */
	private JMenu viewMenu;

	/**
	 *
	 */
	private JMenu sortMenu;

	/**
	 *
	 */
	public FinderListHeaderPopup ( FinderListHeader header , FinderFilter[] availableFilters ) {
		this.header = header;
		menu = new JPopupMenu ( );
		filterMenu = new JMenu ( "Filter" );
		viewMenu = new JMenu ( "View" );
		sortMenu = new JMenu ( "Sort" );

		GDFFileFilter filter1 = new GDFFileFilter ( );
		TextFileFilter filter2 = new TextFileFilter ( );

		JCheckBoxMenuItem allItem = new JCheckBoxMenuItem ( "All Files" );
		JCheckBoxMenuItem gdfItem = new JCheckBoxMenuItem ( filter1.getDescription ( ) );
		JCheckBoxMenuItem textItem = new JCheckBoxMenuItem ( filter2.getDescription ( ) );
		JCheckBoxMenuItem showHiddenItem = new JCheckBoxMenuItem ( "Show Hidden Files" );
		JCheckBoxMenuItem showFileStatsItem = new JCheckBoxMenuItem ( "Show File Stats" );

		JCheckBoxMenuItem defaultItem = new JCheckBoxMenuItem ( "Default" );
		JCheckBoxMenuItem sizeDescendItem = new JCheckBoxMenuItem ( "Largest to Smallest" );
		JCheckBoxMenuItem sizeAscendItem = new JCheckBoxMenuItem ( "Smallest to Largest" );
		JCheckBoxMenuItem nameDescendItem = new JCheckBoxMenuItem ( "A to Z" );
		JCheckBoxMenuItem nameAscendItem = new JCheckBoxMenuItem ( "Z to A" );
		JCheckBoxMenuItem filesFirstItem = new JCheckBoxMenuItem ( "Files First" );
		JCheckBoxMenuItem foldersFirstItem = new JCheckBoxMenuItem ( "Folders First" );

		sizeDescendItem.setName ( Keys.sortSizeDescendKey );
		sizeAscendItem.setName ( Keys.sortSizeAscendKey );
		nameDescendItem.setName ( Keys.sortNameDescendKey );
		nameAscendItem.setName ( Keys.sortNameAscendKey );
		filesFirstItem.setName ( Keys.sortFilesKey );
		foldersFirstItem.setName ( Keys.sortFoldersKey );
		allItem.setName ( Keys.allFilterKey );
		gdfItem.setName ( filter1.getName ( ) );
		textItem.setName ( filter2.getName ( ) );

		//TODO: Condense, this is NOT the way..... (yet)
		sizeDescendItem.addActionListener ( e -> {
			header.setActiveSort ( new FileSizeSort ( true ) );
			deselectItem ( sortMenu.getMenuComponents ( ) , sizeDescendItem );
		} );
		sizeAscendItem.addActionListener ( e -> {
			header.setActiveSort ( new FileSizeSort ( false ) );
			deselectItem ( sortMenu.getMenuComponents ( ) , sizeAscendItem );
		} );
		nameDescendItem.addActionListener ( e -> {
			header.setActiveSort ( new FileNameSort ( false ) );
			deselectItem ( sortMenu.getMenuComponents ( ) , nameDescendItem );
		} );
		nameAscendItem.addActionListener ( e -> {
			header.setActiveSort ( new FileNameSort ( true ) );
			deselectItem ( sortMenu.getMenuComponents ( ) , nameAscendItem );
		} );
		filesFirstItem.addActionListener ( e -> {
			header.setActiveSort ( new FileTypeSort ( true ) );
			deselectItem ( sortMenu.getMenuComponents ( ) , filesFirstItem );
		} );
		foldersFirstItem.addActionListener ( e -> {
			header.setActiveSort ( new FileTypeSort ( false ).thenComparing ( new FileNameSort ( false ) ) );
			deselectItem ( sortMenu.getMenuComponents ( ) , foldersFirstItem );
		} );
		showHiddenItem.addActionListener ( e -> {
			header.setShowHidden ( showHiddenItem.isSelected ( ) );
		} );
		showFileStatsItem.addActionListener ( e -> {
			header.setShowFileStats ( showFileStatsItem.isSelected ( ) );
		} );

		ActionListener a1 = e -> {
			header.setActiveFilter ( filter1 );
			deselectItem ( filterMenu.getMenuComponents ( ) , gdfItem );
		};

		ActionListener a2 = e -> {
			header.setActiveFilter ( filter2 );
			deselectItem ( filterMenu.getMenuComponents ( ) , textItem );
		};

		allItem.addActionListener ( e -> {
			header.setActiveFilter ( null );
			deselectItem ( filterMenu.getMenuComponents ( ) , allItem );
		} );

		gdfItem.addActionListener ( a1 );
		textItem.addActionListener ( a2 );

		filterMenu.add ( allItem );
		filterMenu.addSeparator ( );
		filterMenu.add ( gdfItem );
		filterMenu.add ( textItem );
		filterMenu.addSeparator ( );
		createFilterItems ( availableFilters );

		viewMenu.add ( showHiddenItem );
		viewMenu.add ( showFileStatsItem );

		sortMenu.add ( sizeDescendItem );
		sortMenu.add ( sizeAscendItem );
		sortMenu.addSeparator ( );
		sortMenu.add ( nameDescendItem );
		sortMenu.add ( nameAscendItem );
		sortMenu.addSeparator ( );
		sortMenu.add ( filesFirstItem );
		sortMenu.add ( foldersFirstItem );

		menu.add ( filterMenu );
		menu.add ( viewMenu );
		menu.add ( sortMenu );

		setItemSelected ( header.getActiveFilter ( ) );
		showHiddenItem.setSelected ( header.isShowingHidden ( ) );
		showFileStatsItem.setSelected ( header.isShowingFileStats ( ) );
	}

	/**
	 * @return
	 */
	public JPopupMenu getPopup ( ) {
		return menu;
	}

	/**
	 * Creates all the JCheckBoxMenuItems needed from the filters array.
	 *
	 * @param filters
	 */
	private void createFilterItems ( FinderFilter[] filters ) {
		for ( FinderFilter filter : filters ) {
			JCheckBoxMenuItem item = new JCheckBoxMenuItem ( );
			item.setText ( filter.getDescription ( ) );
			item.setName ( filter.getName ( ) );

			item.addActionListener ( e -> {
				header.setActiveFilter ( filter );
			} );

			item.addActionListener ( e -> {
				deselectItem ( filterMenu.getMenuComponents ( ) , item );
			} );

			filterMenu.add ( item );
		}
	}

	/**
	 * Sets the correct JCheckBoxMenuItem to a selected state based off the
	 * provided filter.
	 *
	 * @param filter
	 */
	private void setItemSelected ( FinderFilter filter ) {
		for ( Component comp : filterMenu.getMenuComponents ( ) ) {
			if ( comp instanceof JCheckBoxMenuItem ) {
				String name = comp.getName ( );
				if ( name != null ) {
					if ( filter != null ) {
						if ( comp.getName ( ).equals ( filter.getName ( ) ) ) {
							( ( JCheckBoxMenuItem ) comp ).setSelected ( true );
						}
					} else {
						if ( comp.getName ( ).equals ( Keys.allFilterKey ) ) {
							( ( JCheckBoxMenuItem ) comp ).setSelected ( true );
						}
					}
				}
			}
		}
	}

	/**
	 * Deselects any other selected JCheckBoxMenuItem's in the menu.
	 *
	 * @param source
	 */
	private void deselectItem ( Component[] menuComponents , JCheckBoxMenuItem source ) {
		for ( Component comp : menuComponents ) {
			if ( comp instanceof JCheckBoxMenuItem ) {
				if ( comp != source ) {
					( ( JCheckBoxMenuItem ) comp ).setSelected ( false );
				}
			}
		}
	}
}
