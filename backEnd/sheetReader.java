package com.mycompany.courseproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Ghaith
 */
public class sheetReader {

    static Iterator<Sheet> read(String link) {
        FileInputStream file;
        try {
            file = new FileInputStream(new File(link));

        } catch (FileNotFoundException ex) {
            System.out.println("file not found");
            return null;
        }
        try ( Workbook workbook = new XSSFWorkbook(file)) {

            Iterator<Sheet> sheets = workbook.sheetIterator();
            return sheets;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    static Service analyse(Iterator<Sheet> sheets) {
        DataFormatter dataFormatter = new DataFormatter();
        int i;
        boolean isOpParamsLine = false;
        boolean isFieldLine = false;
        Service serv = new Service();
        OperationBuilder OpB = null;
        FieldBuilder FB = null;
        String[] link = null;
        ArrayList<FieldsCollection> LinkObjs = null;
        while (sheets.hasNext()) { //just in case of having multible sheets not required in project
            Sheet sh = sheets.next();

            for (Row row : sh) {
                i = 0;

                Iterator<Cell> cellIterator = row.iterator();
                Cell cell;
                String cellValue;

                while (cellIterator.hasNext()) {

                    cell = cellIterator.next();
                    cellValue = dataFormatter.formatCellValue(cell);

                    if (cellValue.equalsIgnoreCase("http operation")) {
                        isOpParamsLine = true;
                        break;

                    } else if (cellValue.equalsIgnoreCase("i/o")) {
                        break;
                    } else if (isOpParamsLine) {
                        if (i == 1) {
                            isOpParamsLine = false;
                            if (OpB != null) {
                                OpB.REST_URL = cellValue;
                                LinkObjs = new ArrayList<>();
                                LinkObjs.add(OpB.Build());
                                serv.addOperation((Operation) LinkObjs.get(0));
                            }
                        } else if (i == 0) {
                            if (OpB != null) {
                                OpB.HTTP_operation = cellValue;
                            }
                        }

                    } else if ((cellValue.equalsIgnoreCase("i") || cellValue.equalsIgnoreCase("o")) && i == 0) {

                        FB = new FieldBuilder();
                        if (cellValue.equalsIgnoreCase("i")) {
                            FB.direction = Field.DIR.input;
                        } else if (cellValue.equalsIgnoreCase("o")) {
                            FB.direction = Field.DIR.output;
                        }
                        isFieldLine = true;
                    } else if (isFieldLine) {
                        if (FB == null) {
                            break;
                        }
                        switch (i) {
                            case 1 -> {
                                link = cellValue.split("/");

                                FB.name = link[link.length - 1];
                            }

                            case 2 -> {
                                if (cellValue.charAt(0) == 'S' || cellValue.charAt(0) == 's') {
                                    FB.type = Field.OBJ_TYPE.string;
                                } else if (cellValue.charAt(0) == 'O' || cellValue.charAt(0) == 'o') {
                                    FB.type = Field.OBJ_TYPE.object;
                                }
                            }
                            case 3 ->
                                FB.allowedValues = cellValue.split(",");
                            case 4 -> {
                                isFieldLine = false;

                                if (cellValue.charAt(0) == 'Y' || cellValue.charAt(0) == 'y') {
                                    FB.mandatory = true;
                                } else if (cellValue.charAt(0) == 'N' || cellValue.charAt(0) == 'n') {
                                    FB.mandatory = false;
                                }
                                Field NF = FB.build();
                                if (LinkObjs != null && link != null) {

                                    LinkObjs.get(link.length - 2).addField(NF);
                                    if (LinkObjs.size() > link.length) {
                                        LinkObjs.subList(link.length -1, LinkObjs.size() ).clear();
                                        
                                    }
                                    if (NF instanceof ObjField objField) {
                                        LinkObjs.add(objField);
                                    }
                                }
                            }
                            default ->
                                throw new AssertionError();
                        }

                    } else {
                        if (cellIterator.hasNext()) {
                            OpB = new OperationBuilder();
                            OpB.name = cellValue;
                        }
                    }
                    i++;
                }

            }

        }
        return serv;
    }

}
