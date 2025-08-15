package Repository.ComarcaRepository;

import model.Comarca;
import model.Ciudad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComarcaRepository {

    private final Connection connection;

    public ComarcaRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Comarca> findAll() throws SQLException {
        List<Comarca> comarcas = new ArrayList<>();
        String sql = """
                SELECT co.id_comarca, co.nombre, co.provincia, c.id_ciudad, c.nombre AS ciudad_nombre 
                FROM Comarca co
                LEFT JOIN Ciudad c ON co.id_ciudad = c.id_ciudad
                """;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Comarca comarca = new Comarca(rs.getInt("id_comarca"),
                        rs.getString("nombre"),
                        rs.getString("provincia"),
                        new ArrayList<>());

                // Si hay una ciudad asociada, la agregamos a la comarca
                if (rs.getInt("id_ciudad") != 0) {
                    Ciudad ciudad = new Ciudad(rs.getInt("id_ciudad"),
                            rs.getString("ciudad_nombre"),
                            0,  // Asignamos un valor por defecto para el cp
                            0,  // Asignamos un valor por defecto para habitantes
                            0.0f, 0 // Asignamos un valor por defecto para la extensión
                    );
                    comarca.addCiudad(ciudad);
                }

                comarcas.add(comarca);
            }
        }
        return comarcas;
    }

    public Comarca findById(int id) throws SQLException {
        String sql = """
                SELECT co.id_comarca, co.nombre, co.provincia, c.id_ciudad, c.nombre AS ciudad_nombre 
                FROM Comarca co
                LEFT JOIN Ciudad c ON co.id_ciudad = c.id_ciudad 
                WHERE co.id_comarca = ?
                """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Comarca comarca = new Comarca(rs.getInt("id_comarca"),
                            rs.getString("nombre"),
                            rs.getString("provincia"),
                            new ArrayList<>());

                    // Si hay una ciudad asociada, la agregamos a la comarca
                    if (rs.getInt("id_ciudad") != 0) {
                        Ciudad ciudad = new Ciudad(rs.getInt("id_ciudad"),
                                rs.getString("ciudad_nombre"),
                                0,  // Asignamos un valor por defecto para el cp
                                0,  // Asignamos un valor por defecto para habitantes
                                0.0f, 0 // Asignamos un valor por defecto para la extensión
                        );
                        comarca.addCiudad(ciudad);
                    }
                    return comarca;
                }
            }
        }
        return null;
    }

    public void save(Comarca comarca) throws SQLException {

        // Inserción en la tabla Comarca
        String sql = "INSERT INTO Comarca (nombre, provincia, id_ciudad) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, comarca.getNombre());
            stmt.setString(2, comarca.getProvincia());
            stmt.setInt(3, comarca.getCiudad().get(0).getId_ciudad());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Comarca WHERE id_comarca = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
