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
