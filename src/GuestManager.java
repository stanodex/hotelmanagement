import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public abstract class GuestManager extends JFrame {
    protected JPanel LowerPanel;
    protected JPanel UpperPanel;
    protected DefaultTableModel Model;
    protected JTable GuestTable;
    protected JScrollPane ScrollPane;
    protected JTextField SearchField;
    protected JButton SearchButton;
    protected JButton MenuButton;
    protected JButton ExitButton;
    protected JLabel SearchByLabel;
    protected ButtonGroup ButtonGroup;
    protected JRadioButton RoomNumRadioButton;
    protected JRadioButton GuestNameRadioButton;

    public GuestManager(String FrameTitle, String[] DisplayNames, String[] ColumnNames, String Query) {
        try {
            setTitle(FrameTitle);
            setSize(1000, 1000);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
            Model = new DefaultTableModel(DisplayNames, 0);
            GuestTable = new JTable(Model);
            GuestTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 17));
            GuestTable.setFont(new Font("Times New Roman", Font.ITALIC, 15));
            GuestTable.setDefaultEditor(Object.class, null);
            ScrollPane = new JScrollPane(GuestTable);
            UpperPanel = new JPanel(new FlowLayout());
            UpperPanel.setPreferredSize(new Dimension(1000, 40));
            getContentPane().add(UpperPanel, BorderLayout.NORTH);
            SearchByLabel = new JLabel("Search By");
            SearchByLabel.setPreferredSize(new Dimension(70, 30));
            SearchByLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
            ButtonGroup = new ButtonGroup();
            RoomNumRadioButton = new JRadioButton("Room Number");
            GuestNameRadioButton = new JRadioButton("Guest Name");
            ButtonGroup.add(RoomNumRadioButton);
            ButtonGroup.add(GuestNameRadioButton);
            SearchField = new JTextField();
            SearchField.setPreferredSize(new Dimension(200, 30));
            SearchField.setFont(new Font("Times New Roman", Font.BOLD, 15));
            SearchField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 4));
            SearchButton = new JButton("Search");
            SearchButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
            SearchButton.setPreferredSize(new Dimension(100, 30));
            SearchButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 4));
            getContentPane().add(ScrollPane, BorderLayout.CENTER);
            LowerPanel = new JPanel(new FlowLayout());
            LowerPanel.setPreferredSize(new Dimension(1000, 40));
            getContentPane().add(LowerPanel, BorderLayout.SOUTH);
            MenuButton = new JButton("Menu");
            MenuButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
            MenuButton.setPreferredSize(new Dimension(100, 30));
            MenuButton.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
            ExitButton = new JButton("Exit");
            ExitButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
            ExitButton.setPreferredSize(new Dimension(100, 30));
            ExitButton.setBorder(BorderFactory.createLineBorder(Color.RED, 4));

            UpperPanel.add(SearchByLabel);
            UpperPanel.add(RoomNumRadioButton);
            UpperPanel.add(GuestNameRadioButton);
            UpperPanel.add(SearchField);
            UpperPanel.add(SearchButton);
            LowerPanel.add(MenuButton);
            LowerPanel.add(ExitButton);

            ResultSet resultset = Connect.getConnection().createStatement().executeQuery(Query);

            InsertIntoTable(resultset, ColumnNames, Model);

            MenuButton.addActionListener(MenuClick -> {
                MainMenu menu = new MainMenu();
                dispose();
            });
            ExitButton.addActionListener(ExitClick -> {
                dispose();
                System.exit(0);
            });
            SearchButton.addActionListener(SearchClick -> {
                SearchClick();
            });

        } catch (Exception e) {
            System.out.println("Guest Manager Catch Block" + e.getMessage());
        } finally {
            Connect.CloseConnection();
        }
    }
    protected abstract void SearchClick ();
        public void InsertIntoTable(ResultSet resultSet,String[]columnNames,DefaultTableModel model) throws Exception{
            while (resultSet.next()){
                Object [] row = new Object[columnNames.length];
                for(int i = 0; i<columnNames.length; i++){
                    row[i] = resultSet.getObject(columnNames[i]);
                }
                model.addRow(row);
            }
            resultSet.close();
        }
}
