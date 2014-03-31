package org.hannes.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import org.hannes.grooveshark.node.Song;
import org.hannes.ui.action.DisposeActionListener;

public class DownloadDialog {

	/**
	 * The dialog
	 */
	private JDialog dialog;
	
	/**
	 * The progressbar
	 */
	private JProgressBar progressbar;
	
	/**
	 * The label showing the current downloading song name
	 */
	private JLabel lblSongPath;
	
	/**
	 * 
	 */
	private  JLabel lblSongIndex;
	
	/**
	 * the ok button
	 */
	private JButton okButton;

	public DownloadDialog(Frame frame) {
		dialog = new JDialog(frame.getJFrame(), "Downloading ...");
		dialog.setModal(true);
		
		/*
		 * The content pane
		 */
		JPanel content_pane = new JPanel(new BorderLayout());
		content_pane.setPreferredSize(new Dimension(430, 120));

		/*
		 * The main panel
		 */
		JPanel main_panel = new JPanel(new MigLayout("", "[grow,left]", "[][][][]"));
		main_panel.setBorder(new EmptyBorder(5, 5, 5, 5));

		/*
		 * The song index label
		 */
		lblSongIndex = new JLabel("...");
		lblSongIndex.setFont(lblSongIndex.getFont().deriveFont(18.0F));
		main_panel.add(lblSongIndex, "cell 0 0");
		
		/*
		 * The song path label
		 */
		lblSongPath = new JLabel("...");
		main_panel.add(lblSongPath, "cell 0 1");
		
		/*
		 * The progress bar
		 */
		progressbar = new JProgressBar();
		progressbar.setStringPainted(true);
		main_panel.add(progressbar, "cell 0 2 1 2,growx");

		/*
		 * The button pane
		 */
		JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		/*
		 * The ok button
		 */
		okButton = new JButton("OK");
		okButton.setEnabled(false);
		okButton.addActionListener(new DisposeActionListener(dialog));
		
		/*
		 * The cancel button
		 */
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new DisposeActionListener(dialog));
		
		/*
		 * Add all the components to the button pane
		 */
		buttonPane.add(cancelButton);
		buttonPane.add(okButton);

		/*
		 * Add all the components to the content pane
		 */
		content_pane.add(buttonPane, BorderLayout.SOUTH);
		content_pane.add(main_panel, BorderLayout.CENTER);
		
		/*
		 * Finish up frame
		 */
		dialog.setContentPane(content_pane);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.pack();
		dialog.setResizable(false);
		dialog.setLocationRelativeTo(frame.getJFrame());
	}

	public void setVisible(boolean b) {
		dialog.setVisible(b);
	}

	public void addWindowListener(WindowListener l) {
		dialog.addWindowListener(l);
	}

	public void setCurrentSongId(int index, int max) {
		lblSongIndex.setText("Song " + (index + 1) + " of " + max + ".");
	}

	public void setCurrentDownload(Song song) {
		lblSongPath.setText(song.getArtist() + " - " + song.getName());
	}

	public void setProgress(double progress) {
		progressbar.setValue((int) (progress * (double) (progressbar.getMaximum())));
	}

	public void enableOKButton() {
		okButton.setEnabled(true);
		okButton.repaint();
	}

}