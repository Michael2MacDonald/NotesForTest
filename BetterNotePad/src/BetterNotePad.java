// Import Dependencies
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.io.*;
import java.util.Scanner;
import java.net.URL;

// Main Class
public class BetterNotePad extends JFrame {

	private static JFrame frame = new JFrame("Better NotePad"); // Window
	private static JTextArea textArea = new JTextArea(); // Text area for editing

	JTextArea TBText = new JTextArea(); // Displays the path of the open file in the toolbar

	private String Font = "Comic Sans MS"; // Default font
	private int FontDecoration = 0; // Default font decoration (None)
	private int FontSize = 12; // Default font size (12px)

	private JFileChooser fileChooser = new JFileChooser(); // File chooser

	public BetterNotePad(String[] args) {
		// System.out.println(args.length);
		// System.out.println(args[0]);
		if (args.length > 0) { // If the program is run with an arg, open the arg as a path
				
				fileChooser.setSelectedFile(new File(args[0])); // Save file to fileChooser object
				// Read file to text area
				String ingest = ""; // buffer
				File selectedFile = new File(fileChooser.getSelectedFile().getAbsolutePath()); // save file to File object to work with it
				try { // read file and save contents to textArea for editing
					FileReader read = new FileReader(selectedFile);
					Scanner scan = new Scanner(read);
					while(scan.hasNextLine()){
						String line = scan.nextLine() + "\n";
						ingest = ingest + line;
					}
					scan.close();
					// if (ingest == "\n" || ingest == "\r"){
					//     ingest = ingest.substring(0, ingest.length() - 1);
					// } else if (ingest == "\r\n"){
					//     ingest = ingest.substring(0, ingest.length() - 2);
					// }
					textArea.setText(ingest);
					textArea.setCaretPosition(0);
				} catch ( FileNotFoundException ex) {
					ex.printStackTrace();
				}

				TBText.setText(fileChooser.getSelectedFile().getAbsolutePath());
				frame.setTitle(selectedFile.getName() + " - Better NotePad"); // Set title of app window
		}

		// System.out.println(arg);
		// if (arg.length() > 0) {
		//     fileChooser.setSelectedFile(new File(arg));
		// }

		// String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		// for (String font : fonts) {
		//     System.out.println(font);
		// }
		// Window Settings
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700,500); // Set default window size
		frame.setMinimumSize(new Dimension(400, 300)); // Set minimum window size

		URL imgURL = BetterNotePad.class.getResource("/icon.png"); // App icon
		if(imgURL != null){
				// System.out.println("found icon file");
				ImageIcon myAppImage = new ImageIcon(imgURL);
				frame.setIconImage(myAppImage.getImage());
		} else {
				// System.out.println("could not find icon file");
		}

		// Text Area Settings
		textArea.setCaretPosition(0);
		textArea.setMargin(new Insets(5,5,5,5));
		textArea.setFont(new Font(Font, FontDecoration, FontSize));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

