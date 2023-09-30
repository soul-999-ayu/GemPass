import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class LoginPage extends JFrame implements ActionListener{

private static final long serialVersionUID = 1L;
JLabel lab[] = new JLabel[6];
JTextField uname, code;
JPasswordField pword;
JButton but[] = new JButton[5];
String n,p;
static String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

//TEXT ENCRYPTION LOGIC
String encrypt(String plainText, int shiftKey){
    String cipherText = "";
    for (int i = 0; i < plainText.length(); i++){
        int charPosition = alphabet.indexOf(plainText.charAt(i));
        int keyVal = (shiftKey + charPosition) % 52;
        char replaceVal = alphabet.charAt(keyVal);
        cipherText += replaceVal;
    }
    return cipherText;
}

//TEXT DECRYPTION LOGIC
public static String decrypt(String cipherText, int shiftKey){
	String plainText = "";
	for (int i = 0; i < cipherText.length(); i++){
		int charPosition = alphabet.indexOf(cipherText.charAt(i));
		int keyVal = (charPosition - shiftKey) % 52;
		if (keyVal < 0){
			keyVal = alphabet.length() + keyVal;
        }
		char replaceVal = alphabet.charAt(keyVal);
		plainText += replaceVal;
    }
	return plainText;
}

//STORING CREDENTIALS IN A FILE
void appendingText() {
	BufferedWriter writer = null;
	try {
		writer = new BufferedWriter(new FileWriter("C:\\Users\\"+System.getProperty("user.name")+"\\My Documents\\.Details\\Credentials.txt" , true)); 
		writer.append(encrypt(String.valueOf(uname.getText()), 5)+" : "+encrypt(String.valueOf(pword.getPassword()), 5)+"*"+encrypt(code.getText(),5)+"\n");
	   	} catch (Exception e) {}
	finally {
		try {
			writer.close();
		   	} catch (Exception e) {}
	 	}
	SignUpRevert();
}

//FOR HIDING GUI WHILE SIGN UP OR FORGET PASS
public void hide() {
	if(lab[0].getText().equals("LOGIN PAGE")) {
		but[0].setVisible(false);
		but[3].setVisible(false);
		but[1].setBounds(150, 300, 100, 30);
		code.setBounds(20, 250, 245, 35);
		but[2].setVisible(false);
		lab[1].setText("");
		code.setVisible(true);
		but[4].setVisible(true);
		code.setText("");
	}   
} 
   
//BACK BUTTON INTERNAL LOGIC
void SignUpRevert() {
	but[1].setBounds(155, 230, 100, 30);
	lab[1].setForeground(Color.GREEN);
	lab[1].setText("Registration done!");
	uname.setText("");
	pword.setText("");
	but[0].setVisible(true);
	but[3].setVisible(true);
	but[1].setBounds(155, 230, 100, 30);
	but[2].setVisible(true);
	lab[0].setText("LOGIN PAGE");
	lab[5].setBounds(20, 215, 150, 40);
	lab[5].setVisible(false);
	code.setVisible(false);
	but[4].setVisible(false);
	lab[2].setText("Username:");
	lab[5].setText("Recovery Key:");
}
   
void replacePass(String newString){
	String oldContent = "";       
	BufferedReader reader = null;       
	FileWriter writer = null;  
	String user, key, pass = null;
	try{
		reader = new BufferedReader(new FileReader("C:\\Users\\"+System.getProperty("user.name")+"\\My Documents\\.Details\\Credentials.txt"));
		String line;
		while ((line = reader.readLine()) != null){
			if(!line.equals("")){
				user = decrypt(line.substring(0,line.indexOf(" ")), 5);
				pass = decrypt(line.substring(line.indexOf(" ")+3, line.indexOf("*")), 5);
				key = decrypt(line.substring(line.indexOf("*")+1), 5);
				if(n.equals(user)) {
					line = encrypt(user, 5)+" : "+encrypt(newString, 5)+"*"+encrypt(key, 5);
				}
	    		oldContent = oldContent + line + "\n";                
	    	}
	   	}
		reader.close();
		if(!(pass.equals(newString))) {
			writer = new FileWriter(new File("C:\\Users\\"+System.getProperty("user.name")+"\\My Documents\\.Details\\Credentials.txt"));    
			writer.write(oldContent);
			SignUpRevert();
			lab[1].setForeground(Color.GREEN);
			lab[1].setText("Password Modified!");
			lab[5].setVisible(false);
			lab[3].setVisible(true);
			pword.setVisible(true);
			but[1].setText("Sign up");
			writer.close();
		}	
		else {
			lab[1].setText("This is your old password!");
		}
	}
	catch (IOException e){}
}

LoginPage(){
	//CREATION OF LABELS
	String labels[] = {"LOGIN PAGE","", "Username:", "Password:", "------------------", "Recovery Key:"};
	String buttons[] = {"Login", "Sign up", "Theme: Light", "Forgot Password?", "<--"};
	for(int i=0; i<6; i++) {
		lab[i] = new JLabel(labels[i]);
		lab[i].setFont(new Font("MV Boli", Font.PLAIN, 15));
		lab[i].setForeground(Color.RED);
		lab[i].setVisible(true);
		this.add(lab[i]);
		if(i<5) {
			but[i] = new JButton(buttons[i]);
			but[i].setForeground(Color.BLACK);
			but[i].setBackground(new Color(123,100,255));
			but[i].setFont(new Font("MV Boli", Font.PLAIN, 15));
			but[i].setFocusable(false);
			but[i].setVisible(true);
			but[i].addActionListener(this);
			this.add(but[i]);
		}
	}
	
	//SETTING POSITIONS
	lab[0].setBounds(75, -30, 150, 100);
	lab[4].setBounds(75, -18, 150, 100);
	lab[0].setFont(new Font("MV Boli", Font.PLAIN, 20));
	lab[1].setBounds(20, 0, 200, 100);
	lab[1].setFont(new Font("MV Boli", Font.PLAIN, 12));
	lab[2].setBounds(20, 55, 150, 40);
	lab[3].setBounds(20, 135, 150, 40);
	lab[2].setForeground(Color.BLACK);
	lab[5].setForeground(Color.BLACK);
	lab[3].setForeground(Color.BLACK);
	but[0].setBounds(35, 230, 100, 30);
	but[1].setBounds(155, 230, 100, 30);
	but[2].setBounds(70, 320, 140, 30);
	lab[5].setVisible(false);
	but[4].setBounds(30, 300, 100, 30);
	but[4].setVisible(false);
	
	but[3].setBackground(Color.WHITE);
	but[3].setBorder(null);
	but[3].setBounds(40, 275, 200, 30);
	but[3].setForeground(Color.BLUE);
	
	//USERNAME FIELD
	uname = new JTextField();
	uname.setBounds(20, 90, 245, 35);
	uname.setFont(new Font("MV Boli", Font.PLAIN, 15));
	
	//PASSWORD FIELD
	pword = new JPasswordField();
	pword.setBounds(20, 170, 245, 35);
	pword.setFont(new Font("MV Boli", Font.PLAIN, 15));
	
	//USERNAME FIELD
	code = new JTextField();
	code.setBounds(20, 250, 245, 35);
	code.setFont(new Font("MV Boli", Font.PLAIN, 15));
	code.setVisible(false);
	
	//FRAME
	this.setLocationRelativeTo(null);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setResizable(false);
	this.setLayout(null);
	this.setSize(new Dimension(300,420));
	this.getContentPane().setBackground(Color.WHITE);
	this.add(uname);
	this.add(pword);
	this.add(code);
	this.setVisible(true);
}

@Override
public void actionPerformed(ActionEvent e) {
	//THEME BUTTON
	if(e.getSource()==but[2]) {
		if(this.getContentPane().getBackground()==Color.WHITE) {
			this.getContentPane().setBackground(Color.BLACK);
			lab[2].setForeground(Color.WHITE);
			lab[3].setForeground(Color.WHITE);
			lab[5].setForeground(Color.WHITE);
			but[3].setBackground(Color.BLACK);
			but[2].setText("Theme: Dark");
		}
		else{
			this.getContentPane().setBackground(Color.WHITE);
			lab[2].setForeground(Color.BLACK);
			lab[3].setForeground(Color.BLACK);
			lab[5].setForeground(Color.BLACK);
			but[3].setBackground(Color.WHITE);
			but[2].setText("Theme: Light");
		}
	}
	
	//LOGIN BUTTON (READING CREDENTIAL FILES)
	if(e.getSource()==but[0]) {
		try{
			BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\"+System.getProperty("user.name")+"\\My Documents\\.Details\\Credentials.txt"));
			String line;
			while ((line = reader.readLine()) != null){
				if(!line.equals("")){
					n = decrypt(line.substring(0,line.indexOf(" ")), 5);
					p = decrypt(line.substring(line.indexOf(" ")+3, line.indexOf("*")), 5);
					if(String.valueOf(uname.getText()).equals("")||String.valueOf(pword.getPassword()).equals("")) {
						lab[1].setForeground(Color.red);
						lab[1].setText("Enter valid credentials!");
					}
					else if(n.equals(uname.getText())) {
						if(p.equals(String.valueOf(pword.getPassword()))) {
							new MyFrame(uname.getText());
							this.dispose();
							break;
						}
						else {
							lab[1].setForeground(Color.red);
							lab[1].setText("Wrong Password!");
							break;
						}
					}
					else {
						lab[1].setForeground(Color.red);
						lab[1].setText("User doesn't exist!");
					}
		    	}
		    }
			reader.close();
		}
		catch (Exception e1){}
	}
	
	//SIGN UP BUTTON
	if(e.getSource()==but[1]) {
		if(but[1].getText().equals("Sign up")) {
			if(lab[0].getText().equals("LOGIN PAGE")) {
				hide();
				lab[0].setText(" REGISTER");
				lab[5].setBounds(20, 215, 150, 40);
				lab[5].setVisible(true);
			}
			else {
				if(String.valueOf(uname.getText()).equals("") || String.valueOf(pword.getPassword()).equals("")) {
					lab[1].setForeground(Color.red);
					lab[1].setText("Enter valid credentials!");
				}
				else if(String.valueOf(pword.getPassword()).contains(" ") || uname.getText().contains(" ")) {
					lab[1].setForeground(Color.red);
					lab[1].setText("Spaces are not allowed!");
				}
				else if(String.valueOf(pword.getPassword()).equals(uname.getText())) {
					lab[1].setForeground(Color.red);
					lab[1].setText("Password must be hard!");
				}
				else if(code.getText().isEmpty()) {
					lab[1].setForeground(Color.red);
					lab[1].setText("Enter recovery key");
				}
				else if(code.getText().equals(uname.getText())) {
					lab[1].setForeground(Color.red);
					lab[1].setText("Key should be hard! Eg. X3ka0");
				}
				else if(code.getText().equals(String.valueOf(pword.getPassword()))) {
					lab[1].setForeground(Color.red);
					lab[1].setText("Key should differ from password!");
				}
				else {
					File file = new File("C:\\Users\\"+System.getProperty("user.name")+"\\My Documents\\.Details");
					if(file.isDirectory()) {
						BufferedReader reader;
						try {
							reader = new BufferedReader(new FileReader("C:\\Users\\"+System.getProperty("user.name")+"\\My Documents\\.Details\\Credentials.txt"));
							String line;
							while ((line = reader.readLine()) != null){
								if(!line.isEmpty()){
									n = decrypt(line.substring(0,line.indexOf(" ")), 5);
									lab[1].setText("");
									if(n.equals(uname.getText())) {
					    				lab[1].setForeground(Color.red);
										lab[1].setText("User already exist");
										break;
									}	
								}
				    		}
							if(!(lab[1].getText().equals("User already exist"))) {
								appendingText();
								lab[1].setForeground(Color.GREEN);
								lab[1].setText("Registration done!");
								uname.setText("");
								pword.setText("");
							}	
						} catch (Exception e1) {}
					}
					else {
						file.mkdir();
						appendingText();
					}
				}
			}
		}
		else if(!(lab[2].getText().equals("New Password"))){
			File file = new File("C:\\Users\\"+System.getProperty("user.name")+"\\My Documents\\.Details");
			if(file.isDirectory()) {
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new FileReader("C:\\Users\\"+System.getProperty("user.name")+"\\My Documents\\.Details\\Credentials.txt"));
					String line;
					while ((line = reader.readLine()) != null){
						if(!line.equals("")){
							n = decrypt(line.substring(0,line.indexOf(" ")), 5);
							p = decrypt(line.substring(line.indexOf("*")+1), 5);
							if(String.valueOf(uname.getText()).equals("") || String.valueOf(code.getText()).equals("")) {
			    				lab[1].setForeground(Color.red);
								lab[1].setText("Enter valid credentials!");
							}
			    			else if(n.equals(uname.getText())) {
								if(p.equals(String.valueOf(code.getText()))) {
									lab[1].setText("");
									uname.setText("");
									code.setText("");
									lab[2].setText("New Password");
									lab[5].setText("Confirm Password");
									break;
								}
								else {
									lab[1].setForeground(Color.red);
									lab[1].setText("Incorrect key entered!");
									break;
								}
							}
							else {
								lab[1].setForeground(Color.red);
								lab[1].setText("User doesn't exist!");
							}		
						}	
					}
					reader.close();
					} catch (IOException e1) {}
				}
			}
		else {
			if(!(uname.getText().equals(code.getText()))){
				lab[1].setForeground(Color.RED);
				lab[1].setText("Password should match!");
			}
			else if(uname.getText().contains(" ")){
				lab[1].setForeground(Color.RED);
				lab[1].setText("Spaces are not allowed!");
			}
			else if(uname.getText().contains("*")){
				lab[1].setForeground(Color.RED);
				lab[1].setText("* is not allowed!");
			}
			else if(uname.getText().equals(code.getText())) {
				replacePass(uname.getText());
			}
		}
	}
	
	if(e.getSource()==but[3]) {
		hide();
		lab[0].setText(" RECOVERY");
		lab[5].setBounds(20, 120, 150, 40);
		code.setBounds(20, 155, 245, 35);
		lab[5].setVisible(true);
		lab[3].setVisible(false);
		pword.setVisible(false);
		but[1].setText("Proceed");
	}
	
	if(e.getSource()==but[4]) {
		SignUpRevert();
		lab[1].setText("");
		lab[5].setVisible(false);
		lab[3].setVisible(true);
		pword.setVisible(true);
		but[1].setText("Sign up");
	}
	}
}