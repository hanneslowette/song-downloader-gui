package org.hannes.concurrent;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.List;
import java.util.concurrent.Callable;

import org.hannes.grooveshark.Session;
import org.hannes.grooveshark.node.DownloadInformation;
import org.hannes.grooveshark.node.Song;
import org.hannes.util.Callback;

public class DownloadThread implements Callable<List<File>> {

	/**
	 * The buffer size (56k)
	 */
	private static final int BUFFER_SIZE = 57344;

	/**
	 * The song to be downloaded
	 */
	private final Song[] songs;
	
	/**
	 * The session
	 */
	private final Session session;
	
	/**
	 * Callback for the progress
	 */
	private final Callback<Feedback> callback;

	public DownloadThread(Song[] songs, Session session, Callback<Feedback> callback) {
		this.songs = songs;
		this.session = session;
		this.callback = callback;
	}

	@Override
	public List<File> call() throws Exception {
		try {
			for (int i = 0; i < songs.length; i++) {
				Song song = songs[i];
				DownloadInformation information = song.getDownloadInformation(session);
				
				/*
				 * Start the download from the server
				 */
				session.startDownload(information);
				
				/*
				 * Create the file objects
				 */
				File file = new File(System.getProperty("user.home") + "/Downloads/" + song.getArtist() + " - " + song.getName() + ".mp3");
				RandomAccessFile raf = new RandomAccessFile(file, "rw");
				
				/*
				 * Create the connection
				 */
				HttpURLConnection connection = (HttpURLConnection) song.getDownloadInformation(session).getUrl().openConnection();
				ReadableByteChannel in_channel = Channels.newChannel(connection.getInputStream());
				FileChannel out_channel = raf.getChannel();
				
				/*
				 * Transfer data
				 */
				for (ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE); in_channel.read(buffer) > 0; ) {
					out_channel.write((ByteBuffer) buffer.flip());
					buffer = ByteBuffer.allocate(BUFFER_SIZE);
					
					/*
					 * Update progress
					 */
					callback.call(new Feedback(i, song, (double) out_channel.size() / (double) connection.getContentLength()));
				}
				
				/*
				 * Close the file
				 */
				raf.close();
				
				/*
				 * Close the stream from the server
				 */
				session.verify(information);
			}
			callback.completed();
		} catch (Exception ex) {
			callback.exceptionCaught(ex);
		}
		return null;
	}

	public static class Feedback {
		
		/**
		 * The current song being downloaded
		 */
		private final Song currentSong;
		
		/**
		 * Progress of the current download
		 */
		private final double progress;
		
		/**
		 * Index of the song in the download queue
		 */
		private final int songIndex;

		public Feedback(int songIndex, Song currentSong, double progress) {
			this.currentSong = currentSong;
			this.progress = progress;
			this.songIndex = songIndex;
		}

		public Song getCurrentSong() {
			return currentSong;
		}

		public double getProgress() {
			return progress;
		}

		public int getSongIndex() {
			return songIndex;
		}
		
	}

}