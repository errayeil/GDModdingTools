package com.github.errayeil.ui.Custom;

import javax.accessibility.AccessibleContext;
import javax.swing.*;
import javax.swing.plaf.FileChooserUI;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentListener;

/**
 * Super hacky extension of JFileChooser until I can get around to creating my own File chooser
 * with good features.
 * JFileChooser is the only swing component I legitimately dislike.
 */
public class AMFileChooser extends JFileChooser {

	/**
	 *
	 */
	private JDialog dialog;

	/**
	 *
	 */
	private ComponentAdapter adapter;

	/**
	 *
	 */
	private boolean isNullRelative = true;

	private int x = -1;

	private int y = -1;

	private int width = -1;

	private int height = -1;

	/**
	 *
	 */
	public AMFileChooser() {

	}

	@Override
	protected JDialog createDialog ( Component parent ) throws HeadlessException {
		FileChooserUI ui = getUI();
		String title = ui.getDialogTitle(this);
		putClientProperty( AccessibleContext.ACCESSIBLE_DESCRIPTION_PROPERTY,
				title);

		Window window = SwingUtilities.windowForComponent ( parent );
		dialog = new JDialog (  );
		dialog.setModal ( true );
		dialog.setTitle ( title );

		dialog.setComponentOrientation(this.getComponentOrientation());

		Container contentPane = dialog.getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(this, BorderLayout.CENTER);

		if (JDialog.isDefaultLookAndFeelDecorated()) {
			boolean supportsWindowDecorations =
					UIManager.getLookAndFeel().getSupportsWindowDecorations();
			if (supportsWindowDecorations) {
				dialog.getRootPane().setWindowDecorationStyle( JRootPane.FILE_CHOOSER_DIALOG);
			}
		}

		if (isNullRelative) {
			dialog.setLocationRelativeTo ( null );
		}

		if (x != -1 && y != -1) {
			dialog.setLocation ( x, y );
		}

		if (width != -1 && height != -1)  {
			dialog.setSize ( width, height );
		}


		dialog.addComponentListener ( adapter );
		dialog.pack();

		return dialog;
	}

	/**
	 *
	 * @param isNull
	 */
	public void setNullLocationRelative(boolean isNull) {
		isNullRelative = isNull;
	}

	/**
	 *
	 * @param adapter
	 */
	public void setComponentAdapter ( ComponentAdapter adapter) {
		this.adapter = adapter;
	}

	/**
	 *
	 * @return
	 */
	public JDialog getDialog() {
		return dialog;
	}



	/**
	 *
	 * @param newX
	 */
	public void setDialogCoordinates(int newX, int newY) {
		if (dialog != null) {
			this.x = newX;
			this.y = newY;
			dialog.setLocation ( newX, newY );
		}
	}

	/**
	 *
	 * @param newWidth
	 * @param newHeight
	 */
	public void setDialogSize( int newWidth, int newHeight) {
		if (dialog != null) {
			this.width = newWidth;
			this.height = newHeight;
			dialog.setSize ( newWidth, newHeight );
		}
	}
}
