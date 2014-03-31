package org.hannes.ui.list;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

import org.hannes.util.Resources;

public class SongListCellRenderer implements TableCellRenderer {

	/**
	 * The index of the column that contains the title string
	 */
	public static final int TITLE_COLUMN = 0;
	
	/**
	 * The index of the column that contains the boolean indicating the user wants to download this song
	 */
	public static final int CHECKBOX_COLUMN = 1;

	/**
	 * List item colors
	 */
	private static final Color[] COLORS = { Color.WHITE, Color.LIGHT_GRAY };

	/**
	 * the song list
	 */
	private final SongList list;

	public SongListCellRenderer(SongList list) {
		this.list = list;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = new JLabel();
		label.setBorder(new EmptyBorder(5, 3, 5, 3));

		switch (column) {
		case TITLE_COLUMN:
			label.setText(list.get(row).getArtist() + " - " + list.get(row).getName());
			label.setForeground(isSelected ? table.getBackground() : table.getForeground());
			label.setBackground(isSelected ? table.getForeground() : table.getBackground());
			break;
	
		case CHECKBOX_COLUMN:
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setIcon(new ImageIcon(list.get(row).isSelected() ? Resources.getImage("checkbox_yes") : Resources.getImage("checkbox_no")));
			break;
		}

		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(isSelected ? Color.DARK_GRAY : COLORS[row % 2]);
		panel.add(label, BorderLayout.CENTER);
		return panel;
	}

}