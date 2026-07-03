package com.concurseirodf.backend.config;

import com.concurseirodf.backend.domain.entity.Andamento;
import com.concurseirodf.backend.domain.entity.Cargo;
import com.concurseirodf.backend.domain.entity.Concurso;
import com.concurseirodf.backend.domain.entity.Orgao;
import com.concurseirodf.backend.domain.enums.NivelCargo;
import com.concurseirodf.backend.domain.enums.StatusConcurso;
import com.concurseirodf.backend.domain.repository.AndamentoRepository;
import com.concurseirodf.backend.domain.repository.CargoRepository;
import com.concurseirodf.backend.domain.repository.ConcursoRepository;
import com.concurseirodf.backend.domain.repository.OrgaoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    @Profile("local")
    public CommandLineRunner initData(
            OrgaoRepository orgaoRepository, 
            ConcursoRepository concursoRepository,
            CargoRepository cargoRepository,
            AndamentoRepository andamentoRepository) {
        return args -> {
            // Limpa o banco para garantir que recarregue nossos novos mocks
            andamentoRepository.deleteAll();
            cargoRepository.deleteAll();
            concursoRepository.deleteAll();
            orgaoRepository.deleteAll();
            
            // 1. TCDF
                Orgao tcdf = new Orgao();
                tcdf.setNome("Tribunal de Contas do Distrito Federal");
                tcdf.setSigla("TCDF");
                tcdf.setEsfera("Distrital");
                tcdf = orgaoRepository.save(tcdf);
                
                Concurso c1 = new Concurso();
                c1.setTitulo("Concurso TCDF - Auditor de Controle Externo");
                c1.setLinkPaginaOficial("https://www.cebraspe.org.br/concursos/tcdf_23");
                c1.setOrgao(tcdf);
                c1.setStatus(StatusConcurso.ABERTO);
                c1 = concursoRepository.save(c1);

                Cargo cargo1 = new Cargo();
                cargo1.setConcurso(c1);
                cargo1.setNome("Auditor de Controle Externo");
                cargo1.setNivel(NivelCargo.SUPERIOR);
                cargo1.setSalario(new BigDecimal("19235.88"));
                cargo1.setVagasImediatas(23);
                cargo1.setVagasCadastroReserva(50);
                cargoRepository.save(cargo1);

                Andamento andamento1 = new Andamento();
                andamento1.setConcurso(c1);
                andamento1.setTitulo("Edital de Abertura Publicado");
                andamento1.setDescricao("O edital normativo nº 1 foi publicado no DODF, marcando o início das inscrições.");
                andamento1.setLinkDocumento("http://dodf.df.gov.br/edital1.pdf");
                andamento1.setDataPublicacao(LocalDate.now().minusDays(10));
                andamento1.setExtraidoPorIa(true);
                andamentoRepository.save(andamento1);

                // 2. NOVACAP
                Orgao novacap = new Orgao();
                novacap.setNome("Companhia Urbanizadora da Nova Capital do Brasil");
                novacap.setSigla("NOVACAP");
                novacap.setEsfera("Distrital");
                novacap = orgaoRepository.save(novacap);

                Concurso c2 = new Concurso();
                c2.setTitulo("Concurso NOVACAP - Diversos Cargos");
                c2.setLinkPaginaOficial("https://www.quadrix.org.br/todos-os-concursos/inscricoes-abertas/novacap-2024.aspx");
                c2.setOrgao(novacap);
                c2.setStatus(StatusConcurso.PREVISTO);
                c2 = concursoRepository.save(c2);

                Cargo cargo2 = new Cargo();
                cargo2.setConcurso(c2);
                cargo2.setNome("Técnico Administrativo");
                cargo2.setNivel(NivelCargo.MEDIO);
                cargo2.setSalario(new BigDecimal("4942.94"));
                cargo2.setVagasImediatas(15);
                cargo2.setVagasCadastroReserva(30);
                cargoRepository.save(cargo2);

                // 3. SEDES
                Orgao sedes = new Orgao();
                sedes.setNome("Secretaria de Estado de Desenvolvimento Social");
                sedes.setSigla("SEDES");
                sedes.setEsfera("Distrital");
                sedes = orgaoRepository.save(sedes);

                Concurso c3 = new Concurso();
                c3.setTitulo("Concurso SEDES - Assistência Social");
                c3.setLinkPaginaOficial("https://www.ibades.org.br/");
                c3.setOrgao(sedes);
                c3.setStatus(StatusConcurso.ABERTO);
                c3 = concursoRepository.save(c3);

                Cargo cargo3 = new Cargo();
                cargo3.setConcurso(c3);
                cargo3.setNome("Especialista em Assistência Social");
                cargo3.setNivel(NivelCargo.SUPERIOR);
                cargo3.setSalario(new BigDecimal("7000.00"));
                cargo3.setVagasImediatas(100);
                cargoRepository.save(cargo3);

                // 4. ABGEF (Mocked)
                Orgao abgef = new Orgao();
                abgef.setNome("Agência Brasileira de Gestão e Finanças");
                abgef.setSigla("ABGEF");
                abgef.setEsfera("Federal");
                abgef = orgaoRepository.save(abgef);

                Concurso c4 = new Concurso();
                c4.setTitulo("Concurso ABGEF - Nível Médio e Superior");
                c4.setLinkPaginaOficial("https://www.fgv.br/concursos");
                c4.setOrgao(abgef);
                c4.setStatus(StatusConcurso.EM_ANDAMENTO);
                c4 = concursoRepository.save(c4);

                Andamento andamento4 = new Andamento();
                andamento4.setConcurso(c4);
                andamento4.setTitulo("Retificação do Cronograma");
                andamento4.setDescricao("As datas das provas objetivas foram alteradas devido às fortes chuvas.");
                andamento4.setLinkDocumento("http://dodf.df.gov.br/retificacao.pdf");
                andamento4.setDataPublicacao(LocalDate.now().minusDays(2));
                andamento4.setExtraidoPorIa(true);
                andamentoRepository.save(andamento4);

                // 5. CONTER
                Orgao conter = new Orgao();
                conter.setNome("Conselho Nacional de Técnicos em Radiologia");
                conter.setSigla("CONTER");
                conter.setEsfera("Federal");
                conter = orgaoRepository.save(conter);

                Concurso c5 = new Concurso();
                c5.setTitulo("Concurso CONTER 2024");
                c5.setLinkPaginaOficial("https://www.quadrix.org.br/");
                c5.setOrgao(conter);
                c5.setStatus(StatusConcurso.ABERTO);
                concursoRepository.save(c5);

                // 6. EMBRATUR
                Orgao embratur = new Orgao();
                embratur.setNome("Agência Brasileira de Promoção Internacional do Turismo");
                embratur.setSigla("EMBRATUR");
                embratur.setEsfera("Federal");
                embratur = orgaoRepository.save(embratur);

                Concurso c6 = new Concurso();
                c6.setTitulo("Processo Seletivo EMBRATUR");
                c6.setLinkPaginaOficial("https://www.cebraspe.org.br/");
                c6.setOrgao(embratur);
                c6.setStatus(StatusConcurso.PREVISTO);
                concursoRepository.save(c6);

            // 7. FAB
            Orgao fab = new Orgao();
            fab.setNome("Força Aérea Brasileira");
            fab.setSigla("FAB");
            fab.setEsfera("Federal");
            fab = orgaoRepository.save(fab);

            Concurso c7 = new Concurso();
            c7.setTitulo("Concurso FAB - Sargentos");
            c7.setLinkPaginaOficial("https://www.fab.mil.br/concursos");
            c7.setOrgao(fab);
            c7.setStatus(StatusConcurso.ABERTO);
            concursoRepository.save(c7);
        };
    }
}
