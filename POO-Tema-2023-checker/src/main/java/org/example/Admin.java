package org.example;
import org.constants.RequestStatus;
import org.example.*;
public class Admin<T> extends Staff  implements Observer{
    public Admin() {
        super( "Admin");

    }
    public Admin(String fullName) {
        super(fullName);
    }

    public void resolveRequests() {
        for (Request request : RequestsHolder.getRequests()) {
            if (request.getStatus().equals(RequestStatus.Pending)) {
                request.setStatus(RequestStatus.Resolved);

            }
            for (User<?> user :IMDB.getInstance().getUsers()) {
                if (user.getUsername().equals(request.getUsername())) {
                    user.setExperience(user.getExperience()+1);
                }
            }

        }

    }
    public void rejectRequests() {
        for (Request request : RequestsHolder.getRequests()) {
            if (request.getStatus().equals(RequestStatus.Pending)) {
                request.setStatus(RequestStatus.Rejected);

            }
        }

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



}

