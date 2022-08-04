package com.github.errayeil.ui.finder.Filters;

import com.github.errayeil.Persistence.Persistence.Keys;
import com.github.errayeil.utils.ToolsUtils.Extensions;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

public class WavFileFilter implements FinderFilter {
	@Override
	public String getName ( ) {
		return Keys.wavFilterKey;
	}

	@Override
	public String getDescription ( ) {
		return "Wav Files";
	}

	@Override
	public String[] getExtensions ( ) {
		return new String[  ] { Extensions.wavExt };
	}

	@Override
	public boolean accept ( File pathname ) {
		return FilenameUtils.getExtension ( pathname.getName ( ) ).equals ( Extensions.wavExt );
	}
}
