package org.antwalk.repository;

import org.antwalk.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepo  extends JpaRepository<History, Long>{
    
}
