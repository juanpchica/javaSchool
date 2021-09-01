package instituto.modelo;

import java.util.ArrayList;

public interface IDAO {
    public boolean create(Estudiante estudiante);
    public boolean update(int id, Estudiante estudiante);
    public ArrayList<Estudiante> read();
    public Estudiante get(int id);
}
