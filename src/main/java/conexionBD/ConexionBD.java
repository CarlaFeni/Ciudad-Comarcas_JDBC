package conexionBD;

import Repository.CiudadRepository.CiudadRepository;
import Repository.ComarcaRepository.ComarcaRepository;
import model.Ciudad;
import model.Comarca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class ConexionBD {

    public static void main(String[] args) {
        // URL de conexión a la base de datos
        String url = "jdbc:mysql://localhost:3306/Comarcas";
        String usuario = "root";
        String contrasena = "admin";

        Connection conexion = null;

        try {
            // Establece la conexión
            conexion = DriverManager.getConnection(url, usuario, contrasena);
            System.out.println("Conexión exitosa a la base de datos!");

            ComarcaRepository comarcaRepo = new ComarcaRepository(conexion);
            CiudadRepository ciudadRepo = new CiudadRepository(conexion);

            // Crear una nueva ciudad
            Ciudad ciudad = new Ciudad(0, "Ciudad Ejemplo", 12345, 10000, 50.0f, 0);

            System.out.println("CIUDADES GUARDADAS:");
            ciudadRepo.save(ciudad);
            System.out.println(ciudad.getNombre());

            System.out.println("-------------------------------------------------");
            // Listar todas las ciudades
            System.out.println("CIUDADES EN LA BASE DE DATOS:");
            List<Ciudad> ciudades = ciudadRepo.findAll();
            for (Ciudad c : ciudades) {
                System.out.println(c.getNombre() + " con " + c.getHabitantes() + " habitantes.");
            }
            // Crear una nueva comarca
            System.out.println("COMARCAS GUARDADAS:");
            Comarca comarca = new Comarca(0, "Comarca Ejemplo", "Provincia Ejemplo", ciudades);
            comarcaRepo.save(comarca);
            System.out.println(comarca.getNombre() + " con ID: " + comarca.getId_comarca());

            System.out.println("-------------------------------------------------");
            // Listar todas las comarcas
            List<Comarca> comarcas = comarcaRepo.findAll();
            System.out.println("COMARCAS EN LA BASE DE DATOS:");
            for (Comarca c : comarcas) {
                System.out.println(c.getNombre() + " en " + c.getProvincia());
            }

            // Buscar una comarca por ID
            System.out.println("-------------------------------------------------");
            System.out.println("COMARCA BUSCADA POR ID:");
            Ciudad comarcaBuscada = ciudadRepo.findById(2);
            System.out.println((comarcaBuscada != null ? comarcaBuscada.getNombre() : "No encontrada"));

            // Eliminar una ciudad
            System.out.println("-------------------------------------------------");
            System.out.println("CIUDAD ELIMINADA: ");
            ciudadRepo.delete(ciudad.getId_ciudad());
            System.out.println(ciudad.getNombre());

            // Eliminar una comarca
            System.out.println("-------------------------------------------------");
            System.out.println("COAMRCA ELIMINADA: ");
            comarcaRepo.delete(comarca.getId_comarca());
            System.out.println(comarca.getNombre());

        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos:");
            e.printStackTrace();
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                    System.out.println("Conexión cerrada.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}