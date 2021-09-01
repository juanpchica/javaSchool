package instituto.modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EstudianteDao implements IDAO{
    Conexion conexion;
    
    public EstudianteDao(){
        conexion = new Conexion();
    }
    
    @Override
    public boolean create(Estudiante estudiante) {
        String sql = "insert into estudiantes (nombre,identidad,edad,estrato,nota1,nota2,nota3,internacional,tieneDescuento,promedio) value ('"+estudiante.getNombre()+"','"+estudiante.getIdentidad()+"',"+estudiante.getEdad()+","+estudiante.getEstrato()+","+estudiante.getNotas()[0]+","+estudiante.getNotas()[1]+","+estudiante.getNotas()[2]+",'"
                + (estudiante.isInternacional()?"si":"no") + "', '"+(estudiante.isTieneDescuento()?"si":"no")+"',"+estudiante.getPromedio()+")";
        
        try {
            //Obtengo id insertado
            int id = conexion.getIdRow(sql);

            //Inserto descuento para el estudiante
            if(estudiante.isTieneDescuento()){
                String sqlDescuento = "insert into descuentos (idEstudiante,valorDescontado) value ("+id+","+estudiante.getValorDescontado()+")";
                conexion.set(sqlDescuento);
                return true;
            }
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
        
        return false;
    }

    @Override
    public boolean update(int id, Estudiante grado) {
        return true;
    }

    @Override
    public ArrayList<Estudiante> read() {
        ArrayList<Estudiante> estudiantes = new ArrayList<>();
        Estudiante estudiante;
        ResultSet result = conexion.get("select e.*,d.valorDescontado from estudiantes e LEFT OUTER JOIN descuentos d on e.id = d.idEstudiante ORDER BY e.id");
        try {
            if(result.next()){
                while(result.next()){
                    estudiante = new Estudiante(result.getString("nombre"),result.getInt("edad"),result.getInt("estrato"),result.getString("identidad"));
                    estudiante.setNotas(new double[]{result.getDouble("nota1"),result.getDouble("nota2"),result.getDouble("nota3")});
                    estudiante.setPromedio(result.getDouble("promedio"));
                    estudiante.setInternacional(result.getString("internacional").equals("si"));
                    estudiante.setTieneDescuento(result.getString("tieneDescuento").equals("si"));
                    estudiante.setValorDescontado(result.getDouble("valorDescontado"));
                    
                    estudiantes.add(estudiante);
                }
            }else{
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return estudiantes;
    }

    @Override
    public Estudiante get(int id) {
        return null;
    }
    
    public int getTotal(){
        int total = 0;
        ResultSet result = conexion.get("select sum(valorDescontado) as total from descuentos");
        try {
            if(result.next()){
                total = result.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return total;
    }
    
}
