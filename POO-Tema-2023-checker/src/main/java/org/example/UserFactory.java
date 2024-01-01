package org.example;

public class UserFactory extends Admin {
    @SuppressWarnings("unchecked")
    public static <T extends User<?>> T create(String fullName, AccountType accountType) {
        return switch (accountType) {
            case Regular -> (T) new Regular<T>(fullName);
            case Contributor -> (T) new Contributor(fullName);
            case Admin -> (T) new Admin(fullName);
        };
    }
}
