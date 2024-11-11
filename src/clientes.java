import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un cliente y sus operaciones CRUD
 */
public class clientes {
    // Atributos del cliente
    private int idCliente;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;

    // Constructor vacío
    public clientes() {}

    // Constructor con parámetros
    public clientes(int idCliente, String nombre, String apellido, String telefono, String email) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
    }

    // Getters y Setters
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Métodos CRUD
    public void crear() {
        String sql = "INSERT INTO Cliente (nombre, apellido, telefono, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, telefono);
            stmt.setString(4, email);
            
            stmt.executeUpdate();
            
            // Obtener el ID generado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.idCliente = rs.getInt(1);
            }
            System.out.println("Cliente creado exitosamente");
        } catch (SQLException e) {
            System.out.println("Error al crear el cliente: " + e.getMessage());
        }
    }

    public void actualizar() {
        String sql = "UPDATE Cliente SET nombre=?, apellido=?, telefono=?, email=? WHERE id_cliente=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, telefono);
            stmt.setString(4, email);
            stmt.setInt(5, idCliente);
            
            stmt.executeUpdate();
            System.out.println("Cliente actualizado exitosamente");
        } catch (SQLException e) {
            System.out.println("Error al actualizar el cliente: " + e.getMessage());
        }
    }

    public void eliminar() {
        String sql = "DELETE FROM Cliente WHERE id_cliente=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idCliente);
            stmt.executeUpdate();
            System.out.println("Cliente eliminado exitosamente");
        } catch (SQLException e) {
            System.out.println("Error al eliminar el cliente: " + e.getMessage());
        }
    }

    // Métodos estáticos para búsqueda
    public static clientes buscarPorId(int id) {
        String sql = "SELECT * FROM Cliente WHERE id_cliente=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new clientes(
                    rs.getInt("id_cliente"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono"),
                    rs.getString("email")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el cliente: " + e.getMessage());
        }
        return null;
    }

    public static List<clientes> listarTodos() {
        List<clientes> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";
        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                clientes.add(new clientes(
                    rs.getInt("id_cliente"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono"),
                    rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar los clientes: " + e.getMessage());
        }
        return clientes;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "ID=" + idCliente +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
