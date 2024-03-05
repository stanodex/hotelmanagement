public class Customer {
    public int CustomerID;
    public String CustomerName;
    public String CustomerPhone;
    public static int CustomerCount = 3000;
    public int AssignedRoomNumber;
    public String AssignedRoomType;

    public Customer(String CustomerName, String CustomerPhone, int AssignedRoomNumber, String AssignedRoomType) {
        CustomerCount++;
        this.CustomerID = CustomerCount ;
        this.CustomerName = CustomerName;
        this.CustomerPhone = CustomerPhone;
        this.AssignedRoomNumber = AssignedRoomNumber;
        this.AssignedRoomType = AssignedRoomType;


    }

}
