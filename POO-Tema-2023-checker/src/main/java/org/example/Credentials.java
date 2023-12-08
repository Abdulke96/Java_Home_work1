package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Credentials {

    private final String email;
    private final String password;
    private final LocalDateTime creationDate;
    public Credentials(){
        this.email = null;
        this.password = null;
        this.creationDate = LocalDateTime.now();
    }

    // Private constructor to enforce the use of the builder
  public   Credentials(Builder builder) {
        this.email = builder.email;
        this.password = builder.password;
        this.creationDate = LocalDateTime.now();
    }

    // Getter methods
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    // Builder class for Credentials
    public static class Builder {
        private final String email;
        private final String password;

        // Constructor with required parameters
        public Builder(String email, String password) {
            this.email = email;
            this.password = password;
        }

        // Build method to create an instance of Credentials
        public Credentials build() {
            return new Credentials(this);
        }
    }


    // Other methods as needed
    public String toString() {
        return "Credentials{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", creationDate=" + creationDate +
                '}';

    }
}
