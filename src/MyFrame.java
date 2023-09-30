import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

@SuppressWarnings({"unchecked", "unlikely-arg-type"})
public class MyFrame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	//Creating Variables and Objects
	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	JComboBox<?> letters;
	public static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
	public static final String NUMBERS = "1234567890";
	public static final String SYMBOLS = "!@#$%^&*()-_=+\\/~?";
	JLabel labels[] = new JLabel[6];
	JButton button[] = new JButton[3];
	Random random = new Random();
	JTextField field[] = new JTextField[3];
	String user, s, u, p, alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*()-_=+\\/~?";
	int initial = 90, count = 0;
	JButton copy[];
	JPanel pan[], mainPanel;
	JScrollPane scroll;
	
	//TEXT ENCRYPTION LOGIC
	String encrypt(String plainText, int shiftKey){
	    String cipherText = "";
	    for (int i = 0; i < plainText.length(); i++){
	        int charPosition = alphabet.indexOf(plainText.charAt(i));
	        int keyVal = (shiftKey + charPosition) % 80;
	        char replaceVal = alphabet.charAt(keyVal);
	        cipherText += replaceVal;
	    }
	    return cipherText;
	}

	//TEXT DECRYPTION LOGIC
	String decrypt(String cipherText, int shiftKey){
		String plainText = "";
		for (int i = 0; i < cipherText.length(); i++){
			int charPosition = alphabet.indexOf(cipherText.charAt(i));
			int keyVal = (charPosition - shiftKey) % 80;
			if (keyVal < 0){
				keyVal = alphabet.length() + keyVal;
	        }
			char replaceVal = alphabet.charAt(keyVal);
			plainText += replaceVal;
	    }
		return plainText;
	}
	
	void appendingText(String Service, String User, String Pass) {
		BufferedWriter writer = null;
		try {
			File file = new File("C:\\Users\\"+System.getProperty("user.name")+"\\My Documents\\PassGen\\");
			if(!file.exists()) 
				file.mkdir();
			File file1 = new File("C:\\Users\\"+System.getProperty("user.name")+"\\My Documents\\PassGen\\"+user);
			if(!file1.exists()) 
				file1.mkdir();
			File file2 = new File("C:\\Users\\"+System.getProperty("user.name")+"\\My Documents\\PassGen\\"+user+"\\.Data");
			if(!file2.exists()) 
				file2.mkdir();
			file = new File("C:\\Users\\"+System.getProperty("user.name")+"\\My Documents\\PassGen\\"+user+"\\.Data\\SavedPass.txt");
			writer = new BufferedWriter(new FileWriter("C:\\Users\\"+System.getProperty("user.name")+"\\My Documents\\PassGen\\"+user+"\\.Data\\SavedPass.txt" , true));
			writer.append(encrypt(Service, 5)+" : "+encrypt(User, 5)+" {"+encrypt(Pass,5)+"\n");
		} catch (Exception e) {}
		finally {
			try {
				writer.close();
			} catch (Exception e) {}
		}
	}
	
	void load(String command, int ind) {
		if(!command.equals("copy")) {
			File file = new File("C:\\Users\\"+System.getProperty("user.name")+"\\My Documents\\PassGen\\"+user+"\\.Data");
			if(file.isDirectory()) {
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new FileReader("C:\\Users\\"+System.getProperty("user.name")+"\\My Documents\\PassGen\\"+user+"\\.Data\\SavedPass.txt"));
					String line;
					while ((line = reader.readLine()) != null){
						if(!line.equals(""))
							count++;
					}
				}
				catch (IOException e1) {}}
			
			pan = new JPanel[count];
			JLabel ser[] = new JLabel[count];
			JLabel use[] = new JLabel[count];
			JLabel pas[] = new JLabel[count];
			copy = new JButton[count];
			
			mainPanel = new JPanel(null);
			mainPanel.setBounds(0, 90, 335, 300);
			
			for(int i=0; i<count; i++) {
					pan[i] = new JPanel(null);
					pan[i].setBounds(20, initial-65, 300, 50);
					initial += 55;
					pan[i].setBackground(Color.LIGHT_GRAY);
					pan[i].setVisible(true);
					mainPanel.add(pan[i]);	
					
					ser[i] = new JLabel("Service");
					ser[i].setBackground(new Color(123,100,255));
					ser[i].setForeground(Color.RED);
					ser[i].setFont(new Font("MV Boli", Font.PLAIN, 17));
					ser[i].setFocusable(false);
					ser[i].setVisible(true);
					ser[i].setBounds(10, 5, 180, 15);
					pan[i].add(ser[i]);

					use[i] = new JLabel("user");
					use[i].setBackground(new Color(123,100,255));
					use[i].setForeground(Color.black);
					use[i].setFont(new Font("MV Boli", Font.PLAIN, 13));
					use[i].setFocusable(false);
					use[i].setVisible(true);
					use[i].setBounds(10, 25, 100, 15);
					pan[i].add(use[i]);
					
					pas[i] = new JLabel("pass");
					pas[i].setBackground(new Color(123,100,255));
					pas[i].setForeground(Color.black);
					pas[i].setFont(new Font("MV Boli", Font.PLAIN, 13));
					pas[i].setFocusable(false);
					pas[i].setVisible(true);
					pas[i].setBounds(115, 25, 100, 15);
					pan[i].add(pas[i]);
					
					copy[i] = new JButton("Copy");
					copy[i].setForeground(Color.BLACK);
					copy[i].setBackground(new Color(123,100,255));
					copy[i].setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
					copy[i].setFocusable(false);
					copy[i].setVisible(true);
					copy[i].addActionListener(this);
					copy[i].setBounds(220, 10, 70, 25);
					pan[i].add(copy[i]);
				}
			
			scroll = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scroll.setVisible(true);
			scroll.setBounds(0, 90, 345, 300);
			System.out.println(initial);
			if(initial>250) {
				mainPanel.setPreferredSize(new Dimension(300, initial+80));
				scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			}
			
			mainPanel.add(button[2]);
			labels[3].setVisible(false);
			button[0].setVisible(false);
			button[2].setBounds(110, initial-50, 100, 25);
			button[2].setText("<-----");
			
			this.setSize(new Dimension(345, 340));
			this.add(scroll);
			
			super.update(getGraphics());
			int index = 0;
			if(file.isDirectory()) {
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new FileReader("C:\\Users\\"+System.getProperty("user.name")+"\\My Documents\\PassGen\\"+user+"\\.Data\\SavedPass.txt"));
					String line;
					while ((line = reader.readLine()) != null){
						if(!line.equals("")){
							s = decrypt(line.substring(0,line.indexOf(" ")), 5);
							u = decrypt(line.substring(line.indexOf(" ")+3, line.indexOf(" {")), 5);
							p = decrypt(line.substring(line.indexOf("{")+1), 5);
							ser[index].setText(s);
							use[index].setText(u+" : ");
							pas[index].setText(p);
							index++;
						}
					}
				}
				catch (IOException e1) {}}
		}
		else {
			int in = 0;
			File file = new File("C:\\Users\\"+System.getProperty("user.name")+"\\My Documents\\PassGen\\"+user+"\\.Data");
			if(file.isDirectory()) {
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new FileReader("C:\\Users\\"+System.getProperty("user.name")+"\\My Documents\\PassGen\\"+user+"\\.Data\\SavedPass.txt"));
					String line;
					while ((line = reader.readLine()) != null){
						if(!line.equals("")){
							p = decrypt(line.substring(line.indexOf("{")+1), 5);
							if(in==ind) {
								StringSelection selection = new StringSelection(p);
								clipboard.setContents(selection, selection);
								labels[1].setForeground(Color.GREEN);
								labels[1].setText("Text Copied to Clipboard!");
								labels[1].setBounds(20, 53, 250, 25);
								labels[1].setVisible(true);
								break;
							}
							in++;
						}
					}
				}
				catch (IOException e1) {}}
		}
	}
	
	@SuppressWarnings("rawtypes")
	MyFrame(String user){
		this.user=user;
		String Lab[] = {"Pass Generator", "Note: 16 digits is more secure.", "----------------------", "Choose any option:", "Website:", "Username/Email:"};
		String But[] = {"Generate", "Copy", "Check"};
		for(int i=0; i<6; i++) {
			//Creating Labels
			labels[i] = new JLabel(Lab[i]);
			labels[i].setBackground(new Color(123,100,255));
			labels[i].setForeground(Color.black);
			labels[i].setFont(new Font("MV Boli", Font.PLAIN, 17));
			labels[i].setFocusable(false);
			labels[i].setVisible(true);
			this.add(labels[i]);
			
			//Creating Button
			if(i<3) {
				button[i] = new JButton(But[i]);
				button[i].setForeground(Color.BLACK);
				button[i].setBackground(new Color(123,100,255));
				button[i].setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
				button[i].setFocusable(false);
				button[i].setVisible(true);
				button[i].addActionListener(this);
				this.add(button[i]);
			}
		}
	
		//Text field for password
		for(int i=0; i<3; i++) {
			field[i] = new JTextField();
			field[i].setEditable(true);
			field[i].setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		}
		
		//Choose Letters
		String digits[] = {"8 Digits", "16 Digits"};
		letters = new JComboBox(digits);
		letters.setSelectedItem("8 Digits");
		letters.addActionListener(this);
		letters.setBounds(200, 70, 100, 25);
		letters.setVisible(false);
		
		//Settings Positions
		labels[0].setFont(new Font("MV Boli", Font.PLAIN, 20));
		labels[0].setForeground(Color.RED);
		labels[0].setBounds(90, 20, 180, 25);	
		labels[2].setBounds(80, 20, 180, 50);	
		labels[2].setForeground(Color.RED);
		labels[1].setForeground(Color.RED);
		labels[1].setBounds(20, 160, 250, 25);	
		labels[3].setBounds(90, 55, 180, 50);
		button[0].setBounds(170, 115, 100, 25);
		button[2].setBounds(60, 115, 100, 25);
		labels[1].setVisible(false);
		labels[4].setVisible(false);
		labels[5].setVisible(false);
		labels[4].setBounds(20, 135, 250, 25);	
		field[1].setBounds(20, 165, 250, 25);	
		labels[5].setBounds(20, 195, 250, 25);	
		field[2].setBounds(20, 225, 250, 25);	
		
		//Main Frame
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(new Dimension(345, 250));
		this.setVisible(true);
		this.add(letters);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		for(int i=0; i<count; i++) {
			if(e.getSource()==copy[i]) {
				load("copy", i);
			}
		}
		
		//Logic for Generate and Back button
		if(e.getSource()==button[0]) {
			if(button[0].getText().equals("Generate") && !button[2].getText().equals("Check")) {
				String pass = "";
				int[] done = null;
				if(String.valueOf(letters.getSelectedItem()).equals("8 Digits")) {
					done = new int[9];
				}
				if(String.valueOf(letters.getSelectedItem()).equals("16 Digits")) {
					done = new int[17];
				}
				int index = 0;
				for(int i=0; i<Integer.parseInt(String.valueOf(letters.getSelectedItem()).replace(" Digits", "")); i++) {
					int r = random.nextInt(4);
					while(Arrays.asList(done).contains(r) && !(Arrays.asList(done).contains(0) && Arrays.asList(done).contains(1) && Arrays.asList(done).contains(2) && Arrays.asList(done).contains(3)))
						r = random.nextInt(4);
					if(r == 0) {
						done[index] = 0;
						index++;
						pass+=UPPERCASE_LETTERS.charAt(random.nextInt(26));
					}
					else if(r == 1) {
						done[index] = 1;
						index++;
						pass+=LOWERCASE_LETTERS.charAt(random.nextInt(26));
					}
					else if(r == 2) {
						done[index] = 2;
						index++;
						pass+=NUMBERS.charAt(random.nextInt(10));
					}
					else if(r == 3) {
						done[index] = 3;
						index++;
						pass+=SYMBOLS.charAt(random.nextInt(18));
					}
				}
				this.remove(letters);
				this.remove(labels[3]);
				this.add(field[0]);
				this.add(button[1]);
				field[0].setText(pass);
				button[1].setBounds(220, 70, 100, 25);
				field[0].setBounds(20, 70, 180, 25);
				button[0].setText("Back");
				labels[1].setText("");
				super.paint(getGraphics());
				button[2].setText("Save");
			}
			else if(button[0].getText().equals("Generate") && button[2].getText().equals("Check")) {
				labels[3].setBounds(20, 55, 180, 50);
				labels[3].setText("Choose digits of pass:");
				labels[1].setText("Note: 16 digits is more secure.");
				labels[1].setForeground(Color.RED);
				letters.setVisible(true);
				labels[1].setVisible(true);
				button[2].setText("Back");
			}
			else {
				this.setSize(new Dimension(345, 250));
				labels[4].setVisible(false);
				labels[5].setVisible(false);
				field[1].setVisible(false);
				field[2].setVisible(false);
				labels[1].setBounds(20, 160, 250, 25);	
				button[0].setBounds(170, 115, 100, 25);
				button[2].setBounds(60, 115, 100, 25);
				button[2].setEnabled(true);
				
				button[2].setText("Back");
				this.add(letters);
				this.add(labels[3]);
				this.remove(field[0]);
				this.remove(button[1]);
				field[0].setText("");
				labels[3].setBounds(20, 55, 180, 50);
				letters.setBounds(200, 70, 100, 25);
				button[0].setText("Generate");
				super.paint(getGraphics());
				labels[1].setForeground(Color.RED);
				labels[1].setText("Note: 16 digits is more secure.");
			}
		}
		
		//Logic for copy button
		if(e.getSource()==button[1]) {
			StringSelection selection = new StringSelection(field[0].getText());
			clipboard.setContents(selection, selection);
			labels[1].setForeground(Color.GREEN);
			labels[1].setText("Text Copied to Clipboard!");
		}
		
		if(e.getSource()==button[2]) {
			if(button[2].getText().equals("Check")) {
				load("", 0);
			}
			else if(button[2].getText().equals("<-----")) {
				this.remove(scroll);
				this.add(button[2]);
				for(int i=0; i<count; i++)
					this.remove(pan[i]);
				button[2].setText("Check");
				labels[3].setVisible(true);
				button[0].setVisible(true);
				button[2].setBounds(60, 115, 100, 25);
				this.setSize(new Dimension(345, 250));
				initial = 90;
				count = 0;
				labels[1].setBounds(20, 160, 250, 25);	
				labels[1].setVisible(false);
				super.paint(getGraphics());
			}
			if(button[2].getText().equals("Back")) {
				labels[3].setBounds(90, 55, 180, 50);
				labels[3].setText("Choose any option:");
				letters.setVisible(false);
				labels[1].setVisible(false);
				button[2].setText("Check");
			}
			if(button[2].getText().equals("Save")) {
				if(!labels[4].isVisible()) {
					this.setSize(new Dimension(345, 350));
					this.add(field[1]);
					this.add(field[2]);
					labels[4].setVisible(true);
					labels[5].setVisible(true);
					field[1].setVisible(true);
					field[2].setVisible(true);
					labels[1].setBounds(20, 105, 250, 25);
					button[2].setBounds(60, 260, 100, 25);
					button[0].setBounds(170, 260, 100, 25);
				}
				else {
					labels[1].setForeground(Color.red);
					if(field[1].getText().equals("") && !field[2].getText().equals(""))
						labels[1].setText("Enter a website.");
					else if(field[2].getText().equals("") && !field[1].getText().equals(""))
						labels[1].setText("Enter a username.");
					else if(field[2].getText().equals("") && field[1].getText().equals(""))
						labels[1].setText("Fill the details.");
					else {
						appendingText(field[1].getText(), field[2].getText(), field[0].getText());
						labels[1].setForeground(Color.GREEN);
						labels[1].setText("Password saved successfully!");
						button[2].setEnabled(false);
					}
				}
			}
		}
		
	}
}