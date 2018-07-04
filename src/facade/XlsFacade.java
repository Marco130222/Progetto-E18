package facade;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import persone.*;

import java.awt.*;
import java.io.*;
import java.util.*;

import static persone.Invitato.setID_Inv;

public class XlsFacade {
    Workbook workbook;

    FileOutputStream fileOut = null;
    private Sheet sheet;
    private String xlsGuest = "Guest.xls", xlsVincoli = "Vincoli.xls";


    public boolean generateXlsGuests(String nomeEvento){
        workbook = new HSSFWorkbook();

        boolean generated = true;



        CreationHelper createHelper = workbook.getCreationHelper();
        sheet = workbook.createSheet(nomeEvento);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());


        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);


        Row headerRow = sheet.createRow(0);

        ArrayList<String> columns = new ArrayList<>();
        //columns.add("IDentificativo");
        columns.add("Name");
        columns.add("Surname");
        columns.add("eta'");

        for (int count = 0; count < columns.size(); count++) {
            Cell cell = headerRow.createCell(count);
            cell.setCellValue(columns.get(count));
            cell.setCellStyle(headerCellStyle);
        }

        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
        for (int count = 0; count < columns.size(); count++) {
            sheet.autoSizeColumn(count);
        }
        try {
            fileOut = new FileOutputStream(nomeEvento+".xls");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            generated = false;
        } catch (IOException e1) {
            e1.printStackTrace();
            generated = false;
        }
        return generated;
    }

    public ArrayList<Invitato> readXlsGuests(String nomeEvento){
        //boolean done = true;

        ArrayList<Invitato> invitati= new ArrayList<Invitato>(2);
        try {
            String file = nomeEvento+".xls";
            FileInputStream excelFile = new FileInputStream(new File(file));
            workbook = new HSSFWorkbook(excelFile);
            Sheet dataTypeSheet = workbook.getSheetAt(0);
            Iterator iterator = dataTypeSheet.iterator();
            iterator.next();

            while (iterator.hasNext()) {
                Invitato fantoccio=null,elemento=null;
                String name=null,surname=null;
                int eta=-1;
                Row currentRow = (Row) iterator.next();
                Iterator cellIterator = currentRow.iterator();

                while (cellIterator.hasNext()) {
                    Cell currentCell = (Cell) cellIterator.next();
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        System.out.print(currentCell.getStringCellValue() + "  ");
                        if(currentCell.getColumnIndex()==0){
                            name=currentCell.getStringCellValue();
                        }else if(currentCell.getColumnIndex()==1){
                            surname=currentCell.getStringCellValue();
                        }
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        System.out.print(currentCell.getNumericCellValue() + " anni ");
                        eta=(int)currentCell.getNumericCellValue();
                    }
                    if (!cellIterator.hasNext()) {
                        fantoccio = new Invitato(name, surname, eta);
                        elemento = new Invitato(fantoccio.getID_Inv(),name,surname,eta);
                        invitati.add(elemento);
                    }

                }
                //System.out.println();
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
           // done = false;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            //done = false;
        }
        return invitati;
    }


    public boolean openfile(String nameFile) throws IOException{
        File file = new File(nameFile);


        if(!Desktop.isDesktopSupported()){
            System.out.println("Desktop is not supported");
            return false;
        }

        Desktop desktop = Desktop.getDesktop();
        if(file.exists())
        desktop.open(file);
        return true;
    }

    


}



