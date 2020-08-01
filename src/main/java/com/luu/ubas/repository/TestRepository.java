package com.luu.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.luu.auth.domain.Test;

public interface TestRepository extends JpaRepository<Test, Long> {}
