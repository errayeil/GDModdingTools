package com.github.errayeil.ui.finder.Filters;

import com.github.errayeil.Persistence.Persistence.Keys;

import java.io.File;

/**
 * @author Errayeil
 * @version 0.1
 * @since 0.1
 */
public class AllFinderFilter implements FinderFilter {

	/**
	 *
	 * @return
	 */
	@Override
	public String getName ( ) {
		return Keys.allFilterKey;
	}

	@Override
	public String getDescription ( ) {
		return "All Files";
	}

	@Override
	public String[] getExtensions ( ) {
		return new String[  ] {  };
	}

	@Override
	public boolean accept ( File pathname ) {
		return true;
	}
}
