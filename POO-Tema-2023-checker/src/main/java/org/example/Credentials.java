package org.example;

import java.time.LocalDateTime;
import lombok.*;
@Data
public class Credentials {
    private  String email;
    private String password;
    private LocalDateTime creationDate;
    public Credentials(){
        this.email = "null";
        this.password = "null";
        this.creationDate = LocalDateTime.now();
    }

  public Credentials(Builder builder) {
        this.email = builder.email;
        this.password = builder.password;
        this.creationDate = LocalDateTime.now();
    }

    public static class Builder {
        private final String email;
        private final String password;
        public Builder(String email, String password) {
            this.email = email;
            this.password = password;
        }
        public Credentials build() {
            return new Credentials(this);
        }
    }
}
