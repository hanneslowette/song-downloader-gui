package org.hannes.ui.list;

import org.hannes.grooveshark.node.Song;

public class SongListElement {
	
	/**
	 * The song
	 */
	private final Song song;
	
	/**
	 * Indicates this song has to be downloaded
	 */
	private boolean selected;

	public SongListElement(Song song) {
		this.song = song;
	}

	public Song getSong() {
		return song;
	}

	public int getId() {
		return song.getId();
	}

	public String getName() {
		return song.getName();
	}

	public String getArtist() {
		return song.getArtist();
	}

	public String getAlbumName() {
		return song.getAlbumName();
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}