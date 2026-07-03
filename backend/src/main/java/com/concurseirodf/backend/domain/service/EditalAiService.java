package com.concurseirodf.backend.domain.service;

import com.concurseirodf.backend.api.dto.DisciplinaRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditalAiService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    public EditalAiService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
        this.objectMapper = new ObjectMapper();
    }

    public List<DisciplinaRequestDTO> extrairDisciplinas(String trechoEdital) {
        String systemPrompt = """
                Você é um assistente especialista em concursos públicos do Brasil.
                Sua tarefa é ler um trecho de edital fornecido pelo usuário e extrair as disciplinas e os tópicos cobrados.
                Retorne ESTRITAMENTE um array JSON contendo objetos no seguinte formato:
                [
                  {
                    "nome": "Nome da Disciplina (Ex: Língua Portuguesa)",
                    "topicosJson": "Tópicos como uma string separada por vírgula (Ex: Compreensão de texto, Crase, Concordância)"
                  }
                ]
                Não inclua nenhuma outra palavra, apenas o JSON válido.
                """;

        String jsonResponse = chatClient.prompt()
                .system(systemPrompt)
                .user(trechoEdital)
                .call()
                .content();

        // Limpa possíveis marcações de bloco de código Markdown retornadas pela IA
        if (jsonResponse != null) {
            jsonResponse = jsonResponse.replace("```json", "").replace("```", "").trim();
        }

        try {
            return objectMapper.readValue(jsonResponse, new TypeReference<List<DisciplinaRequestDTO>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao processar JSON da IA: " + e.getMessage(), e);
        }
    }
}
