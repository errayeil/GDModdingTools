package com.github.errayeil.ui.finder.Sort;

import java.io.File;
import java.util.Comparator;

/**
 * @see "https://stackoverflow.com/questions/15672195/how-to-sort-a-list-of-files-directories-so-directories-are-listed-first"
 */
public class FileNameSort implements Comparator<File> {

	/**
	 *
	 */
	private final boolean reverseOrder;

	/**
	 *
	 * @param reverseOrder
	 */
	public FileNameSort (final boolean reverseOrder) {
		this.reverseOrder = reverseOrder;
	}

	/**
	 * @param file1 the first object to be compared.
	 * @param file2 the second object to be compared.
	 *
	 * @return
	 */
	@Override
	public int compare ( File file1 , File file2 ) {
		if (!reverseOrder) {
			return String.CASE_INSENSITIVE_ORDER.compare ( file1.getName ( ) ,
					file2.getName ( ) );
		} else {
			return String.CASE_INSENSITIVE_ORDER.compare ( file2.getName ( ) ,
					file1.getName ( ) );
		}
	}
}
