package servicios;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.*;

/**
 *
 * @author Mauricio
 */
public class ModeloExcel implements Runnable {

    Workbook wb;
    File archivo;
    JTable tbPstiients;
    JProgressBar ProgresImport;
    JPanel pnListPatientImport;

    public ModeloExcel(File archivo, JTable tbPstiients, JProgressBar ProgresImport, JPanel pnListPatientImport) {
        this.archivo = archivo;
        this.tbPstiients = tbPstiients;
        this.ProgresImport = ProgresImport;
        this.pnListPatientImport = pnListPatientImport;
    }

    

    @Override
    public void run() {
        String response;
        DefaultTableModel model;
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {//para evitar que las celdas sean editables
                return false;
            }
        };
        tbPstiients.setModel(model);
        try {
            wb = WorkbookFactory.create(new FileInputStream(archivo));
            Sheet hoja = wb.getSheetAt(0);
            Iterator filaIterator = hoja.rowIterator();
            int totalFilas = hoja.getLastRowNum();
            int indiceFila = -1;
            float porcentaje = (float) (100.0 / totalFilas);            
            float aux = (float) 0;
            ProgresImport.setMaximum(totalFilas);
            while (filaIterator.hasNext()) {
                indiceFila++;                             
                if (indiceFila >= 1) {
                    aux += porcentaje;                    
                } 
                ProgresImport.setString(Integer.toString((int) Math.ceil(aux)) + "%");
                ProgresImport.setStringPainted(true);                
                ProgresImport.setValue(indiceFila);
                Row fila = (Row) filaIterator.next();
                Iterator columnaitr = fila.cellIterator();
                Object[] Listacolumna = new Object[4];
                int indiceColumna = -1;
                while (columnaitr.hasNext()) {
                    indiceColumna++;
                    Cell celda = (Cell) columnaitr.next();
                    if (indiceFila == 0) {
                        model.addColumn(celda.getStringCellValue());
                    } else {
                        if (celda != null) {
                            switch (celda.getCellType()) {
                                case Cell.CELL_TYPE_NUMERIC:
                                    Listacolumna[indiceColumna] = (int) Math.round(celda.getNumericCellValue());
                                    break;
                                case Cell.CELL_TYPE_STRING:
                                    Listacolumna[indiceColumna] = celda.getStringCellValue();
                                    break;
                                default:
                                    Listacolumna[indiceColumna] = celda.getDateCellValue();
                                    break;

                            }
                        }
                    }
                    Thread.sleep(100);
                }
                if (indiceFila != 0) {
                    model.addRow(Listacolumna);
                }

            }
            response = "Importacion exitosa";
            JOptionPane.showMessageDialog(null, response);
            pnListPatientImport.setVisible(true);
            ProgresImport.setValue(0);
            ProgresImport.setVisible(false);            
        } catch (IOException | InvalidFormatException | EncryptedDocumentException e) {
            response = "Error: " + e;
        } catch (InterruptedException ex) {
            Logger.getLogger(ModeloExcel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
