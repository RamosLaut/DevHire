package TP_Final.devhire.Services;

import TP_Final.devhire.Assemblers.PublicationAssembler;
import TP_Final.devhire.DTOS.PublicationDTO;
import TP_Final.devhire.Entities.CompanyEntity;
import TP_Final.devhire.Entities.PublicationEntity;
import TP_Final.devhire.Entities.DeveloperEntity;
import TP_Final.devhire.Exceptions.*;
import TP_Final.devhire.Mappers.PublicationMapper;
import TP_Final.devhire.Repositories.CompanyRepository;
import TP_Final.devhire.Repositories.PublicationsRepository;
import TP_Final.devhire.Repositories.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PublicationService{
    private final PublicationsRepository publicationsRepository;
    private final PublicationAssembler publicationAssembler;
    private final CompanyRepository companyRepository;
    private final DeveloperRepository developerRepository;
    private final PublicationMapper mapper;
    @Autowired
    public PublicationService(PublicationsRepository publicationsRepository, PublicationAssembler publicationAssembler, CompanyRepository companyRepository, DeveloperRepository developerRepository, PublicationMapper mapper) {
        this.publicationsRepository = publicationsRepository;
        this.publicationAssembler = publicationAssembler;
        this.companyRepository = companyRepository;
        this.developerRepository = developerRepository;
        this.mapper = mapper;
    }

    public EntityModel<PublicationDTO> save(PublicationDTO publicationDTO) {
        PublicationEntity publication = mapper.convertToPublicationEntity(publicationDTO);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<CompanyEntity> companyOpt = companyRepository.findByCredentials_Email(email);
        Optional<DeveloperEntity> devOpt = developerRepository.findByCredentials_Email(email);
        if (companyOpt.isPresent()) {
            publication.setCompany(companyOpt.get());
        } else devOpt.ifPresent(publication::setDeveloper);
        publicationsRepository.save(publication);
        return publicationAssembler.toModel(publication);
    }
    public CollectionModel<EntityModel<PublicationDTO>> findAll(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<PublicationEntity> publications = publicationsRepository.findAll();
        if(publications.isEmpty()){
            throw new NotFoundException("No publications found");
        }
        if(companyRepository.findByCredentials_Email(email).isPresent()){
            CompanyEntity company = companyRepository.findByCredentials_Email(email).get();
            List<EntityModel<PublicationDTO>> ownPublications = publications.stream()
                    .filter(pub -> company.equals(pub.getCompany()))
                    .map(publicationAssembler::toOwnPublicationModel)
                    .toList();

            List<EntityModel<PublicationDTO>> otherPublications = publications.stream()
                    .filter(pub -> !company.equals(pub.getCompany()))
                    .map(publicationAssembler::toModel)
                    .toList();
            List<EntityModel<PublicationDTO>> allPublications = Stream.concat(ownPublications.stream(), otherPublications.stream()).toList();
            if(allPublications.isEmpty()){
                throw new NotFoundException("No publications found");
            }
            return CollectionModel.of(allPublications);

        } else if (developerRepository.findByCredentials_Email(email).isPresent()) {
            DeveloperEntity developer = developerRepository.findByCredentials_Email(email).get();
            List<EntityModel<PublicationDTO>> ownPublications = publications.stream()
                    .filter(pub -> developer.equals(pub.getDeveloper()))
                    .map(publicationAssembler::toOwnPublicationModel)
                    .toList();

            List<EntityModel<PublicationDTO>> otherPublications = publications.stream()
                    .filter(pub -> !developer.equals(pub.getDeveloper()))
                    .map(publicationAssembler::toModel)
                    .toList();
            List<EntityModel<PublicationDTO>> allPublications = Stream.concat(otherPublications.stream(), ownPublications.stream()).toList();
            if(allPublications.isEmpty()){
                throw new NotFoundException("No publications found");
            }
            return CollectionModel.of(allPublications);
        }else throw new CredentialsRequiredException("You need to be logged in to see publications");
    }
    public CollectionModel<EntityModel<PublicationDTO>> findOwnPublications(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(companyRepository.findByCredentials_Email(email).isPresent()){
            CompanyEntity company = companyRepository.findByCredentials_Email(email).get();
            List<EntityModel<PublicationDTO>> publications = publicationsRepository.findByCompanyId(company.getId()).stream()
                    .map(publicationAssembler::toOwnPublicationModel)
                    .toList();
            if(publications.isEmpty()){
                throw new NotFoundException("No publications found");
            }
            return CollectionModel.of(publications);
        }else {
            DeveloperEntity developer = developerRepository.findByCredentials_Email(email).orElseThrow(()-> new NotFoundException("Developer not found"));
            List<EntityModel<PublicationDTO>> publications = publicationsRepository.findByDeveloperId(developer.getId()).stream()
                    .map(publicationAssembler::toOwnPublicationModel)
                    .toList();
            if(publications.isEmpty()){
                throw new NotFoundException("No publications found");
            }
            return CollectionModel.of(publications);
        }
    }
    public EntityModel<PublicationDTO> findById(Long id)throws NotFoundException{
        if(publicationsRepository.findById(id).isEmpty()){
            throw new NotFoundException("Publication not found");
        }
        PublicationEntity publication = publicationsRepository.findById(id).get();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(companyRepository.findByCredentials_Email(email).isPresent()){
            CompanyEntity company = companyRepository.findByCredentials_Email(email).get();
            if(company.equals(publication.getCompany())){
                return publicationAssembler.toOwnPublicationModel(publication);
            }else return publicationAssembler.toModel(publication);
        } else if (developerRepository.findByCredentials_Email(email).isPresent()) {
            DeveloperEntity developer = developerRepository.findByCredentials_Email(email).get();
            if(developer.equals(publication.getDeveloper())){
                return publicationAssembler.toOwnPublicationModel(publication);
            }else return publicationAssembler.toModel(publication);
        }else throw new CredentialsRequiredException("You need to be logged in to see publications");
    }
    public CollectionModel<EntityModel<PublicationDTO>> findByDevId(Long id)throws NotFoundException {
        List<PublicationEntity> publications = publicationsRepository.findByDeveloperId(id);
        if(publications.isEmpty()){
            throw new NotFoundException("No publications found");
        }
        return CollectionModel.of(publications.stream()
                .map(publicationAssembler::toModel)
                .toList());
    }
    public void deleteById(Long id)throws UnauthorizedException, NotFoundException{
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<CompanyEntity> companyOpt = companyRepository.findByCredentials_Email(email);
        Optional<DeveloperEntity> devOpt = developerRepository.findByCredentials_Email(email);
        Optional<PublicationEntity> publicationOpt = publicationsRepository.findById(id);
        if(publicationOpt.isEmpty()){
            throw new NotFoundException("Publication not found");
        } else if (publicationOpt.get().getDeveloper() != null && devOpt.isPresent() && devOpt.get().equals(publicationOpt.get().getDeveloper())) {
            publicationsRepository.deleteById(id);
        }else if(publicationOpt.get().getCompany() != null && companyOpt.isPresent() && companyOpt.get().equals(publicationOpt.get().getCompany())){
            publicationsRepository.deleteById(id);
        }else throw new UnauthorizedException("You don't have permission to delete this publication");
    }
    public EntityModel<PublicationDTO> updateContent(PublicationDTO publicationDTO)throws UnauthorizedException, NotFoundException, IdRequiredException{
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        PublicationEntity publication = publicationsRepository.findById(publicationDTO.getId()).orElseThrow(()-> new NotFoundException("Publication not found"));
        if(companyRepository.findByCredentials_Email(email).isPresent()){
            CompanyEntity company = companyRepository.findByCredentials_Email(email).get();
            if(publication.getCompany() != null && company.equals(publication.getCompany())){
                publicationsRepository.updateContent(publicationDTO.getContent(), publicationDTO.getId());
            }else throw new UnauthorizedException("You don't have permission to update this publication");
        }
        if(developerRepository.findByCredentials_Email(email).isPresent()){
            DeveloperEntity developer = developerRepository.findByCredentials_Email(email).get();
            if(publication.getDeveloper() != null && developer.equals(publication.getDeveloper())){
                publicationsRepository.updateContent(publicationDTO.getContent(), publicationDTO.getId());
            }else throw new UnauthorizedException("You don't have permission to update this publication");
        }
        return publicationAssembler.toOwnPublicationModel(publication);
    }
}
