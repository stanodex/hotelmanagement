import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Login {
    public static void main(String[] args) {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(900,700);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.add(loginPanel);

        JLabel idLabel = new JLabel("Receptionist ID:");
        idLabel.setBounds(180,150,400,100);
        idLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));

        JTextField idField = new JTextField(5);
        idField.setBounds(400,160,300,75);
        idField.setFont(new Font("Times New Roman", Font.BOLD,30));
        idField.setBackground(Color.LIGHT_GRAY);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(255,245,400,100);
        passwordLabel.setFont(new Font("Times New Roman", Font.BOLD,30));

        JPasswordField passwordField = new JPasswordField(5);
        passwordField.setBounds(400,260,300,75);
        passwordField.setFont(new Font("Times New Roman", Font.BOLD,30));
        passwordField.setBackground(Color.LIGHT_GRAY);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(425,390,250,60);
        loginButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
        loginButton.setForeground(Color.BLUE);

        JLabel SuccessLabel = new JLabel("Login Successful...");
        SuccessLabel.setBounds(450,480 ,280,60);
        SuccessLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
        SuccessLabel.setForeground(Color.GREEN);

        JLabel UnsuccessLabel = new JLabel("Wrong Username or Password");
        UnsuccessLabel.setBounds(390,480,400,60);
        UnsuccessLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
        UnsuccessLabel.setForeground(Color.RED);

        loginPanel.add(idLabel);
        loginPanel.add(idField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(SuccessLabel);
        SuccessLabel.setVisible(false);
        loginPanel.add(UnsuccessLabel);
        UnsuccessLabel.setVisible(false);
        loginFrame.setVisible(true);

        loginButton.addActionListener(x -> {
            try {
                //login works with r_id and r_password
                Statement LoginStmt = Connect.getConnection().createStatement();
                System.out.println("Connection created");

                ResultSet LoginRs = LoginStmt.executeQuery("select r_password from receptionists where r_id = '" + idField.getText() + "'");
                LoginRs.next();
                if (LoginRs.getString("r_password").equals(String.valueOf(passwordField.getPassword()))) {
                    //Login successful label with 2 seconds delay
                    UnsuccessLabel.setVisible(false);
                    SuccessLabel.setVisible(true);
                    Timer timer = new Timer(2000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            MainMenu menu = new MainMenu();
                            loginFrame.setVisible(false);
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                }
                else {UnsuccessLabel.setVisible(true);}
                LoginRs.close();
                LoginStmt.close();
            }catch (Exception e) {JOptionPane.showMessageDialog(null,"Login Catch Block" + e.getMessage(),"Warning",JOptionPane.WARNING_MESSAGE);
            }finally {Connect.CloseConnection();}
        });
    }
}
