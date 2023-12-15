package org.example;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
public class RequestsHolder {
    @Getter
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

