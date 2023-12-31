package org.example;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/*Static class that contains a list of all the requests that the entire admin team needs to resolve,
 as well as the necessary methods for managing this list (adding/removing a request). Being a static class,
  its members are accessible directly through the class name, without the need to create an instance of the class,
  and the changes made to it are visible throughout the application.*/
@Data
public class RequestsHolder {
@Getter @Setter
    private static List<Request> requests = new ArrayList<>();

    private RequestsHolder() {

    }

    public static void addRequest(Request request) {
        requests.add(request);
    }
    public static void removeRequest(Request request) {
        requests.remove(request);
    }

}

