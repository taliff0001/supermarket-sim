package lepers;

import java.awt.FileDialog;
import java.awt.Frame;


public class InOut {
		/**
		 * File path of the file to read the file.
		 */
		static String readLocation;
		/**
		 * File path of the location to write a file.
		 */
		static String writeLocation;
	
		/**
		 * Display a file dialog that is used to get the path of a file to read.
		 * @return file path of the selected file
		 */
	public static String getFile() {
		
		Frame frame1 = new Frame();
		FileDialog FD = new FileDialog(frame1, "Open file", 0);
		FD.setVisible(true);
		String fileName = FD.getFile();
		String directory = FD.getDirectory();
		readLocation = directory + fileName;
		return readLocation;
	}
	/**
	 * Display a file dialog that is used to choose a location to save a file.
	 * @return file path of where the user wants to write that file in.
	 */
	public static String getWriteLocation() {
		Frame frame1 = new Frame();
		FileDialog FD = new FileDialog(frame1, "Save to disk", 1);
		FD.setVisible(true);
		String fileName = FD.getFile();
		String directory = FD.getDirectory();
		writeLocation = directory + fileName;
		return writeLocation;
	}

}

