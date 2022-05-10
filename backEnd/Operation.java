package com.mycompany.courseproject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Ghaith
 */
public class Operation implements FieldsCollection{

    private final String name; //API name
    private final String HTTP_operation;
    private final String REST_URL;
    private ArrayList<Field> fields;

    public Operation(String name, String HTTP_operation, String REST_URL) {
        this.name = name;
        this.HTTP_operation = HTTP_operation;
        this.REST_URL = REST_URL;
        fields = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String get_REST_URL() {
        return REST_URL;
    }

    public String get_HTTP_opertion() {
        return HTTP_operation;
    }

    @Override
    public Iterator<Field> getFields() {
        return fields.iterator();
    }

    @Override
    public void addField(Field f) {
        fields.add(f);
    }

}

class OperationBuilder {

    public String name; //API name
    public String HTTP_operation;
    public String REST_URL;

    public Operation Build() {
        return new Operation(name, HTTP_operation, REST_URL);
    }
}
