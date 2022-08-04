package com.github.errayeil.ui.finder.Filters;

import com.github.errayeil.Persistence.Persistence.Keys;
import com.github.errayeil.utils.ToolsUtils.Extensions;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

public class ArczFileFilter implements FinderFilter {

	/**
	 * @return
	 */
	@Override
	public String getName ( ) {
		return Keys.arcFilterKey;
	}

	@Override
	public String getDescription ( ) {
		return "Arc + Arz Files";
	}

	/**
	 *
	 * @return
	 */
	@Override
	public String[] getExtensions ( ) {
		return new String[] { Extensions.arzExt, Extensions.arcExt };
	}

	/**
	 * @param path The abstract pathname to be tested
	 *
	 * @return
	 */
	@Override
	public boolean accept ( File path ) {
		String ext = FilenameUtils.getExtension ( path.getName ( ) );

		return ext.equals ( Extensions.arcExt ) || ext.equals ( Extensions.arzExt );
	}
}
