package com.mycompany.courseproject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Ghaith
 */
public class Service {
   
    private ArrayList<Operation>  operations = new ArrayList<>();
    
    
    public void addOperation(Operation op){
        operations.add(op);
    }
    public Iterator getOperations(){
        return operations.iterator();
    }
    
}
