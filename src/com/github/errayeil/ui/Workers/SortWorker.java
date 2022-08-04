package com.github.errayeil.ui.Workers;

import com.github.errayeil.ui.finder.List.FinderList;
import com.github.errayeil.ui.finder.List.FinderListModel;

import javax.swing.SwingWorker;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 */
public class SortWorker extends SwingWorker {

	/**
	 *
	 */
	private FinderList list;

	/**
	 *
	 */
	private FinderListModel<File> model;

	/**
	 *
	 */
	private Comparator<File> activeSort;

	/**
	 *
	 */
	private List<File> files;

	/**
	 *
	 */
	public SortWorker ( FinderList list, Comparator<File> activeSort ) {
		this.list = list;
		this.model = (FinderListModel<java.io.File>) list.getModel ();
		this.activeSort = activeSort;
	}

	@Override
	protected Object doInBackground ( ) throws Exception {
		files = new ArrayList<> ( Collections.list ( model.elements ( ) ) );
		files.sort ( activeSort );
		model = new FinderListModel<> ( );
		model.addAll ( files );
		return null;
	}

	@Override
	protected void done ( ) {
		list.setModel ( model );
	}
}
