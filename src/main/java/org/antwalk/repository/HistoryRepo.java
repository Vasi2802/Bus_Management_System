package org.antwalk.repository;

import java.time.LocalDate;
import java.util.List;

import org.antwalk.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepo  extends JpaRepository<History, Long>{

    List<History> findAllByReceiptDateGreaterThanEqualOrderByReceiptDate(LocalDate januaryFirst);
    
}
