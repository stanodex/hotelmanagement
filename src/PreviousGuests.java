import javax.swing.*;
import java.sql.ResultSet;

public class PreviousGuests extends GuestManager {
    protected static String Query = "SELECT * FROM previousguests";
    protected static String[] DisplayNames = {"Guest ID", "Guest Name", "Phone Number", "Check In Date", "Check Out Date", "Room Number", "Room Type"};
    protected static String[] ColumnNames = {"guest_id", "guest_name", "phone_number", "check_in_date", "check_out_date", "assigned_room_number", "assigned_room_type"};
    protected static String FrameTitle = "Previous Guests";

    public PreviousGuests(String FrameTitle, String[] DisplayNames, String[] ColumnNames, String Query) {
        super(FrameTitle, DisplayNames, ColumnNames, Query);
    }
    @Override
    protected void SearchClick(){
        try {
            if (RoomNumRadioButton.isSelected()) {
                String NumConstraint = "\\d+";
                if (SearchField.getText().matches(NumConstraint) && !SearchField.getText().contains(" ")) {
                    ResultSet SearchRs = Connect.getConnection().createStatement().executeQuery("SELECT * FROM previousguests WHERE assigned_room_number = '" + SearchField.getText() + "'");
                    Model.setRowCount(0);
                    InsertIntoTable(SearchRs, ColumnNames, Model);
                } else if (SearchField.getText().isBlank()) {
                    ResultSet SearchRs = Connect.getConnection().createStatement().executeQuery("SELECT * FROM previousguests");
                    Model.setRowCount(0);
                    InsertIntoTable(SearchRs, ColumnNames, Model);
                } else {JOptionPane.showMessageDialog(super.getContentPane(), "Please enter correct format", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }else if (GuestNameRadioButton.isSelected()) {
                String Constraint = "^[a-zA-Z ]+$";
                if(SearchField.getText().isBlank()){
                    ResultSet SearchRs = Connect.getConnection().createStatement().executeQuery("SELECT * FROM previousguests");
                    Model.setRowCount(0);
                    InsertIntoTable(SearchRs, ColumnNames, Model);
                }else if (SearchField.getText().toUpperCase().matches(Constraint) && SearchField.getText().contains(" ")) {
                    ResultSet SearchRs = Connect.getConnection().createStatement().executeQuery("SELECT * FROM previousguests WHERE guest_name = '" + SearchField.getText().toUpperCase() + "'");
                    Model.setRowCount(0);
                    InsertIntoTable(SearchRs, ColumnNames, Model);
                }else {JOptionPane.showMessageDialog(super.getContentPane(), "Please enter correct format", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        }catch (Exception e) {JOptionPane.showMessageDialog(super.getContentPane(), "Previousguests SearchClick Listener Catch Block\n" + e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
        }finally {Connect.CloseConnection();}
    }
}
