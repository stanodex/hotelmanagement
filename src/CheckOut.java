import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class CheckOut {
    public CheckOut() {
        JFrame CheckOutFrame = new JFrame("Check Out");
        CheckOutFrame.setSize(900,900);
        CheckOutFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel CheckOutPanel = new JPanel();
        CheckOutPanel.setLayout(null);
        CheckOutFrame.add(CheckOutPanel);
        JLabel RoomNumberLabel = new JLabel("Room Number:");
        RoomNumberLabel.setBounds(205,50,345,104);
        RoomNumberLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        JTextField RoomNumberField = new JTextField(6);
        RoomNumberField.setBounds(415,50,345,104);
        RoomNumberField.setFont(new Font("Times New Roman", Font.BOLD,30));
        RoomNumberField.setBorder(BorderFactory.createLineBorder(Color.GRAY,4));
        JLabel GuestNameLabel = new JLabel("Guest Name:");
        GuestNameLabel.setBounds(240,174,345,104);
        GuestNameLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        JTextField GuestNameField = new JTextField(20);
        GuestNameField.setBounds(415,174,345,104);
        GuestNameField.setFont(new Font("Times New Roman", Font.BOLD,30));
        GuestNameField.setBorder(BorderFactory.createLineBorder(Color.GRAY,4));
        JButton MenuButton = new JButton("MENU");
        MenuButton.setBounds(150,298,250,80);
        MenuButton.setFont(new Font("Times New Roman",Font.BOLD,30));
        MenuButton.setBorder(BorderFactory.createLineBorder(Color.RED,4));
        JButton CheckButton = new JButton("CHECK");
        CheckButton.setBounds(500,298,250,80);
        CheckButton.setFont(new Font("Times New Roman", Font.BOLD,30));
        CheckButton.setBorder(BorderFactory.createLineBorder(Color.GREEN,4));
        JLabel CheckInDateLabel = new JLabel();
        CheckInDateLabel.setBounds(205,408,360,104);
        CheckInDateLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        JLabel CheckOutDateLabel = new JLabel();
        CheckOutDateLabel.setBounds(205,532,400,104);
        CheckOutDateLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        JLabel TotalAmountLabel = new JLabel();
        TotalAmountLabel.setBounds(205,656,360,104);
        TotalAmountLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        JButton PaidButton = new JButton("PAID");
        PaidButton.setBounds(280,780,340,80);
        PaidButton.setFont(new Font("Times New Roman", Font.BOLD,30));
        PaidButton.setBorder(BorderFactory.createLineBorder(Color.GREEN,4));

        CheckOutPanel.add(CheckInDateLabel);
        CheckOutPanel.add(CheckOutDateLabel);
        CheckOutPanel.add(TotalAmountLabel);
        CheckOutPanel.add(PaidButton);
        CheckOutFrame.setVisible(true);
        CheckOutPanel.add(RoomNumberLabel);
        CheckOutPanel.add(RoomNumberField);
        CheckOutPanel.add(MenuButton);
        CheckOutPanel.add(CheckButton);
        CheckOutPanel.add(GuestNameLabel);
        CheckOutPanel.add(GuestNameField);
        CheckInDateLabel.setVisible(false);
        CheckOutDateLabel.setVisible(false);
        TotalAmountLabel.setVisible(false);
        PaidButton.setVisible(false);


        CheckButton.addActionListener((ActionEvent CheckClick) -> {
            try{
                String Constraint = "^[a-zA-Z ]+$";
                String NumConstraint = "\\d+";
                if(!RoomNumberField.getText().matches(NumConstraint) || !GuestNameField.getText().matches(Constraint)){
                    JOptionPane.showMessageDialog(CheckOutFrame, "Enter the values in correct format","ERROR", JOptionPane.ERROR_MESSAGE);
                }else{
                    try{
                        Statement CheckStmt = Connect.getConnection().createStatement();
                        ResultSet CheckRs = CheckStmt.executeQuery("SELECT COUNT(guest_id) AS count FROM currentguests WHERE assigned_room_number = '" + RoomNumberField.getText() + "' AND guest_name = '" + GuestNameField.getText().toUpperCase() + "'");
                        CheckRs.next();
                        if (CheckRs.getInt("count") == 1) {
                            CheckRs = CheckStmt.executeQuery("SELECT check_in_date FROM currentguests WHERE assigned_room_number = '" + RoomNumberField.getText() + "' AND guest_name = '" + GuestNameField.getText().toUpperCase() + "'");
                            CheckRs.next();
                            LocalDate checkindate = CheckRs.getDate("check_in_date").toLocalDate();
                            LocalDate checkoutdate = LocalDate.now();
                            CheckRs = CheckStmt.executeQuery("SELECT price_per_night FROM rooms WHERE room_id = '" + RoomNumberField.getText() +"'");
                            CheckRs.next();
                            int roomprice = CheckRs.getInt("price_per_night");
                            int DaysStayed = checkindate.until(checkoutdate).getDays();
                            int totalamount = (roomprice * DaysStayed) + roomprice;
                            CheckInDateLabel.setText("Check In Date: " + checkindate);
                            CheckOutDateLabel.setText("Check Out Date: "+ checkoutdate);
                            TotalAmountLabel.setText("Total Amount: "+ totalamount);
                            CheckInDateLabel.setVisible(true);
                            CheckOutDateLabel.setVisible(true);
                            TotalAmountLabel.setVisible(true);
                            PaidButton.setVisible(true);
                            CheckStmt.close();
                            CheckRs.close();
                        }else{JOptionPane.showMessageDialog(CheckOutFrame, "There is no result with the information given!","ERROR", JOptionPane.ERROR_MESSAGE);}
                    }catch (Exception e){JOptionPane.showMessageDialog(null,"CheckClickCatchBlock"+ e.getMessage(), "Warning!", JOptionPane.WARNING_MESSAGE);}
                }
            }catch (Exception e){JOptionPane.showMessageDialog(null,e.getMessage(), "Warning!", JOptionPane.WARNING_MESSAGE);}
            finally {Connect.CloseConnection();}
        });
        MenuButton.addActionListener((ActionEvent MenuClick) -> {
            CheckOutFrame.dispose();
            MainMenu menu = new MainMenu();
        });
        PaidButton.addActionListener((ActionEvent PaidClick) -> {
            // I do not add the customer to the previous guests table because currentguest table has triggered for moving the customer to the previous guests table
            // so just delete from currentguests table
            try {
                Statement CurrentToPrevious = Connect.getConnection().createStatement();
                int rowsAffected = CurrentToPrevious.executeUpdate("DELETE FROM currentguests WHERE assigned_room_number = '" + RoomNumberField.getText() + "' AND guest_name = '" + GuestNameField.getText().toUpperCase() + "'");
                CurrentToPrevious.executeUpdate("UPDATE rooms SET is_available = 1 WHERE room_id = '" + RoomNumberField.getText() +"'");
                int option = JOptionPane.showConfirmDialog(CheckOutFrame, "Check Out Completed Successfully!\n"+rowsAffected+" guest has checked out.\n\nGo to the Main Menu?", "Check Out", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    MainMenu menu = new MainMenu();
                    CheckOutFrame.dispose();
                    CurrentToPrevious.close();

                }else{
                    CheckOutFrame.dispose();
                    CheckOut checkout = new CheckOut();
                }

            }catch (Exception e){JOptionPane.showMessageDialog(null,"PaidClickCatchBlock"+ e.getMessage(), "Warning!", JOptionPane.WARNING_MESSAGE);}
        });

    }
}
