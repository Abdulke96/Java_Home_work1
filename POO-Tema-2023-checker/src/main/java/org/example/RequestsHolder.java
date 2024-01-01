package org.example;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Data
public class RequestsHolder {
@Getter @Setter
    private static List<Request> requests = IMDB.getInstance().getRequests();

    public static void addRequest(Request request) {
        requests.add(request);
    }
    public static void removeRequest(Request request) {
        for (Request r : requests) {
            if (r.equals(request)) {
                requests.remove(r);
                break;
            }
        }
    }

}

