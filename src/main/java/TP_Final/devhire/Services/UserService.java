package TP_Final.devhire.Services;

import TP_Final.devhire.Entities.UserEntity;
import TP_Final.devhire.Repositories.UserRepository;
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

    int page = 0;
    int size = 10;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity register (UserEntity user){
        if (userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new RuntimeException("El nombre de usuario ya existe");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("El email ya se encuentra registrado");
        }
        if (userRepository.findByDni(user.getDni()).isPresent()){
            throw new RuntimeException("El dni ya se encuentra registrado");
        }
        return userRepository.save(user);
    }

    public Optional<UserEntity> findById (Long id){
        return userRepository.findById(id);
    }

    public Page<UserEntity> listAllPage(){
        Pageable pageable = PageRequest.of(page, size);
        Page<UserEntity> entityPage;
        return  entityPage = userRepository.findAll(pageable);
    }

    public List<UserEntity> listAll(){
        return userRepository.findAll();
    }

    public void delete (Long userID){
        userRepository.deleteById(userID);
    }

}
