package com.concurseirodf.backend.domain.service;

import com.concurseirodf.backend.api.dto.RegisterRequest;
import com.concurseirodf.backend.domain.entity.Usuario;
import com.concurseirodf.backend.domain.repository.UsuarioRepository;
import com.concurseirodf.backend.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UsuarioRepository repository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private NotificacaoService notificacaoService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void register_shouldThrowException_whenEmailAlreadyExists() {
        RegisterRequest request = new RegisterRequest("Test User", "test@test.com", "123456", "Superior", new BigDecimal("5000.0"));
        
        when(repository.findByEmail("test@test.com")).thenReturn(Optional.of(new Usuario()));

        assertThrows(RuntimeException.class, () -> authenticationService.register(request));
        
        verify(repository, never()).save(any(Usuario.class));
        verify(notificacaoService, never()).enviarEmailBoasVindas(any(Usuario.class));
    }
}
