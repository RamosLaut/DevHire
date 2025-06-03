package TP_Final.devhire.Services;

import TP_Final.devhire.Assemblers.FollowAssembler;
import TP_Final.devhire.Entities.FollowEntity;
import TP_Final.devhire.Exceptions.CompanyNotFound;
import TP_Final.devhire.Exceptions.CredentialsRequiredException;
import TP_Final.devhire.Exceptions.DeveloperNotFoundException;
import TP_Final.devhire.Repositories.CompanyRepository;
import TP_Final.devhire.Repositories.DeveloperRepository;
import TP_Final.devhire.Repositories.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final DeveloperRepository developerRepository;
    private final CompanyRepository companyRepository;
    private final FollowAssembler assembler;
    @Autowired
    public FollowService(FollowRepository followRepository, DeveloperRepository developerRepository, CompanyRepository companyRepository, FollowAssembler assembler) {
        this.followRepository = followRepository;
        this.developerRepository = developerRepository;
        this.companyRepository = companyRepository;
        this.assembler = assembler;
    }

    public EntityModel<Object> followDev(Long devFollowedId)throws DeveloperNotFoundException{
        if(developerRepository.findById(devFollowedId).isEmpty()){
            throw new DeveloperNotFoundException("Developer not found");
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        FollowEntity follow = new FollowEntity();
        if(developerRepository.findByCredentials_Email(email).isPresent()){
            follow.setDevFollower(developerRepository.findByCredentials_Email(email).get());
            follow.setDevFollowed(developerRepository.findById(devFollowedId).get());
            followRepository.save(follow);
        } else if (companyRepository.findByCredentials_Email(email).isPresent()) {
            follow.setCompanyFollower(companyRepository.findByCredentials_Email(email).get());
            follow.setDevFollowed(developerRepository.findById(devFollowedId).get());
            followRepository.save(follow);
        }else throw new CredentialsRequiredException("Credentials required");
        return assembler.toModel(follow);
    }
    public EntityModel<Object> followCompany(Long companyFollowedId)throws CompanyNotFound{
        if(companyRepository.findById(companyFollowedId).isEmpty()){
            throw new CompanyNotFound("Company not found");
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        FollowEntity follow = new FollowEntity();
        if(developerRepository.findByCredentials_Email(email).isPresent()){
            follow.setDevFollower(developerRepository.findByCredentials_Email(email).get());
            follow.setCompanyFollowed(companyRepository.findById(companyFollowedId).get());
            followRepository.save(follow);
        } else if (companyRepository.findByCredentials_Email(email).isPresent()) {
            follow.setCompanyFollower(companyRepository.findByCredentials_Email(email).get());
            follow.setCompanyFollowed(companyRepository.findById(companyFollowedId).get());
            followRepository.save(follow);
        } else throw new CredentialsRequiredException("Credentials required");
    return assembler.toModel(follow);
    }
}
