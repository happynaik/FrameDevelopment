package com.oracle.framework;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.invoke.MethodHandles;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataProvider {
	private String path;
	private FileInputStream fis = null;
	private FileOutputStream fos = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;
	static final Logger log = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public DataProvider(String path) {

		this.path = path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			log.info(e);
		}

	}

	// returns the row count in a sheet
	public int getRowCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1) {
			return 0;
		} else {
			sheet = workbook.getSheetAt(index);
			return sheet.getLastRowNum() + 1;
		}
	}

	// returns the Column count in a sheet
	public int getColCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1) {
			return 0;
		} else {
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(0);
			return row.getLastCellNum();
		}

	}

	// Read Excel Data Based On Column Name
	public String readDataFromExcel(String sheetName, String colName, int rowNum) {
		try {
			int colNum = 0;
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
					colNum = i;
					break;
				}
			}
			row = sheet.getRow(rowNum);
			cell = row.getCell(colNum);

			if (cell.getCellTypeEnum() == CellType.STRING) {
				return cell.getStringCellValue();
			} else if (cell.getCellTypeEnum() == CellType.NUMERIC || cell.getCellTypeEnum() == CellType.FORMULA) {
				String cellValue = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					DateFormat df = new SimpleDateFormat("dd/MM/yy");
					Date date = cell.getDateCellValue();
					cellValue = df.format(date);
				}
				return cellValue;
			} else if (cell.getCellTypeEnum() == CellType.BLANK) {
				return "";
			} else {
				return String.valueOf(cell.getBooleanCellValue());
			}
		} catch (Exception e) {
			log.info(e);
		}
		return cell.getStringCellValue();
	}

	// Write Data To Excel Based On Column Name
	public boolean writeDataToExcel(String sheetName, String colName, int rowNum, String value) {
		try {
			int colNum = 0;
			sheet = workbook.getSheet(sheetName);

			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName)) {
					colNum = i;
				}
			}
			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum);
			if (row == null) {
				row = sheet.createRow(rowNum);
				cell = row.getCell(colNum);
			}
			/* if (cell == null) { */
			cell = row.createCell(colNum);
			cell.setCellValue(value);
			/* } */
			fos = new FileOutputStream(path);
			workbook.write(fos);
			fos.close();
		} catch (Exception e) {
			log.info(e);
			return false;
		}
		return true;
	}
}
