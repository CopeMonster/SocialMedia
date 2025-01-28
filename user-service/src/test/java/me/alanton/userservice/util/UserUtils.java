package me.alanton.userservice.util;

import me.alanton.userservice.entity.User;

public class UserUtils {
    public static User alan() {
        return User.builder()
                .username("alan")
                .firstname("Alan")
                .lastname("Kassymbek")
                .email("alan@gmail.com")
                .password("password")
                .build();
    }

    public static User dima() {
        return User.builder()
                .username("dima")
                .firstname("Dima")
                .lastname("Olegov")
                .email("dima@gmail.com")
                .password("qwerty123")
                .build();
    }

    public static User alex() {
        return User.builder()
                .username("alex")
                .firstname("Alex")
                .lastname("Borisov")
                .email("borisov@gmail.com")
                .password("password123")
                .build();
    }
}
