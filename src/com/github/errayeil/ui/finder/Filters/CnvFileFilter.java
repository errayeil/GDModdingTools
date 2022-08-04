package com.github.errayeil.ui.finder.Filters;

import com.github.errayeil.Persistence.Persistence.Keys;
import com.github.errayeil.utils.ToolsUtils.Extensions;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

/**
 * @author Errayeil
 * @version 0.1
 * @since 0.1
 */
public class CnvFileFilter implements FinderFilter {

	/**
	 * Returns the name of the filter.
	 * @return
	 */
	@Override
	public String getName ( ) {
		return Keys.cnvFilterKey;
	}

	@Override
	public String getDescription ( ) {
		return "Conversation Files";
	}

	/**
	 * Returns the file extension that are evaluated by the filter.
	 */
	@Override
	public String[] getExtensions ( ) {
		return new String[  ] { Extensions.cnvExt };
	}

	/**
	 * Determines if the file provided has an extension that matches
	 * the filters.
	 *
	 * @param path  The abstract pathname to be tested
	 * @return True if the extension matches the filter, false otherwise.
	 */
	@Override
	public boolean accept ( File path ) {
		return FilenameUtils.getExtension ( path.getName ( ) ).equals ( Extensions.cnvExt );
	}
}
