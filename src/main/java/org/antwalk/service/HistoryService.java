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

    public List<List<Object>> getBookingsPerMonthForCurrentYear() {
		LocalDate januaryFirst = LocalDate.now().withDayOfYear(1);
        List<History> historyList = historyRepo.findAllByReceiptDateGreaterThanEqualOrderByReceiptDate(januaryFirst);
		LocalDate curMonth;
		List<List<Object>> monthFreq = new ArrayList<>();
		int j = 0;
		
		for (int i = 1; i < 13; i++) {
			curMonth = LocalDate.now().withMonth(i);
			String monthName = curMonth.getMonth().name().substring(0, 3);
			int freq = 0;
			while(j<historyList.size()){
				History history = historyList.get(j);
                String transactionType = history.getTransactionType();
				if (history.getReceiptDate().getMonthValue()<=i) {
                    if(transactionType.equalsIgnoreCase("Booking remove") || transactionType.equalsIgnoreCase("Waitlist Remove")){
                        freq -= 1;
                    }
                    else{
                        freq += 1;

                    }
                        j += 1;
				}
				else{
					break;
				}
			}
			List<Object> nameAndFreq = new ArrayList<>();
			nameAndFreq.add(monthName);
			nameAndFreq.add(freq);
			monthFreq.add(nameAndFreq);
		}
		// System.out.println(monthFreq.toString());

		return monthFreq;
	}
}
