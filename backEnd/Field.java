package com.mycompany.courseproject;

/**
 *
 * @author Ghaith
 */
public abstract class Field {

    public static enum DIR {
        input,
        output
    }

    public static enum OBJ_TYPE {
        object,
        string
    }
    private final DIR direction;//  I/O
    private final String name;

    private final boolean mandatory;

    protected Field(DIR direction, String name, boolean mandatory) {

        this.direction = direction;
        this.name = name;
        this.mandatory = mandatory;
    }

    public static Field getInstance(DIR direction, String name, OBJ_TYPE type, String[] allowedValues, boolean mandatory) {
        if (type == OBJ_TYPE.object) {
            return new ObjField(direction, name, mandatory);
        }
        if (type == OBJ_TYPE.string) {
            return new StringField(direction, name, allowedValues, mandatory);
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public DIR getDirection() {
        return direction;
    }

    

    public boolean isMandatory() {
        return mandatory;
    }

}

class FieldBuilder {

    public Field.DIR direction;//  I/O
    public String name;
    public String[] allowedValues;
    public boolean mandatory;
    public Field.OBJ_TYPE type;

    public Field build() {
        return Field.getInstance(direction, name, type, allowedValues, mandatory);
    }
}
