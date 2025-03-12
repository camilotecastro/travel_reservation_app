package com.example.best_travel.domain.infrastucture.services;

import com.example.best_travel.domain.entities.Customer;
import com.example.best_travel.domain.infrastucture.abstractservices.ReportService;
import com.example.best_travel.domain.repositories.CustomerRepository;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class ExcelService implements ReportService {

  private static final String SHEET_NAME = "Customer total sales";
  private static final String FONT_TYPE = "Arial";
  private static final String COLUMN_CUSTOMER_ID = "id";
  private static final String COLUMN_CUSTOMER_NAME = "name";
  private static final String COLUMN_CUSTOMER_PURCHASES = "purchases";
  private static final String REPORTS_PATH_WITH_NAME = "reports/Sales-%s";
  private static final String REPORTS_PATH = "reports";
  private static final String FILE_TYPE = ".xlsx";
  private static final String FILE_NAME = "Sales-%s.xlsx";

  private final CustomerRepository customerRepository;

  @Override
  public byte[] readFile() {
    try {
      this.createReport();
      var path = Paths.get(REPORTS_PATH, String.format(FILE_NAME, LocalDate.now().getMonth()))
          .toAbsolutePath();
      return Files.readAllBytes(path);
    } catch (IOException e) {
      log.error("Cant read file {}", e.getMessage());
      throw new IllegalStateException();
    }
  }

  private void createReport() {
    log.info("Creating report");
    var workbook = new XSSFWorkbook();
    var sheet = workbook.createSheet(SHEET_NAME);

    sheet.setColumnWidth(0, 5000);
    sheet.setColumnWidth(1, 7000);
    sheet.setColumnWidth(2, 3000);

    var header = sheet.createRow(0);

    var font = workbook.createFont();
    font.setFontName(FONT_TYPE);
    font.setFontHeightInPoints((short) 16);
    font.setBold(true);

    var headerStyle = workbook.createCellStyle();
    headerStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    headerStyle.setFont(font);

    var headerCellId = header.createCell(0);
    headerCellId.setCellValue(COLUMN_CUSTOMER_ID);
    headerCellId.setCellStyle(headerStyle);

    var headerCellName = header.createCell(1);
    headerCellName.setCellValue(COLUMN_CUSTOMER_NAME);
    headerCellName.setCellStyle(headerStyle);

    var headerCellPurchases = header.createCell(2);
    headerCellPurchases.setCellValue(COLUMN_CUSTOMER_PURCHASES);
    headerCellPurchases.setCellStyle(headerStyle);

    var style = workbook.createCellStyle();
    style.setWrapText(true);

    var customers = this.customerRepository.findAll();
    var rowIndex = 1;

    for (var customer : customers) {
      var row = sheet.createRow(rowIndex);

      var cell = row.createCell(0);
      cell.setCellValue(customer.getDni());
      cell.setCellStyle(style);

      cell = row.createCell(1);
      cell.setCellValue(customer.getFullName());
      cell.setCellStyle(style);

      cell = row.createCell(2);
      cell.setCellValue(getTotalPurchase(customer));
      cell.setCellStyle(style);

      rowIndex++;
    }

    // Crear carpeta 'reports' si no existe
    File reportsDir = new File(REPORTS_PATH);
    if (!reportsDir.exists()) {
      reportsDir.mkdirs();
    }

    var report = new File(String.format(REPORTS_PATH_WITH_NAME, LocalDate.now().getMonth()));
    var path = report.getAbsolutePath();
    var fileLocation = path + FILE_TYPE;

    log.info("Archivo generado en: {}", fileLocation);

    try (var OutputStream = new FileOutputStream(fileLocation)) {
      workbook.write(OutputStream);
      workbook.close();
    } catch (IOException e) {
      log.error("Cant create report excel {}", e.getMessage());
      throw new IllegalStateException(e);
    }

  }

  private static Integer getTotalPurchase(Customer customer) {
    return customer.getTotalFlights() + customer.getTotalTours() + customer.getTotalLodgings();
  }


}
