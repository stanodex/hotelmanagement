**Hotel Management**
------------------------------------------------------------------------------------------
**This project is implemented by using Swing and MySQL database. By using this project you can:**
- Guest Check In
- Guest Check Out
- Add Room
- Add Receptionists
- Show Current Guests (Guest id, full name, phone number, check in date, assigned room number, assigned room type)
- Show Previous Guests (Guest id, full name, phone number, check in date, check out date, assigned room number, assigned room type)
------------------------------------------------------------------------------------------
**Notes**
- In login page you will use receptionist id and password. There is default receptionist for first login ***(ID: 1001 Pass: password)*** . You can add another receptionist.
- You can search guest by using room number or guest name.
- When you click check in button and if there is no available room, it will not allow you to check in.
- While making check in, you will select room capacity and room type. When you select capacity, room type will be updated by your selected capacity.
- Room number starts from 1, receptionist number starts from 1001, guest number starts from 3000. It provides clarity.
- When you check out, the page updates itself and shows check in date and current date to check out and calculates the total amount.
------------------------------------------------------------------------------------------
***Steps to run the application***
1. Open MySQLWorkbench and create a database named '**HOTELDATABASE**'.
2. Create the tables and trigger by using **mysqlquery.txt**.
3. Populate the tables by using this project or query.
4. Import the project in IDE.
5. Import the jar file from lib folder.
6. Run the project.
