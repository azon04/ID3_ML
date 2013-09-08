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
    
    public ArrayList<Atr> loadAtr(String sFile) throws FileNotFoundException, IOException {
        String line_read;		
        LineNumberReader source;
        source = new LineNumberReader(new FileReader(sFile));
        line_read = source.readLine(); // header
        while (source.ready()) {
                line_read = source.readLine();
                String[] sTemp = line_read.split(" ");
                if (sTemp[0].compareTo("@attribute") != 0) {
                    
                } 
        }
        source.close();
        
        return null;
    }
    
    public ArrayList<Data> loadData(String sFile) throws FileNotFoundException, IOException {
        String line_read;		
        LineNumberReader source;
        source = new LineNumberReader(new FileReader(sFile));
        line_read = source.readLine(); // header
        while (source.ready()) {
                line_read = source.readLine();
                String[] sTemp = line_read.split(" ");
                if (sTemp[0].compareTo("@data") != 0) {
                    
                } 
        }
        source.close();
        
        return null;
    }
}
