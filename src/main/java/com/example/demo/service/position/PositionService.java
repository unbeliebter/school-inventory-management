package com.example.demo.service.position;

import com.example.demo.daos.PositionDao;
import com.example.demo.entities.PositionEntity;
import com.example.demo.service.IPageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;

@Service
public class PositionService implements IPageService<PositionEntity> {

    @Autowired
    private PositionDao dao;
    @Autowired
    private PositionMapper mapper;

    public List<PositionEntity> getAll() {
        return dao.findAll();
    }

    @Transactional
    public PositionEntity create(PostionRequest request) {
        var entity = mapper.mapToEntity(request, new PositionEntity());
        return dao.save(entity);
    }

    @Transactional
    public PositionEntity create(PositionEntity entity) {
        return dao.save(entity);
    }

    public PositionEntity getById(String id) {
        return dao.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(String id) {
        var toDelete = dao.findById(id);

        if (toDelete.isPresent()) {
            dao.deleteById(id);
        }
    }

    @Override
    public void writeToCsv(List<PositionEntity> entities, PrintWriter writer) {
        // TODO;
    }
}
