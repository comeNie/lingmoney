package com.mrbt.lingmoney.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	public static void copyRow(XSSFWorkbook workbook, XSSFSheet worksheet,
			int sourceRowNum, int destinationRowNum) {
		// Get the source / new row
		XSSFRow newRow = worksheet.getRow(destinationRowNum);
		XSSFRow sourceRow = worksheet.getRow(sourceRowNum);

		// If the row exist in destination, push down all rows by 1 else create
		// a new row
		if (newRow != null) {
			worksheet
					.shiftRows(destinationRowNum, worksheet.getLastRowNum(), 1);
		} else {
			newRow = worksheet.createRow(destinationRowNum);
		}

		// Loop through source columns to add to new row
		for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
			// Grab a copy of the old/new cell
			XSSFCell oldCell = sourceRow.getCell(i);
			XSSFCell newCell = newRow.createCell(i);

			// If the old cell is null jump to next cell
			if (oldCell == null) {
				newCell = null;
				continue;
			}

			// Copy style from old cell and apply to new cell
			XSSFCellStyle newCellStyle = workbook.createCellStyle();
			newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
			newCell.setCellStyle(newCellStyle);

			// If there is a cell comment, copy
			if (oldCell.getCellComment() != null) {
				newCell.setCellComment(oldCell.getCellComment());
			}

			// If there is a cell hyperlink, copy
			if (oldCell.getHyperlink() != null) {
				newCell.setHyperlink(oldCell.getHyperlink());
			}

			// Set the cell data type
			newCell.setCellType(oldCell.getCellType());

			// Set the cell data value
			switch (oldCell.getCellType()) {
			case Cell.CELL_TYPE_BLANK:
				newCell.setCellValue(oldCell.getStringCellValue());
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				newCell.setCellValue(oldCell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_ERROR:
				newCell.setCellErrorValue(oldCell.getErrorCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				newCell.setCellFormula(oldCell.getCellFormula());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				newCell.setCellValue(oldCell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_STRING:
				newCell.setCellValue(oldCell.getRichStringCellValue());
				break;
			}
		}
	}

	/**
	 * 设置数据的有效性
	 * 
	 * @param sheet
	 * @param firstRow
	 * @param lastRow
	 * @param firstCol
	 * @param lastCol
	 * @param explicitListValues
	 * @throws IllegalArgumentException
	 */
	public static void setValidationData(XSSFSheet sheet, int firstRow,
			int lastRow, int firstCol, int lastCol, String[] explicitListValues)
			throws IllegalArgumentException {
		if (firstRow < 0 || lastRow < 0 || firstCol < 0 || lastCol < 0
				|| lastRow < firstRow || lastCol < firstCol) {
			throw new IllegalArgumentException("Wrong Row or Column index : "
					+ firstRow + ":" + lastRow + ":" + firstCol + ":" + lastCol);
		}
		XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);

		XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
				.createExplicitListConstraint(explicitListValues);
		CellRangeAddressList addressList = new CellRangeAddressList(firstRow,
				lastRow, firstCol, lastCol);
		XSSFDataValidation validation = (XSSFDataValidation) dvHelper
				.createValidation(dvConstraint, addressList);
		validation.setSuppressDropDownArrow(true);
		validation.setShowErrorBox(true);
		sheet.addValidationData(validation);
	}
}
