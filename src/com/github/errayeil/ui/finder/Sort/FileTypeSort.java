package com.github.errayeil.ui.finder.Sort;

import java.io.File;
import java.util.Comparator;

/**
 * Basically a glorified comparator, but I named the Comparator classes Sorts to make it more identifiable when
 * mucking about in the codebase. I don't mean that in a bad way, I'm glad you're here reading this. <3
 *
 * @TODO: <s>Fix files first sort. It does not put folders to bottom.</s><p>HAHA NVM, fixed it just minutes after this. I'm not gonna talk about why it wouldn't work.</p>
 * @author Errayeil
 * @version 0.1
 * @since 0.1
 */
public class FileTypeSort implements Comparator<File> {

	/**
	 * Boolean determining if the sort should occur in reverse order.
	 */
	private final boolean reverseOrder;

	/**
	 * @param reverseOrder
	 */
	public FileTypeSort ( final boolean reverseOrder ) {
		this.reverseOrder = reverseOrder;
	}

	/**
	 *
	 * @param file1 the first object to be compared.
	 * @param file2 the second object to be compared.
	 * @return
	 */
	@Override
	public int compare ( File file1 , File file2 ) {
		if ( !reverseOrder ) {
			return sort ( file1 , file2 );
		} else {
			return reverseSort ( file1 , file2 );
		}
	}

	/**
	 * @param o1
	 * @param o2
	 *
	 * @return
	 */
	private int sort ( File o1 , File o2 ) {
		if ( o1.isDirectory ( ) && o2.isFile ( ) ) {
			return -1;
		} else if ( o1.isDirectory ( ) && o2.isDirectory ( ) ) {
			return 1;
		} else if ( o1.isFile ( ) && o2.isFile ( ) ) {
			return 0;
		}

		return 1;
	}

	private int reverseSort ( File o1 , File o2 ) {
		if ( o1.isFile () && o2.isDirectory () ) {
			return -1;
		} else if ( o1.isFile ( ) && o2.isFile ( ) ) {
			return 1;
		} else if ( o1.isDirectory ( ) && o2.isDirectory ( ) ) {
			return 0;
		}

		return 1;
	}
}
