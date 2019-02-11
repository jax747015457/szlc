package com.stylefeng.guns.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * Excel报表导出工具类
 */
public class ExcelExportUtil {
	
	/**
	 * 简单模板excel导出功能
	 * @param fileName 报表文件名
	 * @param sheetName 报表表名
	 * @param dataList 报表数据（包含行头和列内容）
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void easySheet(String fileName, String sheetName, List<List<String>> dataList, HttpServletRequest request, HttpServletResponse response) throws IOException {
		//设置响应头，输出文件
		setResponseHeader(response, fileName);
		
		HSSFWorkbook  workbook = new HSSFWorkbook();
		Sheet sheet = null;
		
		HSSFCellStyle style = workbook.createCellStyle();
		// 创建字体对象
        Font ztFont = workbook.createFont();   
        ztFont.setItalic(true);                     // 设置字体为斜体字   
        ztFont.setColor(Font.COLOR_RED);            // 将字体设置为“红色”   
        ztFont.setFontHeightInPoints((short)22);    // 将字体大小设置为18px   
        ztFont.setFontName("华文行楷");             // 将“华文行楷”字体应用到当前单元格上   
        ztFont.setUnderline(Font.U_DOUBLE);         // 添加（Font.U_SINGLE单条下划线/Font.U_DOUBLE双条下划线）   
        style.setFont(ztFont);                    // 将字体应用到样式上面   
        
        // 设置单元格边框样式 
        style.setBorderBottom(CellStyle.BORDER_THICK);   
        style.setBorderTop(CellStyle.BORDER_DASHED);   
        style.setBorderLeft(CellStyle.BORDER_DOUBLE);   
        style.setBorderRight(CellStyle.BORDER_THIN);   
           
        // 设置单元格边框颜色   
        style.setBottomBorderColor(HSSFColor.ORANGE.index);   
        style.setTopBorderColor(HSSFColor.ORANGE.index);   
        style.setLeftBorderColor(HSSFColor.ORANGE.index);
		
	    style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    
	    // 设置单元格内容垂直对其方式
	    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		sheet = workbook.createSheet(sheetName);//设置表明
	    sheet.setDefaultColumnWidth(15);//setColumnWidth设置cell的宽度 
	    sheet.setDefaultRowHeightInPoints(20);
	    
	    //填充报表数据
	    for (int y = 0; y < dataList.size(); y++) {
	    	List<String> cellList = dataList.get(y);
	    	Row row = sheet.createRow(y);
	    	for (int x = 0; x < cellList.size(); x++) {
				row.createCell(x).setCellValue(cellList.get(x));
			}
		}

		OutputStream outStream = response.getOutputStream();
		try {
			workbook.write(outStream);  
		} catch (IOException e) { 
			e.printStackTrace(); 
		}
		outStream.flush();  
		outStream.close();
	}
	
	/**
	 * 复杂杂模板excel导出（合并单元格、设置表格样式等）
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void carSheet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HSSFWorkbook  workbook = new HSSFWorkbook();
		Sheet sheet = null;
		HSSFCellStyle style = workbook.createCellStyle();
		/** 创建字体对象   */
        Font ztFont = workbook.createFont();   
        ztFont.setItalic(true);                     // 设置字体为斜体字   
        ztFont.setColor(Font.COLOR_RED);            // 将字体设置为“红色”   
        ztFont.setFontHeightInPoints((short)22);    // 将字体大小设置为18px   
        ztFont.setFontName("华文行楷");             // 将“华文行楷”字体应用到当前单元格上   
        ztFont.setUnderline(Font.U_DOUBLE);         // 添加（Font.U_SINGLE单条下划线/Font.U_DOUBLE双条下划线）   
        //ztFont.setStrikeout(true);                  // 是否添加删除线   
        style.setFont(ztFont);                    // 将字体应用到样式上面   
        //ztCell.setCellStyle(ztStyle);               // 样式应用到该单元格上
        
        /** 设置单元格边框样式   */
        // CellStyle.BORDER_DOUBLE      双边线   
        // CellStyle.BORDER_THIN        细边线   
        // CellStyle.BORDER_MEDIUM      中等边线   
        // CellStyle.BORDER_DASHED      虚线边线   
        // CellStyle.BORDER_HAIR        小圆点虚线边线   
        // CellStyle.BORDER_THICK       粗边线   
        style.setBorderBottom(CellStyle.BORDER_THICK);   
        style.setBorderTop(CellStyle.BORDER_DASHED);   
        style.setBorderLeft(CellStyle.BORDER_DOUBLE);   
        style.setBorderRight(CellStyle.BORDER_THIN);   
           
