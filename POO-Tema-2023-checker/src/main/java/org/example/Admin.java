package org.example;
import org.example.*;
public class Admin<T> extends Staff  implements ExperienceStrategy, Observer{
    public Admin() {
        super( "Admin");

    }
    public Admin(String fullName) {
        super(fullName);
    }

    public void resolveRequests() {



    }

    public void addProduction(Production production) {


    }

    public void removeProduction(Production production) {

    }

    public void updateProductionInformation(Production production) {



    }

    // Method to add a user to the system
    public <E extends User<?>> E addUser(String fullName, AccountType accountType) {
        return UserFactory.create(fullName, accountType);
    }

    public void removeUser(User<?> user) {
        if (user != null) {
            removeUserDetails(user);

        }
    }

    private void removeUserDetails(User<?> user) {
        for (Production production : user.getFavorites()) {
            production.removeReviewsByUser(user.getUsername());
        }
        for (Request request : RequestsHolder.getRequests()) {
            if (request.getUsername().equals(user.getUsername())) {
                RequestsHolder.removeRequest(request);
            }
        }

    }

    @Override
    public void logout() {
        System.out.println("Admin logged out.");
        System.exit(0);
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
   public static class UserFactory {
        @SuppressWarnings("unchecked")
        public static <T extends User<?>> T create(String fullName, AccountType accountType) {
            return switch (accountType) {
                case Regular -> (T) new Regular<T>(fullName);
                case Contributor -> (T) new Contributor<T>(fullName);
                case Admin -> (T) new Admin<T>(fullName);
            };
        }
    }

}

