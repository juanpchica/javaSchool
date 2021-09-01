package instituto.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexion {
    private Connection conector;
    
    public Conexion(){
        conectar();
    }
    
    private void conectar(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conector = DriverManager.getConnection("jdbc:mysql://localhost:3306/escuela?autoReconnect=true", "root","");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public ResultSet get(String sql){
        try{
            return conector.createStatement().executeQuery(sql);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public boolean set(String sql){
        try{
            conector.createStatement().execute(sql);
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public int getIdRow(String sql){
        int primkey = 0 ;
           
        try {

            String columnNames[] = new String[] { "id" };

            PreparedStatement pstmt = conector.prepareStatement( sql, columnNames );

            if (pstmt.executeUpdate() > 0) {
                // Retrieves any auto-generated keys created as a result of executing this Statement object
                java.sql.ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if ( generatedKeys.next() ) {
                    primkey = generatedKeys.getInt(1);
                }
            }
            System.out.println("Record updated with id = "+primkey);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        return primkey;
        
    }
    
}
