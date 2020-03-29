package com.davsan.simplechat.repository;

import com.davsan.simplechat.model.Provider;
import com.davsan.simplechat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByProviderIdAndProvider(String providerId, Provider provider);

    List<User> findByNameContainingIgnoreCase(String search);
}
