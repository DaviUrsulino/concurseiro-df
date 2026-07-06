package com.concurseirodf.backend.domain.service;

import com.concurseirodf.backend.api.dto.UpdateUsuarioRequest;
import com.concurseirodf.backend.api.dto.UsuarioResponse;
import com.concurseirodf.backend.domain.entity.Usuario;
import com.concurseirodf.backend.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioResponse getMeuPerfil(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return mapToResponse(usuario);
    }

    @Transactional
    public UsuarioResponse atualizarPerfil(String email, UpdateUsuarioRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (request.getNome() != null && !request.getNome().isBlank()) {
            usuario.setNome(request.getNome());
        }
        if (request.getNivelEscolaridade() != null) {
            usuario.setNivelEscolaridade(request.getNivelEscolaridade());
        }
        if (request.getPretensaoSalarial() != null) {
            usuario.setPretensaoSalarial(request.getPretensaoSalarial());
        }
        if (request.getNovaSenha() != null && !request.getNovaSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(request.getNovaSenha()));
        }

        usuarioRepository.save(usuario);
        return mapToResponse(usuario);
    }

    private UsuarioResponse mapToResponse(Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setNome(usuario.getNome());
        response.setEmail(usuario.getEmail());
        response.setNivelEscolaridade(usuario.getNivelEscolaridade());
        response.setPretensaoSalarial(usuario.getPretensaoSalarial());
        return response;
    }
}
