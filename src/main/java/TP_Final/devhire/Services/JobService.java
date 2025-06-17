package TP_Final.devhire.Services;

import TP_Final.devhire.Assemblers.JobAssembler;
import TP_Final.devhire.Assemblers.SkillAssembler;
import TP_Final.devhire.Model.DTOS.JobDTO;
import TP_Final.devhire.Model.Entities.CompanyEntity;
import TP_Final.devhire.Model.Entities.JobEntity;
import TP_Final.devhire.Model.Entities.SkillModel;
import TP_Final.devhire.Model.Enums.HardSkills;
import TP_Final.devhire.Model.Enums.SoftSkills;
import TP_Final.devhire.Exceptions.AlreadyExistsException;
import TP_Final.devhire.Exceptions.NotFoundException;
import TP_Final.devhire.Exceptions.UnauthorizedException;
import TP_Final.devhire.Model.Mappers.JobMapper;
import TP_Final.devhire.Repositories.CompanyRepository;
import TP_Final.devhire.Repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final JobAssembler jobAssembler;
    private final CompanyRepository companyRepository;
    private final SkillAssembler skillAssembler;
    private final JobMapper jobMapper;

    @Autowired
    public JobService(JobRepository jobRepository, JobAssembler jobAssembler, CompanyRepository companyRepository, SkillAssembler skillAssembler, JobMapper jobMapper) {
        this.jobRepository = jobRepository;
        this.jobAssembler = jobAssembler;
        this.companyRepository = companyRepository;
        this.skillAssembler = skillAssembler;
        this.jobMapper = jobMapper;
    }
    public EntityModel<JobDTO> save(JobDTO jobDTO) throws NotFoundException{
        JobEntity jobEntity = jobMapper.convertToEntity(jobDTO);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        jobEntity.setCompany(companyRepository.findByCredentials_Email(email).orElseThrow(()->new NotFoundException("Company not found")));
        jobRepository.save(jobEntity);
        return jobAssembler.toModel(jobEntity);
    }
    public CollectionModel<EntityModel<JobDTO>> findAll() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (companyRepository.findByCredentials_Email(email).isPresent()){
            CompanyEntity company = companyRepository.findByCredentials_Email(email).get();
            List<EntityModel<JobDTO>> ownOffers = jobRepository.findAll().stream()
                    .filter(job -> company.equals(job.getCompany()))
                    .map(jobAssembler::toModel)
                    .toList();
            List<EntityModel<JobDTO>> otherOffers = jobRepository.findAll().stream()
                    .filter(job -> !company.equals(job.getCompany()))
                    .map(jobAssembler::toDevModel)
                    .toList();
            List<EntityModel<JobDTO>> allOffers = Stream.concat(ownOffers.stream(), otherOffers.stream()).toList();
            if(allOffers.isEmpty()){
                throw new NotFoundException("No jobs were found");
            }
            return CollectionModel.of(allOffers);
        }else {
            if(jobRepository.findAll().isEmpty()){
                throw new NotFoundException("No jobs were found");
            }
            return CollectionModel.of(jobRepository.findAll().stream()
                    .map(jobAssembler::toDevModel)
                    .toList());
        }
    }
    public EntityModel<JobDTO> findById(Long jobId) throws NotFoundException {
        if(jobRepository.findById(jobId).isEmpty()){
            throw new NotFoundException("Job not found");
        }
        JobEntity job = jobRepository.findById(jobId).get();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(companyRepository.findByCredentials_Email(email).isPresent()){
            CompanyEntity company = companyRepository.findByCredentials_Email(email).get();
            if(company.getJobs().contains(job)){
                return jobAssembler.toModel(job);
            }else {
                return jobAssembler.toDevModel(job);
            }
        } else{
            return jobAssembler.toDevModel(jobRepository.findById(jobId).orElseThrow(() -> new NotFoundException("Job not found")));
        }
    }
    public CollectionModel<EntityModel<JobDTO>> findOwnOffers() throws NotFoundException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(companyRepository.findByCredentials_Email(email).isEmpty()){
            throw new UnauthorizedException("You need to be registered as a company to view your own job offers");
        }
        CompanyEntity company = companyRepository.findByCredentials_Email(email).get();
        List<EntityModel<JobDTO>> jobs = company.getJobs().stream()
                .map(jobAssembler::toModel)
                .toList();
        if(jobs.isEmpty()){
            throw new NotFoundException("No jobs were found");
        }
        return CollectionModel.of(jobs);
    }
    public CollectionModel<SkillModel> getSkills(long id)throws NotFoundException {
        if(jobRepository.findById(id).isEmpty()){
            throw new NotFoundException("Job not found");
        }
        List<String> hardSkills = Arrays.stream(HardSkills.values()).map(Enum::name).toList();
        List<String> softSkills = Arrays.stream(SoftSkills.values()).map(Enum::name).toList();
        List<String> skills = Stream.concat(hardSkills.stream(), softSkills.stream())
                .toList();
        return skillAssembler.toCollection(skills, id);
    }
    public void addRequirement(long id, String skill)throws NotFoundException, UnauthorizedException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        CompanyEntity company = companyRepository.findByCredentials_Email(email)
                .orElseThrow(()->new NotFoundException("Company not found"));
        if(jobRepository.findById(id).isEmpty()){
            throw new NotFoundException("Job not found");
        }
        JobEntity job = jobRepository.findById(id).get();

        List<String> hardSkills = Arrays.stream(HardSkills.values()).map(Enum::name).toList();
        List<String> softSkills = Arrays.stream(SoftSkills.values()).map(Enum::name).toList();
        if(company.getJobs().contains(job)){
            if(hardSkills.contains(skill)){
                if(job.getHardSkills().contains(HardSkills.valueOf(skill))){
                    throw new AlreadyExistsException("Hard skill already exists");
                }else {
                    job.getHardSkills().add(HardSkills.valueOf(skill));
                    jobRepository.save(job);
                }
            }else if(softSkills.contains(skill)) {
                if (job.getSoftSkills().contains(SoftSkills.valueOf(skill))) {
                    throw new AlreadyExistsException("Soft skill already exists");
                }else {
                    job.getSoftSkills().add(SoftSkills.valueOf(skill));
                    jobRepository.save(job);
                }
                }else throw new NotFoundException("Skill not found");
            }else throw new UnauthorizedException("You don't have permission to update this job offer");
    }
    public List<String> findJobRequirements(long jobId)throws NotFoundException {
        if (jobRepository.findById(jobId).isEmpty()){
            throw new NotFoundException("Job not found");
        }
        JobEntity job = jobRepository.findById(jobId).get();
        List<String> hardSkills = job.getHardSkills().stream().map(Enum::name).toList();
        List<String> softSkills = job.getSoftSkills().stream().map(Enum::name).toList();
        List<String> allSkills = Stream.concat(hardSkills.stream(), softSkills.stream())
                .toList();
        if(allSkills.isEmpty()){
            throw new NotFoundException("No skills required were found for this job");
        }
        return allSkills;
    }
    public EntityModel<JobDTO> update(long jobId, JobDTO jobDTO)throws NotFoundException, UnauthorizedException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        CompanyEntity company = companyRepository.findByCredentials_Email(email)
                .orElseThrow(()->new NotFoundException("Company not found"));
        if(jobRepository.findById(jobId).isEmpty()){
            throw new NotFoundException("Job not found");
        }
        JobEntity job = jobRepository.findById(jobId).get();
        if(company.getJobs().contains(job)){
            if(jobDTO.getDescription()!=null){
                job.setDescription(jobDTO.getDescription());
            }
            if(jobDTO.getPosition()!=null){
                job.setPosition(jobDTO.getPosition());
            }
            if(jobDTO.getLocation()!=null){
                job.setLocation(jobDTO.getLocation());
            }
            jobRepository.save(job);
        }else throw new UnauthorizedException("You don't have permission to update this job offer");
        return jobAssembler.toModel(job);
    }
    public void deleteById(Long id)throws NotFoundException, UnauthorizedException{
        if(jobRepository.findById(id).isEmpty()){
            throw new NotFoundException("Job not found");
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        CompanyEntity company = companyRepository.findByCredentials_Email(email)
                .orElseThrow(()->new NotFoundException("Company not found"));
        JobEntity job = jobRepository.findById(id).get();
        if(company.getJobs().contains(job)){
            jobRepository.deleteById(id);
        }else throw new UnauthorizedException("You don't have permission to delete this job offer");
    }
    public CollectionModel<EntityModel<JobDTO>> findBySkill(String skill)throws NotFoundException{
        String normalized = skill.trim().replaceAll("[-\\s]", "_").toUpperCase();

        Optional<HardSkills> maybeHardSkill = Arrays.stream(HardSkills.values())
                .filter(hs -> hs.name().equalsIgnoreCase(normalized))
                .findFirst();

        Optional<SoftSkills> maybeSoftSkill = Arrays.stream(SoftSkills.values())
                .filter(ss -> ss.name().equalsIgnoreCase(normalized))
                .findFirst();

        if (maybeHardSkill.isEmpty() && maybeSoftSkill.isEmpty()) {
            throw new NotFoundException("Skill not found");
        }

        List<JobEntity> filteredJobs = jobRepository.findAll().stream()
                .filter(job -> maybeHardSkill.map(job.getHardSkills()::contains).orElse(false)
                        || maybeSoftSkill.map(job.getSoftSkills()::contains).orElse(false))
                .toList();
        if (filteredJobs.isEmpty()) {
            throw new NotFoundException("No jobs were found with this skill requirement.");
        }
        return CollectionModel.of(filteredJobs.stream().map(jobAssembler::toDevModel).toList());
    }
    public CollectionModel<EntityModel<JobDTO>> findByCompanyName(String name)throws NotFoundException{
        Optional<CompanyEntity> companyOpt = companyRepository.findAll().stream()
                .filter(company -> company.getName().equalsIgnoreCase(name))
                .findFirst();
        if(companyOpt.isPresent()){
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            if(companyRepository.findByCredentials_Email(email).isPresent()){
                CompanyEntity company = companyRepository.findByCredentials_Email(email).get();
                if(company.equals(companyOpt.get())){
                    if(companyOpt.get().getJobs().isEmpty()){
                        throw new NotFoundException("Company has no jobs available");
                    }
                    return CollectionModel.of(companyOpt.get().getJobs().stream().map(jobAssembler::toModel).toList());
                }else {
                    return CollectionModel.of(companyOpt.get().getJobs().stream().map(jobAssembler::toDevModel).toList());
                }
            }
            if(companyOpt.get().getJobs().isEmpty()){
                throw new NotFoundException("Company has no jobs available");
            }
            return CollectionModel.of(companyOpt.get().getJobs().stream().map(jobAssembler::toDevModel).toList());
        }else throw new NotFoundException("Company not found");
    }
    public int jobsQuantity(){
        return jobRepository.findAll().size();
    }
    public String hardSkillMostRequested(){
        HardSkills mostRequested = jobRepository.findAll().stream()
                .flatMap(job -> job.getHardSkills().stream())
                .collect(Collectors.groupingBy(skill -> skill, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
        return mostRequested.name();
    }
    public String softSkillMostRequested(){
        SoftSkills mostRequested = jobRepository.findAll().stream()
                .flatMap(job -> job.getSoftSkills().stream())
                .collect(Collectors.groupingBy(skill -> skill, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
        return mostRequested.name();
    }
}






