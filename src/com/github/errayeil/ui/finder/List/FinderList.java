package com.github.errayeil.ui.finder.List;

import com.github.errayeil.Persistence.Persistence;
import com.github.errayeil.Persistence.Persistence.Keys;
import com.github.errayeil.ui.Workers.FilterWorker;
import com.github.errayeil.ui.Workers.SortWorker;
import com.github.errayeil.ui.finder.Filters.FinderFilter;
import com.github.errayeil.ui.finder.Sort.FileNameSort;
import com.github.errayeil.ui.finder.Sort.FileTypeSort;

import javax.swing.JList;
import javax.swing.SwingUtilities;
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileFilter;
import java.util.*;

/**
 * <p>
 * A custom JList tailored to displaying a list of files from a specified directory.
 * This whole Finder package will actually be migrated into a standalone library, but
 * I'm developing this and tailoring the experience for the modding suite.
 * </p>
 * <br>
 * This has the ability to exclude or include hidden files on a whim, in the event someone does
 * or does not want to see hidden files/folders. The list model can also be sorted by file name, size,
 * and type, both in ascending or descending order.
 * </p>
 * <br>
 * <p>
 * File filtering is also possible. Currently, this implementation is hardcoded for Grim Dawn modding files,
 * however, it will eventually be expanded and tailored for a more open experience when extracted into it's own
 * library. It currently supports any Filter that implements the FinderFilter interface.
 * </p>
 * <br>
 * <p>
 * This is hooked up to the Persistence class so variables such as showHidden or showFileStats
 * will be consistent between instances.
 * </p>.
 * <br>
 * <p>
 * <font size="-2"> A long time ago in a galaxy far, far away..... I created this beauty.</font>
 * </p
 *
 * @author Errayeil
 * @version 0.1
 * @TODO: Refactoring - I always make sure to remind my self everything can <b><i>probably</i></b> be refactored.
 * @TODO: Documentation - Need to make sure everything is documented in depth eventually.
 * @TODO: Expandable cells to show a preview of the files in a directory. Super low priority, I just think it'd be cool.
 * @see Persistence
 * @see Keys
 * @see FinderFilter
 * @see FileFilter
 * @see Comparator
 * @since 0.1
 */
public class FinderList extends JList<java.io.File> {

	/**
	 * The Persistence preferences wrapper.
	 */
	private final Persistence persist = Persistence.getInstance ( );

	/**
	 * The root directory we list files from.
	 */
	private java.io.File rootDirectory;

	/**
	 * FileFilter which helps the FinderList filter out
	 * files that do not need to be visible.
	 */
	private FinderFilter activeFilter;

	/**
	 *
	 */
	private Comparator<java.io.File> activeSort;

	/**
	 *
	 */
	private boolean reverseSortOrder = false;

	/**
	 * The model that aids in displaying information from a file.
	 * This actually doesn't do anything right now.
	 * Not sure what to use the model for, tbh
	 */
	private FinderListModel<File> model;

	/**
	 * The renderer creates the custom component that displays the file icon,
	 * name, and optionally file stats.
	 */
	private FinderListCellRenderer renderer;

	/**
	 * A map of hidden files and their respective index in the original list.
	 * This helps track where the hidden files need to be inserted in the FinderList
	 * model in the event showHidden is set to true.
	 */
	private final Map<java.io.File, Integer> hiddenFiles;

	/**
	 * A map of filter files along with their index in the list model.
	 * In case the filter is ever changed again, we can easily insert any
	 * previously filtered files back into the model, provided they are accepted
	 * by the new filter.
	 */
	private final Map<java.io.File, Integer> filteredFiles;

	/**
	 * Constructs a new FinderList with the specified rootDirectory.
	 */
	public FinderList ( java.io.File rootDirectory ) {
		this.rootDirectory = rootDirectory;
		model = new FinderListModel<> ( );
		renderer = new FinderListCellRenderer ( );
		hiddenFiles = new HashMap<> ( );
		filteredFiles = new HashMap<> ( );

		setModel ( model );
		setCellRenderer ( renderer );
		setRoot ( rootDirectory );

		/*
		 * I added this MouseAdapter to register right clicks as a valid index selection input.
		 * The main idea was to take away that functionality from an external MouseAdapter and just
		 * leave external adapters to showing a popup, or something.
		 */
		addMouseListener ( new MouseAdapter ( ) {
			@Override
			public void mouseClicked ( MouseEvent e ) {
				if ( SwingUtilities.isRightMouseButton ( e )  ) {
					int index = locationToIndex ( e.getPoint ( ) );

					//Helps ensure the correct index is selected.
					if ( index > -1 && getCellBounds ( index , index ).contains ( e.getPoint ( ) ) ) {
						setSelectedIndex ( index );
					}
				}
			}
		} );

		setFixedCellHeight ( 26 );
		setFixedCellWidth ( 150 );
	}

	/**
	 * Sets the Root directory to list the files from.
	 * This is called in both the constructor and setRootDirectory().
	 *
	 * @param rootDirectory
	 */
	private void setRoot ( java.io.File rootDirectory ) {
		List<java.io.File> files = new ArrayList<> ( Arrays.asList ( Objects.requireNonNull ( rootDirectory.listFiles ( ) ) ) );

		for ( int i = 0; i < files.size ( ); i++ ) {
			java.io.File file = files.get ( i );
			if ( file.isHidden ( ) ) {
				/*
				 * Save the index for if we ever need to update the model.
				 * This also allows use to remove all the hidden files if needed.
				 */
				hiddenFiles.put ( file , i );
			}
		}

		if ( !persist.getFinderValue ( Keys.showHiddenKey ) ) {
			files.removeAll ( hiddenFiles.keySet ( ) );
		}

		if ( activeFilter != null && !activeFilter.getName ( ).equals ( Keys.allFilterKey ) ) {
			applyFilter ( );
		} else if ( activeFilter == null || activeFilter.getName ( ).equals ( Keys.allFilterKey ) ) {
			/*
			 * Sort the file list by type (directory or file) first and then sort by name.
			 * I made this type of comment because I had something else to write, but I forgot what it was.
			 * Oh well.
			 */
			files.sort ( new FileTypeSort ( false ).thenComparing ( new FileNameSort ( false ) ) );
		}

		model.addAll ( files );
	}

