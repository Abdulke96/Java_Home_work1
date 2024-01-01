package org.example;
import org.constants.RequestStatus;

public class Admin extends Staff implements Observer{
    public Admin() {
        super( "Admin");


    }
    public Admin(String fullName) {
        super(fullName);
    }
    public void resolveRequests( Request r) {
        UserExperienceContext userExperienceContext = new UserExperienceContext();
        int experience;
        for (Request request : RequestsHolder.getRequests()) {
            if (request.getStatus().equals(RequestStatus.Pending) && request.equals(r)) {
                request.setStatus(RequestStatus.Resolved);
            }
          
            for (User<?> user :IMDB.getInstance().getUsers()) {
                if (user.getUsername().equals(request.getUsername())) {
                   if (request.getType().equals(RequestTypes.ACTOR_ISSUE) ||request.getType().equals(RequestTypes.MOVIE_ISSUE)){
                       userExperienceContext.setExperienceStrategy(new CreateIssueStrategy());
                          experience = userExperienceContext.calculateUserExperience();
                            user.updateExperience(experience);
                   }
                }
            }
            request.removeObserver(this);

        }

    }
    public void rejectRequests(Request r) {
        for (Request request : RequestsHolder.getRequests()) {
            if (request.getStatus().equals(RequestStatus.Pending) && request.equals(r)) {
                request.setStatus(RequestStatus.Rejected);
                request.removeObserver(this);
            }
        }

    }

    // Method to add a user to the system
    public <E extends User<?>> E addUser(String fullName, AccountType accountType) {
        return UserFactory.create(fullName, accountType);
    }

    public void removeUser(User<?> user) {
        IMDB.getInstance().getUsers().remove(user);

    }

    public void removeUserDetails(User<?> user) {
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
    public void update(String notification) {
        addNotification(notification);
    }

    @Override
    public void logout() {
        System.out.println("Admin logged out.");
        System.exit(0);
    }

}

