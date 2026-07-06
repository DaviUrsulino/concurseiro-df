package com.concurseirodf.backend.domain.service;

import com.concurseirodf.backend.domain.entity.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class NotificacaoService {

    private static final String EMAILS_DIR = "emails_simulados";

    public void enviarEmailBoasVindas(Usuario usuario) {
        log.info("Iniciando envio de e-mail de boas-vindas para {}", usuario.getEmail());
        
        try {
            Path dirPath = Paths.get(EMAILS_DIR);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = EMAILS_DIR + "/BoasVindas_" + usuario.getNome().replaceAll("\\s+", "_") + "_" + timestamp + ".html";

            String htmlTemplate = """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                    <meta charset="UTF-8">
                    <title>Bem-vindo ao Concurseiro DF!</title>
                    <style>
                        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f4f5; margin: 0; padding: 0; }
                        .container { max-width: 600px; margin: 40px auto; background-color: #ffffff; border-radius: 12px; overflow: hidden; box-shadow: 0 4px 6px rgba(0,0,0,0.05); }
                        .header { background-color: #4f46e5; padding: 40px 20px; text-align: center; color: white; }
                        .header h1 { margin: 0; font-size: 28px; letter-spacing: -0.5px; }
                        .content { padding: 40px 30px; color: #3f3f46; line-height: 1.6; }
                        .content h2 { color: #18181b; margin-top: 0; }
                        .btn { display: inline-block; background-color: #4f46e5; color: white; text-decoration: none; padding: 12px 24px; border-radius: 8px; font-weight: bold; margin-top: 20px; }
                        .footer { background-color: #f8fafc; padding: 20px; text-align: center; font-size: 12px; color: #64748b; border-top: 1px solid #e2e8f0; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h1>📖 Concurseiro DF</h1>
                        </div>
                        <div class="content">
                            <h2>Olá, %s! 🎉</h2>
                            <p>Sua conta foi criada com sucesso! Estamos muito felizes em ter você na maior plataforma de inteligência de editais do Distrito Federal.</p>
                            <p>Nosso objetivo é revolucionar a forma como você estuda. A partir de agora, o nosso <strong>Smart Matcher</strong> já mapeou o seu perfil e o seu <strong>Dashboard Interativo</strong> está pronto para uso.</p>
                            
                            <a href="http://localhost:3000" class="btn">Acessar Plataforma</a>
                        </div>
                        <div class="footer">
                            <p>Este é um e-mail gerado automaticamente. Por favor, não responda.</p>
                            <p>&copy; 2026 Concurseiro DF. Todos os direitos reservados.</p>
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(usuario.getNome());

            try (FileWriter fileWriter = new FileWriter(new File(fileName))) {
                fileWriter.write(htmlTemplate);
            }

            log.info("E-mail de boas-vindas gerado e salvo localmente com sucesso em: {}", fileName);

        } catch (IOException e) {
            log.error("Erro ao gerar e-mail de boas-vindas simulado para {}", usuario.getEmail(), e);
        }
    }

    public void enviarEmailRecuperacaoSenha(String email, String resetLink) {
        log.info("Iniciando envio de e-mail de recuperação de senha para {}", email);
        
        try {
            Path dirPath = Paths.get(EMAILS_DIR);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = EMAILS_DIR + "/RecuperacaoSenha_" + email.replaceAll("[^a-zA-Z0-9]", "_") + "_" + timestamp + ".txt";

            String body = "Olá,\n\nVocê solicitou a redefinição de sua senha.\nClique no link abaixo para criar uma nova senha:\n" +
                          resetLink + "\n\nSe você não solicitou, ignore este e-mail.";

            try (FileWriter fileWriter = new FileWriter(new File(fileName))) {
                fileWriter.write(body);
            }

            log.info("E-mail de recuperação gerado com sucesso em: {}", fileName);

        } catch (IOException e) {
            log.error("Erro ao gerar e-mail de recuperação de senha simulado para {}", email, e);
        }
    }
}
