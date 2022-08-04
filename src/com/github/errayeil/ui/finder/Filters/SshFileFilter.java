package com.github.errayeil.ui.finder.Filters;

import com.github.errayeil.Persistence.Persistence.Keys;
import com.github.errayeil.utils.ToolsUtils.Extensions;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

public class SshFileFilter implements FinderFilter {

	@Override
	public String getName ( ) {
		return Keys.sshFilterKey;
	}

	@Override
	public String getDescription ( ) {
		return "Ssh Files";
	}

	@Override
	public String[] getExtensions ( ) {
		return new String[ ] { Extensions.sshExt };
	}

	@Override
	public boolean accept ( File pathname ) {
		return FilenameUtils.getExtension ( pathname.getName ( ) ).equals ( Extensions.sshExt );
	}
}
