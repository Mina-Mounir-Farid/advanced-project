package com.mycompany.courseproject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Ghaith
 */
public class ObjField extends Field implements FieldsCollection{

    private ArrayList<Field> subFields;

    protected ObjField(Field.DIR direction, String name, boolean mandatory) {
        super(direction, name, mandatory);
        subFields = new ArrayList<>();
    }

    public Field.OBJ_TYPE getType() {
        return Field.OBJ_TYPE.object;
    }

    @Override
    public Iterator<Field> getFields() {
        return subFields.iterator();
    }

    @Override
    public void addField(Field f) {
        subFields.add(f);
    }
    
    @Override
    public Field getFieldByIndex(int I) {
        return subFields.get(I);
    }
}
