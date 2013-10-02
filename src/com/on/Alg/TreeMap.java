package com.on.Alg;


import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ahmad Fauzan
 */
public class TreeMap<K,V> {
    public TreeMap<K,V> Parent;
    public Dictionary<K,TreeMap> children;
    public V data;
    
    public TreeMap(V data) {
        this.data = data;
    }
    
    public TreeMap(){
        this.data = data;
        children = new Hashtable<>();
    }
    
    public void AddChildren(K key, TreeMap tree) {
        children.put(key, tree);
        tree.Parent = this;
    }
    
    public void PrintTree() {
        System.out.println(data);
        Enumeration<K> keyIter = children.keys();
        while(keyIter.hasMoreElements()) {
           K key = keyIter.nextElement();
           System.out.print(key);
           String s = "-----";
           children.get(key).PrintTreeWithPrefix(s);
        }
    }
    
    public void PrintTreeWithPrefix(String prefix) {
        System.out.println(prefix + data);
        Enumeration<K> keyIter = children.keys();
        while(keyIter.hasMoreElements()) {
           K key = keyIter.nextElement();
           System.out.print(prefix+ key);
           String s = prefix + "-----";
           children.get(key).PrintTreeWithPrefix(s);
        }
    }
}
