package com.fishpound.accountservice.service.tools;

import com.fishpound.accountservice.entity.OrderApply;
import com.fishpound.accountservice.entity.OrderList;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FileGenerator {
    public static void generateExcel(HttpServletResponse response, OrderApply orderApply, Boolean createSignPath)
            throws IOException
    {
        int n = 0, no = 1;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(orderApply.getApplyDate());
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("设备采购申请单");

        //样式
        HSSFCellStyle styleT = createStyle(workbook, (short)18, HSSFColor.HSSFColorPredefined.BLACK.getIndex(), true,
                HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false);
        HSSFCellStyle styleClb = createStyle(workbook, (short)11, HSSFColor.HSSFColorPredefined.BLACK.getIndex(), false,
                HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM, false);
        HSSFCellStyle styleCcc = createStyle(workbook, (short)11, HSSFColor.HSSFColorPredefined.BLACK.getIndex(), false,
                HorizontalAlignment.CENTER, VerticalAlignment.CENTER, true);
        HSSFCellStyle styleClcr = createStyle(workbook, (short)11, HSSFColor.HSSFColorPredefined.RED.getIndex(), true,
                HorizontalAlignment.LEFT, VerticalAlignment.CENTER, true);
        HSSFCellStyle styleCrb = createStyle(workbook, (short)11, HSSFColor.HSSFColorPredefined.BLACK.getIndex(), false,
                HorizontalAlignment.RIGHT, VerticalAlignment.BOTTOM, true);

        //第一行
        HSSFRow rowTitle = sheet.createRow(n++);
        rowTitle.setHeightInPoints(20);
        HSSFCell cellTitle = rowTitle.createCell(0);
        cellTitle.setCellValue(calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH)+1) + "月设备采购单");
        sheet.addMergedRegion(new CellRangeAddress(n-1, n-1, 0, 9));
        cellTitle.setCellStyle(styleT);
        //第二行
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        HSSFRow row1 = sheet.createRow(n++);
        row1.setHeightInPoints(21);
        HSSFCell cell1 = row1.createCell(0);
        cell1.setCellValue("编号：" + orderApply.getId() +
                "       申请部门：" + orderApply.getApplyDepartment() +
                "       申请人：" + orderApply.getApplyUser() +
                "       采购经费代码：" + orderApply.getFundCode() +
                "       申请日期：" + dateFormat.format(orderApply.getApplyDate()));
        sheet.addMergedRegion(new CellRangeAddress(n-1, n-1, 0, 9));
        cell1.setCellStyle(styleClb);
        //第三行
        //申请列表
        HSSFRow rowContent = sheet.createRow(n++);
        //创建表头
        HSSFCell cellTemp;
        cellTemp = rowContent.createCell(0);
        cellTemp.setCellValue("序号");
        cellTemp.setCellStyle(styleCcc);
        cellTemp = rowContent.createCell(1);
        cellTemp.setCellValue("物资名称");
        cellTemp.setCellStyle(styleCcc);
        cellTemp = rowContent.createCell(2);
        cellTemp.setCellValue("品牌型号");
        cellTemp.setCellStyle(styleCcc);
        cellTemp = rowContent.createCell(3);
        cellTemp.setCellValue("配置或技术参数");
        cellTemp.setCellStyle(styleCcc);
        cellTemp = rowContent.createCell(4);
        cellTemp.setCellValue("单位");
        cellTemp.setCellStyle(styleCcc);
        cellTemp = rowContent.createCell(5);
        cellTemp.setCellValue("数量");
        cellTemp.setCellStyle(styleCcc);
        cellTemp = rowContent.createCell(6);
        cellTemp.setCellValue("预算单价(元)");
        cellTemp.setCellStyle(styleCcc);
        cellTemp = rowContent.createCell(7);
        cellTemp.setCellValue("预算总价(元)");
        cellTemp.setCellStyle(styleCcc);
        cellTemp = rowContent.createCell(8);
        cellTemp.setCellValue("申购原因（请详细列明申购理由，并阐述采购必要性，要求不少于100字，具体内容可参考备注部分）如有旧设备，请列明");
        cellTemp.setCellStyle(styleClcr);
        cellTemp = rowContent.createCell(9);
        cellTemp.setCellValue("新设备使用人");
        cellTemp.setCellStyle(styleCcc);
        //申请列表内容
        for(OrderList orderList : orderApply.getOrderLists()){
            int l = 0;
            HSSFRow rowList = sheet.createRow(n++);
            HSSFCell cell;
            cell = rowList.createCell(l++);
            cell.setCellValue(no++);
            cell.setCellStyle(styleCcc);
            cell = rowList.createCell(l++);
            cell.setCellValue(orderList.getName());
            cell.setCellStyle(styleCcc);
            cell = rowList.createCell(l++);
            cell.setCellValue(orderList.getType());
            cell.setCellStyle(styleCcc);
            cell = rowList.createCell(l++);
            cell.setCellValue(orderList.getConfiguration());
            cell.setCellStyle(styleCcc);
            cell = rowList.createCell(l++);
            cell.setCellValue(orderList.getUnit());
            cell.setCellStyle(styleCcc);
            cell = rowList.createCell(l++);
            cell.setCellValue(orderList.getQuantity());
            cell.setCellStyle(styleCcc);
            cell = rowList.createCell(l++);
            cell.setCellValue(new DecimalFormat("¥#,###.00").format(orderList.getBudgetUnitPrice()));
            cell.setCellStyle(styleCcc);
            cell = rowList.createCell(l++);
            cell.setCellValue(new DecimalFormat("¥#,###.00").format(orderList.getBudgetTotalPrice()));
            cell.setCellStyle(styleCcc);
            cell = rowList.createCell(l++);
            cell.setCellValue(orderList.getReason());
            cell.setCellStyle(styleCcc);
            cell = rowList.createCell(l++);
            cell.setCellValue(orderList.getNewUser());
            cell.setCellStyle(styleCcc);
        }
        //汇总
        HSSFRow rowSum = sheet.createRow(n++);
        rowSum.setHeightInPoints(20);
        HSSFCell cellSum, cellBorder;
        cellSum = rowSum.createCell(0);
        cellSum.setCellValue("采购总金额");
        cellSum.setCellStyle(styleCcc);
        CellRangeAddress cra = new CellRangeAddress(n-1, n-1, 0, 1);
        sheet.addMergedRegion(cra);
        setBorder(BorderStyle.THIN, cra, sheet);
        cellSum = rowSum.createCell(7);
        cellSum.setCellValue(new DecimalFormat("¥#,###.00").format(orderApply.getTotal()));
        cellSum.setCellStyle(styleCcc);
        cellBorder = rowSum.createCell(9);
        cellBorder.setCellStyle(styleCcc);
        //签名
        if(createSignPath){
            HSSFRow rowDept = sheet.createRow(n++);
            rowDept.setHeightInPoints(31);
            HSSFRow rowInst = sheet.createRow(n++);
            rowInst.setHeightInPoints(31);
            cra = new CellRangeAddress(n-2, n-1, 0, 0);
            sheet.addMergedRegion(cra);
            setBorder(BorderStyle.THIN, cra, sheet);
            HSSFCell cellSign = rowDept.createCell(0);
            cellSign.setCellValue("审批意见");
            cellSign.setCellStyle(styleCcc);

            cellSign = rowDept.createCell(1);
            cellSign.setCellValue("部门领导");
            cellSign.setCellStyle(styleCcc);

            cellSign = rowInst.createCell(1);
            cellSign.setCellValue("主管院领导");
            cellSign.setCellStyle(styleCcc);

            cra = new CellRangeAddress(n-2, n-2, 2, 9);
            sheet.addMergedRegion(cra);
            setBorder(BorderStyle.THIN, cra, sheet);
            HSSFCell cellD =  rowDept.createCell(2);
            cellD.setCellValue("签名：           日期：     年     月     日");
            cellD.setCellStyle(styleCrb);

            cra = new CellRangeAddress(n-1, n-1, 2, 9);
            sheet.addMergedRegion(cra);
            setBorder(BorderStyle.THIN, cra, sheet);
            HSSFCell cellI =  rowInst.createCell(2);
            cellI.setCellValue("签名：           日期：     年     月     日");
            cellI.setCellStyle(styleCrb);
        }
        //设置列宽度
        sheet.setColumnWidth(0, 4*256);
        sheet.setColumnWidth(1, 10*256);
        sheet.setColumnWidth(2, 10*256);
        sheet.setColumnWidth(3, 20*256);
        sheet.setColumnWidth(4, 6*256);
        sheet.setColumnWidth(5, 8*256);
        sheet.setColumnWidth(6, 10*256);
        sheet.setColumnWidth(7, 10*256);
        sheet.setColumnWidth(8, 40*256);
        sheet.setColumnWidth(9, 8*256);

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("申请单", "UTF-8") + ".xls");
        response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
        response.flushBuffer();
        OutputStream outputStream = response.getOutputStream();
        workbook.write(response.getOutputStream());
        workbook.close();
        outputStream.flush();
        outputStream.close();
    }

    /**
     * Excel样式生成
     * @param workbook HSSFWorkBook对象
     * @param height 字体高度(大小)
     * @param color 字体颜色
     * @param bold 加粗
     * @param hAlign 水平对齐方式
     * @param vAlign 垂直对齐方式
     * @param border 是否生成边框
     * @return HSSFCellStyle对象
     */
    private static HSSFCellStyle createStyle(HSSFWorkbook workbook, short height, short color, boolean bold,
                                             org.apache.poi.ss.usermodel.HorizontalAlignment hAlign,
                                             org.apache.poi.ss.usermodel.VerticalAlignment vAlign,
                                             boolean border){
        HSSFCellStyle style = workbook.createCellStyle();
        //字体
        HSSFFont font = workbook.createFont();
        font.setBold(bold);
        font.setFontName("宋体");
        font.setFontHeightInPoints(height);
        font.setColor(color);
        //边框
        if(border){
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
        }
        //样式
        style.setFont(font);
        style.setAlignment(hAlign);
        style.setVerticalAlignment(vAlign);
        style.setWrapText(true);

        return style;
    }

    /**
     * 设置合并后单元格的边框
     * @param borderStyle 边框样式
     * @param cellRangeAddress 合并的单元格信息
     * @param sheet 由 HSSFWorkBook 生成的 Sheet 表单对象
     */
    private static void setBorder(BorderStyle borderStyle, CellRangeAddress cellRangeAddress, Sheet sheet){
        RegionUtil.setBorderTop(borderStyle, cellRangeAddress, sheet);
        RegionUtil.setBorderBottom(borderStyle, cellRangeAddress, sheet);
        RegionUtil.setBorderLeft(borderStyle, cellRangeAddress, sheet);
        RegionUtil.setBorderRight(borderStyle, cellRangeAddress, sheet);
    }
}