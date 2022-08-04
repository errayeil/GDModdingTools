package com.github.errayeil.ui.finder.Filters;

import com.github.errayeil.Persistence.Persistence.Keys;
import com.github.errayeil.utils.ToolsUtils.Extensions;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

public class TexFileFilter implements FinderFilter {

	@Override
	public String getName ( ) {
		return Keys.texFilterKey;
	}

	@Override
	public String getDescription ( ) {
		return "Texture Files";
	}

	@Override
	public String[] getExtensions ( ) {
		return new String[  ] { Extensions.texExt };
	}

	@Override
	public boolean accept ( File pathname ) {
		return FilenameUtils.getExtension ( pathname.getName ( ) ).equals ( Extensions.texExt );
	}
}
