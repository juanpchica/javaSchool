/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instituto.controlador;

import instituto.modelo.EstudianteInternacional;
import instituto.modelo.Estudiante;
import instituto.modelo.EstudianteDao;
import instituto.vista.Registro;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author juanpchica
 */
public class EstudianteBO {
    private EstudianteDao estudianteDao;
    public static DefaultTableModel modelo;
    
    public EstudianteBO(){
        estudianteDao = new EstudianteDao();
    }
    
    public String insertar(String nombre,String identificacion,String edad, String estrato,String nota1, String nota2, String nota3,boolean internacional){
        String mensaje;
        double[] notas = new double[3];   
        
        //Valido campos digitados
        
        if(nombre.length() == 0){
            return "Campo Nombre Vacío";
        }
        
        if(identificacion.length() == 0){
            return "Campo Identificacion Vacío";
        }
        
        try{
            Integer.parseInt(edad);
        }catch(NumberFormatException e){
            System.out.println(e);
            return "Edad No es un número valido";
        }
        
        try{
            int estratoInt = Integer.parseInt(estrato);
            if(estratoInt < 0 || estratoInt > 8){
                return "Estrato no valido";
            }
        }catch(NumberFormatException e){
            System.out.println(e);
            return "Estrato No es un número valido";
        }
        
        try{
            double nota1D = Double.parseDouble(nota1);
            if(nota1D < 0 || nota1D > 5){
                return "Nota1 no esta en el rango de 0 a 5";
            }else{
                notas[0] = nota1D;
            }
        }catch(NumberFormatException e){
            System.out.println(e);
            return "Nota1 no es un valor valido";
        }
        
        try{
            double nota2D = Double.parseDouble(nota2);
            if(nota2D < 0 || nota2D > 5){
                return "Nota2 no esta en el rango de 0 a 5";
            }else{
                notas[1] = nota2D;
            }
        }catch(NumberFormatException e){
            System.out.println(e);
            return "Nota2 no es un valor valido";
        }
        
        try{
            double nota3D = Double.parseDouble(nota3);
            if(nota3D < 0 || nota3D > 5){
                return "Nota3 no esta en el rango de 0 a 5";
            }else{
                notas[2] = nota3D;
            }
        }catch(NumberFormatException e){
            System.out.println(e);
            return "Nota3 no es un valor valido";
        }
        
        
        //Luego de validar creo el estudiante con sus datos y ejecuto sus calculos
        Estudiante estudiante;
        estudiante = (internacional) ? (new Estudiante(nombre, Integer.parseInt(edad), Integer.parseInt(estrato), identificacion)):new EstudianteInternacional(nombre, Integer.parseInt(edad), Integer.parseInt(estrato), identificacion);
        
        //Obtengo promedio y descuentos
        estudiante.setInternacional(internacional);
        estudiante.setNotas(notas);
        estudiante.calcularPromedio();
        estudiante.validarTieneDescuento();
        
                
        if(estudianteDao.create(estudiante)){
            mensaje = "Estudiante registrado correctamente!";
        }else{
            mensaje = "No se pudo insertar el estudiante, contacta administrador!";
        }
            
        return mensaje;
    }
    
    public void resultados(){
        ArrayList<Estudiante> estudiantes = estudianteDao.read();
        int valorTotal = estudianteDao.getTotal();
        
        //Lleno tabla de vista con datos obtenidos
        modelo = new DefaultTableModel();    
	Registro.tabla.setModel(modelo);
        
        modelo.addColumn("NOMBRE");
        modelo.addColumn("IDENTIDAD");
        modelo.addColumn("EDAD");
        modelo.addColumn("ESTRATO");
        modelo.addColumn("NOTA 1");
        modelo.addColumn("NOTA 2");
        modelo.addColumn("NOTA 3");
        modelo.addColumn("INTERNACIONAL");
        modelo.addColumn("PROMEDIO");
        modelo.addColumn("TIENE DESCUENTO");
        modelo.addColumn("DESCUENTO");
        
        Object[] fila = new Object[11];
        for (Estudiante estudiante: estudiantes) {
            fila[0] = estudiante.getNombre();
            fila[1] = estudiante.getIdentidad();
            fila[2] = estudiante.getEdad();
            fila[3] = estudiante.getEstrato();
            fila[4] = estudiante.getNotas()[0];
            fila[5] = estudiante.getNotas()[1];
            fila[6] = estudiante.getNotas()[2];
            fila[7] = (estudiante.isInternacional())?"SI":"NO";
            fila[8] = estudiante.getPromedio();
            fila[9] = (estudiante.isTieneDescuento())?"SI":"NO";
            fila[10] = estudiante.getValorDescontado();
            
            modelo.addRow(fila);
        } 
        
        //Lleno campo total
        Registro.totalDescuento.setText(String.valueOf(valorTotal));
        
    } 
}
