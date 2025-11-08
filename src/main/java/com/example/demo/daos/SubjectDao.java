package com.example.demo.daos;

import com.example.demo.entities.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectDao extends JpaRepository<SubjectEntity, String> {
}