	/**
	 * Applies the FinderFilter.
	 */
	private void applyFilter ( ) {
		FilterWorker worker = new FilterWorker ( activeFilter , filteredFiles , this );
		worker.execute ( );
	}

	/**
	 * Applies the active sort.
	 */
	private void applySort ( ) {
		final SortWorker worker = new SortWorker ( this , activeSort );
		worker.execute ( );
	}

	/**
	 * Tells the FinderList to update its view of the root directory.
	 * This should be called if a "listener" has been placed on the root directory
	 * to identify changes. If changes occur this can be called for the FinderList
	 * to reflect those changes.
	 * @TODO: DO this
	 */
	public void updateList() {

	}

	/**
	 * Removes the selected file from the list and returns the File object.
	 * <br>
	 * The returned File object can be used for misc. tasks, if needed.
	 *
	 * @return Removed selected File
	 */
	public java.io.File removeSelectedFile ( ) {
		return model.remove ( getSelectedIndex ( ) );
	}

	/**
	 * Gets the currently selected file in the list.
	 *
	 * @return Selected File
	 */
	public java.io.File getSelectedFile ( ) {
		return model.get ( getSelectedIndex ( ) );
	}

	/**
	 * @return
	 *
	 * @TODO: Implement transferable for files and push to clipboard
	 */
	public java.io.File copySelectedFile ( ) {


		return model.get ( getSelectedIndex ( ) );
	}

	/**
	 * Moves selected file to the recycle bin.
	 *
	 * @return Returns true if the action is supported and completed. False otherwise.
	 */
	public boolean trashSelectedFile ( ) {
		if ( Desktop.getDesktop ( ).isSupported ( Action.MOVE_TO_TRASH ) ) {
			Desktop.getDesktop ( ).moveToTrash ( model.get ( getSelectedIndex ( ) ) );
			model.remove ( getSelectedIndex ( ) );
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the current root directory for the FinderList.
	 *
	 * @return Current root
	 */
	public java.io.File getRootDirectory ( ) {
		return rootDirectory;
	}

	/**
	 *
	 */
	public FinderFilter getFinderFilter ( ) {
		return activeFilter;
	}

	/**
	 * Sets the root directory. <br>
	 * This causes the model to update and the FinderList to repaint.
	 *
	 * @param rootDirectory
	 */
	public void setRootDirectory ( java.io.File rootDirectory ) {
		this.rootDirectory = rootDirectory;

		model = new FinderListModel<> ( );
		hiddenFiles.clear ( );

		setModel ( model );
		setRoot ( rootDirectory );
	}

	/**
	 * Sets the file filter for the list. This will apply the filter immediately and files
	 * that are not accepted will be removed from the list.
	 * Null finder filters will reset the list to show all files/folders in the root directory.
	 *
	 * @param filter
	 */
	public void setFinderFilter ( FinderFilter filter ) {
		this.activeFilter = filter;
		applyFilter ( );
	}

	/**
	 * Sets the active sort method.
	 *
	 * @param sort The comparator used to sort the file list.
	 */
	public void setSort ( Comparator<java.io.File> sort ) {
		this.activeSort = sort;
		applySort ( );
	}

	/**
	 * Sets if hidden files should be visible or not.
	 * <br>
	 * If true, hidden files in the directory will be inserted into the model
	 * at their respective and sorted index.
	 * If false, they will be removed but can be inserted back at the same index
	 * in the future.
	 * <p>This is why I love Maps</p>
	 *
	 * @param showHidden
	 */
	public void setShowHidden ( boolean showHidden ) {
		persist.setFinderValue ( Keys.showHiddenKey , showHidden );

		List<java.io.File> keys = hiddenFiles.keySet ( ).stream ( ).toList ( );
		for ( java.io.File f : keys ) {
			int index = hiddenFiles.get ( f );
			if ( showHidden ) {
				model.add ( index , f ); //Inserts the hidden file where it would have been if it was displayed.
			} else {
				model.removeElementAt ( index );
			}
		}
	}

	/**
	 * Sets if the file stats should display in the list cells.
	 * This causes the FinderList to repaint.
	 * @TODO: Possible swing worker worthy? It be wanting a worker. ;)
	 * @param showStats
	 */
	public void setShowFileStats ( boolean showStats ) {
		persist.setFinderValue ( Keys.showFileStatsKey , showStats );

		//Get each FinderListCell component to update the showFileStats label.
		for ( int i = 0; i < model.getSize ( ); i++ ) {
			java.io.File value = model.get ( i );
			FinderListCell cell = ( FinderListCell ) renderer.getListCellRendererComponent ( this , value , i , false , false );
			cell.setShowFileStats ( showStats );
		}

		repaint ( );
	}

	/**
	 * If the FinderList is supposed to be showing file stats, or not.
	 */
	public boolean isShowingStats ( ) {
		return persist.getFinderValue ( Keys.showFileStatsKey );
	}

	/**
	 * If the FinderList is supposed to be showing hidden files, or not.
	 *
	 * @return
	 */
	public boolean isShowingHidden ( ) {
		return persist.getFinderValue ( Keys.showHiddenKey );
	}
}
