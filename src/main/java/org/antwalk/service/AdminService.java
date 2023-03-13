package org.antwalk.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.antwalk.entity.Admin;
import org.antwalk.entity.BookingDetails;
import org.antwalk.repository.AdminRepo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AdminService {

	@Autowired
	private AdminRepo adminRepo;

	@Autowired
	private BookingDetailsService bookingDetailsService;

	public Admin insertAdmin(Admin a) {
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

	public ResponseEntity<byte[]> generateReport() {

		LocalDate today = LocalDate.now();

		List<BookingDetails> bookingDetailsList = bookingDetailsService
				.getAllBookingDetailsForPeriod(today.withDayOfMonth(1), today.withDayOfMonth(today.lengthOfMonth()));

		Workbook workbook = new XSSFWorkbook();

		Sheet sheet = workbook.createSheet("Bookings_in_" + today.getMonth().name());
		sheet.setColumnWidth(0, 6000);
		sheet.setColumnWidth(1, 4000);

		// Header Creation

		{
			Row header = sheet.createRow(0);

			// CellStyle headerStyle = workbook.createCellStyle();
			// headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
			// headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			// XSSFFont font = ((XSSFWorkbook) workbook).createFont();
			// font.setFontName("Arial");
			// font.setFontHeightInPoints((short) 16);
			// font.setBold(true);
			// headerStyle.setFont(font);

			Cell headerCell = header.createCell(0);
			headerCell.setCellValue("Booking ID");
			// headerCell.setCellStyle(headerStyle);

			headerCell = header.createCell(1);
			headerCell.setCellValue("Employee ID");
			// headerCell.setCellStyle(headerStyle);

			headerCell = header.createCell(2);
			headerCell.setCellValue("Employee Name");
			// headerCell.setCellStyle(headerStyle);

			headerCell = header.createCell(3);
			headerCell.setCellValue("Bus ID");
			// headerCell.setCellStyle(headerStyle);

			headerCell = header.createCell(4);
			headerCell.setCellValue("Booking Date");
			// headerCell.setCellStyle(headerStyle);
		}

		// Table CReation

		int rowNum = 2;
		for (BookingDetails bookingDetails : bookingDetailsList) {
			CellStyle style = workbook.createCellStyle();
			style.setWrapText(true);

			Row row = sheet.createRow(rowNum);

			Cell cell = row.createCell(0);
			cell.setCellValue(bookingDetails.getBookingId());
			// cell.setCellStyle(style);

			cell = row.createCell(1);
			cell.setCellValue(bookingDetails.getE().getEid());
			// cell.setCellStyle(style);

			cell = row.createCell(2);
			cell.setCellValue(bookingDetails.getE().getName());
			// cell.setCellStyle(style);

			cell = row.createCell(3);
			cell.setCellValue(bookingDetails.getB().getBid());
			// cell.setCellStyle(style);

			cell = row.createCell(4);
			cell.setCellValue(bookingDetails.getBookingForMonth().toString());
			// cell.setCellStyle(style);

			rowNum += 1;
		}

		File file = new File("files\\reports" + "trial1.xlsx");

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=trial1.xlsx");
		// header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		// header.add("Pragma", "no-cache");
		// header.add("Expires", "0");

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			workbook.write(outputStream);
			outputStream.close();
			workbook.close();
		} catch (IOException e) {
			System.out.println("IO EXCEPTION");
		}

		return ResponseEntity.ok()
				.headers(header)
				.contentLength(file.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(outputStream.toByteArray());

	}
}
