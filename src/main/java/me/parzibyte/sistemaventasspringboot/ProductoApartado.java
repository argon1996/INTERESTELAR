package me.parzibyte.sistemaventasspringboot;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ProductoApartado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Producto producto;

    private Float cantidad;
    private LocalDateTime fechaApartado;
    private Float existenciaAntes;
    private String nombrePersona;
    private Float abonoInicial;
    private Float totalAbono;
    private LocalDateTime fechaUltimoAbono;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int existenciaActual = 0;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int existenciaEnApartado = 0;

    // Constructor predeterminado (sin parámetros)
    public ProductoApartado() {
    }

    // Constructor con todos los parámetros
    public ProductoApartado(Producto producto, Float cantidad, LocalDateTime fechaApartado, Float existenciaAntes, String nombrePersona, Float abonoInicial) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.fechaApartado = fechaApartado;
        this.existenciaAntes = existenciaAntes;
        this.nombrePersona = nombrePersona;
        this.abonoInicial = abonoInicial;
        this.totalAbono = abonoInicial;
        this.fechaUltimoAbono = fechaApartado;
    }

    // Getters y setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Float getCantidad() {
        return cantidad;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFechaApartado() {
        return fechaApartado;
    }

    public void setFechaApartado(LocalDateTime fechaApartado) {
        this.fechaApartado = fechaApartado;
    }

    public Float getExistenciaAntes() {
        return existenciaAntes;
    }

    public void setExistenciaAntes(Float existenciaAntes) {
        this.existenciaAntes = existenciaAntes;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    public Float getAbonoInicial() {
        return abonoInicial;
    }

    public void setAbonoInicial(Float abonoInicial) {
        this.abonoInicial = abonoInicial;
    }

    public Float getTotalAbono() {
        return totalAbono;
    }

    public void setTotalAbono(Float totalAbono) {
        this.totalAbono = totalAbono;
    }

    public LocalDateTime getFechaUltimoAbono() {
        return fechaUltimoAbono;
    }

    public void setFechaUltimoAbono(LocalDateTime fechaUltimoAbono) {
        this.fechaUltimoAbono = fechaUltimoAbono;
    }

    public int getExistenciaActual() {
        return existenciaActual;
    }

    public void setExistenciaActual(int existenciaActual) {
        this.existenciaActual = existenciaActual;
    }

    public int getExistenciaEnApartado() {
        return existenciaEnApartado;
    }

    public void setExistenciaEnApartado(int existenciaEnApartado) {
        this.existenciaEnApartado = existenciaEnApartado;
    }

    @Transient
    public Float getAbonoPendiente() {
        return producto.getPrecio() * cantidad - totalAbono;
    }

    @Transient
    public String getEstado() {
        return getAbonoPendiente() > 0 ? "En proceso" : "Abono completo";
    }
}
