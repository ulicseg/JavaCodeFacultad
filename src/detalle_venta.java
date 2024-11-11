import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa el detalle de una venta y sus operaciones CRUD
 */
public class detalle_venta {
    // Atributos del detalle de venta
    private int idDetalle;
    private int idVenta;
    private int idProducto;
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    // Constructor vacío
    public detalle_venta() {
        this.precioUnitario = BigDecimal.ZERO;
        this.subtotal = BigDecimal.ZERO;
    }

    // Constructor con parámetros
    public detalle_venta(int idDetalle, int idVenta, int idProducto, int cantidad, 
                        BigDecimal precioUnitario, BigDecimal subtotal) {
        this.idDetalle = idDetalle;
        this.idVenta = idVenta;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    // Getters y Setters
    public int getIdDetalle() { return idDetalle; }
    public void setIdDetalle(int idDetalle) { this.idDetalle = idDetalle; }
    public int getIdVenta() { return idVenta; }
    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }
    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    // Método para calcular el subtotal
    public void calcularSubtotal() {
        this.subtotal = this.precioUnitario.multiply(new BigDecimal(this.cantidad));
    }

    // Métodos CRUD
    public void crear() {
        String sql = "INSERT INTO DetalleVenta (id_venta, id_producto, cantidad, precio_unitario, subtotal) " +
                    "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, idVenta);
            stmt.setInt(2, idProducto);
            stmt.setInt(3, cantidad);
            stmt.setBigDecimal(4, precioUnitario);
            stmt.setBigDecimal(5, subtotal);
            
            stmt.executeUpdate();
            
            // Obtener el ID generado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.idDetalle = rs.getInt(1);
            }
            System.out.println("Detalle de venta creado exitosamente");
        } catch (SQLException e) {
            System.out.println("Error al crear el detalle de venta: " + e.getMessage());
        }
    }

    public void actualizar() {
        String sql = "UPDATE DetalleVenta SET id_venta=?, id_producto=?, cantidad=?, " +
                    "precio_unitario=?, subtotal=? WHERE id_detalle=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idVenta);
            stmt.setInt(2, idProducto);
            stmt.setInt(3, cantidad);
            stmt.setBigDecimal(4, precioUnitario);
            stmt.setBigDecimal(5, subtotal);
            stmt.setInt(6, idDetalle);
            
            stmt.executeUpdate();
            System.out.println("Detalle de venta actualizado exitosamente");
        } catch (SQLException e) {
            System.out.println("Error al actualizar el detalle de venta: " + e.getMessage());
        }
    }

    public void eliminar() {
        String sql = "DELETE FROM DetalleVenta WHERE id_detalle=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idDetalle);
            stmt.executeUpdate();
            System.out.println("Detalle de venta eliminado exitosamente");
        } catch (SQLException e) {
            System.out.println("Error al eliminar el detalle de venta: " + e.getMessage());
        }
    }

    // Métodos estáticos para búsqueda
    public static detalle_venta buscarPorId(int id) {
        String sql = "SELECT * FROM DetalleVenta WHERE id_detalle=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new detalle_venta(
                    rs.getInt("id_detalle"),
                    rs.getInt("id_venta"),
                    rs.getInt("id_producto"),
                    rs.getInt("cantidad"),
                    rs.getBigDecimal("precio_unitario"),
                    rs.getBigDecimal("subtotal")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el detalle de venta: " + e.getMessage());
        }
        return null;
    }

    public static List<detalle_venta> listarPorVenta(int idVenta) {
        List<detalle_venta> detalles = new ArrayList<>();
        String sql = "SELECT * FROM DetalleVenta WHERE id_venta = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idVenta);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                detalles.add(new detalle_venta(
                    rs.getInt("id_detalle"),
                    rs.getInt("id_venta"),
                    rs.getInt("id_producto"),
                    rs.getInt("cantidad"),
                    rs.getBigDecimal("precio_unitario"),
                    rs.getBigDecimal("subtotal")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar los detalles de venta: " + e.getMessage());
        }
        return detalles;
    }

    @Override
    public String toString() {
        return "DetalleVenta{" +
                "ID=" + idDetalle +
                ", idVenta=" + idVenta +
                ", idProducto=" + idProducto +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", subtotal=" + subtotal +
                '}';
    }
}
