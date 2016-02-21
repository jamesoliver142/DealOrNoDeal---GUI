/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dealornodealgui1;

/**
 *
 * @author jamesoliver142
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class DealOrNoDealGUI1 implements MouseListener {

    JFrame frame = new JFrame("Deal or No Deal");
    JPanel titlePanel = new JPanel(new BorderLayout());
    JPanel gamePanel = new JPanel(new BorderLayout());
    JLayeredPane titlePane = new JLayeredPane();
    JLayeredPane gamePane = new JLayeredPane();
    ImageIcon briefCase[] = new ImageIcon[26];
    ImageIcon prize[][] = new ImageIcon[26][26];
    ImageIcon messages[] = new ImageIcon[7];
    ImageIcon revealPrize[] = new ImageIcon[26];
    JLabel prizeLabel[] = new JLabel[26];
    JLabel briefCaseLabel[] = new JLabel[26];

    boolean opening;
    boolean banker;
    double offer;
    double[] prizes = new double[26];
    double[] originalPrize = {
        0.01, 1, 5, 10, 25, 50, 75, 100, 200, 300, 400, 500, 750, 1000, 5000,
        10000, 25000, 50000, 75000, 100000, 200000, 300000, 400000, 500000, 750000, 1000000
    };

    int messageBoxState = 6;
    int casesRemaining = 26;
    int clickedCase = 25;
    boolean clickable = true;
    int turnNumber = 1;
    String offerString;
    //set images and add them to jlabels
    ImageIcon background = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/background.png")));
    ImageIcon mainScreen = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/background.png")));
    ImageIcon login = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/loginIcon.png")));
    ImageIcon register = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/register.png")));
    ImageIcon smallLogin = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/smallLogin.png")));
    ImageIcon smallLoginHover = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/smallLoginHover.png")));
    ImageIcon loginHover = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/loginIconHover.png")));
    ImageIcon registerHover = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/registerHover.png")));
    ImageIcon userCase = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/case?.png")));
    ImageIcon offerToUser = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/loginIcon.png")));
    ImageIcon acceptDeal = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/smallDealIconHover.png")));
    ImageIcon declineDeal = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/smallNoDealIconHover.png")));
    JLabel accepted = new JLabel(acceptDeal);
    JLabel declined = new JLabel(declineDeal);
    ImageIcon black = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/background.png")));
    JLabel blackScreen = new JLabel(black);
    JLabel displayWinnings = new JLabel();
    JLabel userOffer = new JLabel(offerToUser);
    JLabel loginScreen = new JLabel(background);
    JLabel mainBackground = new JLabel(mainScreen);
    JLabel loginButton = new JLabel(login);
    JLabel registerButton = new JLabel(register);
    JLabel smallLoginButton = new JLabel(smallLogin);
    JLabel enterName = new JLabel("Enter Name");
    JLabel enterPass = new JLabel("Enter Password");
    JTextField userName = new JTextField();
    JPasswordField userPass = new JPasswordField();
    JLabel userChoice = new JLabel(userCase);
    JLabel reveals = new JLabel();
    JLabel messageBox = new JLabel(messages[messageBoxState]);
    JLabel displayOffer = new JLabel();

    //used for databasing and allowing a user to login or create a new user
    public void enterName() {

        System.out.println("---- WELCOME TO DEAL OR NO DEAL ----");
        System.out.println();
        System.out.println("> Press 'R' to register a new account or 'L' to login");
        Scanner userInput = new Scanner(System.in);
        String input = userInput.next();
        while (!input.equals("L") && !input.equals("R")
                && !input.equals("l") && !input.equals("R")) {
            System.out.println("Error - Failed to recognise command");
            input = userInput.nextLine();
        }
        if (input.equals("R") || input.equals("r")) {
            createNewAccount();
        } else {
            accountLogIn();
        }
    }

    // login to your account
    public void accountLogIn() {
        boolean success = false;
        Scanner userInput = new Scanner(System.in);
        System.out.print("Account Name: ");
        String userAcc = userInput.nextLine();
        System.out.print("Account Password: ");
        String userPass = userInput.nextLine();
        try {
            Scanner input = new Scanner(new File(userAcc + ".txt"));
            if (!userPass.equals(input.nextLine())) {
                System.out.println("Account or Password is incorrect. Please Try again..");
                accountLogIn();
            } else {
                success = true;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Account or Password is incorrect. Please Try again..");
            accountLogIn();
        }
        //Account login successful
        if (success) {
            System.out.println("Account Login Successful!");
            System.out.println();
        }
    }

    //Create a new account
    public void createNewAccount() {
        Scanner userInput = new Scanner(System.in);
        String userAcc = userInput.nextLine();
        while (userAcc.length() > 8) {
            System.out.println("Please enter a maximum of 8 characters");
            System.out.print("Account Name: ");
            userAcc = userInput.nextLine();
        }
        System.out.print("Account Password: ");
        String userPass = userInput.nextLine();
        while (userPass.length() > 8) {
            System.out.println("Your password must be a maximum of 8 characters long");
            System.out.print("Account Password: ");
            userPass = userInput.nextLine();
        }
        // Account doesn't already exist
        try {
            Scanner input = new Scanner(new File(userAcc + ".txt"));
            System.out.println("There is already an account with that name, Try again.");
            createNewAccount();
        } catch (FileNotFoundException e) {
        }
        // Create player name
        System.out.println("Great! Your Username is " + userAcc + " and your Password is " + userPass);
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(userAcc + ".txt"));
            out.write(userPass + "\n");
            out.close();
        } catch (IOException e) {
        }
        startGame();
    }

    public void showImages() {
        //add Cases
        for (int i = 0; i < 26; i++) {
            briefCase[i] = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/case" + i + ".png")));
            briefCaseLabel[i] = new JLabel(briefCase[i]);
            briefCaseLabel[i].addMouseListener(this);
        }
        for (int i = 0, m = 242; i < 7; i++, m += 105) {
            briefCaseLabel[i].setBounds(m, 120, 90, 70);
        }
        for (int i = 7, m = 300; i < 13; i++, m += 105) {
            briefCaseLabel[i].setBounds(m, 200, 90, 70);
        }
        for (int i = 13, m = 242; i < 20; i++, m += 105) {
            briefCaseLabel[i].setBounds(m, 280, 90, 70);
        }
        for (int i = 20, m = 300; i < 26; i++, m += 105) {
            briefCaseLabel[i].setBounds(m, 365, 90, 70);
        }
        for (int i = 0; i < 26; i++) {
            gamePane.add(briefCaseLabel[i], new Integer(2));
        }

        //add Values
        for (int j = 0; j < 26; j++) {
            prize[j][0] = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/" + j + ".png")));
            prize[0][j] = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/" + j + ".png")));
            prizeLabel[j] = new JLabel(prize[j][0]);
        }
        for (int l = 0, m = 40; l < 13; l++, m += 40) {
            prizeLabel[l].setBounds(43, m, 164, 30);
        }
        for (int l = 13, m = 40; l < 26; l++, m += 40) {
            prizeLabel[l].setBounds(990, m, 164, 30);
        }
        for (int i = 0; i < 26; i++) {
            gamePane.add(prizeLabel[i], new Integer(2));
        }
        for (int i = 0; i < 26; i++) {
            prizeLabel[i].setIcon(prize[i][0]);
        }

        //message box for how many cases to open
        for (int i = 0; i < 7; i++) {
            messages[i] = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/choice" + i + ".png")));
        }

        //Add the revealing prize images
        for (int i = 0; i < 26; i++) {
            revealPrize[i] = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/" + i + ".png")));

        }

    }

    public void showGUI() {
        //Title and login screen     
        showImages();
        titlePane.setPreferredSize(new Dimension(1200, 600));
        loginScreen.setBounds(0, 0, 1200, 600);
        loginButton.setBounds(650, 364, 400, 115);
        smallLoginButton.setBounds(550, 250, 100, 100);
        registerButton.setBounds(150, 364, 400, 115);
        userName.setBounds(630, 200, 120, 30);
        enterName.setBounds(470, 200, 120, 30);
        userPass.setBounds(630, 225, 120, 30);
        enterPass.setBounds(470, 225, 170, 30);
        enterPass.setForeground(Color.white);
        enterPass.setFont(new Font("Serif", Font.PLAIN, 25));
        enterName.setForeground(Color.white);
        enterName.setFont(new Font("Serif", Font.PLAIN, 25));

        //final screen 
        displayOffer.setFont(new Font("Serif", Font.PLAIN, 50));
        displayOffer.setForeground(Color.WHITE);
        displayWinnings.setFont(new Font("Serif", Font.PLAIN, 130));
        displayWinnings.setForeground(Color.WHITE);

        //add mouse listeners
        loginButton.addMouseListener(this);
        registerButton.addMouseListener(this);
        smallLoginButton.addMouseListener(this);
        accepted.addMouseListener(this);
        declined.addMouseListener(this);

        //show deal or no deal screen
        userOffer.setBounds(278, 250, 648, 102);
        accepted.setBounds(461, 464, 137, 41);
        declined.setBounds(602, 464, 137, 41);
        blackScreen.setBounds(204, 0, 790, 600);
        displayOffer.setBounds(100, 235, 800, 100);
        displayWinnings.setBounds(650, 250, 400, 300);

        //add buttons to login screen
        titlePane.add(loginScreen, new Integer(0));
        titlePane.add(loginButton, new Integer(1));
        titlePane.add(registerButton, new Integer(2));
        titlePanel.add(titlePane);

        //set up main game screen
        messageBox.setBounds(278, 457, 648, 102);
        gamePane.setPreferredSize(new Dimension(1200, 600));
        mainBackground.setBounds(0, 0, 1200, 600);
        gamePane.add(mainBackground, new Integer(0));
        gamePane.add(userChoice, new Integer(2));
        gamePane.add(accepted, new Integer(3));
        gamePane.add(declined, new Integer(3));
        gamePane.add(blackScreen, new Integer(3));
        gamePane.add(displayOffer, new Integer(4));

        //hide final screen and deal buttons
        blackScreen.setVisible(false);
        userOffer.setVisible(false);
        accepted.setVisible(false);
        declined.setVisible(false);
        displayOffer.setVisible(false);
        gamePane.add(messageBox, new Integer(1));
        messageBox.setIcon(messages[0]);

        //add the revealing images for when you click on a case
        reveals.setBounds(520, 80, 164, 30);
        gamePane.add(reveals, new Integer(3));
        gamePane.add(displayWinnings, new Integer(6));
        gamePanel.add(gamePane);

        //add game frame
        frame.add(titlePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1200, 600));
        frame.setResizable(false);
        frame.setVisible(true);
        frame.pack();

    }

    public void mouseClicked(MouseEvent ME)//mouseClicked method
    {
        //User logs in and is sent to enter user name and password
        if (ME.getSource() == loginButton) {
            titlePane.remove(loginButton);
            titlePane.remove(registerButton);
            titlePane.repaint();
            titlePane.add(userName, new Integer(3));
            titlePane.add(enterName, new Integer(3));
            titlePane.add(smallLoginButton, new Integer(3));
            titlePane.add(enterPass, new Integer(3));
            titlePane.add(userPass, new Integer(3));
            //user wants to create a new account so is sent to create a user name and password
        } else if (ME.getSource() == registerButton) {
            titlePane.remove(loginButton);
            titlePane.remove(registerButton);
            titlePane.repaint();
            titlePane.add(userName, new Integer(3));
            titlePane.add(enterName, new Integer(3));
            titlePane.add(smallLoginButton, new Integer(3));
            titlePane.add(enterPass, new Integer(3));
            titlePane.add(userPass, new Integer(3));
            //user logs in and is sent to main game screen
        } else if (ME.getSource() == smallLoginButton) {
            titlePane.removeAll();
            frame.add(gamePanel);
            jumble();
            frame.revalidate();
            //user declines offer
        } else if (ME.getSource() == declined) {
            noDeal();
            // user accepts offer
        } else if (ME.getSource() == accepted) {
            deal();
        } else {
            for (int i = 0; i < 26; i++)//loop through briefcase[0]-briefcase[25]
            {
                if (ME.getSource() == briefCaseLabel[i] && briefCaseLabel[i].getY() != 468 && clickable == true)//briefcase clicked, not your case and clickable equals true?
                {
                    briefcaseClicked(ME);
                }
            }
        }

    }

    public void mousePressed(MouseEvent ME)//mousePressed method
    {
    }//end of mousePressed method

    public void mouseReleased(MouseEvent ME)//mouseReleased method
    {
    }//end of mouseReleased method

    //check if mouse enters the bounds of buttons to change the icon
    public void mouseEntered(MouseEvent ME)//mouseEntered method
    {
        if (ME.getSource() == loginButton) {
            loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            loginButton.setIcon(loginHover);
        } else if (ME.getSource() == registerButton) {
            registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            registerButton.setIcon(registerHover);
        } else if (ME.getSource() == smallLoginButton) {
            smallLoginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            smallLoginButton.setIcon(smallLoginHover);
        }

    }

    //checks if mouse is outside the b ounds of a button to reset icon
    public void mouseExited(MouseEvent ME) {
        if (ME.getSource() == loginButton) {
            loginButton.setCursor(Cursor.getDefaultCursor());
            loginButton.setIcon(login);
        } else if (ME.getSource() == registerButton) {
            registerButton.setCursor(Cursor.getDefaultCursor());
            registerButton.setIcon(register);
        } else if (ME.getSource() == smallLoginButton) {
            smallLoginButton.setCursor(Cursor.getDefaultCursor());
            smallLoginButton.setIcon(smallLogin);
        }

    }

    //checks how many cases remaining and sends to approriate method to perfom the needed task
    public void briefcaseClicked(MouseEvent ME)//briefcaseClicked method, takes MouseEvent ME
    {

        for (int i = 0; i < 26; i++)//loop through briefcase[0]-briefcase[25]
        {
            if (ME.getSource() == briefCaseLabel[i])//briefcase clicked?
            {
                clickedCase = i;//set clickedCase to i
            }
        }
        if (casesRemaining == 26)//casesRemaining equals 26?
        {
            userChoice.setVisible(false);
            briefCaseLabel[clickedCase].setBounds(298, 468, 90, 70);//set bounds of briefcase[clickedCase]        

            messageBox.setIcon(messages[messageBoxState]);//set icon of messageBox to messageBoxIcon[messageBoxState]
            casesRemaining--;

        } else if (casesRemaining >= 20) {
            messageBoxState--;//subtract 1 from messageBoxState
            messageBox.setIcon(messages[messageBoxState]);//set icon of messageBox to messageBoxIcon[messageBoxState]	
            displayValue();
            casesRemaining--;//subtract 1 from casesRemaining
            if (casesRemaining == 19) {
                messageBoxState = 4;
                messageBox.setIcon(messages[5]);
                banker();
                offer();
            }
        } else if (casesRemaining >= 15) {
            messageBox.setIcon(messages[messageBoxState]);
            messageBoxState--;
            displayValue();
            casesRemaining--;
            if (casesRemaining == 14) {
                messageBoxState = 3;
                messageBox.setIcon(messages[4]);
                turnNumber++;
                banker();
                offer();
            }
        } else if (casesRemaining >= 11) {
            messageBox.setIcon(messages[messageBoxState]);
            messageBoxState--;
            displayValue();
            casesRemaining--;
            if (casesRemaining == 10) {
                messageBoxState = 2;
                messageBox.setIcon(messages[3]);
                turnNumber++;
                banker();
                offer();
            }
        } else if (casesRemaining >= 8) {
            messageBox.setIcon(messages[messageBoxState]);
            messageBoxState--;
            displayValue();
            casesRemaining--;
            if (casesRemaining == 7) {
                messageBoxState = 1;
                messageBox.setIcon(messages[2]);
                turnNumber++;
                banker();
                offer();
            }
        } else if (casesRemaining >= 6) {
            messageBox.setIcon(messages[messageBoxState]);
            messageBoxState--;
            displayValue();
            casesRemaining--;
            if (casesRemaining == 5) {
                messageBoxState = 0;
                messageBox.setIcon(messages[1]);
                turnNumber++;
                banker();
                offer();
            }
        } else if (casesRemaining >= 5) {
            messageBox.setIcon(messages[messageBoxState]);
            messageBoxState--;
            displayValue();
            casesRemaining--;
            if (casesRemaining == 4) {
                messageBoxState = 0;
                messageBox.setIcon(messages[1]);
                turnNumber++;
                banker();
                offer();
            }
        } else if (casesRemaining >= 4) {
            messageBox.setIcon(messages[messageBoxState]);
            messageBoxState--;
            displayValue();
            casesRemaining--;
            if (casesRemaining == 3) {
                messageBoxState = 0;
                messageBox.setIcon(messages[1]);
                turnNumber++;
                banker();
                offer();

            }
        } else if (casesRemaining >= 3) {
            messageBox.setIcon(messages[messageBoxState]);
            messageBoxState--;
            displayValue();
            casesRemaining--;
            if (casesRemaining == 2) {
                messageBoxState = 0;
                messageBox.setIcon(messages[1]);
                turnNumber++;
                banker();
                offer();
            }
        } else if (casesRemaining >= 2) {
            messageBox.setIcon(messages[messageBoxState]);
            messageBoxState--;
            displayValue();
            casesRemaining--;
            if (casesRemaining == 1) {
                messageBoxState = 0;
                messageBox.setIcon(messages[1]);
                turnNumber++;
                banker();
                offer();
            }
        } else if (casesRemaining >= 1) {
            messageBox.setIcon(messages[messageBoxState]);
            messageBoxState--;
            displayValue();
            casesRemaining--;
            if (casesRemaining == 0) {
                messageBoxState = 0;
                messageBox.setIcon(messages[0]);
                turnNumber++;
                banker();
                deal();
            }
        }

    }

    public void displayValue()//displayGif method
    {
        opening = true;
        briefCaseLabel[clickedCase].setVisible(false);
        if (opening) {
            for (double x : originalPrize)//loop through originalPrize
            {
                if (prizes[clickedCase] == x)//prize[clickedCase] equals x of originalPrize?
                {
                    for (int y = 0; y < 26; y++)//loop through originalPrize[0]-originalPrize[25]
                    {
                        if (prizes[clickedCase] == originalPrize[y])//prizes[clickedCase] equals originalPrize[y]?
                        {
                            offer = x;//set offer to x
                            prizeLabel[y].setIcon(prize[y][1]);
                            prizeLabel[y].setVisible(false);
                            reveals.setIcon(revealPrize[y]);                    
                        }
                    }
                }
                opening = false;
            }
        }
    }

    //method for displaying offer for user 
    public void offer() {
        clickable = false;
        blackScreen.setVisible(true);
        displayOffer.setText("The banker has offered you: ");
        displayWinnings.setFont(new Font("Serif", Font.PLAIN, 80));
        displayWinnings.setText("$" + offerString);
        displayWinnings.setVisible(true);
        displayWinnings.setBounds(475, 250, 400, 300);
        displayOffer.setVisible(true);
        displayOffer.setHorizontalAlignment(SwingConstants.CENTER);
        displayOffer.setBounds(200, 235, 800, 100);
        accepted.setVisible(true);
        declined.setVisible(true);
    }

    //player clicks deal button sends to final screen and displays winnings
    public void deal() {
        displayOffer.setVisible(true);
        gamePane.add(loginScreen, new Integer(4));
        displayOffer.setText("Congratulations you have won:");
        displayWinnings.setText("$" + offerString);
        displayWinnings.setVisible(true);

    }

    //player clicks no deal and this method sends back to game screen
    public void noDeal() {
        clickable = true;
        blackScreen.setVisible(false);
        displayOffer.setVisible(false);
        displayWinnings.setVisible(false);
        accepted.setVisible(false);
        declined.setVisible(false);

    }

    //shuffle the prizes
    public void jumble()//jumble method
    {
        int i = 0;
        ArrayList<Double> prizesOrdered = new ArrayList<Double>();
        for (double x : originalPrize)//loop through originalPrize
        {
            prizesOrdered.add(x);//add values from originalPrize to prizesOrdered 
        }
        Collections.shuffle(prizesOrdered);//shuffle prizesOrdered
        for (double x : prizesOrdered)//loop through prizesOrdered
        {
            prizes[i] = x;
            i++;

        }
    }

    //method to calculate the offer to be presented to the user
    public void banker() {
        clickable = false;
        offer = 0;
        int x = 0;
        for (int i = 0; i < 26; i++) {
            if (prizeLabel[i].getIcon() == prize[i][0])//icon of prize[i] equals prizeIcon[i][0]?
            {
                offer += originalPrize[i];//add originalPrize[i] to offer
                x++;
            }
        }
        offer = offer / x * turnNumber / 10;//calculate offer using algorithm: offer=Average of remaining cases*(roundNumber/10)
        offerString = Integer.toString((int) (Math.round(offer)));//convert offer into String offerString
    }

    public void startGame() {
        showGUI();
    }

    public static void main(String[] args) {
        DealOrNoDealGUI1 dnd = new DealOrNoDealGUI1();
        dnd.startGame();

    }

}
