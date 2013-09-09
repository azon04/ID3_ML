/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package onid3;
import java.util.ArrayList;

/**
 *
 * @author Nurul Fithria
 */
public class Data {
    
 /**
 *
 * data with discrete attributes
 */
    
    private ArrayList<Atr> attributes;
    private ArrayList<Integer> values;
    
    public Data() {
        
    }
    
    public Data(ArrayList<Atr> _attributes, ArrayList<Integer> _values) {
        attributes = _attributes;
        values = _values;
    }
    
    public void setAttributes(ArrayList<Atr> _attributes) {
        attributes = _attributes;
    }
    
    public void setValue(Atr atr, String atr_value) {
        int idx = attributes.indexOf(atr);
        values.add(idx, new Integer(attributes.get(idx).getValues().indexOf(atr_value)));
    }
    
    public ArrayList<Atr> getAttributes() {
        return attributes;
    }
    
    public ArrayList<Integer> getValues() {
        return values;
    }
   
}
