package org.antwalk.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import org.antwalk.entity.Bus;
import org.antwalk.entity.Employee;
import org.antwalk.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;



@Service
public class EmailService {
 
    private final JavaMailSender emailSender;


    @Autowired
	private BusService busServices;

    @Autowired
	private DelayService delayServices;

    @Autowired
	private EmployeeRepo empRepo;

    @Autowired
	private EntityManager entityManager;


    private static List<HashMap<String,String>> buses;


    @Autowired
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }
 
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        // emailSender.send(message);

    }


    public void emailAllEmployee(long bus_id,String subject, String text){
        // List<String> emails = entityManager.createNativeQuery( "select user.username from user, employee where employee.auth_id = user.id and employee.bus_id = " + bus_id).getResultList();
        List<String> emails = new ArrayList<>();
        for(Employee emp : empRepo.findAllByB(busServices.getBusById(bus_id))){
            emails.add(emp.getUser().getUserName());
            
        }
        // System.out.println("-------------------------------------------------------------");
        for(String email: emails ){
            System.out.println("emailing " + email +"\n sub = " + subject + "\n msg = " + text);
            sendEmail(email,subject,text);
            System.out.println(" ");
        }
        // System.out.println("-------------------------------------------------------------");
    }


    public static String getDelayMessage1(Long bus_id, LocalTime startTime){
        return "Bus Update : bus {id: " + bus_id+ " } that was supposed to start at " + startTime + " has not started yet by the driver. sorry for inconvenience. \n -NRI Fintech Bus Mangament System"; 
    }

    public static String getDelaySubject(Long bus_id){
        return "Delay Status Update for bus {id: " + bus_id+ " } ";
    }

    public static String getDelayMessage2(Long bus_id, LocalTime startTime, LocalTime delayedTime){
        return "Bus Update : bus {id: " + bus_id+ " } that was supposed to start at " + startTime + " has started its journey at " + delayedTime +". sorry for inconvenience. \n -NRI Fintech Bus Mangament System";
    }

    // */15    07-19        *     * * 
    // @Scheduled(fixedRate = 50000)
    // public void delayEmailling(){
    //     if(this.buses == null){
    //         System.out.println("loading routes");
    //         EmailService.buses = busServices.getBusData();
    //         delayServices.flush();
    //     }  
    //     int nBus = EmailService.buses.size();
    //     LocalTime startTime;
    //     Long id;
    //     int status;
    //     String msg,sub;
    //     System.out.println("------------------------------------------" + LocalTime.now() + "------------------------------------------");
    //     System.out.println("no.of busses : " + nBus);
    //     for(int idx = nBus-1;idx >-1; idx--){
    //         startTime = LocalTime.parse(EmailService.buses.get(idx).get("startTime"));
    //         id = Long.valueOf(EmailService.buses.get(idx).get("id"));
    //         // if(startTime.compareTo(LocalTime.now()) > 0){  // this is the correct line 
    //         if(startTime.compareTo(LocalTime.parse("00:00:00")) > 0 ){ // this is line is just for testing , comment it out and use the above line 
    //             if(busServices.getActiveStatus(id) == "YES"){
    //                 EmailService.buses.remove(idx);
    //             }
    //             else{
    //                 System.out.print("mailling to employees of bus id = " + id);
    //                 System.out.println(" ");
    //                 emailAllEmployee(id, getDelaySubject(id), getDelayMessage1(id, startTime));
    //             }
    //         }
    //         // emailAllEmployee(id, getDelaySubject(id), getDelayMessage1(id, startTime));
    //         // System.out.println("id = " + id + "startTime= " +"active = "+ busServices.getActiveStatus(id));
    //     }
    //     System.out.println("------------------------------------------------------------------------------------------------------");
    // } 
}


//     @Scheduled(fixedRate = 5000)
//     public void delayEmailling(){
//         if(this.buses == null){
//             System.out.println("loading routes");
//             EmailService.buses = busServices.getBusData();
//         }  
//         int nBus = EmailService.buses.size();
//         LocalTime startTime;
//         Long id;
//         int status;
//         String msg,sub;
//         System.out.println("------------------------------------------" + LocalTime.now() + "------------------------------------------");
//         System.out.println("no.of busses : " + nBus);
//         for(int idx = nBus-1;idx >-1; idx--){
//             startTime = LocalTime.parse(EmailService.buses.get(idx).get("startTime"));
//             id = Long.valueOf(EmailService.buses.get(idx).get("id"));
//             status = Integer.parseInt(EmailService.buses.get(idx).get("status"));
//             System.out.println("id = " + id + "startTime= " + startTime + "status = " + status +"active = "+ busServices.getActiveStatus(id));
//             if(startTime.compareTo(LocalTime.now()) > 0  && status < 3){
//                 if(busServices.getActiveStatus(id) == "YES"){
//                     if(status == 2){
//                         sub = EmailService.getDelaySubject(id);
//                         msg = EmailService.getDelayMessage2(id, startTime, startTime); // change last one to delayed TIme
//                         emailAllEmployee(id,sub,msg);
//                     }
//                     EmailService.buses.remove(idx);
//                 }
//                 else{
//                     sub = EmailService.getDelaySubject(id);
//                     if(status == 1){
//                         msg = EmailService.getDelayMessage1(id, startTime);
//                         emailAllEmployee(id,sub,msg);
//                     }
//                     if(status == 2){
//                         msg = EmailService.getDelayMessage2(id, startTime, startTime); // change last one to delayed TIme
//                         emailAllEmployee(id,sub,msg);
//                     }
//                     EmailService.buses.get(idx).put("status",String.valueOf(status + 1));
//                 }
//             }
//             else{
//                 EmailService.buses.remove(idx);
//             }
//         }
//         System.out.println("------------------------------------------------------------------------------------------------------");
//     } 
// }
