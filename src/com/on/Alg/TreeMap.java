package com.on.Alg;


import java.util.Dictionary;

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
    
    public void AddChildren(K key, TreeMap tree) {
        children.put(key, tree);
        tree.Parent = this;
    }
}
