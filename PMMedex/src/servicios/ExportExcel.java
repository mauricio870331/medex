package servicios;

import java.io.*;
import javax.swing.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

/**
 *
 * @author Mauricio
 */
public class ExportExcel{

    Workbook wb;  

    public ExportExcel() {              
        
    }

    public String Export(File archivo, JTable tbControlPatienst) {
        String response;
        int numFilas = tbControlPatienst.getRowCount(), numColumnas = tbControlPatienst.getColumnCount();
        if (archivo.getName().endsWith("xls")) {
            wb = new HSSFWorkbook();
        } else {
            wb = new XSSFWorkbook();
        }
        Sheet hoja = wb.createSheet("Listado de Controles");
        try {
            for (int i = -1; i < numFilas; i++) {
                Row fila = hoja.createRow(i + 1);
                for (int j = 0; j < numColumnas; j++) {
                    Cell celda = fila.createCell(j);
                    if (i == -1) {
                        celda.setCellValue(String.valueOf(tbControlPatienst.getColumnName(j)));
                    } else {
                        celda.setCellValue(String.valueOf(tbControlPatienst.getValueAt(i, j)));
                    }
                    wb.write(new FileOutputStream(archivo));
                }
            }
            response = "Exportacion Exitosa";
        } catch (Exception e) {
            response = "Error:" + e;
        }
        return response;
    }
    
}
