package com.mycompany.courseproject;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 * @author Ghaith
 */
public class CourseProject {

    static void iterFields(FieldsCollection FC) {
        Iterator<Field> fields = FC.getFields();
        while (fields.hasNext()) {
            Field field = fields.next();
            printField(field);
            if (field instanceof ObjField objF) {
                System.out.println(field.getName() + ":");
                iterFields(objF);
                System.out.println("end " + field.getName());
            }
        }
    }

    static void printField(Field F) {
        if (F instanceof StringField f) {
            System.out.println(F.getDirection() + "\t" + F.getName()+ "\t" + "string" +"\t" +Arrays.toString(f.getAllowedVals())+"\t" + (F.isMandatory()?"y":"N"));
        } else {
            System.out.println(F.getDirection() + "\t" + F.getName() + "\t" + "object" +"\t \t" + (F.isMandatory()?"y":"N"));
        }
    }

    public static void main(String[] args) {
        String link = new File("Example.xlsx").getAbsolutePath();

        Iterator<Sheet> iter = sheetReader.read(link);

        Service service = sheetReader.analyse(iter);
        Iterator<Operation> operations = service.getOperations();
        while (operations.hasNext()) {
            Operation op = operations.next();
            System.out.println(op.getName() + "\n" + op.get_HTTP_opertion() + "\t" + op.get_REST_URL());
            iterFields(op);
        }

    }

}
