package com.github.errayeil.ui.finder.Sort;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;

public class FileSizeSort implements Comparator<File> {

	/**
	 *
	 */
	private boolean reverseOrder;

	/**
	 *
	 * @param reverseOrder
	 */
	public FileSizeSort (boolean reverseOrder) {
		this.reverseOrder = reverseOrder;
	}

	@Override
	public int compare ( File o1 , File o2 ) {
		if (!reverseOrder) {
			return sort ( o1 , o2 );
		} else {
			return reverseSort ( o1 , o2 );
		}
	}

	/**
	 *
	 * @param o1
	 * @param o2
	 * @return
	 */
	private int sort(File o1, File o2) {
		if ( o1.length ( ) < o2.length ( ) ) {
			return -1;
		} else if ( o1.length ( ) > o2.length ( ) ) {
			return 1;
		} else {
			return 0;
		}
	}

	private int reverseSort( File o1 , File o2) {
		if ( o1.length () > o2.length () ) {
			return -1;
		} else if (o1.length ( )  < o2.length ()) {
			return 1;
		} else {
			return 0;
		}
	}
}
