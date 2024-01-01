package org.example;
import org.constants.RequestStatus;

import java.util.List;


public class Contributor extends Staff implements RequestsManager , Observer {

    public Contributor() {
        super("");
    }
    public Contributor(String fullName) {
        super(fullName);
    }




    public void resolveRequests(Request r) {
        UserExperienceContext userExperienceContext = new UserExperienceContext();
        for (Request request : RequestsHolder.getRequests()) {
            if (request.getStatus().equals(RequestStatus.Pending) && request.getTo().equals("CONTRIBUTOR/ADMIN") && request.equals(r)) {
                request.setStatus(RequestStatus.Resolved);
                for (User<?> user :IMDB.getInstance().getUsers()) {
                    if (user.getUsername().equals(request.getUsername())) {
                          userExperienceContext.setExperienceStrategy(new CreateIssueStrategy());
                            int experience = userExperienceContext.calculateUserExperience();
                            user.updateExperience(experience);
                    }
                }

            }
        }
    }
    public void rejectRequests(Request r) {
        for (Request request : RequestsHolder.getRequests()) {
            if (request.getStatus().equals(RequestStatus.Pending) && request.getTo().equals("CONTRIBUTOR/ADMIN") && request.equals(r)) {
                request.setStatus(RequestStatus.Rejected);

            }
        }
    }

    @Override
    public void createRequest(Request r) {
        RequestsHolder.addRequest(r);
        RequestsHolder.addRequest(r);
    }

    @Override
    public void removeRequest(Request r) {
        List<Request> requests = IMDB.getInstance().getRequests();
        for(Request request : requests){
            if( request.equals(r) && request.getUsername().equals(r.getUsername())){
                requests.remove(r);
            }
        }
        RequestsHolder.removeRequest(r);
    }


    @Override
    public void update(String notification) {
        addNotification(notification);
    }
    public void logout() {
        System.out.println("Contributor logged out.");
    }

}
