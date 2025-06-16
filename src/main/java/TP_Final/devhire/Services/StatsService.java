package TP_Final.devhire.Services;

import org.springframework.stereotype.Service;

@Service
public class StatsService {
    private final PublicationService publicationService;
    private final CommentService commentService;
    private final LikeService likeService;
    private final JobService jobService;
    private final DeveloperService developerService;
    private final CompanyService companyService;
    private final ApplicationService applicationService;

    public StatsService(PublicationService publicationService, CommentService commentService, LikeService likeService, JobService jobService, DeveloperService developerService, CompanyService companyService, ApplicationService applicationService) {
        this.publicationService = publicationService;
        this.commentService = commentService;
        this.likeService = likeService;
        this.jobService = jobService;
        this.developerService = developerService;
        this.companyService = companyService;
        this.applicationService = applicationService;
    }

    public String getStats(){
        int publications = publicationService.publicationsQuantity();
        int comments = commentService.commentsQuantity();
        int likes = likeService.likesQuantity();
        int jobs = jobService.jobsQuantity();
        int devs = developerService.devsQuantity();
        int companies = companyService.companiesQuantity();
        int applications = applicationService.applicationsQuantity();
        double acceptedApplications = applicationService.percentageOfAcceptedApplications();
        double averagesPosts = publicationService.averageByUser();
        return "General stats"+'\n'
                +"Publications: "+publications+'\n'
                +"Average number of posts per user: "+averagesPosts+'\n'
                +"Comments: "+comments+'\n'
                +"Likes: "+likes+'\n'
                +"Developers: "+devs+'\n'
                +"Companies: "+companies+'\n'
                +"Jobs offers: "+jobs+'\n'
                +"Applications to jobs: "+applications+'\n'
                +"% of accepted applications: "+'%'+acceptedApplications;
    }
}
