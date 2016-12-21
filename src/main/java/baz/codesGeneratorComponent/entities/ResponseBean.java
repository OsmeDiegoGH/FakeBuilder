package baz.codesGeneratorComponent.entities;

import java.util.HashMap;

public class ResponseBean {
    
    private int codigo;
    private HashMap<String, Object> data;
    private String mensaje;
    
    public ResponseBean(int codigo, String mensaje){
        this.codigo = codigo;
        this.mensaje = mensaje;
    }
    
    public ResponseBean(int codigo, HashMap<String, Object> data){
        this.codigo = codigo;
        this.data = data;
    }
    
    public ResponseBean(int codigo, HashMap<String, Object> data, String mensaje){
        this.codigo = codigo;
        this.data = data;
        this.mensaje = mensaje;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    public Object getData(String elementName){
        return data.get(elementName);
    }
    
    public Object setData(String elementName){
        return data.get(elementName);
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
