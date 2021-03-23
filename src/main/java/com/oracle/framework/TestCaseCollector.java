package com.oracle.framework;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//Code For Getting The List Of TC's From Excel
public class TestCaseCollector {
	static List<String> testProjectPackages;
	private List<String> testCasesToRun = new ArrayList<>();

	private static TestCaseCollector instance = null;

	private TestCaseCollector() {

	}

	public static TestCaseCollector getInstance() {
		if (instance == null) {
			instance = new TestCaseCollector();
		}
		return instance;
	}

	protected Class<?> getTestClassPackage(String testcaseName) throws IOException {
		testProjectPackages = new ArrayList<String>();
		String ROOT = System.getProperty("user.dir") + "\\src";
		FileVisitor<Path> fileProcessor = new ProcessFile();
		Files.walkFileTree(Paths.get(ROOT), fileProcessor);

		for (String name : testProjectPackages) {
			if (name.contains("src")) {
				String[] temp = name.split("\\\\");
				int i = 0, j = 0;
				String folderName = "";
				for (i = 0; i < temp.length; i++) {
					if (temp[i].equals("src")) {
						for (j = i + 1; j < temp.length; j++) {
							folderName += temp[j] + ".";
							try {
								Class<?> c = Class.forName(folderName + testcaseName);
								return c;
							} catch (ClassNotFoundException cnf) {
								continue;
							}
						}
					} else {
						continue;
					}
				}
			}
		}
		return null;
	}

	private final class ProcessFile extends SimpleFileVisitor<Path> {
		@Override
		public FileVisitResult preVisitDirectory(Path aDir, BasicFileAttributes aAttrs) throws IOException {
			testProjectPackages.add(aDir.toString());
			return FileVisitResult.CONTINUE;
		}
	}

	public Class<?>[] getTestClasses() throws Exception {
		InputStream input;
		XSSFWorkbook wb;
		XSSFSheet sheet;
		Iterator<Row> rows;
		Class<?>[] testCasesTobeExecuted;
		List<String> testCases = new ArrayList<String>();
		int counter = 0;
		input = new BufferedInputStream(new FileInputStream("./RegressionSuite/RegressionSuite.xlsx"));
		wb = new XSSFWorkbook(OPCPackage.open(input));
		sheet = wb.getSheetAt(0);
		rows = sheet.rowIterator();
		while (rows.hasNext()) {
			XSSFRow row = (XSSFRow) rows.next();
			if (counter == 0) {
				counter = 1;
				continue;
			} else {
				if (null == row.getCell(0))
					continue;
				// testCases.add(row.getCell(0).toString().trim());
				if (row.getCell(1).toString().equalsIgnoreCase("Yes"))

					testCases.add(row.getCell(0).toString().trim());
				continue;
			}
		}
		wb.close();
		setTestCasesList(testCases);
		testCasesTobeExecuted = new Class<?>[testCases.size()];
		int casesCount = 0;
		for (String testCase : testCases) {
			if (testCase.endsWith(".java")) {
				testCase = FilenameUtils.removeExtension(testCase);
				System.out.println(testCase);
			}
			testCasesTobeExecuted[casesCount] = getTestClassPackage(testCase);
			casesCount++;
		}
		return testCasesTobeExecuted;
	}

	protected void setTestCasesList(List<String> casesToRun) {
		this.testCasesToRun = casesToRun;
	}

	protected List<String> getTestCasesList() {
		return this.testCasesToRun;
	}
}
