package com.project.SpringClean.repository;

import com.project.SpringClean.model.CompanyCleaner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyCleanerRepository extends JpaRepository<CompanyCleaner, Long> {
}
