package baz.codesGeneratorComponent.structures;

import java.util.regex.Pattern;

public class PipedStringList {
    
    String[] elements;
    String strValue;
    
    public PipedStringList(String pipedString){
        this.strValue = pipedString;
        this.elements = pipedString.split(Pattern.quote("|"));     
    }
    
    public PipedStringList(String[] pipedString){
        this.strValue = "";
        this.elements = pipedString;
        for(int i = 0, total = this.elements.length; i < total; i++){
            this.strValue += this.elements[i] + "|";
        }
    }
    
    public String[] getElements(){
        return this.elements;
    }
    
    public String getElement(int i){
        return this.elements[i];
    }
    
    @Override
    public String toString(){
        String result = "";
        for(int i = 0, total = this.elements.length; i < total; i++){
            result += this.elements[i] + "|";
        }
        return result;
    }
}
