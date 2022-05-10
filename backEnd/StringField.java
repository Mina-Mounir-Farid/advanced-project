package com.mycompany.courseproject;

/**
 *
 * @author Ghaith
 */
public class StringField extends Field {
    private final String[] allowedValues;
    protected StringField(Field.DIR direction, String name, String[] allowedValues, boolean mandatory) {
        super(direction, name, mandatory);
        this.allowedValues = allowedValues;
    }

    public Field.OBJ_TYPE getType() {
        return Field.OBJ_TYPE.string;
    }
    
    public String[] getAllowedVals() {
        return allowedValues;
    }
}
