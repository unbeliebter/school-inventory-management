package de.schoolinventorymanagement.service.user.services;

import de.schoolinventorymanagement.daos.UserDao;
import de.schoolinventorymanagement.daos.UserPasswordChangeDao;
import de.schoolinventorymanagement.entities.user.UserEntity;
import de.schoolinventorymanagement.service.IPageService;
import de.schoolinventorymanagement.service.user.UserRequest;
import de.schoolinventorymanagement.service.user.mapper.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;

@Service
public class UserService implements IPageService<UserEntity, UserRequest> {

    @Autowired
    private UserDao dao;
    @Autowired
    private UserMapper mapper;
    @Autowired
    private UserPasswordChangeDao changeDao;

    public List<UserEntity> getAll() {
        return dao.findAll();
    }

    @Transactional
    public UserEntity create(UserRequest request) {
        var entity = mapper.mapToEntityCreate(request, new UserEntity());
        return dao.save(entity);
    }

    @Transactional
    public UserEntity create(UserEntity entity) {
        return dao.save(entity);
    }

    public UserEntity getById(String id) {
        return dao.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(String id) {
        var toDelete = dao.findById(id);

        if (toDelete.isPresent()) {
            changeDao.deleteAllByUserId(toDelete.get());
            dao.deleteById(id);
        }
    }

    @Override
    public void writeToCsv(List<UserEntity> entities, PrintWriter writer) {
        writer.println("Nutzername,PW-Hash,E-Mail,VorName,NachName,Rolle");

        for (UserEntity e : entities) {
            String sb = e.getUsername() + "," +
                    e.getPassword() + "," +
                    e.getEmail() + "," +
                    e.getFirstname() + "," +
                    e.getLastname() + "," +
                    e.getRole().getFrontendName();

            writer.println(sb);
        }
    }

    @Override
    public List<UserEntity> getFilteredAsList(UserRequest request) {
        List<UserEntity> list = dao.findAll();

        String userName = request.getUsername().isEmpty() ? null : request.getUsername();
        String firstName = request.getFirstName().isEmpty() ? null : request.getFirstName();
        String lastName = request.getLastName().isEmpty() ? null : request.getLastName();
        String eMail = request.getEmail().isEmpty() ? null : request.getEmail();
        String roleId = request.getRole() == null ? null : request.getRole().getId();
        Boolean pwRequested = request.getRequiresPasswordReset();
        list = list.stream()
                .filter(e -> userName == null || e.getUsername().equals(userName))
                .filter(e -> firstName == null || e.getFirstname().equals(firstName))
                .filter(e -> lastName == null || e.getLastname().equals(lastName))
                .filter(e -> eMail == null || e.getEmail().equals(eMail))
                .filter(e -> roleId == null || e.getRole().getId().equals(roleId))
                .filter(e -> pwRequested == null || e.isRequiresPasswordReset() == pwRequested)
                .toList();
        return list;
    }

    public UserEntity findByUsername(String username) {
        return dao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("username can't be found"));
    }

    public UserEntity save(UserEntity user) {
        return dao.save(user);
    }
}
