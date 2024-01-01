package org.example;

public class Regular<T> extends User implements RequestsManager , Observer{

    public Regular() {

    }
    public Regular(String fullName) {
        super(fullName);
    }


    @Override
    public void logout() {
       System.exit(0);

    }

    @Override
    public void createRequest(Request r) {
        RequestsHolder.addRequest(r);

    }

    @Override
    public void removeRequest(Request r) {
       for (Request request : RequestsHolder.getRequests()) {
           if (request.equals(r) && request.getUsername().equals(r.getUsername())) {
               RequestsHolder.removeRequest(request);
               break;
           }
       }

    }

    @Override
    public void update(String notification) {
        addNotification(notification);

    }

}
