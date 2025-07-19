package org.iset.emplois.service;

import java.util.List;
import java.util.Optional;     
import org.iset.emplois.security.entities.AppUser;
import org.iset.emplois.security.repositories.AppUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
private final AppUserRepository  repository;

    public UserService(AppUserRepository repository) {
        this.repository = repository;
    }
 public Page<AppUser> findusers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

     public List<AppUser> findAll() {
        return repository.findAll();
    }
    public Page<AppUser> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<AppUser> findById(String id) {
        return repository.findById(id);
    }

    public AppUser save(AppUser user) {
        return repository.save(user);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }


    public List<AppUser> searchGrades(String username, String email) {
        return repository.findByNomfrOrEmail(username, email);
    }


    public Page<AppUser > searchusers(String username, String email, Pageable pageable) {
   
        return repository.searchusers(
            username != null ? username : "", 
            email != null ? email : "", 
                pageable);
    }

}
