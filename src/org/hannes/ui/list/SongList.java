package org.hannes.ui.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.hannes.grooveshark.node.Song;
import org.hannes.ui.Frame;

@SuppressWarnings("serial")
public class SongList extends JTable implements TableModel {

	/**
	 * Amount of columns
	 */
	private static final int COLUMN_COUNT = 2;
	
	/**
	 * The names of the columns (in respective order)
	 */
	private static final String[] COLUMN_NAMES = { "Title", "Download" };
	
	/**
	 * The collection containing all the songs for this list
	 */
	private final List<SongListElement> list = new ArrayList<>();
	
	/**
	 * Collection of listeners
	 */
	private final List<TableModelListener> listeners = new ArrayList<>();

	private final Frame parent;

	/**
	 * So we can use default constructor
	 */
	public SongList(Frame parent) {
		this.parent = parent;
		super.setModel(this);
		super.setDefaultRenderer(Object.class, new SongListCellRenderer(this));
		
		/*
		 * Resize columns
		 */
		super.getColumnModel().getColumn(1).setMinWidth(50);
		super.getColumnModel().getColumn(1).setMaxWidth(50);
		super.getColumnModel().getColumn(1).setWidth(50);
		
		/*
		 * Remove the table header
		 */
		super.setTableHeader(null);
		
		/*
		 * Row height
		 */
		super.setRowHeight(25);
		
		/*
		 * Add the listeners
		 */
		super.addKeyListener(new SongListActionListener(this));
		super.addMouseListener(new SongListActionListener(this));
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMN_COUNT;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return COLUMN_NAMES[columnIndex];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return Object.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return list.get(rowIndex);
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		if (value instanceof SongListElement) {
			list.set(rowIndex, (SongListElement) value);
		}
		throw new IllegalArgumentException("value must be of type Song");
	}

	@Override
	public void addTableModelListener(TableModelListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeTableModelListener(TableModelListener listener) {
		listeners.remove(listener);
	}

	public SongListElement get(int row) {
		return list.get(row);
	}

	public Iterator<SongListElement> iterator() {
		return list.iterator();
	}

	public boolean add(SongListElement e) {
		return list.add(e);
	}

	public void addAll(Collection<? extends Song> collection) {
		for (Iterator<? extends Song> iterator = collection.iterator(); iterator.hasNext(); ) {
			add (new SongListElement(iterator.next()));
		}
	}

	public void clear() {
		list.clear();
	}

	public Song[] getSelectedSongs() {
		List<Song> songs = new ArrayList<>();
		for (Iterator<SongListElement> iterator = list.iterator(); iterator.hasNext(); ) {
			SongListElement element = iterator.next();
			
			if (element.isSelected()) {
				songs.add(element.getSong());
			}
		}
		return songs.toArray(new Song[songs.size()]);
	}

	public Frame getFrame() {
		return parent;
	}

}