        // 设置单元格边框颜色   
        style.setBottomBorderColor(HSSFColor.ORANGE.index);   
        style.setTopBorderColor(HSSFColor.ORANGE.index);   
        style.setLeftBorderColor(HSSFColor.ORANGE.index);
		
	    style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// 设置单元格内容水平对其方式   
        // XSSFCellStyle.ALIGN_CENTER       居中对齐   
        // XSSFCellStyle.ALIGN_LEFT         左对齐   
        // XSSFCellStyle.ALIGN_RIGHT        右对齐 
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    // 设置单元格内容垂直对其方式
	    // XSSFCellStyle.VERTICAL_TOP       上对齐
	    // XSSFCellStyle.VERTICAL_CENTER    中对齐
	    // XSSFCellStyle.VERTICAL_BOTTOM    下对齐 
	    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		sheet = workbook.createSheet("测试数据导出");
	    sheet.setDefaultColumnWidth(14);//setColumnWidth设置cell的宽度 
	    sheet.setDefaultRowHeightInPoints(20);
		//sheet.setColumnWidth(0, 12 * 256);//setColumnWidth设置cell的宽度 
		Row row = sheet.createRow(0);
		//heightInPoints 设置的值永远是height属性值的20倍  
		row.setHeightInPoints(20);
		row.createCell(0).setCellValue("用户代码");
			//合并单元格 （起始行，结束行，起始列，结束列）	
			sheet.addMergedRegion(new CellRangeAddress(0,(short)1,0,(short)(0)));
		row.createCell(1).setCellValue("用户名");
			sheet.addMergedRegion(new CellRangeAddress(0,(short)1,1,(short)(1)));
		row.createCell(2).setCellValue("资金总额");
			sheet.addMergedRegion(new CellRangeAddress(0,(short)1,2,(short)(2)));
		row.createCell(3).setCellValue("可用金额");  
			sheet.addMergedRegion(new CellRangeAddress(0,(short)1,3,(short)(3)));
		row.createCell(4).setCellValue("冻结");
			sheet.addMergedRegion(new CellRangeAddress(0,(short)1,4,(short)(4)));
		row.createCell(5).setCellValue("收入");
			sheet.addMergedRegion(new CellRangeAddress(0,(short)0,5,(short)(11)));
		row.createCell(12).setCellValue("待收");
		sheet.addMergedRegion(new CellRangeAddress(0,(short)0,12,(short)(14)));
		Row row1 = sheet.createRow(1);
		row1.setHeightInPoints(20);
		row1.createCell(5).setCellValue("总计");
		row1.createCell(6).setCellValue("本金");
		row1.createCell(7).setCellValue("利息");
		row1.createCell(8).setCellValue("提前还款罚息");
		row1.createCell(9).setCellValue("逾期罚息");
		row1.createCell(10).setCellValue("奖励");
		row1.createCell(11).setCellValue("活动");
		row1.createCell(12).setCellValue("总计");
		row1.createCell(13).setCellValue("本金");
		row1.createCell(14).setCellValue("利息");
		row = sheet.createRow(2);
		//heightInPoints 设置的值永远是height属性值的20倍  
		row.setHeightInPoints(20);
		row.createCell(0).setCellValue("测试");
		row.createCell(1).setCellValue("测试数据");
		row.createCell(2).setCellValue(Double.parseDouble("7599.68"));
		row.createCell(3).setCellValue(Double.parseDouble("8599.68"));
		row.createCell(4).setCellValue(Double.parseDouble("9599.68"));
		row.createCell(5).setCellValue(Double.parseDouble("9699.68"));
		row.createCell(6).setCellValue(Double.parseDouble("9799.68"));
		row.createCell(7).setCellValue(Double.parseDouble("9899.68"));
		row.createCell(8).setCellValue(Double.parseDouble("9999.68"));
		row.createCell(9).setCellValue(Double.parseDouble("17599.68"));
		row.createCell(10).setCellValue(Double.parseDouble("12599.68"));
		row.createCell(11).setCellValue(Double.parseDouble("17599.68"));
		row.createCell(12).setCellValue(Double.parseDouble("17699.68"));
		row.createCell(13).setCellValue(Double.parseDouble("17799.68"));
		row.createCell(14).setCellValue(Double.parseDouble("17899.68"));
		row = sheet.createRow(3);
		row.setHeightInPoints(20);
		row.createCell(0).setCellValue("测试2");
		row.createCell(1).setCellValue("测试数据3");
		row.createCell(2).setCellValue(Double.parseDouble("7599.61"));
		row.createCell(3).setCellValue(Double.parseDouble("8599.62"));
		row.createCell(4).setCellValue(Double.parseDouble("9599.63"));
		row.createCell(5).setCellValue(Double.parseDouble("9699.64"));
		row.createCell(6).setCellValue(Double.parseDouble("9799.65"));
		row.createCell(7).setCellValue(Double.parseDouble("9899.66"));
		row.createCell(8).setCellValue(Double.parseDouble("9999.67"));
		row.createCell(9).setCellValue(Double.parseDouble("17599.88"));
		row.createCell(10).setCellValue(Double.parseDouble("12599.69"));
		row.createCell(11).setCellValue(Double.parseDouble("17599.70"));
		row.createCell(12).setCellValue(Double.parseDouble("17699.71"));
		row.createCell(13).setCellValue(Double.parseDouble("17799.72"));
		row.createCell(14).setCellValue(Double.parseDouble("17899.73"));
		OutputStream outStream = new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("/resource/xls/finance.xls")));
		try {
			workbook.write(outStream);  
		} catch (IOException e) { 
			e.printStackTrace(); 
		}
		outStream.flush();  
		outStream.close();
		/*int z = 2;
		DecimalFormat df = new DecimalFormat("#,##0.00");
		for (Iterator<Object> it = objPageView.getRecords().iterator(); it.hasNext();) {
			row = sheet.createRow(z);
			Object[] obj = (Object[])it.next();
			row.createCell(0).setCellValue(obj[2] != null ? obj[2].toString() : null);
			row.createCell(1).setCellValue(obj[1] != null ? obj[1].toString() : null);
			row.createCell(2).setCellValue(df.format(Double.parseDouble(obj[4].toString())+Double.parseDouble(obj[11].toString())+Double.parseDouble(obj[12].toString())));
			row.createCell(3).setCellValue(df.format(Double.parseDouble(obj[4].toString())));
			row.createCell(4).setCellValue(df.format(Double.parseDouble(obj[11].toString())));
			row.createCell(5).setCellValue(df.format(Double.parseDouble(obj[6].toString())));
			row.createCell(6).setCellValue(df.format(Double.parseDouble(obj[7].toString())));
			row.createCell(7).setCellValue(df.format(Double.parseDouble(obj[8].toString())));
			row.createCell(8).setCellValue(df.format(Double.parseDouble(obj[15].toString())));
			row.createCell(9).setCellValue(df.format(Double.parseDouble(obj[16].toString())));
			row.createCell(10).setCellValue(df.format(Double.parseDouble(obj[9].toString())));
			row.createCell(11).setCellValue(df.format(Double.parseDouble(obj[10].toString())));
			row.createCell(12).setCellValue(df.format(Double.parseDouble(obj[12].toString())));
			row.createCell(13).setCellValue(df.format(Double.parseDouble(obj[13].toString())));
			row.createCell(14).setCellValue(df.format(Double.parseDouble(obj[14].toString())));
			z++;
		}
			row = sheet.createRow(z);
			row.createCell(1).setCellValue("总计");
			row.createCell(2).setCellValue(df.format(fundsAccountTotal.getTotal()));
			row.createCell(3).setCellValue(df.format(fundsAccountTotal.getBalance()));
			row.createCell(4).setCellValue(df.format(fundsAccountTotal.getFrost()));
			row.createCell(5).setCellValue(df.format(fundsAccountTotal.getIncome()));
			row.createCell(6).setCellValue(df.format(fundsAccountTotal.getIncomeCapital()));
			row.createCell(7).setCellValue(df.format(fundsAccountTotal.getIncomeInterest()));
			row.createCell(8).setCellValue(df.format(fundsAccountTotal.getIncomePrepayment()));
			row.createCell(9).setCellValue(df.format(fundsAccountTotal.getIncomeOverdue()));
			row.createCell(10).setCellValue(df.format(fundsAccountTotal.getIncomeAward()));
			row.createCell(11).setCellValue(df.format(fundsAccountTotal.getIncomeActivity()));
			row.createCell(12).setCellValue(df.format(fundsAccountTotal.getAwait()));
			row.createCell(13).setCellValue(df.format(fundsAccountTotal.getAwaitCapital()));
			row.createCell(14).setCellValue(df.format(fundsAccountTotal.getAwaitInterest()));*/
	}
	
	/**
	 * 设置响应头 
	 * @param response
	 */
	public static void setResponseHeader(HttpServletResponse response,String excelName) {
		response.setContentType("application/octet-stream;charset=ISO8859-1");
		try {                                                                                                
			excelName = new String(excelName.getBytes("gb2312"),"ISO8859-1");
			response.setHeader("Content-Disposition", "attachment;filename="+excelName+URLEncoder.encode(".xls", "ISO8859-1"));
		} catch (Exception e) {   
			e.printStackTrace();
		}
	}
}
