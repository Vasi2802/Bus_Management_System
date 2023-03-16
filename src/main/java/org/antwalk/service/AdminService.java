package org.antwalk.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.antwalk.entity.Admin;
import org.antwalk.entity.BookingDetails;
import org.antwalk.entity.Bus;
import org.antwalk.entity.Employee;
import org.antwalk.entity.Route;
import org.antwalk.entity.WaitingList;
import org.antwalk.repository.AdminRepo;
import org.antwalk.repository.ArrivalTimeRepo;
import org.antwalk.repository.BookingDetailsRepo;
import org.antwalk.repository.BusRepo;
import org.antwalk.repository.EmployeeRepo;
import org.antwalk.repository.RouteRepo;
import org.antwalk.repository.UserRepo;
import org.antwalk.repository.WaitingListRepo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

	@Autowired
	private AdminRepo adminRepo;

	@Autowired
	private BookingDetailsService bookingDetailsService;


	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private BusRepo busRepo;
	
	@Autowired
	private WaitingListRepo waitingListRepo;

	@Autowired
	private RouteRepo routeRepo;

	@Autowired
	private ArrivalTimeRepo arrivalTimeRepo;

	@Autowired
	private BookingDetailsRepo bookingDetailsRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private EmployeeRepo employeeRepo;public Admin insertAdmin(Admin a) {
		return adminRepo.save(a);
	}

	public List<Admin> getAllAdmin() {
		return adminRepo.findAll();
	}

	public Admin getAdminById(long id) {
		return adminRepo.findById(id).get();
	}

	public String deleteAdminById(long id) {
		adminRepo.deleteById(id);
		return "Admin Deleted";
	}

	public String updateAdminById(Admin a, long id) {
		List<Admin> adminList = adminRepo.findAll();
		for (Admin obj : adminList) {
			if (obj.getAid() == id) {
				if (a.getAid() == id) {
					adminRepo.save(a);
					return "Admin Updated";
				}

				else {
					return "Admin exists but your input id does not match with the existing Admin id";
				}

			}
		}
		return "Admin does not exist";

	}

	public ResponseEntity<Resource> generateReport() {

		LocalDate today = LocalDate.now();

		List<BookingDetails> bookingDetailsList = bookingDetailsService
				.getAllBookingDetailsForPeriod(today.withDayOfMonth(1), today.withDayOfMonth(today.lengthOfMonth()));

		Workbook workbook = new XSSFWorkbook();

		Sheet sheet = workbook.createSheet("Bookings_in_" + today.getMonth().name());
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 4000);
		sheet.setColumnWidth(2, 10000);
		sheet.setColumnWidth(3, 2000);
		sheet.setColumnWidth(4, 10000);

		// =================================== Header Creation =======================================

		{
			Row header = sheet.createRow(0);

			CellStyle headerStyle = workbook.createCellStyle();
			// headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());	
			// headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerStyle.setWrapText(true);

			XSSFFont font = ((XSSFWorkbook) workbook).createFont();
			font.setFontName("Arial");
			font.setFontHeightInPoints((short) 12);
			font.setBold(true);
			headerStyle.setFont(font);

			Cell headerCell = header.createCell(0);
			headerCell.setCellValue("Booking ID");
			headerCell.setCellStyle(headerStyle);

			headerCell = header.createCell(1);
			headerCell.setCellValue("Employee ID");
			headerCell.setCellStyle(headerStyle);

			headerCell = header.createCell(2);
			headerCell.setCellValue("Employee Name");
			headerCell.setCellStyle(headerStyle);

			headerCell = header.createCell(3);
			headerCell.setCellValue("Bus ID");
			headerCell.setCellStyle(headerStyle);

			headerCell = header.createCell(4);
			headerCell.setCellValue("Booking Date");
			headerCell.setCellStyle(headerStyle);
		}

		// ======================= TABLE CREATION ================================

		int rowNum = 2;
		for (BookingDetails bookingDetails : bookingDetailsList) {
			CellStyle style = workbook.createCellStyle();
			style.setWrapText(false);

			Row row = sheet.createRow(rowNum);

			Cell cell = row.createCell(0);
			cell.setCellValue(bookingDetails.getBookingId());
			cell.setCellStyle(style);

			cell = row.createCell(1);
			cell.setCellValue(bookingDetails.getE().getEid());
			cell.setCellStyle(style);

			cell = row.createCell(2);
			cell.setCellValue(bookingDetails.getE().getName());
			cell.setCellStyle(style);

			cell = row.createCell(3);
			cell.setCellValue(bookingDetails.getB().getBid());
			cell.setCellStyle(style);

			cell = row.createCell(4);
			cell.setCellValue(bookingDetails.getBookingForMonth().toString());
			cell.setCellStyle(style);

			rowNum += 1;
		}

		String fileName = "Bookings_in_" + today.getMonth().name() + ".xlsx";

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			workbook.write(outputStream);
			workbook.close();
		} catch (IOException e) {
			System.out.println("IO EXCEPTION");
		}

		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

		InputStreamResource file = new InputStreamResource(inputStream);


		
		return ResponseEntity.ok()
		.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
		// .contentLength(file.length())
		.contentType(MediaType.APPLICATION_OCTET_STREAM)
		.body(file);
		
	}

	public String deleteEmployee(long employeeId){
		String message= "";
		Optional<Employee> employeeOptional = employeeRepo.findById(employeeId);
		if(employeeOptional.isPresent()){
			Employee employee = employeeOptional.get();
			
			// removes booking or waiting
			employeeService.removeBooking(employee.getEid()); 
			message += "Booking/Waiting removed";

			// delete booking details associated with this employee
			bookingDetailsRepo.deleteByE(employee);

			// delete user associated with this employee
			userRepo.delete(employee.getUser());

			// delete employee 
			employeeRepo.deleteById(employee.getEid());
			message += "employee deleted";

		}
		else
			message = "employee not found";
		return message;
	}

    public String deleteBus(long busId) {
		Optional<Bus> busOptional = busRepo.findById(busId);
		String message = "";
		if(busOptional.isPresent()){
			Bus bus = busOptional.get();
			
			// Remove all waitingList Entry associated with this bus
			waitingListRepo.deleteAllByB(bus);
			message += "waitLists Removed\n";
			
			// Remove All Employee's booking associated with this bus
			List<Employee> employees = employeeRepo.findAllByB(bus);
			for(Employee employee: employees){
				employeeService.removeBooking(employee.getEid());
			}
			message += "employees' bus removed \n";

			// Remove Booking Details associated with this bus
			bookingDetailsRepo.deleteByB(bus);
			message += "booking details associated with this bus deleted\n";

		}
		else{
			message = "bus does not exist";
		}
        return message;
    }

    public String deleteRoute(long routeId) {
		String message="";
		Optional<Route> routeOptional = routeRepo.findById(routeId);
		if(routeOptional.isPresent()){
			Route route = routeOptional.get();
			
			// remove buses associated with the route
			busRepo.deleteByR(route);
			message += "buses removed += \n";

			// remove time table entries associated with this route
			arrivalTimeRepo.deleteByRouteStopId_Route(route);
			message += "time table associated deleted";

		}
		else{
			message = "Route Id Invalid";
		}
        return message;
    }
}
