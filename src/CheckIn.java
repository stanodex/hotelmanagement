import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class CheckIn {
    public CheckIn() {
        JFrame CheckInFrame = new JFrame("Check In");
        CheckInFrame.setSize(900,700);
        CheckInFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel CheckInPanel = new JPanel();
        CheckInPanel.setLayout(null);
        CheckInFrame.add(CheckInPanel);
        JLabel FullNameLabel = new JLabel("Full Name:");
        FullNameLabel.setBounds(205,50,345,104);
        FullNameLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        JTextField FullNameField = new JTextField(20);
        FullNameField.setBounds(415,50,345,104);
        FullNameField.setFont(new Font("Times New Roman", Font.BOLD,30));
        FullNameField.setBorder(BorderFactory.createLineBorder(Color.GRAY,4));
        JLabel PhoneLabel = new JLabel("Phone Number:");
        PhoneLabel.setBounds(143,174,345,104);
        PhoneLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        JTextField PhoneField = new JTextField(13);
        PhoneField.setBounds(415,174,345,104);
        PhoneField.setFont(new Font("Times New Roman", Font.BOLD,30));
        PhoneField.setBorder(BorderFactory.createLineBorder(Color.GRAY,4));
        JLabel RoomCapacityLabel = new JLabel("Room Capacity:");
        RoomCapacityLabel.setBounds(140,298,345,104);
        RoomCapacityLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        JComboBox <Integer>RoomCapacityBox = new JComboBox<>();
        RoomCapacityBox.setBounds(415,298,345,104);
        RoomCapacityBox.setFont(new Font("Times New Roman", Font.BOLD,20));
        JLabel RoomTypeLabel = new JLabel("Room Type:");
        RoomTypeLabel.setBounds(190,422,345,104);
        RoomTypeLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        JComboBox <String> RoomTypeBox = new JComboBox<>();
        RoomTypeBox.setBounds(415,422,345,104);
        RoomTypeBox.setFont(new Font("Times New Roman", Font.BOLD,20));
        JButton MenuButton = new JButton("MENU");
        MenuButton.setBounds(150,546,250,104);
        MenuButton.setFont(new Font("Times New Roman", Font.BOLD,30));
        MenuButton.setBorder(BorderFactory.createLineBorder(Color.RED,4));
        JButton OkButton = new JButton("OK");
        OkButton.setBounds(500,546,250,104);
        OkButton.setFont(new Font("Times New Roman", Font.BOLD,30));
        OkButton.setBorder(BorderFactory.createLineBorder(Color.GREEN,4));
        CheckInPanel.add(FullNameLabel);
        CheckInPanel.add(FullNameField);
        CheckInPanel.add(PhoneLabel);
        CheckInPanel.add(PhoneField);
        CheckInPanel.add(RoomCapacityLabel);
        CheckInPanel.add(RoomCapacityBox);
        CheckInPanel.add(RoomTypeLabel);
        CheckInPanel.add(RoomTypeBox);
        CheckInPanel.add(MenuButton);
        CheckInPanel.add(OkButton);
        CheckInFrame.setVisible(true);

        //loading items into roomcapacitybox and roomtypebox
        try{
            Statement CheckInStmt = Connect.getConnection().createStatement();
            ResultSet CheckInRs = CheckInStmt.executeQuery("SELECT DISTINCT room_capacity FROM rooms WHERE is_available = 1");
            RoomCapacityBox.addItem(null);
            while (CheckInRs.next()){RoomCapacityBox.addItem(CheckInRs.getInt("room_capacity"));}
            CheckInRs.close();
            CheckInStmt.close();
            Connect.CloseConnection();
            RoomCapacityBox.addItemListener((ItemEvent CapacityClick) -> {
                    if (CapacityClick.getStateChange() == ItemEvent.SELECTED) {
                        RoomTypeBox.removeAllItems();
                        try {
                            ResultSet CheckInRs2 = Connect.getConnection().createStatement().executeQuery("SELECT DISTINCT room_type FROM rooms WHERE is_available = 1 AND room_capacity = " + (Integer) RoomCapacityBox.getSelectedItem());
                            while(CheckInRs2.next()) {RoomTypeBox.addItem(CheckInRs2.getString("room_type"));}
                            CheckInRs2.close();
                        } catch (SQLException e) {System.out.println(e.getMessage());}
                        finally {Connect.CloseConnection();}
                    }
            });
            OkButton.addActionListener((ActionEvent OkClick) -> {
                    try {
                        String Constraint = "^[a-zA-Z ]+$";
                        String PhoneConstraint = "\\d+";
                        //Name and phone number conditions
                        if (RoomCapacityBox.getSelectedItem() == null || RoomTypeBox.getSelectedItem() == null)
                            JOptionPane.showMessageDialog(null,"Please select values for check in.","Warning!",JOptionPane.ERROR_MESSAGE);
                        else {
                            if (!FullNameField.getText().contains(" ") || FullNameField.getText().isEmpty() || !FullNameField.getText().matches(Constraint)) {
                                JOptionPane.showMessageDialog(null, "Please enter your full name correctly", "Warning!", JOptionPane.WARNING_MESSAGE);
                            } else if (PhoneField.getText().contains(" ") || PhoneField.getText().isEmpty() || !PhoneField.getText().matches(PhoneConstraint)) {
                                JOptionPane.showMessageDialog(null, "Please enter a valid phone number format", "Warning!", JOptionPane.WARNING_MESSAGE);
                            } else {
                                Statement CheckInStmt3 = Connect.getConnection().createStatement();
                                ResultSet CheckInRs3 = CheckInStmt3.executeQuery("SELECT room_id FROM rooms WHERE room_capacity = " + (Integer) RoomCapacityBox.getSelectedItem() + " AND room_type = '" + RoomTypeBox.getSelectedItem().toString() + "' AND is_available = 1 LIMIT 1");
                                CheckInRs3.next();
                                int roomnumber = CheckInRs3.getInt("room_id");
                                Customer customer = new Customer(FullNameField.getText().toUpperCase(), PhoneField.getText(), roomnumber, RoomTypeBox.getSelectedItem().toString());
                                CheckInStmt3.executeUpdate("INSERT INTO currentguests(guest_name, phone_number, check_in_date, assigned_room_number, assigned_room_type) VALUES('" + customer.CustomerName + "','" + customer.CustomerPhone +
                                        "', NOW()," + customer.AssignedRoomNumber + ",'" + customer.AssignedRoomType + "')");
                                CheckInStmt3.executeUpdate("UPDATE rooms SET is_available = 0 WHERE room_id = " + roomnumber);
                                int option = JOptionPane.showConfirmDialog(null, "Check In Completed Successfully \n\nDETAILS\n\nName: " + customer.CustomerName + "\nRoom Number: " + customer.AssignedRoomNumber + "\n\nGo to the Main Menu?", "Check In", JOptionPane.YES_NO_OPTION);
                                if (option == JOptionPane.YES_OPTION) {
                                    MainMenu menu = new MainMenu();
                                    CheckInFrame.dispose();
                                    CheckInRs3.close();
                                    CheckInStmt3.close();
                                } else {
                                    CheckInFrame.dispose();
                                    CheckIn checkIn = new CheckIn();
                                }
                            }
                        }
                    } catch (Exception e) {JOptionPane.showMessageDialog(null,"CheckIn OK button's listener error\n"+ e.getStackTrace()[0], "Check In", JOptionPane.WARNING_MESSAGE);}
            });
            MenuButton.addActionListener((ActionEvent MenuClick) -> {
                CheckInFrame.dispose();
                MainMenu menu = new MainMenu();
            });
        }catch (Exception e) {System.out.println(e.getMessage());}
        finally {Connect.CloseConnection();}
    }
    }
