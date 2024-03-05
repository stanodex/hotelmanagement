import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddRoom {

    public AddRoom(){
        JFrame AddRoomFrame = new JFrame("Add Room");
        AddRoomFrame.setSize(900,800);
        AddRoomFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel AddRoomPanel = new JPanel();
        AddRoomPanel.setLayout(null);
        AddRoomFrame.add(AddRoomPanel);
        JLabel RoomTypeLabel = new JLabel("Room Type: ");
        RoomTypeLabel.setBounds(220,50,300,150);
        RoomTypeLabel.setFont(new Font("Times New Roman", Font.BOLD,30));
        JComboBox<String> RoomTypeBox = new JComboBox<>();
        RoomTypeBox.setBounds(415,105,300,45);
        RoomTypeBox.setFont(new Font("Times New Roman", Font.BOLD,20));
        RoomTypeBox.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLUE));
        JLabel RoomCapacityLabel = new JLabel("Room Capacity: ");
        RoomCapacityLabel.setBounds(175,250,300,150);
        RoomCapacityLabel.setFont(new Font("Times New Roman", Font.BOLD,30));
        JComboBox<Integer> RoomCapacityBox = new JComboBox<>();
        RoomCapacityBox.setBounds(415,300,300,45);
        RoomCapacityBox.setFont(new Font("Times New Roman", Font.BOLD,20));
        RoomCapacityBox.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLUE));
        JLabel PricePerNightLabel = new JLabel("Price Per Night: ");
        PricePerNightLabel.setBounds(175,450,300,150);
        PricePerNightLabel.setFont(new Font("Times New Roman", Font.BOLD,30));
        JTextField PricePerNightField = new JTextField(4);
        PricePerNightField.setBounds(415,490,300,65);
        PricePerNightField.setFont(new Font("Times New Roman", Font.BOLD,30));
        PricePerNightField.setBorder(BorderFactory.createLineBorder(Color.BLUE,2));
        JButton MenuButton = new JButton("MENU");
        MenuButton.setBounds(125,650,300,100);
        MenuButton.setFont(new Font("Times New Roman", Font.BOLD,30));
        MenuButton.setBorder(BorderFactory.createLineBorder(Color.RED,4));
        JButton AddButton = new JButton("ADD");
        AddButton.setBounds(475,650,300,100);
        AddButton.setFont(new Font("Times New Roman", Font.BOLD,30));
        AddButton.setBorder(BorderFactory.createLineBorder(Color.GREEN,4));

        AddRoomPanel.add(RoomTypeLabel);
        AddRoomPanel.add(RoomTypeBox);
        AddRoomPanel.add(RoomCapacityLabel);
        AddRoomPanel.add(RoomCapacityBox);
        AddRoomPanel.add(PricePerNightLabel);
        AddRoomPanel.add(PricePerNightField);
        AddRoomPanel.add(MenuButton);
        AddRoomPanel.add(AddButton);
        AddRoomFrame.setVisible(true);

        String [] RoomTypes = {"S", "M", "L"};
        RoomTypeBox.addItem(null);
        RoomCapacityBox.addItem(null);
        for (int i = 0 ; i <=2 ; i++){
            RoomCapacityBox.addItem(i+1);
            RoomTypeBox.addItem(RoomTypes[i]);
        }

        AddButton.addActionListener(AddButtonClick -> {
            try {
                String NumberConstraint = "\\d+";
                if(RoomCapacityBox.getSelectedItem() == null || RoomTypeBox.getSelectedItem() == null){
                    JOptionPane.showMessageDialog(null,"Please select values for room.","Warning!",JOptionPane.WARNING_MESSAGE);
                }else{
                    if (PricePerNightField.getText().matches(NumberConstraint) && !PricePerNightField.getText().contains(" ")) {
                        Statement AddRoomStatement = Connect.getConnection().createStatement();
                        int affected_row =  AddRoomStatement.executeUpdate("INSERT INTO rooms (room_type, room_capacity, price_per_night, is_available) VALUES('" + RoomTypeBox.getSelectedItem().toString() + "', '" + (Integer) RoomCapacityBox.getSelectedItem() + "', '" + Integer.valueOf(PricePerNightField.getText()) + "', '" + 1 +"')");
                        ResultSet AddRoomRs = AddRoomStatement.executeQuery("SELECT max(room_id) as room_number FROM rooms WHERE room_type = '" + RoomTypeBox.getSelectedItem().toString() + "' AND room_capacity = '" + (Integer)RoomCapacityBox.getSelectedItem() + "' AND price_per_night = '" + PricePerNightField.getText() + "'");
                        AddRoomRs.next();
                        int room_id = AddRoomRs.getInt("room_number");
                        int option = JOptionPane.showConfirmDialog(null,affected_row + " Room added successfully. \n\nDETAILS\n"
                                +"Room Number: " + room_id + "\nRoom Capacity: " + (Integer)RoomCapacityBox.getSelectedItem()+ "\nRoom Type: " + RoomTypeBox.getSelectedItem().toString() + "\nPrice Per Night: " + PricePerNightField.getText() +
                                "\nGo to the Main Menu?","Add Room",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
                        AddRoomRs.close();
                        AddRoomStatement.close();
                        if (option == JOptionPane.YES_OPTION){
                            MainMenu menu = new MainMenu();
                            AddRoomFrame.dispose();
                        }else{
                            AddRoomFrame.dispose();
                            AddRoom addroom = new AddRoom();
                        }
                    }else{JOptionPane.showMessageDialog(null,"Please enter correct input format.","Warning!",JOptionPane.ERROR_MESSAGE);}
                }
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,"AddRoom class AddButton Click Catch Block \n"+e.getMessage(),"Warning!",JOptionPane.WARNING_MESSAGE);
            }finally {Connect.CloseConnection();}
        });
        MenuButton.addActionListener(MenuClick -> {
            MainMenu menu = new MainMenu();
            AddRoomFrame.dispose();
        });
    }
}
