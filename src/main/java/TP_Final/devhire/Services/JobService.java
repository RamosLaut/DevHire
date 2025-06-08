package TP_Final.devhire.Services;

import TP_Final.devhire.Assemblers.JobAssembler;
import TP_Final.devhire.DTOS.JobDTO;
import TP_Final.devhire.Entities.CompanyEntity;
import TP_Final.devhire.Entities.JobEntity;
import TP_Final.devhire.Exceptions.NotFoundException;
import TP_Final.devhire.Repositories.CompanyRepository;
import TP_Final.devhire.Repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


@Service
public class JobService {
    private final JobRepository jobRepository;
    private final JobAssembler assembler;
    private final CompanyRepository companyRepository;


    @Autowired
    public JobService(JobRepository jobRepository, JobAssembler assembler, CompanyRepository companyRepository) {
        this.jobRepository = jobRepository;
        this.assembler = assembler;
        this.companyRepository = companyRepository;
    }

    public void save(JobEntity job) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        job.setCompany(companyRepository.findByCredentials_Email(email).orElseThrow());
        jobRepository.save(job);
    }

    public CollectionModel<EntityModel<JobDTO>> findAll() {
        List<EntityModel<JobDTO>> jobs = jobRepository.findAll().stream()
                .map(assembler::toModel)
                .toList();
        return CollectionModel.of(jobs);
    }

    public EntityModel<JobDTO> findById(Long jobId) throws NotFoundException {
        return assembler.toModel(jobRepository.findById(jobId).orElseThrow(() -> new NotFoundException("Job not found")));
    }

    public CollectionModel<EntityModel<JobDTO>> findOwnOffers() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<CompanyEntity> optCompany = companyRepository.findByCredentials_Email(email);
        List<EntityModel<JobDTO>> jobs = jobRepository.findAll().stream()
                .filter(job -> job.getCompany().equals(optCompany.orElseThrow()))
                .map(assembler::toModel)
                .toList();
        return CollectionModel.of(jobs);
    }

    public List<String> getRequirements(long id)throws NotFoundException {
        JobEntity job = jobRepository.findById(id).orElseThrow(() -> new NotFoundException("Job not found"));
        List<String> hardSkills = job.getHardSkills().stream().map(Enum::name).toList();
        List<String> softSkills = job.getSoftSkills().stream().map(Enum::name).toList();
        return Stream.concat(hardSkills.stream(), softSkills.stream())
                .toList();
    }
    }



