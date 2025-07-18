package com.cineverse.data;

import org.junit.jupiter.params.provider.Arguments;

import java.util.UUID;
import java.util.stream.Stream;

public class AuthTestData {

    public static Stream<Arguments> validRegisterUsers() {
        return Stream.of(
                Arguments.of("Jeffery", "Asamoah", "useremaidl@gmail.com!", "Password123@", 200),
                Arguments.of("John", "Doe", uniqueEmail(), "Password123@")
        );
    }

    public static Stream<Arguments> invalidRegisterUsers() {
        return Stream.of(
                Arguments.of("MissingLast", "", "missinglast@example.com", "pass"), //missing last name
                Arguments.of("", "MissingFirst", "missingfirst@example.com", "pass"), //missing first name
                Arguments.of("Missing", "Email", "missingfirst@example.com", "pass"), //missing email
                Arguments.of("Weak", "Password", "weakpassword@example.com", "weakpassword") // weak password
        );
    }
    public static Stream<Arguments> validLoginUsers() {
        return Stream.of(
                Arguments.of("test_user@example.com", "Password123!") // ensure this user is already registered
        );
    }

    public static Stream<Arguments> invalidLoginUsers() {
        return Stream.of(
                Arguments.of("test_user@example.com", "WrongPassword"),
                Arguments.of("nonexistent_" + UUID.randomUUID() + "@example.com", "SomePassword123!")
        );
    }

    private static String uniqueEmail() {
        return "test_" + UUID.randomUUID() + "@example.com";
    }
}

