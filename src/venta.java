import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una venta y sus operaciones CRUD
 */
public class venta {
    // Atributos de la venta
    private int idVenta;
    private int idCliente;
    private LocalDateTime fechaVenta;
    private BigDecimal total;

    // Constructor vacío
    public venta() {
        this.fechaVenta = LocalDateTime.now();
        this.total = BigDecimal.ZERO;
    }

    // Constructor con parámetros
    public venta(int idVenta, int idCliente, LocalDateTime fechaVenta, BigDecimal total) {
        this.idVenta = idVenta;
        this.idCliente = idCliente;
        this.fechaVenta = fechaVenta;
        this.total = total;
    }

    // Getters y Setters
    public int getIdVenta() { return idVenta; }
    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public LocalDateTime getFechaVenta() { return fechaVenta; }
    public void setFechaVenta(LocalDateTime fechaVenta) { this.fechaVenta = fechaVenta; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    // Métodos CRUD
    public void crear() {
        String sql = "INSERT INTO Venta (id_cliente, fecha_venta, total) VALUES (?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, idCliente);
            stmt.setTimestamp(2, Timestamp.valueOf(fechaVenta));
            stmt.setBigDecimal(3, total);
            
            stmt.executeUpdate();
            
            // Obtener el ID generado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.idVenta = rs.getInt(1);
            }
            System.out.println("Venta creada exitosamente");
        } catch (SQLException e) {
            System.out.println("Error al crear la venta: " + e.getMessage());
        }
    }

    public void actualizar() {
        String sql = "UPDATE Venta SET id_cliente=?, fecha_venta=?, total=? WHERE id_venta=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idCliente);
            stmt.setTimestamp(2, Timestamp.valueOf(fechaVenta));
            stmt.setBigDecimal(3, total);
            stmt.setInt(4, idVenta);
            
            stmt.executeUpdate();
            System.out.println("Venta actualizada exitosamente");
        } catch (SQLException e) {
            System.out.println("Error al actualizar la venta: " + e.getMessage());
        }
    }

    public void eliminar() {
        String sql = "DELETE FROM Venta WHERE id_venta=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idVenta);
            stmt.executeUpdate();
            System.out.println("Venta eliminada exitosamente");
        } catch (SQLException e) {
            System.out.println("Error al eliminar la venta: " + e.getMessage());
        }
    }

    // Métodos estáticos para búsqueda
    public static venta buscarPorId(int id) {
        String sql = "SELECT * FROM Venta WHERE id_venta=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new venta(
                    rs.getInt("id_venta"),
                    rs.getInt("id_cliente"),
                    rs.getTimestamp("fecha_venta").toLocalDateTime(),
                    rs.getBigDecimal("total")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar la venta: " + e.getMessage());
        }
        return null;
    }

    public static List<venta> listarTodos() {
        List<venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM Venta";
        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                ventas.add(new venta(
                    rs.getInt("id_venta"),
                    rs.getInt("id_cliente"),
                    rs.getTimestamp("fecha_venta").toLocalDateTime(),
                    rs.getBigDecimal("total")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar las ventas: " + e.getMessage());
        }
        return ventas;
    }

    @Override
    public String toString() {
        return "Venta{" +
                "ID=" + idVenta +
                ", idCliente=" + idCliente +
                ", fecha='" + fechaVenta + '\'' +
                ", total=" + total +
                '}';
    }
}
