## Contacts
This is an Android App to store phone contacts.

### Project setup
Clone the repo, open the project in Android Studio, hit "Run". Done!

##### Technologies used
- Android
- Java
- SQLite

##### Database Table
Fields of the contacts model,

| contacts      |   type
| ----------- | ----------- |
| contact_id (PRIMARY KEY)   | INTEGER |
| contact_name      |TEXT    |
| contact_number    |  INTEGER  |
| contact_email     | TEXT   |
| contact_address   | TEXT   |
| contact_birthday  | TEXT   |

#### Functions
1. insert new contact = **CREATE** new contact
2. view all contacts = **GET** all
3. search contact = **GET** by name
4. update contact = **UPDATE** by id
5. delete contact = **DELETE** by id

Create the UI, attributes for EditTexts, Buttons, TextViews.
Declare the database reference, the EditText variables.
In method onCreate(), instantiate the Database and the EditTexts.
Create a table by using pure SQL add data types;


### The Code Challenge

Android App, incorporating an SQLite Database for storing address book contacts.
More specifically, add a few more fields in your DB, such as e-mail, address, etc.

The app must support all the basic DB operations: 
- Insertion of a new contact.
- Viewing all available contacts.
- Searching for a specific contact.
- Updating a specific contact’s details.
- Deleting a contact.
