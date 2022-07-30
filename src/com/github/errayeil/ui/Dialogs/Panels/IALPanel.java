package com.github.errayeil.ui.Dialogs.Panels;

import com.github.errayeil.ListApps.ListApps;
import com.github.errayeil.ListApps.Software;
import com.github.errayeil.ui.Custom.IALCellRenderer;
import io.codeworth.panelmatic.PanelMatic;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.*;

import static com.github.errayeil.utils.CompUtils.fill;
import static io.codeworth.panelmatic.componentbehavior.Modifiers.*;
import static io.codeworth.panelmatic.util.Groupings.lineGroup;

/**
 *
 */
public class IALPanel extends JComponent {

	/**
	 *
	 */
	private JTextField selectedPathField;

	/**
	 *
	 */
	public IALPanel ( ) {
		JList<Software> appList = new JList<> ( );
		JScrollPane scrollPane = new JScrollPane ( appList );
		DefaultListModel<Software> model = new DefaultListModel<> ( );
		JTextField searchField = new JTextField ( 40 );
		selectedPathField = new JTextField ( 40 );
		JButton filterButton = new JButton ( "F" );
		JButton okButton = new JButton ( "Accept" );
		JButton cancelButton = new JButton ( "Cancel" );

		okButton.setEnabled ( false );
		selectedPathField.setEditable ( false );
		selectedPathField.setFocusable ( false );

		model.addAll ( loadInstalledApps ( ) );

		appList.setModel ( model );
		appList.setCellRenderer ( new IALCellRenderer ( ) );

		ActionListener al = e -> {
			SwingUtilities.windowForComponent ( IALPanel.this ).dispose ();
		};

		okButton.addActionListener ( al );
		cancelButton.addActionListener ( al );

		appList.addListSelectionListener ( (l) -> {
			Software selected = appList.getSelectedValue ();
			selectedPathField.setText ( selected.getInstallLocation () );
			okButton.setEnabled ( true );
		} );

		PanelMatic.begin ( this )
				.add ( lineGroup ( fill ( 5 , 0 ) , searchField , fill ( 5 , 0 ) , filterButton, fill ( 5, 0 ) ) )
				.add ( lineGroup( fill ( 5, 0 ), scrollPane), L_START, GROW_MORE )
				.add ( lineGroup ( fill( 1, 0 ), selectedPathField  ), L_START, GROW_MORE)
				.addFlexibleSpace ()
				.add ( lineGroup ( okButton, fill ( 5, 0 ), cancelButton ), L_CENTER )
				.get (  );

	}

	/**
	 *
	 * @return
	 */
	public String getSelectedApp() {
		return selectedPathField.getText ();
	}


	/**
	 * @return
	 */
	private List<Software> loadInstalledApps ( ) {
		List<Software> list = new ArrayList<> ( ListApps.getInstalledApps ( false ).values ( ).stream ( ).toList ( ) );
		List<Software> toRemove = new ArrayList<> (  );

		for (Software s : list) {
			if (s.getInstallLocation () == null) {
				toRemove.add ( s );
			}
		}

		list.removeAll ( toRemove ); //Removes all apps with null paths. TODO: Figure out why they return null
		list.sort ( Comparator.comparing ( Software::getDisplayName ));

		return list;
	}
}
