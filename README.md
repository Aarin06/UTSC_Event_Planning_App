# CSCB07_Project
7/30/2022
* Moved classes from Classes to com.example.cscb07_project
* Changed type of userID from int to String under Account
* Added field of type integer named status under Account to differentiate between admin and user

8/02/2022

* Added getters in the User class for FireBase serialization
* Added, but did not implement, leaveEvent() method in User class
* Added EventRecyclerAdapter for RecyclerView display for viewing events
* User ID's are now stored in Shared Preferences after login
* Implemented refresh button on view events screen when database change is detected
* Changed enrolledPlayers field in Event class to type ArrayList<String>

8/09/2022
Refactoring Changes:
- Rename Admin Venue and it's activity to AdminvenueActivity
- Modify the button in admin_venue to "view pending events"

TODO:
Final Notes for Prof Rawad, Vishal and TA team:
* Can register a user account but admin account should be manually added.
Steps: On home page, register a new user. Please remember the password. Go to Firebase, in the User node, find the most recent node/your created user and change the status field to 1 instead of 0. Proceed to login.
