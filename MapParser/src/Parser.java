import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Parser {

	public static void main(String[] args) throws FileNotFoundException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("H:\\"));
		fileChooser.setSelectedFile(new File("data.dat"));

		int result = fileChooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			String filename = fileChooser.getSelectedFile().getPath();
			JOptionPane.showMessageDialog(null, "You selected " + filename);
			InputStream is = new DataInputStream(new FileInputStream(new File(
					filename)));
			Scanner in = new Scanner(is);
			String word, previous = "";
			int numRepeats = 1;
			while (in.hasNextLine()) {
				word = in.nextLine();
				
			System.out.println(word);
			

			}
		} else if (result == JFileChooser.CANCEL_OPTION) {
			JOptionPane.showMessageDialog(null, "You selected nothing.");
		} else if (result == JFileChooser.ERROR_OPTION) {
			JOptionPane.showMessageDialog(null, "An error occurred.");
		}

	}

}
