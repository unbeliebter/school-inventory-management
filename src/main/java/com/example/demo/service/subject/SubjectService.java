package com.example.demo.service.subject;

import com.example.demo.daos.SubjectDao;
import com.example.demo.entities.SubjectEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectDao dao;
    @Autowired
    private SubjectMapper mapper;

    public List<SubjectEntity> getAll() {
        return dao.findAll();
    }

    @Transactional
    public SubjectEntity create(SubjectRequest request) {
        var entity = mapper.mapToEntity(request, new SubjectEntity());
        return dao.save(entity);
    }

    public SubjectEntity getById(String id) {
        return dao.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(String id) {
        var toDelete = dao.findById(id);

        if (toDelete.isPresent()) {
            dao.deleteById(id);
        }
    }
}
