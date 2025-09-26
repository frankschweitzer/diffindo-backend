package com.diffindo.backend.service.user;

import com.diffindo.backend.model.User;
import com.diffindo.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService mockUserService;

    @Mock
    UserRepository mockUserRepository;

    @Mock
    BCryptPasswordEncoder mockBCryptPasswordEncoder;

    @Test
    public void testRegisterUser_Success() {
        when(mockUserRepository.save(any(User.class))).thenReturn(new User("test", "test", "test", "test"));

        User user = mockUserService.registerUser("test", "test", "test", "test");

        assertEquals("test", user.getName());
    }

    @Test
    public void testRegisterUser_duplicate() {
        when(mockUserRepository.save(any(User.class)))
                .thenThrow(new DataIntegrityViolationException("Duplicate key value violates unique constraint"));

        assertThrows(DataIntegrityViolationException.class, () -> {
            mockUserService.registerUser("","","","");
        });
    }
}
