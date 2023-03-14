package org.antwalk.repository;

import org.antwalk.entity.GlobalDb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalRepo extends JpaRepository<GlobalDb, Long>{

}
