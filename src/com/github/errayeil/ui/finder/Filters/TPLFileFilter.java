package com.github.errayeil.ui.finder.Filters;

import com.github.errayeil.Persistence.Persistence.Keys;
import com.github.errayeil.utils.ToolsUtils.Extensions;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

public class TPLFileFilter implements FinderFilter {


	@Override
	public String getName ( ) {
		return Keys.tplFilterKey;
	}

	@Override
	public String getDescription ( ) {
		return "Template Files";
	}

	@Override
	public String[] getExtensions ( ) {
		return new String[ ] { Extensions.tplExt };
	}

	@Override
	public boolean accept ( File pathname ) {
		return FilenameUtils.getExtension ( pathname.getName ( ) ).equals ( Extensions.tplExt );
	}
}
