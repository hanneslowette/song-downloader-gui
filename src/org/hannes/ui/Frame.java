package org.hannes.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import org.hannes.grooveshark.Session;
import org.hannes.ui.action.DownloadActionListener;
import org.hannes.ui.action.SearchActionListener;
import org.hannes.ui.list.SongList;
import org.hannes.util.Resources;

public class Frame {

	private final JFrame frame;

	public Frame(String title) {
		this.frame = new JFrame(title);
	}

	public void show(Session session) {
		JPanel content_pane = new JPanel(new BorderLayout());
		content_pane.setPreferredSize(new Dimension(640, 380));
		
		/*
		 * Initialize the song list
		 */
		SongList song_list = new SongList(this);
		song_list.setShowGrid(false);
		song_list.setIntercellSpacing(new Dimension(0, 0));
		
		/*
		 * 
		 */
		JScrollPane scroll_pane = new JScrollPane(song_list);
		
		/*
		 * Initialize the toolbar
		 */
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		
		/*
		 * create search textfield and set font larger for stylepoints
		 */
		JTextField search_field = new JTextField();
		search_field.setFont(search_field.getFont().deriveFont(18f));
		search_field.addActionListener(new SearchActionListener(song_list, search_field, scroll_pane, session));
		
		/*
		 * Create search button
		 */
		JButton search_button = new JButton(new ImageIcon(Resources.getImage("search_button")));
		search_button.addActionListener(new SearchActionListener(song_list, search_field, scroll_pane, session));
		
		/*
		 * Create search button
		 */
		JButton download_button = new JButton(new ImageIcon(Resources.getImage("download_button")));
		download_button.addActionListener(new DownloadActionListener(session, song_list));
		
		/*
		 * Add all components to the toolbar
		 */
		toolbar.add(search_field);
		toolbar.add(search_button);
		toolbar.add(download_button);

		/*
		 * Add all the components to the content pane
		 */
		content_pane.add(toolbar, BorderLayout.NORTH);
		content_pane.add(scroll_pane, BorderLayout.CENTER);
		
		/*
		 * Finish up and show the frame
		 */
		frame.setContentPane(content_pane);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public JFrame getJFrame() {
		return frame;
	}

}