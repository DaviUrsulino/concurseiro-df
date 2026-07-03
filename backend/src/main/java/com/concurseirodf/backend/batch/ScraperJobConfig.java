package com.concurseirodf.backend.batch;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;
import java.util.List;

@Configuration
public class ScraperJobConfig {

    private static final Logger log = LoggerFactory.getLogger(ScraperJobConfig.class);

    @Bean
    public Job concursoScraperJob(JobRepository jobRepository, Step step1) {
        return new JobBuilder("concursoScraperJob", jobRepository)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository)
                .<String, String>chunk(10, transactionManager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public ItemReader<String> itemReader() {
        // Exemplo: Simulando uma lista de links de sites de bancas ou DODF
        List<String> linksFicticios = Arrays.asList(
                "http://banca-ficticia.com.br/concurso/1",
                "http://banca-ficticia.com.br/concurso/2"
        );
        return new ListItemReader<>(linksFicticios);
    }

    @Bean
    public ItemProcessor<String, String> itemProcessor() {
        return link -> {
            log.info("Processando link: {}", link);
            // Aqui conectaríamos no site real com Jsoup.connect(link).get();
            // Para fim de arquitetura, vamos simular o HTML retornado
            String mockHtml = "<html><body><h1>Concurso SEDF</h1><a href='edital.pdf'>Edital de Abertura</a></body></html>";
            Document doc = Jsoup.parse(mockHtml);
            
            String tituloHtml = doc.select("h1").text();
            String linkEdital = doc.select("a:contains(Edital)").attr("href");
            
            if (!linkEdital.isEmpty()) {
                log.info("Encontrado edital '{}' no link {}", tituloHtml, linkEdital);
                return "Novo Edital: " + tituloHtml + " - Link: " + linkEdital;
            }
            return null; // Ignora se não achou nada relevante
        };
    }

    @Bean
    public ItemWriter<String> itemWriter() {
        return items -> {
            for (String item : items) {
                log.info("Salvando no banco de dados (via Service): {}", item);
                // Aqui injetaríamos o ConcursoService para criar os andamentos reais no banco
            }
        };
    }
}
