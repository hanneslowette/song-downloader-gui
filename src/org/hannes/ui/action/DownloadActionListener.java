package org.hannes.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.SwingUtilities;

import org.hannes.concurrent.DownloadThread;
import org.hannes.concurrent.DownloadThread.Feedback;
import org.hannes.grooveshark.Session;
import org.hannes.ui.DownloadDialog;
import org.hannes.ui.list.SongList;
import org.hannes.util.Callback;

public class DownloadActionListener implements ActionListener {

	/**
	 * The session
	 */
	private final Session session;
	
	/**
	 * The song list
	 */
	private final SongList list;

	public DownloadActionListener(Session session, SongList list) {
		this.session = session;
		this.list = list;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/*
		 * Create and show the dialog
		 */
		final DownloadDialog dialog = new DownloadDialog(list.getFrame());
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				dialog.setVisible(true);
			}
			
		});
		
		/*
		 * Create the download thread + monitor progress
		 */
		final Future<List<File>> future = Executors.newSingleThreadExecutor().submit(new DownloadThread(list.getSelectedSongs(), session, new Callback<Feedback>() {
			
			@Override
			public void call(final Feedback feedback) throws Exception {
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						dialog.setCurrentDownload(feedback.getCurrentSong());
						dialog.setCurrentSongId(feedback.getSongIndex(), list.getSelectedSongs().length);
						dialog.setProgress(feedback.getProgress());
					}
					
				});
			}
			
			@Override
			public void exceptionCaught(Exception ex) {
				ex.printStackTrace();
			}

			@Override
			public void completed() {
				dialog.enableOKButton();
			}
			
		}));
		
		/*
		 * If dialog closed, cancel the download
		 */
		dialog.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosed(WindowEvent e) {
				future.cancel(true);
			}
			
		});
	}

}