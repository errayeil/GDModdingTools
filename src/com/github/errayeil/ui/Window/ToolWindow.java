package com.github.errayeil.ui.Window;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 */
public class ToolWindow {

	/**
	 *
	 */
	private JFrame frame;

	/**
	 * Button to open AssetManager.
	 */
	private JButton amwOpenButton;

	/**
	 * Button to open DBREditTool.
	 */
	private JButton dbrOpenButton;

	/**
	 * Button to open TextEditorTool.
	 */
	private JButton tetOpenButton;

	/**
	 * Button to open LuaEditorTool.
	 */
	private JButton letOpenButton;

	/**
	 * Button to open LTNameTool.
	 */
	private JButton ltntOpenButton;

	/**
	 * Button to open LTResetTool.
	 */
	private JButton ltrtOpenButton;

	/**
	 * Button to open LTWeightTool.
	 */
	private JButton ltwtOpenButton;

	/**
	 * Button to open RecordListTool.
	 */
	private JButton rltOpenButton;

	/**
	 *
	 */
	public ToolWindow() {
		frame = new JFrame ( "Suite Tools" );
	}

	/**
	 *
	 */
	public void openToolWindow() {

	}

	/**
	 *
	 */
	public void closeToolWindow() {
		//TODO do stuff

		System.exit ( 0 );
	}
}
