package TP_Final.devhire.Services;

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
    @Autowired
    public FollowService(FollowRepository followRepository, DeveloperRepository developerRepository, CompanyRepository companyRepository) {
        this.followRepository = followRepository;
        this.developerRepository = developerRepository;
        this.companyRepository = companyRepository;
    }
}
