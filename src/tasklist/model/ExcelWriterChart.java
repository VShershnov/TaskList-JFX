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
import static org.apache.poi.hssf.usermodel.HeaderFooter.file;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Chart;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.charts.AxisCrosses;
import org.apache.poi.ss.usermodel.charts.AxisPosition;
import org.apache.poi.ss.usermodel.charts.ChartDataSource;
import org.apache.poi.ss.usermodel.charts.ChartLegend;
import org.apache.poi.ss.usermodel.charts.DataSources;
import org.apache.poi.ss.usermodel.charts.LegendPosition;
import org.apache.poi.ss.usermodel.charts.ScatterChartData;
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
            Iterator<Task> it = taskData.iterator();
            int rowIndex = 1;
            while (it.hasNext()) {
                Task tsk1 = it.next();
                
                row = sheet.createRow((short) rowIndex);
                
                cell = row.createCell(0);
                cell.setCellValue(tsk1.getName());
                
                cell = row.createCell(1);
                cell.setCellValue(tsk1.getPID());
                
                cell = row.createCell(2);
                cell.setCellValue(tsk1.getMemory());
                
                rowIndex++;
                }
            
            
        /*
            Drawing drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 5, 10, 15);
            
            Chart chart = drawing.createChart(anchor);
            ChartLegend legend = chart.getOrCreateLegend();
            legend.setPosition(LegendPosition.TOP_RIGHT);
            
            ScatterChartData data = chart.getChartDataFactory().createScatterChartData();
            
            ValueAxis bottomAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.BOTTOM);
            ValueAxis leftAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
            leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
            
            ChartDataSource<Number> xs = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(0, 0, 0, NUM_OF_COLUMNS - 1));
            ChartDataSource<Number> ys1 = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(1, 1, 0, NUM_OF_COLUMNS - 1));
            ChartDataSource<Number> ys2 = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(2, 2, 0, NUM_OF_COLUMNS - 1));
            
            
            data.addSerie(xs, ys1);
            data.addSerie(xs, ys2);
            
            chart.plot(data, bottomAxis, leftAxis);
            
        */
            
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

