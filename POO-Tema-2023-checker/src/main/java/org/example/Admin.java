package org.example;

import java.lang.reflect.Constructor;

public class Admin<T> extends Staff  implements ExperienceStrategy{

    // Constructor
    public Admin() {

    }
//    public Admin(String fullName) {
//        super(fullName);
//    }

    public void resolveRequests() {

    }

    public void addProduction(Production production) {

    }

    public void removeProduction(Production production) {

    }

    public void updateProductionInformation(Production production) {

    }

    // Method to add a user to the system
    public <T extends User> T addUser(String fullName, Class<T> userType) {
        T user = UserFactory.create(fullName, userType);
        // Additional logic for adding a user
        return user;
    }

    // Method to remove a user from the system
    public void removeUser(User user) {
        if (user != null) {
            // Remove associated details (reviews, requests, etc.)
            removeUserDetails(user);

            // Additional logic for removing a user
        }
    }

    // Remove associated details (reviews, requests, etc.) when removing a user
    private void removeUserDetails(User user) {
        // Remove reviews associated with the user
        for (Production production : user.getFavorites()) {
            production.removeReviewsByUser(user.getUsername());
        }

        // Remove requests associated with the user
        for (Request request : RequestsHolder.getRequests()) {
            if (request.getUsername().equals(user.getUsername())) {
                RequestsHolder.removeRequest(request);
            }
        }

        // Additional logic for removing other details associated with the user
    }

    // Override the logout method
    @Override
    public void logout() {
        // Additional logic for admin logout
        System.out.println("Admin logged out.");
    }

    public void addProductionSystem(Production p) {

    }

    public void addActorSystem(Actor a) {

    }

    public void removeProductionSystem(String name) {

    }

    public void removeActorSystem(String name) {

    }

    public void updateProduction(Production p) {

    }

    public void updateActor(Actor a) {

    }

    @Override
    public int calculateExperience() {
        return 0;
    }

    // Other methods as needed
}

//class UserFactory {
//    public static <T extends User> T create(String fullName, Class<T> userType) {
//        try {
//            // Assuming the user type class has a constructor that takes a String parameter (fullName)
//            return userType.getConstructor(String.class).newInstance(fullName);
//        } catch (Exception e) {
//            // Handle instantiation exceptions
//            e.printStackTrace();
//            return null;
//        }
//    }
//}

class UserFactory {
    public static <T extends User> T create(String fullName, Class<T> userType) {
        try {
            Constructor<T> constructor = userType.getDeclaredConstructor(String.class);

            // Make the constructor accessible, if it is not already
            constructor.setAccessible(true);

            return constructor.newInstance(fullName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}