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
        RequestsHolder.removeRequest(r);

    }

    @Override
    public void update(String notification) {
        System.out.println(notification);

    }

}
