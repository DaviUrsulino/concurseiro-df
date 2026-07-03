package com.concurseirodf.backend.batch;

import com.concurseirodf.backend.domain.entity.Concurso;
import com.concurseirodf.backend.domain.entity.Orgao;
import com.concurseirodf.backend.domain.enums.StatusConcurso;
import com.concurseirodf.backend.domain.repository.ConcursoRepository;
import com.concurseirodf.backend.domain.repository.OrgaoRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
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
import java.util.Optional;

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
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager, ItemWriter<Concurso> itemWriter) {
        return new StepBuilder("step1", jobRepository)
                .<String, Concurso>chunk(10, transactionManager)
                .reader(itemReader())
                .processor(itemProcessor(null)) // we will inject it
                .writer(itemWriter)
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
    public ItemProcessor<String, Concurso> itemProcessor(OrgaoRepository orgaoRepository) {
        return link -> {
            log.info("Processando link: {}", link);
            // Aqui conectaríamos no site real com Jsoup.connect(link).get();
            // Para fim de arquitetura, vamos simular o HTML retornado
            String mockHtml = "<html><body><h1>Concurso TCDF " + System.currentTimeMillis() + "</h1><a href='edital.pdf'>Edital de Abertura</a></body></html>";
            Document doc = Jsoup.parse(mockHtml);
            
            String tituloHtml = doc.select("h1").text();
            String linkEdital = doc.select("a:contains(Edital)").attr("href");
            
            if (!linkEdital.isEmpty() && orgaoRepository != null) {
                log.info("Encontrado edital '{}' no link {}", tituloHtml, linkEdital);
                
                // Create Mock Data
                Orgao orgao = new Orgao();
                orgao.setNome("Tribunal de Contas do Distrito Federal");
                orgao.setSigla("TCDF");
                orgao.setEsfera("Distrital");
                orgao = orgaoRepository.save(orgao);
                
                Concurso concurso = new Concurso();
                concurso.setTitulo(tituloHtml);
                concurso.setLinkPaginaOficial(link);
                concurso.setOrgao(orgao);
                concurso.setStatus(StatusConcurso.ABERTO);
                
                return concurso;
            }
            return null;
        };
    }

    @Bean
    public ItemWriter<Concurso> itemWriter(ConcursoRepository concursoRepository) {
        return concursos -> {
            for (Concurso concurso : concursos) {
                log.info("Salvando no banco de dados: {}", concurso.getTitulo());
                concursoRepository.save(concurso);
            }
        };
    }
}
