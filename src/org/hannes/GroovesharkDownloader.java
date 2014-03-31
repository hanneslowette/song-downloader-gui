package org.hannes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.hannes.grooveshark.Grooveshark;
import org.hannes.ui.Frame;
import org.hannes.util.Resources;

public class GroovesharkDownloader {

	public static void main(String[] args) throws InvocationTargetException, InterruptedException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, IOException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		/*
		 * Initialize the resources
		 */
		Resources.initialize();

		/*
		 * Show the frame
		 */
		Frame frame = new Frame("Grooveshark Download 1.0 Alpha");
		frame.show(Grooveshark.createSession());
	}

}