package com.github.errayeil.ui.Workers;

import com.github.errayeil.Persistence.Persistence;
import com.github.errayeil.Persistence.Persistence.Keys;
import com.github.errayeil.ui.finder.Filters.FinderFilter;
import com.github.errayeil.ui.finder.List.FinderList;
import com.github.errayeil.ui.finder.List.FinderListModel;
import com.github.errayeil.ui.finder.Sort.FileNameSort;
import com.github.errayeil.ui.finder.Sort.FileTypeSort;

import javax.swing.SwingWorker;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FilterWorker extends SwingWorker {

	/**
	 *
	 */
	final Persistence persist = Persistence.getInstance ();

	/**
	 *
	 */
	private final FinderFilter activeFilter;

	/**
	 *
	 */
	private final Map<File, Integer> filteredFiles;

	/**
	 *
	 */
	private final FinderList list;

	/**
	 *
	 */
	private FinderListModel<java.io.File> model;

	/**
	 *
	 */
	private final List<Integer> indexes = new ArrayList<> (  );

	/**
	 *
	 */
	private List<File> files;

	/**
	 *
	 */
	public FilterWorker ( FinderFilter activeFilter, Map<File, Integer> filteredFiles, FinderList list ) {
		this.activeFilter = activeFilter;
		this.filteredFiles = filteredFiles;
		this.list = list;
		this.model = (FinderListModel<java.io.File>) list.getModel ();
	}


	@Override
	protected Object doInBackground ( ) throws Exception {

		if ( activeFilter != null && !activeFilter.getName ( ).equals ( Keys.allFilterKey ) ) {
			persist.setFinderValue ( Keys.fileFilterKey , activeFilter.getName ( ) );

			List<File> keys = filteredFiles.keySet ( ).stream ( ).toList ( );

			//Remove all previously filtered files and add them back to the list
			for ( File key : keys ) {
				if ( activeFilter.accept ( key ) && !key.isDirectory ( ) ) {
					model.add ( filteredFiles.get ( key ) , key );
				}
			}

			filteredFiles.clear ();

			//Get new filtered files and remove them.
			for ( int i = 0; i < model.size ( ); i++ ) {
				File file = model.get ( i );

				if ( !activeFilter.accept ( file ) || file.isDirectory ( ) ) {
					filteredFiles.put ( file , i );
					indexes.add ( i );
				}
			}
			indexes.sort ( Collections.reverseOrder ( ) );
		} else {
			persist.remove ( Keys.fileFilterKey );

			files = Collections.list ( model.elements ( ) );
			files.addAll ( filteredFiles.keySet () );
			filteredFiles.clear ();

			files.sort ( new FileTypeSort ( false ).thenComparing ( new FileNameSort ( false ) ) );
			model = new FinderListModel<> ( );
			model.addAll ( files );
		}

		return null;
	}

	@Override
	protected void done ( ) {
		if ( activeFilter != null && !activeFilter.getName ( ).equals ( Keys.allFilterKey ) ) {
			for ( int i : indexes ) {
				model.remove ( i );
			}
		} else {
			list.setModel ( model );
		}
	}
}
