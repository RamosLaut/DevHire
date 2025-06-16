package TP_Final.devhire.Services;

import TP_Final.devhire.Assemblers.ApplicationAssembler;
import TP_Final.devhire.Assemblers.DeveloperAssembler;
import TP_Final.devhire.Model.DTOS.ApplicationDTO;
import TP_Final.devhire.Model.DTOS.DeveloperApplicantDTO;
import TP_Final.devhire.Model.Entities.ApplicationEntity;
import TP_Final.devhire.Model.Entities.CompanyEntity;
import TP_Final.devhire.Model.Entities.DeveloperEntity;
import TP_Final.devhire.Model.Entities.JobEntity;
import TP_Final.devhire.Model.Enums.HardSkills;
import TP_Final.devhire.Exceptions.AlreadyExistsException;
import TP_Final.devhire.Exceptions.CredentialsRequiredException;
import TP_Final.devhire.Exceptions.NotFoundException;
import TP_Final.devhire.Exceptions.UnauthorizedException;
import TP_Final.devhire.Model.Enums.State;
import TP_Final.devhire.Repositories.ApplicantsRepository;
import TP_Final.devhire.Repositories.CompanyRepository;
import TP_Final.devhire.Repositories.DeveloperRepository;
import TP_Final.devhire.Repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {
    private final ApplicantsRepository applicantsRepository;
    private final DeveloperRepository developerRepository;
    private final CompanyRepository companyRepository;
    private final JobRepository jobRepository;
    private final ApplicationAssembler applicationAssembler;
    private final DeveloperAssembler developerAssembler;
    @Autowired
    public ApplicationService(ApplicantsRepository applicantsRepository, DeveloperRepository developerRepository, CompanyRepository companyRepository, JobRepository jobRepository, ApplicationAssembler applicationAssembler, DeveloperAssembler developerAssembler) {
        this.applicantsRepository = applicantsRepository;
        this.developerRepository = developerRepository;
        this.companyRepository = companyRepository;
        this.jobRepository = jobRepository;
        this.applicationAssembler = applicationAssembler;
        this.developerAssembler = developerAssembler;
    }
    public EntityModel<ApplicationDTO> apply(Long jobId)throws NotFoundException, UnauthorizedException, AlreadyExistsException{
        ApplicationEntity application = new ApplicationEntity();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(jobRepository.findById(jobId).isEmpty()){
            throw new NotFoundException("Job not found");
        }
        JobEntity job = jobRepository.findById(jobId).get();
        if(developerRepository.findByCredentials_Email(email).isEmpty()){
            throw new UnauthorizedException("You don't have permission to apply to this job");
        }
        DeveloperEntity developer = developerRepository.findByCredentials_Email(email).get();
        Optional<DeveloperEntity> devOpt = job.getApplicants().stream()
                .map(ApplicationEntity::getDev)
                .filter(dev -> dev.equals(developer))
                .findAny();
        if(devOpt.isPresent()){
            throw new AlreadyExistsException("You have already applied to this job");
        }
        application.setJob(job);
        application.setDev(developer);
        application.setState(State.PENDING);
        applicantsRepository.save(application);
        return applicationAssembler.toDevModel(application);
    }

    public void deleteApplicationById(Long id)throws NotFoundException, UnauthorizedException{
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(developerRepository.findByCredentials_Email(email).isEmpty()){
            throw new UnauthorizedException("You don't have permission to delete this application");
        }
        DeveloperEntity developer = developerRepository.findByCredentials_Email(email).get();
        if(applicantsRepository.findById(id).isEmpty()){
            throw new NotFoundException("Application not found");
        }
        ApplicationEntity application = applicantsRepository.findById(id).get();
        if(application.getDev().equals(developer)){
            applicantsRepository.deleteById(id);
        }else throw new UnauthorizedException("You don't have permission to delete this application");
    }
    public CollectionModel<EntityModel<ApplicationDTO>> findAll(){
        return CollectionModel.of(applicantsRepository.findAll().stream()
                .map(applicationAssembler::toModel)
                .toList());
    }
    public CollectionModel<EntityModel<ApplicationDTO>> findOwnApplications(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(developerRepository.findByCredentials_Email(email).isEmpty()){
            throw new CredentialsRequiredException("You need to login to view your applications");
        }
        DeveloperEntity developer = developerRepository.findByCredentials_Email(email).get();
        return CollectionModel.of(developer.getPostulatedJobs().stream()
                .map(applicationAssembler::toModel)
                .toList());
    }
    public EntityModel<ApplicationDTO> findById(Long id)throws NotFoundException{
        if(applicantsRepository.findById(id).isEmpty()){
            throw new NotFoundException("Application not found");
        }
        ApplicationEntity application = applicantsRepository.findById(id).get();
        JobEntity job = application.getJob();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(developerRepository.findByCredentials_Email(email).isPresent()) {
            DeveloperEntity developer = developerRepository.findByCredentials_Email(email).get();
            if (developer.getPostulatedJobs().contains(application)) {
                return applicationAssembler.toDevModel(application);
            }else {
                return applicationAssembler.toModel(application);
            }
        }else {
            CompanyEntity company = companyRepository.findByCredentials_Email(email).get();
            if (company.getJobs().contains(job)) {
                return applicationAssembler.toCompanyModel(application, job.getId());
            } else {
                return applicationAssembler.toModel(application);
            }
        }
    }
    public CollectionModel<EntityModel<DeveloperApplicantDTO>> findApplicantsByJobId(Long jobId)throws NotFoundException, UnauthorizedException{
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(companyRepository.findByCredentials_Email(email).isEmpty()){
            throw new UnauthorizedException("You don't have permission to view applicants to this job");
        }
        CompanyEntity company = companyRepository.findByCredentials_Email(email).get();
        if(jobRepository.findById(jobId).isEmpty()){
            throw new NotFoundException("Job not found");
        }
        JobEntity job = jobRepository.findById(jobId).get();
        if(!company.getJobs().contains(job)){
            throw new UnauthorizedException("You don't have permission to view applicants to this job");
        }
        List<EntityModel<DeveloperApplicantDTO>> applicants = job.getApplicants().stream()
                .map(ApplicationEntity::getDev)
                .map(dev->developerAssembler.toDevApplicantModel(dev, jobId))
                .toList();
        if(applicants.isEmpty()){
            throw new NotFoundException("No applicants found for this job");
        }
        return CollectionModel.of(applicants);
    }
    public CollectionModel<EntityModel<DeveloperApplicantDTO>> findApplicantsWithAllHardRequirements(Long jobId)throws NotFoundException{
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(companyRepository.findByCredentials_Email(email).isEmpty()){
            throw new UnauthorizedException("You don't have permission to view applicants to this job");
        }
        CompanyEntity company = companyRepository.findByCredentials_Email(email).get();
        if(jobRepository.findById(jobId).isEmpty()){
            throw new NotFoundException("Job not found");
        }
        JobEntity job = jobRepository.findById(jobId).get();
        List<ApplicationEntity> applicants = job.getApplicants().stream().toList();
        List<HardSkills> hardSkillsRequired = job.getHardSkills();
        List<DeveloperEntity> filtered = new ArrayList<>();
        if(company.getJobs().contains(job)){
            for(ApplicationEntity app : applicants) {
                DeveloperEntity dev = app.getDev();
                List<HardSkills> devHardSkills = dev.getHardSkills();
                long hardSkillsMatched = devHardSkills.stream()
                        .filter(hardSkillsRequired::contains)
                        .count();
                if(hardSkillsMatched == hardSkillsRequired.size()){
                    filtered.add(dev);
                }
            }
        }else throw new UnauthorizedException("You don't have permission to view applicants to this job");
        if(filtered.isEmpty()){
            throw new NotFoundException("No applicants were found with all the required qualifications for this job.");
        }
        return CollectionModel.of(filtered.stream().map(dev->developerAssembler.toDevApplicantModel(dev,jobId)).toList());
    }
    public CollectionModel<EntityModel<DeveloperApplicantDTO>> findApplicantsWithAnyHardRequirements(Long jobId)throws NotFoundException{
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(companyRepository.findByCredentials_Email(email).isEmpty()){
            throw new UnauthorizedException("You don't have permission to view applicants to this job");
        }
        CompanyEntity company = companyRepository.findByCredentials_Email(email).get();
        if(jobRepository.findById(jobId).isEmpty()){
            throw new NotFoundException("Job not found");
        }
        JobEntity job = jobRepository.findById(jobId).get();
        List<DeveloperEntity> filtered = new ArrayList<>();
        if(company.getJobs().contains(job)){
            List<ApplicationEntity> applicants = job.getApplicants().stream().toList();
            List<HardSkills> hardSkillsRequired = job.getHardSkills();
            for(ApplicationEntity app : applicants) {
                DeveloperEntity dev = app.getDev();
                List<HardSkills> devHardSkills = dev.getHardSkills();
                long hardSkillsMatched = devHardSkills.stream()
                        .filter(hardSkillsRequired::contains)
                        .count();
                if(hardSkillsMatched > 0){
                    filtered.add(dev);
                }
            }
        }else throw new UnauthorizedException("You don't have permission to view applicants to this job");
        if(filtered.isEmpty()){
            throw new NotFoundException("No applicants were found with any of the required qualifications for this job.");
        }
        return CollectionModel.of(filtered.stream().map(dev->developerAssembler.toDevApplicantModel(dev, jobId)).toList());
    }
    public CollectionModel<EntityModel<DeveloperApplicantDTO>> findApplicantsWithMinHardRequirements(Long jobId, int minRequirements)throws NotFoundException{
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(companyRepository.findByCredentials_Email(email).isEmpty()){
            throw new UnauthorizedException("You don't have permission to view applicants to this job");
        }
        CompanyEntity company = companyRepository.findByCredentials_Email(email).get();
        if(jobRepository.findById(jobId).isEmpty()){
            throw new NotFoundException("Job not found");
        }
        JobEntity job = jobRepository.findById(jobId).get();
        List<DeveloperEntity> filtered = new ArrayList<>();
        if(company.getJobs().contains(job)){
            List<ApplicationEntity> applicants = job.getApplicants().stream().toList();
            List<HardSkills> hardSkillsRequired = job.getHardSkills();
            for(ApplicationEntity app : applicants) {
                DeveloperEntity dev = app.getDev();
                List<HardSkills> devHardSkills = dev.getHardSkills();
                long hardSkillsMatched = devHardSkills.stream()
                        .filter(hardSkillsRequired::contains)
                        .count();
                if(hardSkillsMatched >= minRequirements){
                    filtered.add(dev);
                }
            }
        }else throw new UnauthorizedException("You don't have permission to view applicants to this job");
        if(filtered.isEmpty()){
            throw new NotFoundException("No applicants were found with at least " + minRequirements + " of the required qualifications for this job.");
        }
        return CollectionModel.of(filtered.stream().map(dev -> developerAssembler.toDevApplicantModel(dev, jobId)).toList());
    }
    public void acceptApplication(long id) throws NotFoundException, UnauthorizedException{
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(companyRepository.findByCredentials_Email(email).isEmpty()){
            throw new UnauthorizedException("You don't have permission to accept this application");
        }
        CompanyEntity company = companyRepository.findByCredentials_Email(email).get();
        if(applicantsRepository.findById(id).isEmpty()){
            throw new NotFoundException("Application not found");
        }
        ApplicationEntity application = applicantsRepository.findById(id).get();
        JobEntity job = application.getJob();
        if(!company.getJobs().contains(job)){
            throw new UnauthorizedException("You don't have permission to accept this application");
        }
        if(application.getState().equals(State.PENDING)){
            application.setState(State.ACCEPTED);
            applicantsRepository.save(application);
        }else throw new UnauthorizedException("This application has already been accepted");
    }
    public void rejectApplication(long id) throws NotFoundException, UnauthorizedException{
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(companyRepository.findByCredentials_Email(email).isEmpty()){
            throw new UnauthorizedException("You don't have permission to reject this application");
        }
        CompanyEntity company = companyRepository.findByCredentials_Email(email).get();
        if(applicantsRepository.findById(id).isEmpty()){
            throw new NotFoundException("Application not found");
        }
        ApplicationEntity application = applicantsRepository.findById(id).get();
        JobEntity job = application.getJob();
        if(!company.getJobs().contains(job)){
            throw new UnauthorizedException("You don't have permission to reject this application");
        }
        if(application.getState().equals(State.PENDING)){
            application.setState(State.REJECTED);
            applicantsRepository.save(application);
        }else throw new UnauthorizedException("This application has already been rejected");
    }
    public int applicationsQuantity(){
        return applicantsRepository.findAll().size();
    }
    public double percentageOfAcceptedApplications(){
        int acceptedApplications = (int)applicantsRepository.findAll().stream()
                .filter(app -> app.getState().equals(State.ACCEPTED))
                .count();
        int totalApplications = applicantsRepository.findAll().size();
        return (double)acceptedApplications / totalApplications * 100;
    }
    public CollectionModel<EntityModel<ApplicationDTO>> findAcceptedApplications(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(developerRepository.findByCredentials_Email(email).isEmpty()){
            throw new CredentialsRequiredException("You need to login to view your applications");
        }
        DeveloperEntity developer = developerRepository.findByCredentials_Email(email).get();
        return CollectionModel.of(developer.getPostulatedJobs().stream()
                .filter(app -> app.getState().equals(State.ACCEPTED))
                .map(applicationAssembler::toDevModel)
                .toList());
    }
    public CollectionModel<EntityModel<ApplicationDTO>> findRejectedApplications(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(developerRepository.findByCredentials_Email(email).isEmpty()){
            throw new CredentialsRequiredException("You need to login to view your applications");
        }
        DeveloperEntity developer = developerRepository.findByCredentials_Email(email).get();
        return CollectionModel.of(developer.getPostulatedJobs().stream()
                .filter(app -> app.getState().equals(State.REJECTED))
                .map(applicationAssembler::toDevModel)
                .toList());
    }
}
