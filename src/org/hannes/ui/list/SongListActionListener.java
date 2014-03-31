package org.hannes.ui.list;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SongListActionListener implements KeyListener, MouseListener {

	/**
	 * The song list for this action listener
	 */
	private final SongList list;

	public SongListActionListener(SongList list) {
		this.list = list;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int column = list.columnAtPoint(e.getPoint());
		int row = list.rowAtPoint(e.getPoint());
		
		if (row >= 0 && row < list.getRowCount() && column == SongListCellRenderer.CHECKBOX_COLUMN) {
			list.get(row).setSelected(!list.get(row).isSelected());
			list.repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_SPACE) {
			for (int row : list.getSelectedRows()) {
				list.get(row).setSelected(!list.get(row).isSelected());
			}
			list.repaint();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

}