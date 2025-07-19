package com.cineverse.data;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.UUID;
import java.util.stream.Stream;

public class TestDataProvider {

    public static Stream<Arguments> validRegisterUserCredentials() {
        return Stream.of(
                Arguments.of(new AuthRegisterData("Jeffery", "Asamoah", "newuseremail@gmail.com", "Password123@"),
                        200),
                Arguments.of(new AuthRegisterData("New", "User", "new111111email@gmail.com", "Password123@"),
                        200),
                Arguments.of(new AuthRegisterData("John", "Doe", uniqueEmail(), "Password123@"),
                        200),
                Arguments.of(new AuthRegisterData("John", "Doe", "john.doe@example.com", "Password123@"),
                        200)
        );
    }

    public static Stream<Arguments> invalidRegisterUserCredentials() {
        return Stream.of(
                Arguments.of(new AuthRegisterData("MissingLast", "", "missinglast@example.com", "Password123@"),
                        400),
                Arguments.of(new AuthRegisterData("", "MissingFirst", "missingfirst@example.com", "Password123@"),
                        400),
                Arguments.of(new AuthRegisterData("Missing", "Email", "missingfirst@example.com", "Password123@"),
                        400),
                Arguments.of(
                        new AuthRegisterData("Weak", "Password", "weakpassword@example.com", "weakpassword"),
                        400)
        );
    }

    public static Stream<Arguments> validLoginCredentials() {
        return Stream.of(
                Arguments.of(new AuthLoginData("useremaidl@gmail.com!", "Password123@"), 200), // ensure this user is already registered
                Arguments.of(new AuthLoginData("new111111email@gmail.com!", "Password123@"), 200),
                Arguments.of(new AuthLoginData("john.doe@example.com", "Password123@"), 200)
                );
    }

    public static Stream<Arguments> invalidLoginCredentials() {
        return Stream.of(
                Arguments.of(new AuthLoginData("test_user@example.com", "WrongPassword"), 400), // wrong password
                Arguments.of(new AuthLoginData("johndoe@example.com", "Password123@"), 400), // Non-existent user
                Arguments.of(new AuthLoginData("invalid-email", "Password123@"), 400), // Invalid email format
                Arguments.of(new AuthLoginData("", "Password123@"), 400),  // Empty email
                Arguments.of(new AuthLoginData("nonexistent_" + UUID.randomUUID() + "@example.com", "SomePassword123!"), 400) // Non-existent user
        );
    }

    public static class AuthRegisterDataProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(
                            // Positive case
                            new AuthRegisterData("John", "Doe", "john.doe@example.com", "Password123!"),
                            200
                    ),
                    Arguments.of(
                            // Negative case: Empty email
                            new AuthRegisterData("John", "Doe", "", "Password123!"),
                            400
                    ),
                    Arguments.of(
                            // Edge case: Very long name
                            new AuthRegisterData("A".repeat(100), "B".repeat(100), "longname@example.com", "Password123!"),
                            400
                    ),
                    Arguments.of(
                            // Edge case: Weak password
                            new AuthRegisterData("John", "Doe", "weak@example.com", "weak"),
                            400
                    )
            );
        }




    }



    public static class AuthLoginDataProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(
                            // Positive case
                            new AuthLoginData("john.doe@example.com", "Password123!"),
                            200
                    ),
                    Arguments.of(
                            // Negative case: Wrong password
                            new AuthLoginData("john.doe@example.com", "WrongPass"),
                            401
                    ),
                    Arguments.of(
                            // Edge case: Invalid email format
                            new AuthLoginData("invalid-email", "Password123!"),
                            400
                    )
            );
        }
    }

    public static class AuthRegisterData {
        private String firstName;
        private String lastName;
        private String email;
        private String password;

        public AuthRegisterData(String firstName, String lastName, String email, String password) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.password = password;
        }

        // Getters
        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public String getEmail() { return email; }
        public String getPassword() { return password; }
    }

    public static class AuthLoginData {
        private String email;
        private String password;

        public AuthLoginData(String email, String password) {
            this.email = email;
            this.password = password;
        }

        // Getters
        public String getEmail() { return email; }
        public String getPassword() { return password; }
    }

    private static String uniqueEmail() {
        return "test_" + UUID.randomUUID() + "@example.com";
    }
}