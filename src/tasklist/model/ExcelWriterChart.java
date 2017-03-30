/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tasklist.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Chart;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.charts.AxisCrosses;
import org.apache.poi.ss.usermodel.charts.AxisPosition;
import org.apache.poi.ss.usermodel.charts.ChartAxis;
import org.apache.poi.ss.usermodel.charts.ChartDataSource;
import org.apache.poi.ss.usermodel.charts.ChartLegend;
import org.apache.poi.ss.usermodel.charts.DataSources;
import org.apache.poi.ss.usermodel.charts.LegendPosition;
import org.apache.poi.ss.usermodel.charts.LineChartData;
import org.apache.poi.ss.usermodel.charts.ValueAxis;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author user
 */
public class ExcelWriterChart {
        Row row;
        Cell cell;
        Workbook  wb = new XSSFWorkbook();
        Sheet  sheet = wb.createSheet("Sheet1");
        
        public ExcelWriterChart(List<Task> taskData, File file) throws IOException {
            
            //add Excel table Header
            row = sheet.createRow(0);
            cell = row.createCell(0);
            cell.setCellValue("Name");
            
            cell = row.createCell(1);
            cell.setCellValue("PID");
            
            cell = row.createCell(2);
            cell.setCellValue("Memory");
            
            // Create a row and put some cells in it. Rows are 0 based.
            int rowIndex = 1;
            for (Task taskD : taskData ){
              
                row = sheet.createRow((short) rowIndex);
                
                cell = row.createCell(0);
                cell.setCellValue(taskD.getName());
                
                cell = row.createCell(1);
                cell.setCellValue(taskD.getPID());
                
                cell = row.createCell(2);
                cell.setCellValue(taskD.getMemory());
                
                rowIndex++;
                }
         
        //auto fit "Nmae" column
        sheet.autoSizeColumn(0);
            
        Drawing drawing = sheet.createDrawingPatriarch();
        ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 4, 1, 15, 25);

        Chart chart = drawing.createChart(anchor);
        ChartLegend legend = chart.getOrCreateLegend();
        legend.setPosition(LegendPosition.TOP_RIGHT);

        LineChartData data = chart.getChartDataFactory().createLineChartData();

        // Use a category axis for the bottom axis.
        ChartAxis bottomAxis = chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
        ValueAxis leftAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
        leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);

        ChartDataSource<Number> xs = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(1,taskData.size(), 0, 0));
        ChartDataSource<Number> ys1 = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(1, taskData.size(), 2, 2));
        
        data.addSeries(xs, ys1);
        

        chart.plot(data, bottomAxis, leftAxis);
            
            // Write the output to a file
            FileOutputStream fileOut;
            try {
                fileOut = new FileOutputStream(file.getPath());
                wb.write(fileOut);
                fileOut.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ExcelWriterChart.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }

}

