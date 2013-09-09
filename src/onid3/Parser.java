/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package onid3;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

/**
 *
 * @author Nurul Fithria
 */
public class Parser {
    
    public Parser(){
        
    }
    
      
    public Dataset loadDataset(String sFile) throws FileNotFoundException, IOException {
        ArrayList<Atr> _atr = new ArrayList<Atr>();
        Dataset _dataset = new Dataset();
        String line_read;		
        LineNumberReader source;
        source = new LineNumberReader(new FileReader(sFile));
        while (source.ready()) {
                line_read = source.readLine();
                String[] sTemp = line_read.split(" ");
                if (sTemp[0].equals("@attribute")) {
                    String _name = sTemp[1];
                    ArrayList<String> _values = new ArrayList<String>();
                    String[] stringOfValues = sTemp[2].split(",");
                    stringOfValues[0] = stringOfValues[0].substring(1);
                    stringOfValues[stringOfValues.length - 1] = stringOfValues[stringOfValues.length - 1].replace("}", "");
                    for(int i=0;i<stringOfValues.length;i++) {
                        _values.add(i,stringOfValues[i]);
                    }
                    Atr atr = new Atr(_name,_values);
                    _atr.add(atr);
                } else if (sTemp[0].equals("@data")) {
                    while (source.ready()) {
                        line_read = source.readLine();
                        String[] valuesOfRecord = line_read.split(",");
                        ArrayList<Integer> record = new ArrayList<Integer>();
                        for(int i=0;i<valuesOfRecord.length;i++) {
                            int idxOfValue = _atr.get(i).getValues().indexOf(valuesOfRecord[i]);
                            record.add(i,idxOfValue);
                        }
                        _dataset.addData(record);
                    }
                } 
        }
        source.close();
        _dataset.setAttributes(_atr);
        return _dataset;
    }
}
