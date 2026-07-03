package com.concurseirodf.backend.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobLauncher jobLauncher;
    private final Job concursoScraperJob;

    @PostMapping("/scraper/start")
    public ResponseEntity<String> startScraperJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            
            jobLauncher.run(concursoScraperJob, jobParameters);
            return ResponseEntity.ok("Job de Scraping iniciado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao iniciar Job: " + e.getMessage());
        }
    }
}
