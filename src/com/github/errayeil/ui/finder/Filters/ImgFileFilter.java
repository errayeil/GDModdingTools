package com.github.errayeil.ui.finder.Filters;

import com.github.errayeil.utils.ToolsUtils.Extensions;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

/**
 * @author Errayeil
 * @version 0.1
 * @since 0.1
 */
public class ImgFileFilter implements FinderFilter{

	/**
	 *
	 */
	private final String[] extensions = { Extensions.psdExt ,
										  Extensions.tgaExt ,
										  Extensions.bmpExt ,
										  Extensions.pngExt ,
										  Extensions.jpgExt };

	@Override
	public String getName ( ) {
		return null;
	}

	@Override
	public String getDescription ( ) {
		return "Image Files";
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
