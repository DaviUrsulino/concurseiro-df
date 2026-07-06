package com.concurseirodf.backend.domain.service;

import com.concurseirodf.backend.domain.entity.PasswordResetToken;
import com.concurseirodf.backend.domain.entity.Usuario;
import com.concurseirodf.backend.domain.repository.PasswordResetTokenRepository;
import com.concurseirodf.backend.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;
    private final NotificacaoService notificacaoService;

    @Transactional
    public void createPasswordResetTokenForUser(String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            // Não lançamos erro para evitar enumeração de contas
            return;
        }

        Usuario usuario = usuarioOpt.get();
        tokenRepository.deleteByUsuario(usuario); // deleta tokens antigos

        String token = UUID.randomUUID().toString();
        PasswordResetToken myToken = new PasswordResetToken();
        myToken.setToken(token);
        myToken.setUsuario(usuario);
        myToken.setExpiryDate(LocalDateTime.now().plusHours(1)); // 1 hora de validade
        tokenRepository.save(myToken);

        String resetLink = "http://localhost:3000/redefinir-senha?token=" + token;
        notificacaoService.enviarEmailRecuperacaoSenha(usuario.getEmail(), resetLink);
    }

    @Transactional
    public boolean resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> tokenOpt = tokenRepository.findByToken(token);
        if (tokenOpt.isEmpty() || tokenOpt.get().isExpired()) {
            return false;
        }

        Usuario usuario = tokenOpt.get().getUsuario();
        usuario.setSenha(passwordEncoder.encode(newPassword));
        usuarioRepository.save(usuario);
        tokenRepository.delete(tokenOpt.get());

        return true;
    }
}
