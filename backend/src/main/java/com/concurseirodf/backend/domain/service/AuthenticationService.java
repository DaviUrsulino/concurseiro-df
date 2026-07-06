package com.concurseirodf.backend.domain.service;

import com.concurseirodf.backend.api.dto.AuthenticationRequest;
import com.concurseirodf.backend.api.dto.AuthenticationResponse;
import com.concurseirodf.backend.api.dto.RegisterRequest;
import com.concurseirodf.backend.domain.entity.Usuario;
import com.concurseirodf.backend.domain.repository.UsuarioRepository;
import com.concurseirodf.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final NotificacaoService notificacaoService;

    public AuthenticationResponse register(RegisterRequest request) {
        if (repository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        Usuario user = new Usuario();
        user.setNome(request.nome());
        user.setEmail(request.email());
        user.setSenha(passwordEncoder.encode(request.senha()));
        user.setNivelEscolaridade(request.nivelEscolaridade());
        user.setPretensaoSalarial(request.pretensaoSalarial());

        repository.save(user);
        
        notificacaoService.enviarEmailBoasVindas(user);

        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken, user.getNome(), user.getEmail(), user.getNivelEscolaridade());
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.senha()
                )
        );

        var user = repository.findByEmail(request.email())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken, user.getNome(), user.getEmail(), user.getNivelEscolaridade());
    }
}
