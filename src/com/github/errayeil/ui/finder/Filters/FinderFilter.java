package com.github.errayeil.ui.finder.Filters;

import java.io.File;
import java.io.FileFilter;

public interface FinderFilter extends FileFilter {

	String getName ( );

	String getDescription ();

	String[] getExtensions ( );

	@Override
	boolean accept ( File pathname );
}
