package TP_Final.devhire.Services;

import TP_Final.devhire.Assemblers.HardSkillsAssembler;
import TP_Final.devhire.Assemblers.JobAssembler;
import TP_Final.devhire.Controllers.JobController;
import TP_Final.devhire.DTOS.JobDTO;
import TP_Final.devhire.Entities.CompanyEntity;
import TP_Final.devhire.Entities.JobEntity;
import TP_Final.devhire.Enums.HardSkills;
import TP_Final.devhire.Exceptions.JobNotFoundException;
import TP_Final.devhire.Repositories.CompanyRepository;
import TP_Final.devhire.Repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final JobAssembler assembler;
    private final CompanyRepository companyRepository;
    private final HardSkillsAssembler hardSkillsAssembler;

    @Autowired
    public JobService(JobRepository jobRepository, JobAssembler assembler, CompanyRepository companyRepository, HardSkillsAssembler hardSkillsAssembler) {
        this.jobRepository = jobRepository;
        this.assembler = assembler;
        this.companyRepository = companyRepository;
        this.hardSkillsAssembler = hardSkillsAssembler;
    }

    public void save(JobEntity job) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        job.setCompany(companyRepository.findByEmail(email).orElseThrow());
        jobRepository.save(job);
    }

    public CollectionModel<EntityModel<JobDTO>> findAll() {
        List<EntityModel<JobDTO>> jobs = jobRepository.findAll().stream()
                .map(assembler::toModel)
                .toList();
        return CollectionModel.of(jobs);
    }

    public EntityModel<JobDTO> findById(Long jobId) throws JobNotFoundException {
        return assembler.toModel(jobRepository.findById(jobId).orElseThrow(() -> new JobNotFoundException("Job not found")));
    }

    public CollectionModel<EntityModel<JobDTO>> findOwnOffers() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<CompanyEntity> optCompany = companyRepository.findByEmail(email);
        List<EntityModel<JobDTO>> jobs = jobRepository.findAll().stream()
                .filter(job -> job.getCompany().equals(optCompany.orElseThrow()))
                .map(assembler::toModel)
                .toList();
        return CollectionModel.of(jobs);
    }

    public List<String> getRequirements(long id) {
        JobEntity job = jobRepository.findById(id).orElseThrow(() -> new JobNotFoundException("Job not found"));
        List<String> hardSkills = job.getHardSkills().stream().map(Enum::name).toList();
        List<String> softSkills = job.getSoftSkills().stream().map(Enum::name).toList();
        return Stream.concat(hardSkills.stream(), softSkills.stream())
                .toList();
    }

    public void getHardSkills() {
        Arrays.stream(HardSkills.values()).map(Enum::name).map(hardSkillsAssembler::toModel).toList();
    }

    private CollectionModel<EntityModel<String>> toHardSkillLinks(List<HardSkills> skills, Long jobId) {
        return CollectionModel.of(
                skills.stream().map(skill -> EntityModel.of(skill.name(),
                        linkTo(methodOn(JobController.class).addHardSkill(skill.name(), jobId)).withRel("addHardSkill"))
                ).toList()
        );
    }
//    public void addRequirementHardSkill(long id, String requirement) {
//        JobEntity job = jobRepository.findById(id).orElseThrow(() -> new JobNotFoundException("Job not found"));
//        job.getHardSkills().add(HardSkills.valueOf(requirement));
//        jobRepository.updateHardSkills(id, job.getHardSkills());
//    }
//
//    public List<String> getHardSkills(long id) {
//        List<String> hardSkills = Arrays.stream(HardSkills.values())
//                .map(Enum::name)
//                .toList();
//        List<EntityModel<String>> hardSkillsModels = new ArrayList<>();
//        for (String hardSkill : hardSkills) {
//            hardSkillsModels.add(hardSkillsAssembler.toModel(hardSkill, id));
//        }
//    }
}

