/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package onid3;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 *
 * @author Nurul Fithria
 */
public class Dataset {
    
 /**
 *
 * data with discrete attributes
 */
    
    private ArrayList<Atr> attributes;
    private ArrayList<ArrayList<Integer>> data;
    private int _class = -1;
    
    public Dataset() {
        attributes = new ArrayList<Atr>();
        data = new ArrayList<ArrayList<Integer>>();
    }
    
    public Dataset(ArrayList<Atr> _attributes, ArrayList<ArrayList<Integer>> _values) {
        attributes = _attributes;
        data = _values;
    }
    
    public void setAttributes(ArrayList<Atr> _attributes) {
        attributes = _attributes;
    }
    
    public void addData(ArrayList<Integer> _data) {
        data.add(_data);
    }
    
    public ArrayList<Atr> getAttributes() {
        return attributes;
    }
    
    public ArrayList<Integer> getRecord(int idx) {
        return data.get(idx);
    }
    
    public ArrayList<ArrayList<Integer>> getData() {
        return data;
    }
    
    public void PrintDataset() {
        /*ListIterator<Atr> _iterAtr = attributes.listIterator();
        while (_iterAtr.hasNext()) {
            Atr atr = _iterAtr.next();
            System.out.println(atr.getName());
            System.out.println(atr.getValues().toString());
        }*/
        for(int i=0; i<data.size(); i++) {
            data.get(i);
            for(int j=0; j < data.get(i).size(); j++) {
                System.out.print(attributes.get(j).getValues().get(data.get(i).get(j)) + ",");
            }
            System.out.println("");
        }
        //System.out.println(data.toString());
    }
   
}
