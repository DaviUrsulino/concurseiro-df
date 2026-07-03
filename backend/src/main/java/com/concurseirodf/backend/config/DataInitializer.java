package com.concurseirodf.backend.config;

import com.concurseirodf.backend.domain.entity.Concurso;
import com.concurseirodf.backend.domain.entity.Orgao;
import com.concurseirodf.backend.domain.enums.StatusConcurso;
import com.concurseirodf.backend.domain.repository.ConcursoRepository;
import com.concurseirodf.backend.domain.repository.OrgaoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DataInitializer {

    @Bean
    @Profile("local")
    public CommandLineRunner initData(OrgaoRepository orgaoRepository, ConcursoRepository concursoRepository) {
        return args -> {
            if (orgaoRepository.count() == 0) {
                Orgao tcdf = new Orgao();
                tcdf.setNome("Tribunal de Contas do Distrito Federal");
                tcdf.setSigla("TCDF");
                tcdf.setEsfera("Distrital");
                tcdf = orgaoRepository.save(tcdf);
                
                Orgao novacap = new Orgao();
                novacap.setNome("Companhia Urbanizadora da Nova Capital do Brasil");
                novacap.setSigla("NOVACAP");
                novacap.setEsfera("Distrital");
                novacap = orgaoRepository.save(novacap);

                Concurso c1 = new Concurso();
                c1.setTitulo("Concurso TCDF - Auditor de Controle Externo");
                c1.setLinkPaginaOficial("https://www.cebraspe.org.br/concursos/tcdf_23");
                c1.setOrgao(tcdf);
                c1.setStatus(StatusConcurso.ABERTO);
                concursoRepository.save(c1);

                Concurso c2 = new Concurso();
                c2.setTitulo("Concurso NOVACAP - Diversos Cargos");
                c2.setLinkPaginaOficial("https://www.quadrix.org.br/todos-os-concursos/inscricoes-abertas/novacap-2024.aspx");
                c2.setOrgao(novacap);
                c2.setStatus(StatusConcurso.PREVISTO);
                concursoRepository.save(c2);
            }
        };
    }
}
