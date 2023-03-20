package org.antwalk.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.antwalk.entity.History;
import org.antwalk.repository.HistoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepo historyRepo;

    public void add(History history) {
        historyRepo.save(history);
        System.out.println("\n---------------------------------------");
        System.out.println("History = " + history + "\n");
    }

    public List<History> getAllForPeriod(LocalDate firstDate, LocalDate lastdate) {
        firstDate = firstDate.minusDays(1);
		lastdate = lastdate.plusDays(1);
        List<History> historyList = new ArrayList<>();
        for(History history: historyRepo.findAll()){
            if(history.getReceiptDate().isAfter(firstDate) && 
            history.getReceiptDate().isBefore(lastdate)){
                historyList.add(history);
            }
        }
        
        
        return historyList;
    }

}
