package org.hannes.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

public class DisposeActionListener implements ActionListener {

	/**
	 * 
	 */
	private final JDialog dialog;

	public DisposeActionListener(JDialog dialog) {
		this.dialog = dialog;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dialog.dispose();
	}

}