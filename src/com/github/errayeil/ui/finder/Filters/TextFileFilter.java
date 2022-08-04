package com.github.errayeil.ui.finder.Filters;

import com.github.errayeil.Persistence.Persistence.Keys;
import com.github.errayeil.utils.ToolsUtils.Extensions;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Filter for all plain text file types.
 *
 * @author Errayeil
 * @version 0.1
 * @since 0.1
 */
public class TextFileFilter implements FinderFilter {

	private String[] extensions = { Extensions.txtExt , Extensions.luaExt  , Extensions.dbrExt , Extensions.tplExt };


	@Override
	public String getName ( ) {
		return Keys.ptfFilterKey;
	}

	@Override
	public String getDescription ( ) {
		return "Plain Text Files";
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
