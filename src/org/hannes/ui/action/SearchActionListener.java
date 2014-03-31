package org.hannes.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.hannes.grooveshark.Session;
import org.hannes.grooveshark.node.Query;
import org.hannes.grooveshark.node.QueryResult;
import org.hannes.grooveshark.node.Song;
import org.hannes.ui.list.SongList;

public class SearchActionListener implements ActionListener {

	/**
	 * The song list we need to fill
	 */
	private final SongList list;
	
	/**
	 * the text field containing the search query
	 */
	private final JTextField searchField;
	
	/**
	 * The current session
	 */
	private final Session session;
	
	/**
	 * The JScrollPane that has to be refreshed after a search query
	 */
	private final JScrollPane scrollPane;

	public SearchActionListener(SongList list, JTextField searchField, JScrollPane scrollPane, Session session) {
		this.list = list;
		this.searchField = searchField;
		this.session = session;
		this.scrollPane = scrollPane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			String search_string = searchField.getText();
			
			/*
			 * Request the songs
			 */
			Query<Song> query = session.request(new Query<Song>(search_string, QueryResult.SONGS)).getResult();
			
			/*
			 * Add all the songs to the list
			 */
			list.clear();
			list.addAll(Arrays.asList(query.getResults()));
		
			/*
			 * refresh the scrollpane
			 */
			scrollPane.setViewportView(list);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}