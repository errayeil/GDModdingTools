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
public class AnmFileFilter implements FinderFilter {

	@Override
	public String getName ( ) {
		return Keys.anmFilterKey;
	}

	@Override
	public String getDescription ( ) {
		return "Animation Files";
	}

	@Override
	public String[] getExtensions ( ) {
		return new String[] {Extensions.anmExt};
	}

	@Override
	public boolean accept ( File path ) {
		return FilenameUtils.getExtension ( path.getName ( ) ).equals ( Extensions.anmExt );
	}
}
