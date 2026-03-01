package de.schoolinventorymanagement.service.subject;

import de.schoolinventorymanagement.daos.SubjectDao;
import de.schoolinventorymanagement.entities.SubjectEntity;
import de.schoolinventorymanagement.service.IPageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;

@Service
public class SubjectService implements IPageService<SubjectEntity, SubjectRequest> {

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

    @Transactional
    public SubjectEntity create(SubjectEntity entity) {
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

    public List<SubjectEntity> getFilteredAsList(SubjectRequest request) {
        List<SubjectEntity> list = dao.findAll();

        String name = request.getName().isEmpty() ? null : request.getName();
        String abbreviation = request.getAbbreviation().isEmpty() ? null : request.getAbbreviation();
        list = list.stream()
                .filter(e -> name == null || e.getName().equals(name))
                .filter(e -> abbreviation == null || e.getAbbreviation().equals(abbreviation))
                .toList();
        return list;
    }

    @Override
    public void writeToCsv(List<SubjectEntity> entities, PrintWriter writer) {
        writer.println("Name,Abkürzung");

        for (SubjectEntity e : entities) {
            String sb = e.getName() + "," +
                    e.getAbbreviation();

            writer.println(sb);
        }
    }
}
