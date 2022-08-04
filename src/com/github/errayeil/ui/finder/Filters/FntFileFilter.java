package com.github.errayeil.ui.finder.Filters;

import com.github.errayeil.Persistence.Persistence.Keys;
import com.github.errayeil.utils.ToolsUtils.Extensions;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

public class FntFileFilter implements FinderFilter {

	/**
	 * @return
	 */
	@Override
	public String getName ( ) {
		return Keys.fntFilterKey;
	}

	@Override
	public String getDescription ( ) {
		return "GD Font Files";
	}

	@Override
	public String[] getExtensions ( ) {
		return new String[  ] { Extensions.fntExt };
	}

	/**
	 * @param pathname The abstract pathname to be tested
	 *
	 * @return
	 */
	@Override
	public boolean accept ( File pathname ) {
		return FilenameUtils.getExtension ( pathname.getName ( ) ).equals ( Extensions.fntExt );
	}
}
