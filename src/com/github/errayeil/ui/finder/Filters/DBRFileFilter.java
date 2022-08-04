package com.github.errayeil.ui.finder.Filters;

import com.github.errayeil.Persistence.Persistence.Keys;
import com.github.errayeil.utils.ToolsUtils.Extensions;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

/**
 * File filter for dbr files for the Finder List.
 *
 * @author Errayeil
 * @version 0.1
 * @since 0.1
 */
public class DBRFileFilter implements FinderFilter {


	@Override
	public String getName ( ) {
		return Keys.dbrFilterKey;
	}

	@Override
	public String getDescription ( ) {
		return "DB Record Files";
	}

	@Override
	public String[] getExtensions ( ) {
		return new String[  ] { Extensions.dbrExt };
	}

	@Override
	public boolean accept ( File file ) {
		return FilenameUtils.getExtension ( file.getName ( ) ).equals ( Extensions.dbrExt );
	}
}
