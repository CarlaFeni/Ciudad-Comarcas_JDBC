package Repository.CiudadRepository;

import model.Ciudad;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CiudadRepository {

    private final Connection connection;

    public CiudadRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Ciudad> findAll() throws SQLException {
        List<Ciudad> ciudades = new ArrayList<>();
        String sql = """
                SELECT c.id_ciudad, c.nombre, c.cp, c.habitantes, c.extension,
                       co.id_comarca, co.nombre AS comarca_nombre, co.provincia 
                FROM ciudad c
                LEFT JOIN comarca co ON c.id_ciudad = co.id_ciudad
                """;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {

                Ciudad ciudad = new Ciudad(rs.getInt("id_ciudad"),
                        rs.getString("nombre"),
                        rs.getInt("cp"),
                        rs.getInt("habitantes"),
                        rs.getFloat("extension"),
                        rs.getInt("id_comarca")); // Incluir comarca aquí si es necesario
                ciudades.add(ciudad);
            }
        }
        return ciudades;
    }

    public Ciudad findById(int id) throws SQLException {
        String sql = """
                SELECT c.id_ciudad, c.nombre, c.cp, c.habitantes, c.extension,
                       co.id_comarca, co.nombre AS comarca_nombre, co.provincia 
                FROM ciudad c
                LEFT JOIN comarca co ON c.id_ciudad = co.id_ciudad 
                WHERE c.id_ciudad = ?
                """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Ciudad(rs.getInt("id_ciudad"),
                            rs.getString("nombre"),
                            rs.getInt("cp"),
                            rs.getInt("habitantes"),
                            rs.getFloat("extension"),
                            rs.getInt("id_comarca"));
                }
            }
        }
        return null;
    }

    public void save(Ciudad ciudad) throws SQLException {
        String sql = "INSERT INTO ciudad (nombre, cp, habitantes, extension) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, ciudad.getNombre());
            stmt.setInt(2, ciudad.getCp());
            stmt.setInt(3, ciudad.getHabitantes());
            stmt.setFloat(4, ciudad.getExtension());
            stmt.executeUpdate();


            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ciudad.setId_ciudad(generatedKeys.getInt(1));
                }
            }
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM ciudad WHERE id_ciudad = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
