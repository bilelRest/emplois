package org.iset.emplois.security.repositories;

import java.util.List;
import java.util.Optional;
 
import org.iset.emplois.security.entities.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {
    Optional<AppUser> findByUsername(String username);

    

       @SuppressWarnings("null")
       Page<AppUser> findAll(Pageable pageable);

 
    @Query("SELECT m FROM AppUser m WHERE " +
           "(:username IS NULL OR LOWER(m.username) LIKE LOWER(CONCAT('%', :username, '%'))) AND " +
           "(:email IS NULL OR LOWER(m.email) LIKE LOWER(CONCAT('%', :email, '%')))")
    List<AppUser> findByNomfrOrEmail(@Param("username") String username, @Param("email") String email);


    @Query("SELECT m FROM AppUser m WHERE " +
           "(:username IS NULL OR LOWER(m.username) LIKE LOWER(CONCAT('%', :username, '%'))) AND " +
           "(:email IS NULL OR LOWER(m.email) LIKE LOWER(CONCAT('%', :email, '%')))")
    Page<AppUser> searchusers(
            @Param("username") String username,
            @Param("email") String email,
            Pageable pageable);
}

