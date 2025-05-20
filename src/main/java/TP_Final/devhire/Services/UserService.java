package TP_Final.devhire.Services;

import TP_Final.devhire.Entities.AcademicInfo;
import TP_Final.devhire.Entities.JobExperience;
import TP_Final.devhire.Entities.UserEntity;
import TP_Final.devhire.Enums.HardSkills;
import TP_Final.devhire.Enums.SoftSkills;
import TP_Final.devhire.Exceptions.UserNotFoundException;
import TP_Final.devhire.Repositories.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity register (UserEntity user){
        if (userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new RuntimeException("The username already exists");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("The is already registered");
        }
        if (userRepository.findByDni(user.getDni()).isPresent()){
            throw new RuntimeException("The DNI is already registered");
        }
        return userRepository.save(user);
    }

    public Optional<UserEntity> findById (Long id){
        return userRepository.findById(id);
    }

//    public Page<UserEntity> findAllPage(int page, int size){
//        Pageable pageable = PageRequest.of(page, size);
//        Page<UserEntity> entityPage;
//        return  entityPage = userRepository.findAll(pageable);
//    }

    public List<UserEntity> findAll(){
        return userRepository.findAll();
    }

    public void deleteById (Long userID){
        userRepository.deleteById(userID);
    }

    public UserEntity update (Long userID, UserEntity user){
        user.setUser_id(userID);
        return userRepository.save(user);
    }

    public UserEntity updateUserFields (Long userID, UserEntity user){
        return userRepository.findById(userID).map(u -> {user.setName(user.getName());
            u.setLastName(user.getLastName());
            u.setEmail(user.getEmail());
            u.setDni(user.getDni());
            u.setPassword(user.getPassword());
            u.setAcademicInfo(user.getAcademicInfo());
            u.setLocation(user.getLocation());
            u.setSeniority(user.getSeniority());
            u.setJobExperience(user.getJobExperience());
            u.setSoftSkills(user.getSoftSkills());
            u.setHardSkills(user.getHardSkills());
            return userRepository.save(u);}).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public UserEntity updatePassword (Long userID, String newPassword){
        UserEntity user = userRepository.findById(userID).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    public UserEntity updateSkills (Long userID, List<SoftSkills> softSkills, List<HardSkills> hardSkills){
        UserEntity user = userRepository.findById(userID).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setSoftSkills(softSkills);
        user.setHardSkills(hardSkills);
        return userRepository.save(user);
    }

    public UserEntity updateAcademicInfo (Long userID, List<AcademicInfo> academicInfo){
        UserEntity user = userRepository.findById(userID).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setAcademicInfo(academicInfo);
        return userRepository.save(user);
    }
    public UserEntity updateJobExperience (Long userID, List<JobExperience> jobExperience){
        UserEntity user = userRepository.findById(userID).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setJobExperience(jobExperience);
        return userRepository.save(user);
    }

    public UserEntity deactivate (Long userID){
        UserEntity user = userRepository.findById(userID).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setState(false);
        return userRepository.save(user);
    }

    public UserEntity reactivate (Long userID){
        UserEntity user = userRepository.findById(userID).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setState(true);
        return userRepository.save(user);
    }


}