		// Create menu bar
		JMenuBar MenuBar = new JMenuBar();
		// Create menu items
		JMenu MenuItemFile = new JMenu("File");
		MenuBar.add(MenuItemFile);
		JMenu MenuItemEdit = new JMenu("Edit");
		MenuBar.add(MenuItemEdit);
		JMenu MenuItemFormat = new JMenu("Format");
		MenuBar.add(MenuItemFormat);
		// Create File menu sub-items
		JMenuItem MenuItemOpen = new JMenuItem("Open");
		MenuItemFile.add(MenuItemOpen);
		JMenuItem MenuItemSave = new JMenuItem("Save");
		MenuItemFile.add(MenuItemSave);
		JMenuItem MenuItemSaveAs = new JMenuItem("Save as");
		MenuItemFile.add(MenuItemSaveAs);
		JMenuItem MenuItemExit = new JMenuItem("Exit");
		MenuItemFile.add(MenuItemExit);
		MenuItemExit.addActionListener((event) -> System.exit(0));
		// Create Edit menu sub-items
		JMenuItem MenuItemCut = new JMenuItem("Cut");
		MenuItemEdit.add(MenuItemCut);
		JMenuItem MenuItemCopy = new JMenuItem("Copy");
		MenuItemEdit.add(MenuItemCopy);
		JMenuItem MenuItemPaste = new JMenuItem("Paste");
		MenuItemEdit.add(MenuItemPaste);
		// Create Format sub-menus
		JMenuItem MenuFontOpt = new JMenu("Font Options");
		MenuItemFormat.add(MenuFontOpt);
		JMenuItem MenuFontDecoration = new JMenu("Font Decoration");
		MenuItemFormat.add(MenuFontDecoration);
		// Create Font Options sub-menus
		JMenuItem MenuFonts = new JMenu("Font");
		MenuFontOpt.add(MenuFonts);
		JMenuItem MenuFontSize = new JMenu("Font Size");
		MenuFontOpt.add(MenuFontSize);
		// Create Font menu sub-items
		JMenuItem MenuItemComicSans = new JMenuItem("Comic Sans MS");
		MenuFonts.add(MenuItemComicSans);
		JMenuItem MenuItemTimes = new JMenuItem("Times New Roman");
		MenuFonts.add(MenuItemTimes);
		JMenuItem MenuItemLucida = new JMenuItem("Lucida Sans");
		MenuFonts.add(MenuItemLucida);
		JMenuItem MenuItemArial = new JMenuItem("Arial");
		MenuFonts.add(MenuItemArial);
		JMenuItem MenuItemPapyrus = new JMenuItem("Papyrus");
		MenuFonts.add(MenuItemPapyrus);
		// Create Font Size menu sub-items
		JMenuItem MenuItem10px = new JMenuItem("10px");
		MenuFontSize.add(MenuItem10px);
		JMenuItem MenuItem12px = new JMenuItem("12px");
		MenuFontSize.add(MenuItem12px);
		JMenuItem MenuItem14px = new JMenuItem("14px");
		MenuFontSize.add(MenuItem14px);
		JMenuItem MenuItem18px = new JMenuItem("18px");
		MenuFontSize.add(MenuItem18px);
		JMenuItem MenuItem24px = new JMenuItem("24px");
		MenuFontSize.add(MenuItem24px);
		// Create Font Decoration menu sub-items
		JMenuItem MenuItemNormal = new JMenuItem("Normal");
		MenuFontDecoration.add(MenuItemNormal);
		JMenuItem MenuItemBold = new JMenuItem("Bold");
		MenuFontDecoration.add(MenuItemBold);
		JMenuItem MenuItemItalic = new JMenuItem("Italic");
		MenuFontDecoration.add(MenuItemItalic);


		// Create Scroll Pane
		JScrollPane ScrollPane = new JScrollPane(textArea);
		// ScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Uncomment this if you want the vertical scroll bar to always be visible
		// ScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // Uncomment this if you want the horizontal scroll bar to always be visible

		// Create Bottom Toolbar
		JToolBar ToolBar = new JToolBar();
		FlowLayout ToolBarLayout = new FlowLayout();
		TBText.setEditable(false); // Only for displaying the open file path
		ToolBar.setLayout(ToolBarLayout);
		ToolBar.setFloatable(false);
		ToolBar.add(TBText);

		// Action Listeners for menu items
		// File
		MenuItemOpen.addActionListener((event) -> showOpenFileDialog());
		MenuItemSave.addActionListener((event) -> showSaveFileDialog());
		MenuItemSaveAs.addActionListener((event) -> showSaveAsFileDialog());
		// MenuItemBold.addActionListener((event) -> setNewStyle(textArea, styledDoc, "bold", false));
		// Edit
		MenuItemCopy.addActionListener((event) -> copy());
		MenuItemCut.addActionListener((event) -> cut());
		MenuItemPaste.addActionListener((event) -> paste());
		// Edit Font
		MenuItemComicSans.addActionListener((event) -> SetFont("Comic Sans MS"));
		MenuItemTimes.addActionListener((event) -> SetFont("Times New Roman"));
		MenuItemLucida.addActionListener((event) -> SetFont("LucidaSans"));
		MenuItemArial.addActionListener((event) -> SetFont("Arial"));
		MenuItemPapyrus.addActionListener((event) -> SetFont("Papyrus"));
		// Font Size
		MenuItem10px.addActionListener((event) -> SetFontSize(10));
		MenuItem12px.addActionListener((event) -> SetFontSize(12));
		MenuItem14px.addActionListener((event) -> SetFontSize(14));
		MenuItem18px.addActionListener((event) -> SetFontSize(18));
		MenuItem24px.addActionListener((event) -> SetFontSize(24));
		// Font Decoration
		MenuItemNormal.addActionListener((event) -> SetFontDecoration(0));
		MenuItemBold.addActionListener((event) -> SetFontDecoration(1));
		MenuItemItalic.addActionListener((event) -> SetFontDecoration(2));

