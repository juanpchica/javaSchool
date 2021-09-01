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
public class EstudianteInternacional extends Estudiante{
    
    public EstudianteInternacional(String nombre, int edad, int estrato, String identificacion) {
        super(nombre, edad, estrato, identificacion);
    }
    
    @Override
    public void validarTieneDescuento(){
        if(this.edad < 20){
            this.setValorDescontado(calcularValorDescontado(20));
        }else{
            this.setValorDescontado(calcularValorDescontado(35));
        }
        
        if(getPromedio() >= 4.5){
            this.setValorDescontado(this.getValorDescontado()+calcularValorDescontado(20));
        }
    }
    
}
