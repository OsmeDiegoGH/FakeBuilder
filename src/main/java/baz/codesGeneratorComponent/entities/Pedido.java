package baz.codesGeneratorComponent.entities;

public class Pedido {
    private String pedido;
    private String sucursal;

    public Pedido(String pedido, String sucursal) {
        this.pedido = pedido;
        this.sucursal = sucursal;
    }

    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }
}
