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
public class TxtFileFilter implements FinderFilter {
	@Override
	public String getName ( ) {
		return Keys.txtFilterKey;
	}

	@Override
	public String getDescription ( ) {
		return "Txt Files";
	}

	@Override
	public String[] getExtensions ( ) {
		return new String[ ] { Extensions.txtExt };
	}

	@Override
	public boolean accept ( File pathname ) {
		return FilenameUtils.getExtension ( pathname.getName ( ) ).equals ( Extensions.txtExt );
	}
}
