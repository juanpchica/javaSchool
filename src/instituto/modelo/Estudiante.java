/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instituto.modelo;

/**
 *
 * @author Hugo C
 */
public class Estudiante {
    private double[] notas;
    private String nombre,identidad;
    private int estrato;
    protected int edad;
    private boolean tieneDescuento,yaTieneDescuento,internacional = false;
    private double promedio,valorDescontado;
    
    public Estudiante(String nombre, int edad, int estrato,String identidad) {
        this.nombre = nombre;
        this.edad = edad;
        this.estrato = estrato;
        this.identidad = identidad;
        
    }

    public boolean isInternacional() {
        return internacional;
    }

    public void setInternacional(boolean internacional) {
        this.internacional = internacional;
    }

    public String getIdentidad() {
        return identidad;
    }

    public void setIdentidad(String identidad) {
        this.identidad = identidad;
    }
    
    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    public double getValorDescontado() {
        return valorDescontado;
    }

    public void setValorDescontado(double valorDescontado) {
        this.valorDescontado = valorDescontado;
    }

    public boolean isTieneDescuento() {
        return tieneDescuento;
    }

    public void setTieneDescuento(boolean tieneDescuento) {
        this.tieneDescuento = tieneDescuento;
    }
    
    public double[] getNotas() {
        return notas;
    }

    public void setNotas(double[] notas) {
        this.notas = notas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getEstrato() {
        return estrato;
    }

    public void setEstrato(int estrato) {
        this.estrato = estrato;
    }
    
    public void calcularPromedio() {
        double promedio = 0.0;
        double[] notasValor = new double[]{0.3,0.3,0.4};
        for(int i = 0;i<3;i++){
            promedio += this.notas[i]*notasValor[i];
        }
        
        this.setPromedio(Math.round(promedio* 100.0)/100.0);
    }
    
    public double calcularValorDescontado(double porcentaje){
        if(porcentaje == 0){
            this.tieneDescuento = false;
            return 0;
        }else{
            double valorPorcentaje = porcentaje/100.0;
            double valorMatricula = (this.yaTieneDescuento)?(630000-this.getValorDescontado()):630000;
            this.tieneDescuento = true;
            this.yaTieneDescuento = true;
            
            return (valorMatricula * valorPorcentaje);
        }
    }
    
    public void validarTieneDescuento(){
        if((this.estrato == 1 || this.estrato == 2 || this.estrato == 3)){
            if(this.edad < 18){
                this.setValorDescontado(calcularValorDescontado(70));
            }else{
                this.setValorDescontado(calcularValorDescontado(0));
            }
        }else{
            this.setValorDescontado(calcularValorDescontado(20));
        }
        
        if(getPromedio() >= 4){
            if(this.yaTieneDescuento){
                this.setValorDescontado(this.getValorDescontado()+calcularValorDescontado(50));
            }else{
                this.setValorDescontado(calcularValorDescontado(20));
            }
        }
    }
}