package org.hannes.util;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Resources {

	/**
	 * The images
	 */
	private static final Map<String, Image> images = new HashMap<>();

	public static void initialize() throws IOException {
		images.put("checkbox_no", ImageIO.read(new File("icons/checkbox_no.png")));
		images.put("checkbox_yes", ImageIO.read(new File("icons/checkbox_yes.png")));
		images.put("search_button", ImageIO.read(new File("icons/search.png")));
		images.put("download_button", ImageIO.read(new File("icons/download.png")));
	}

	public static Image getImage(String name) {
		return images.get(name);
	}

}