
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class MainMenu {
    public MainMenu(){
        JFrame mainFrame = new JFrame("Main Menu");
        mainFrame.setSize(900,800);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainFrame.add(mainPanel);
        JButton CheckInButton = new JButton("Check In");
        CheckInButton.setBounds(150,70,250,150);
        CheckInButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
        CheckInButton.setBorder(BorderFactory.createLineBorder(Color.BLACK,4));
        JButton PrevGuestButton = new JButton("Previous Guests");
        PrevGuestButton.setBounds(150,240,250,150);
        PrevGuestButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
        PrevGuestButton.setBorder(BorderFactory.createLineBorder(Color.BLACK,4));
        JButton ExitButton = new JButton("Exit");
        ExitButton.setBounds(325,580,250,150);
        ExitButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
        ExitButton.setBorder(BorderFactory.createLineBorder(Color.RED,4));
        JButton CheckOutButton = new JButton("Check Out");
        CheckOutButton.setBounds(500,70,250,150);
        CheckOutButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
        CheckOutButton.setBorder(BorderFactory.createLineBorder(Color.BLACK,4));
        JButton CurrentGuestsButton = new JButton("Current Guests");
        CurrentGuestsButton.setBounds(500,240,250,150);
        CurrentGuestsButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
        CurrentGuestsButton.setBorder(BorderFactory.createLineBorder(Color.BLACK,4));
        JButton AddReceptionistButton = new JButton("Add Receptionist");
        AddReceptionistButton.setBounds(500,410,250,150);
        AddReceptionistButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
        AddReceptionistButton.setBorder(BorderFactory.createLineBorder(Color.BLACK,4));
        JButton AddRoom = new JButton("Add Room");
        AddRoom.setBounds(150,410,250,150);
        AddRoom.setFont(new Font("Times New Roman", Font.BOLD, 30));
        AddRoom.setBorder(BorderFactory.createLineBorder(Color.BLACK,4));

        mainPanel.add(CheckInButton);
        mainPanel.add(PrevGuestButton);
        mainPanel.add(CheckOutButton);
        mainPanel.add(CurrentGuestsButton);
        mainPanel.add(AddReceptionistButton);
        mainPanel.add(AddRoom);
        mainPanel.add(ExitButton);
        mainFrame.setVisible(true);

        CheckInButton.addActionListener((ActionEvent CheckInClick) -> {
            try {
                ResultSet MainMenuRs = Connect.getConnection().createStatement().executeQuery("select count(room_id) as count from rooms where is_available = 1");
                MainMenuRs.next();
                    if (MainMenuRs.getInt("count") == 0) {
                        JOptionPane.showMessageDialog(null, "There are no available rooms!", "Warning!", JOptionPane.WARNING_MESSAGE);
                    } else {
                        CheckIn checkIn = new CheckIn();
                        mainFrame.dispose();
                    }
                MainMenuRs.close();
            } catch (Exception e) {JOptionPane.showMessageDialog(null,"Main Menu Catch Block","Warning!",JOptionPane.WARNING_MESSAGE);
            }finally {Connect.CloseConnection();}
        });
        CheckOutButton.addActionListener((ActionEvent CheckOutClick) -> {
            CheckOut checkOut = new CheckOut();
            mainFrame.dispose();
        });
        AddReceptionistButton.addActionListener((ActionEvent AddReceptionistClick) -> {
            AddReceptionist addReceptionist = new AddReceptionist();
            mainFrame.dispose();
        });
        CurrentGuestsButton.addActionListener((ActionEvent CurrentGuestsClick) -> {
            CurrentGuests currentGuests = new CurrentGuests(CurrentGuests.FrameTitle,CurrentGuests.DisplayNames,CurrentGuests.ColumnNames,CurrentGuests.Query);
            mainFrame.dispose();
        });
        PrevGuestButton.addActionListener((ActionEvent PreviousGuestClick) -> {
            PreviousGuests previousGuests = new PreviousGuests(PreviousGuests.FrameTitle,PreviousGuests.DisplayNames,PreviousGuests.ColumnNames,PreviousGuests.Query);
            mainFrame.dispose();
        });
        AddRoom.addActionListener((ActionEvent AddRoomClick) -> {
            AddRoom addRoom = new AddRoom();
            mainFrame.dispose();
        });

        ExitButton.addActionListener((ActionEvent ExitClick) -> {
            mainFrame.dispose();
            System.exit(0);
        });
    }
}