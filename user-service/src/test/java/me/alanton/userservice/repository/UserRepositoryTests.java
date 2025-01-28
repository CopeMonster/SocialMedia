package me.alanton.userservice.repository;

import me.alanton.userservice.entity.User;
import me.alanton.userservice.util.UserUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        userRepository.deleteAll();;
    }

    @Test
    @DisplayName("Test find all users functionality")
    public void givenThreeUsersAreStored_whenFindAllUsers_thenReturnAll() {
        // given
        User alan = UserUtils.alan();
        User dima = UserUtils.dima();
        User alex = UserUtils.alex();

        userRepository.saveAll(List.of(alan, dima, alex));

        // when
        List<User> fetchedUsers = userRepository.findAll();

        // then
        assertThat(fetchedUsers).isNotEmpty();
        assertThat(fetchedUsers.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("Test find by user id functionality")
    public void givenUserIsStored_whenFindUserById_thenReturnIt() {
        // given
        User alan = UserUtils.alan();
        userRepository.save(alan);

        // when
        User fetchedUser =
                userRepository.findById(alan.getId()).orElse(null);

        // then
        assertThat(fetchedUser).isNotNull();
    }

    @Test
    @DisplayName("Test find by username functionality")
    public void givenUserIsStored_whenFindByUsername_thenReturnIt() {
        // given
        User alan = UserUtils.alan();
        userRepository.save(alan);

        // when
        User fetchedUser =
                userRepository.findByUsername(alan.getUsername()).orElse(null);

        // then
        assertThat(fetchedUser).isNotNull();
    }

    @Test
    @DisplayName("Test find by email functionality")
    public void givenUserIsStored_whenFindByEmail_thenReturnIt() {
        // given
        User alan = UserUtils.alan();
        userRepository.save(alan);

        // when
        User fetchedUser =
                userRepository.findByEmail(alan.getEmail()).orElse(null);

        // then
        assertThat(fetchedUser).isNotNull();
    }

    @Test
    @DisplayName("Test find by user id with not existing user id")
    public void givenNotExistingUUID_whenFindUserById_thenReturnNull() {
        // given
        UUID notExistingUUID = UUID.randomUUID();

        // when
        User fetchedUser =
                userRepository.findById(notExistingUUID).orElse(null);

        // then
        assertThat(fetchedUser).isNull();
    }

    @Test
    @DisplayName("Test save user functionality")
    public void givenUser_whenSaveUser_thenSaveIt() {
        // given
        User alan = UserUtils.alan();

        // when
        User savedUser = userRepository.save(alan);

        // then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
    }

    @Test
    @DisplayName("Test delete user functionality")
    public void givenUserIsStored_whenDeleteUser_thenDeleteIt() {
        // given
        User alan = UserUtils.alan();
        userRepository.save(alan);

        // when
        userRepository.deleteById(alan.getId());

        User fetchedUser = userRepository.findById(alan.getId()).orElse(null);

        // then
        assertThat(fetchedUser).isNull();
    }
}

