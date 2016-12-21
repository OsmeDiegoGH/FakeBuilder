package baz.codesGeneratorComponent.entities;

public class Perfil {
    private String ICU;
    private String cteAlnova;
    private String numTarjeta;
    private String numSolicitudTienda;
    private String numSolicitudCentralizada;
    private int tipoSolicitud;// credito =1 , captación =2
    private int idRegistroMensaje;

    public String getICU() {
        return ICU;
    }

    public void setICU(String ICU) {
        this.ICU = ICU;
    }

    public String getCteAlnova() {
        return cteAlnova;
    }

    public void setCteAlnova(String cteAlnova) {
        this.cteAlnova = cteAlnova;
    }

    public String getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(String numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public String getNumSolicitudTienda() {
        return numSolicitudTienda;
    }

    public void setNumSolicitudTienda(String numSolicitudTienda) {
        this.numSolicitudTienda = numSolicitudTienda;
    }

    public String getNumSolicitudCentralizada() {
        return numSolicitudCentralizada;
    }

    public void setNumSolicitudCentralizada(String numSolicitudCentralizada) {
        this.numSolicitudCentralizada = numSolicitudCentralizada;
    }

    public int getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(int tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public int getIdRegistroMensaje() {
        return idRegistroMensaje;
    }

    public void setIdRegistroMensaje(int idRegistroMensaje) {
        this.idRegistroMensaje = idRegistroMensaje;
    }

    
    
}
