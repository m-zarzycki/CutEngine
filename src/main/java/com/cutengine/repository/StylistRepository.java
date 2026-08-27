package com.cutengine.repository;

import com.cutengine.entity.Stylist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StylistRepository extends JpaRepository<Stylist, Long> {

    List<Stylist> findBySpecialization(String specialization);

    Optional<Stylist> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
