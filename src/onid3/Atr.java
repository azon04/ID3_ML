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
public class Atr {
    
 /**
 *
 * Class for discrete attributes
 */

    private String name;
    private ArrayList<String> values;
    
    public Atr() {
    }
    
    public Atr(String _name, ArrayList<String> _values) {
        name = _name;
        values = _values;
    }
    
    public void setName(String _name) {
        name = _name;
    }
    
    public void setValues(ArrayList<String> _values) {
        values = _values;
    }
    
    public String getName() {
        return name;
    }
    
    public ArrayList<String> getValues() {
        return values;
    }
    
}
