package com.mycompany.courseproject;

import java.util.Iterator;

/**
 *
 * @author Ghaith
 */
public interface FieldsCollection {
    public Iterator<Field> getFields();
    public void addField(Field f);
    public Field getFieldByIndex(int I);
}
