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
public class GDFFileFilter implements FinderFilter {

	/**
	 *
	 */
	private final String[] extensions = new String[]{
			  Extensions.anmExt , Extensions.arcExt , Extensions.arzExt , Extensions.cnvExt
			, Extensions.dbrExt , Extensions.fntExt , Extensions.luaExt , Extensions.mshExt
			, Extensions.pfxExt , Extensions.qstExt , Extensions.sshExt , Extensions.texExt
			, Extensions.tplExt , Extensions.wavExt , Extensions.wrlExt , Extensions.lvlExt
			, Extensions.rlvExt };

	@Override
	public String getName ( ) {
		return Keys.gdfFilterKey;
	}

	@Override
	public String getDescription ( ) {
		return "All Grim Dawn files";
	}

	@Override
	public String[] getExtensions ( ) {
		return extensions;
	}

	@Override
	public boolean accept ( File pathname ) {
		for ( String ext : extensions ) {
			if ( ext.equals ( FilenameUtils.getExtension ( pathname.getName ( ) ) ) ) {
				return true;
			}
		}
		return false;
	}
}