		// Add All Components To Window
		frame.getContentPane().add(MenuBar, BorderLayout.NORTH);
		frame.getContentPane().add(ScrollPane, BorderLayout.CENTER);
		frame.getContentPane().add(ToolBar, BorderLayout.SOUTH);

		// Set frame as visible
		frame.setVisible(true);

	}

	// Text Font Functions
	void SetFont(String fontSelection){
		Font = fontSelection;
		textArea.setFont(new Font(Font, FontDecoration, FontSize));
	}
	void SetFontDecoration(int decoration){
		FontDecoration = decoration;
		textArea.setFont(new Font(Font, FontDecoration, FontSize));
	}
	void SetFontSize(int size){
		FontSize = size;
		textArea.setFont(new Font(Font, FontDecoration, FontSize));
	}

	// Open File Dialog
	private void showOpenFileDialog() {
		String ingest = "";
		fileChooser.setDialogTitle("Choose a File to open");
		int response = fileChooser.showOpenDialog(this);
		if (response == JFileChooser.APPROVE_OPTION) {
			File selectedFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
			// System.out.println("Save as file: " + selectedFile.getAbsolutePath());
			try {
				FileReader read = new FileReader(selectedFile);
				Scanner scan = new Scanner(read);
				while(scan.hasNextLine()){
					String line = scan.nextLine() + "\n";
					ingest = ingest + line;
				}
				scan.close();
				textArea.setText(ingest);
				textArea.setCaretPosition(0);
			} catch ( FileNotFoundException ex) {
				ex.printStackTrace();
			}
			frame.setTitle(selectedFile.getName() + " - Better NotePad");
			TBText.setText(fileChooser.getSelectedFile().getAbsolutePath());
		}
	}

	// Save File Dialog
	private void showSaveFileDialog() {
		if(fileChooser.getSelectedFile() == null) { // If there is no file then prompt the use to select one
			fileChooser.setDialogTitle("Choose a file to save to");
			int response = fileChooser.showSaveDialog(this);
			if (response != JFileChooser.APPROVE_OPTION) {
				return; // If nothing is selected, abort save
			}
		}
		File selectedFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
		try {
			FileWriter out = new FileWriter(selectedFile);
			out.write(textArea.getText());
			out.close();
		} catch (FileNotFoundException ex) {
			Component f = null;
			JOptionPane.showMessageDialog(f,"File not found.");
		} catch (IOException ex) {
			Component f = null;
			JOptionPane.showMessageDialog(f,"Error.");
		}
		frame.setTitle(selectedFile.getName() + " - Better NotePad");
	}

	// Save File As Dialog
	private void showSaveAsFileDialog() {
		fileChooser.setDialogTitle("Save File As");
		int response = fileChooser.showSaveDialog(this);
		if (response == JFileChooser.APPROVE_OPTION) {
			File selectedFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
			// System.out.println("Save as file: " + selectedFile.getAbsolutePath());
			try {
				FileWriter out = new FileWriter(selectedFile);
				out.write(textArea.getText());
				out.close();
			} catch (FileNotFoundException ex) {
				Component f = null;
				JOptionPane.showMessageDialog(f,"File not found.");
			} catch (IOException ex) {
				Component f = null;
				JOptionPane.showMessageDialog(f,"Error.");
			}
			frame.setTitle(selectedFile.getName() + " - Better NotePad");
		}
	}

	// Copy/Cut/Paste Functions
	public void copy(){
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable transferable = new StringSelection(textArea.getSelectedText());
		clipboard.setContents(transferable, null);
	}
	public void cut(){
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable transferable = new StringSelection(textArea.getSelectedText());
		clipboard.setContents(transferable, null);
		textArea.replaceSelection("");
	}
	public void paste(){
		textArea.paste();
	}

	// Main Method
	public static void main(String[] args) {
		new BetterNotePad(args);
		// new BetterNotePad().setVisible(true);
	}
}
