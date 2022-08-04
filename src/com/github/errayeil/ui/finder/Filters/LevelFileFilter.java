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
public class LevelFileFilter implements FinderFilter {

	/**
	 *
	 */
	private final String[] extensions = { Extensions.wrlExt , Extensions.lvlExt , Extensions.rlvExt };

	@Override
	public String getName ( ) {
		return Keys.lvlFilterKey;
	}

	@Override
	public String getDescription ( ) {
		return "GD Level Files";
	}

	@Override
	public String[] getExtensions ( ) {
		return extensions;
	}

	@Override
	public boolean accept ( File pathname ) {
		for (String ext : extensions) {
			if (ext.equals ( FilenameUtils.getExtension ( pathname.getName () ) )) {
				return true;
			}
		}
		return false;
	}
}
