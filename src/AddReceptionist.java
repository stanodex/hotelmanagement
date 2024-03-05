import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddReceptionist {
    //It will have methods accsessible from receptionist
    // to add new receptionist to the database
    public AddReceptionist(){

        JFrame AddReceptionistFrame = new JFrame("Add Receptionist");
        AddReceptionistFrame.setSize(900,800);
        AddReceptionistFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel AddReceptionistPanel = new JPanel();
        AddReceptionistPanel.setLayout(null);
        AddReceptionistFrame.add(AddReceptionistPanel);
        JLabel FullNameLabel = new JLabel("Full Name:");
        FullNameLabel.setBounds(250,50,250,100);
        FullNameLabel.setFont(new Font("Times New Roman",Font.BOLD,30));
        JTextField FullNameField = new JTextField(30);
        FullNameField.setBounds(435,50,300,100);
        FullNameField.setFont(new Font("Times New Roman",Font.BOLD,30));
        FullNameField.setBorder(BorderFactory.createLineBorder(Color.GRAY,4));
        JLabel PhoneNumberLabel = new JLabel("Phone Number:");
        PhoneNumberLabel.setBounds(190,175,250,100);
        PhoneNumberLabel.setFont(new Font("Times New Roman",Font.BOLD,30));
        JTextField PhoneNumberField = new JTextField(16);
        PhoneNumberField.setBounds(435,175,300,100);
        PhoneNumberField.setFont(new Font("Times New Roman",Font.BOLD,30));
        PhoneNumberField.setBorder(BorderFactory.createLineBorder(Color.GRAY,4));
        JLabel AddressLabel = new JLabel("Address:");
        AddressLabel.setBounds(275,300,300,100);
        AddressLabel.setFont(new Font("Times New Roman",Font.BOLD,30));
        JTextField AddressField = new JTextField(20);
        AddressField.setBounds(435,300,300,100);
        //in case of long address, set smaller font size
        AddressField.setFont(new Font("Times New Roman",Font.BOLD,30));
        AddressField.setBorder(BorderFactory.createLineBorder(Color.GRAY,4));
        JLabel PasswordLabel = new JLabel("Set Password:");
        PasswordLabel.setBounds(210,425,300,100);
        PasswordLabel.setFont(new Font("Times New Roman",Font.BOLD,30));
        JPasswordField PasswordField = new JPasswordField(15);
        PasswordField.setBounds(435,425,300,100);
        PasswordField.setFont(new Font("Times New Roman",Font.BOLD,30));
        PasswordField.setBorder(BorderFactory.createLineBorder(Color.GRAY,4));
        JLabel ConfirmPasswordLabel = new JLabel("Confirm Password:");
        ConfirmPasswordLabel.setBounds(142,550,300,100);
        ConfirmPasswordLabel.setFont(new Font("Times New Roman",Font.BOLD,30));
        JPasswordField ConfirmPasswordField = new JPasswordField(15);
        ConfirmPasswordField.setBounds(435,550,300,100);
        ConfirmPasswordField.setFont(new Font("Times New Roman",Font.BOLD,30));
        ConfirmPasswordField.setBorder(BorderFactory.createLineBorder(Color.GRAY,4));
        JButton AddButton = new JButton("ADD RECEPTIONIST");
        AddButton.setBounds(500,675,250,80);
        AddButton.setFont(new Font("Times New Roman",Font.BOLD,20));
        AddButton.setBorder(BorderFactory.createLineBorder(Color.GREEN,4));
        JButton MenuButton = new JButton("MENU");
        MenuButton.setBounds(150,675,250,80);
        MenuButton.setFont(new Font("Times New Roman",Font.BOLD,20));
        MenuButton.setBorder(BorderFactory.createLineBorder(Color.RED,4));

        AddReceptionistPanel.add(FullNameLabel);
        AddReceptionistPanel.add(FullNameField);
        AddReceptionistPanel.add(PhoneNumberLabel);
        AddReceptionistPanel.add(PhoneNumberField);
        AddReceptionistPanel.add(AddressLabel);
        AddReceptionistPanel.add(AddressField);
        AddReceptionistPanel.add(PasswordLabel);
        AddReceptionistPanel.add(PasswordField);
        AddReceptionistPanel.add(ConfirmPasswordLabel);
        AddReceptionistPanel.add(ConfirmPasswordField);
        AddReceptionistPanel.add(AddButton);
        AddReceptionistPanel.add(MenuButton);
        AddReceptionistFrame.setVisible(true);

        AddButton.addActionListener((ActionEvent AddClick) -> {
           try{
               String Constraint = "^[a-zA-Z ]+$";
               String PhoneConstraint = "\\d+";
               if (!FullNameField.getText().contains(" ") || !FullNameField.getText().matches(Constraint)) {
                   JOptionPane.showMessageDialog(null, "Please enter your full name correctly", "Warning!", JOptionPane.ERROR_MESSAGE);
               }else if (PhoneNumberField.getText().contains(" ") ||!PhoneNumberField.getText().matches(PhoneConstraint)) {
                   JOptionPane.showMessageDialog(null, "Please enter your phone number in correct format", "Warning!", JOptionPane.ERROR_MESSAGE);
               } else if (!String.valueOf(PasswordField.getPassword()).equals(String.valueOf(ConfirmPasswordField.getPassword()))) {
                   JOptionPane.showMessageDialog(AddReceptionistFrame,"Passwords do not match!","Warning!",JOptionPane.ERROR_MESSAGE);
                } else {
                   Statement AddReceptionistStmt = Connect.getConnection().createStatement();
                   AddReceptionistStmt.executeUpdate("INSERT INTO receptionists (r_fullname,r_password,r_address,r_phonenumber) VALUES('" + FullNameField.getText().toUpperCase() + "','" + String.valueOf(PasswordField.getPassword()) + "','" + AddressField.getText() + "','" + PhoneNumberField.getText() + "')");
                   ResultSet ReceptionistRs = AddReceptionistStmt.executeQuery("SELECT r_id FROM receptionists WHERE r_password = '" + String.valueOf(PasswordField.getPassword()) + "' AND r_fullname = '" + FullNameField.getText().toUpperCase() + "'");
                   ReceptionistRs.next();
                   int r_id = ReceptionistRs.getInt("r_id");
                   int option = JOptionPane.showConfirmDialog(AddReceptionistFrame, "Receptionist Added Successfully! \nRECEPTIONIST ID : "+r_id, "Success!", JOptionPane.DEFAULT_OPTION);
                   if (option == JOptionPane.YES_OPTION) {
                       MainMenu menu = new MainMenu();
                       ReceptionistRs.close();
                       AddReceptionistStmt.close();
                       AddReceptionistFrame.dispose();
                   }
               }
           }catch (Exception e){JOptionPane.showMessageDialog(null,"AddClickCatchBlock"+ e.getMessage(), "Warning!", JOptionPane.WARNING_MESSAGE);
           }
        });
        MenuButton.addActionListener(MenuClick ->{
            MainMenu menu = new MainMenu();
            AddReceptionistFrame.dispose();
        });


    }
}
