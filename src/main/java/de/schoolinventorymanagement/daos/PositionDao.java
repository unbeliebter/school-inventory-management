package de.schoolinventorymanagement.daos;

import de.schoolinventorymanagement.entities.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionDao extends JpaRepository<PositionEntity, String> {
}
