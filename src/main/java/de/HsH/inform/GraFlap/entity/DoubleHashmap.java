package de.HsH.inform.GraFlap.entity;

import java.util.HashMap;

public class DoubleHashmap <A, B>{
    private final HashMap<A, B> ABHashMap;
    private final HashMap<B, A> BAHashMap;
    
    public DoubleHashmap(){
        ABHashMap = new HashMap<>();
        BAHashMap = new HashMap<>();
    }
    
    public void add(A a, B b){
        ABHashMap.put(a, b);
        BAHashMap.put(b, a);
    }
    
    public A getFromB(B reference){
        return BAHashMap.get(reference);
    }
    
    public B getFromA(A reference){
        return ABHashMap.get(reference);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(A a : ABHashMap.keySet()){
            sb.append(a.toString()).append(" - ").append(ABHashMap.get(a).toString()).append("\n");
        }
        return sb.toString();
    }
}
