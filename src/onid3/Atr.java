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
    
    public static int NUMERIC = 0;
    public static int NOMINAL = 1;
    private String name;
    private ArrayList<String> values;
    private int type = NOMINAL;
    
    public Atr() {
    }
    
    public Atr(String _name, int type) {
        name = _name;
        this.type = type;
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
    
    public String getValue(int idx) {
        if(type == NUMERIC) return "" + idx;
        else return values.get(idx);
    }

    public int getType() {
        return type;
    }
    
}
