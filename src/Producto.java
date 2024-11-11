import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un producto y sus operaciones CRUD
 */
public class Producto {
    // Atributos del producto
    private int idProducto;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private int stock;

    // Constructor vacío
    public Producto() {}

    // Constructor con parámetros
    public Producto(int idProducto, String nombre, String descripcion, BigDecimal precio, int stock) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters y Setters básicos
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // Métodos CRUD
    public void crear() {
        String sql = "INSERT INTO Producto (nombre, descripcion, precio, stock) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, this.nombre);
            stmt.setString(2, this.descripcion);
            stmt.setBigDecimal(3, this.precio);
            stmt.setInt(4, this.stock);
            
            stmt.executeUpdate();
            
            // Obtener el ID generado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.idProducto = rs.getInt(1);
            }
            System.out.println("Producto creado exitosamente");
        } catch (SQLException e) {
            System.out.println("Error al crear el producto: " + e.getMessage());
        }
    }

    public void actualizar() {
        String sql = "UPDATE Producto SET nombre=?, descripcion=?, precio=?, stock=? WHERE id_producto=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, this.nombre);
            stmt.setString(2, this.descripcion);
            stmt.setBigDecimal(3, this.precio);
            stmt.setInt(4, this.stock);
            stmt.setInt(5, this.idProducto);
            
            stmt.executeUpdate();
            System.out.println("Producto actualizado exitosamente");
        } catch (SQLException e) {
            System.out.println("Error al actualizar el producto: " + e.getMessage());
        }
    }

    public void eliminar() {
        String sql = "DELETE FROM Producto WHERE id_producto=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, this.idProducto);
            stmt.executeUpdate();
            System.out.println("Producto eliminado exitosamente");
        } catch (SQLException e) {
            System.out.println("Error al eliminar el producto: " + e.getMessage());
        }
    }

    // Métodos estáticos para buscar productos
    public static Producto buscarPorId(int id) {
        String sql = "SELECT * FROM Producto WHERE id_producto=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Producto(
                    rs.getInt("id_producto"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getBigDecimal("precio"),
                    rs.getInt("stock")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el producto: " + e.getMessage());
        }
        return null;
    }

    public static List<Producto> listarTodos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM Producto";
        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                productos.add(new Producto(
                    rs.getInt("id_producto"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getBigDecimal("precio"),
                    rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar los productos: " + e.getMessage());
        }
        return productos;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "ID=" + idProducto +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                '}';
    }
}
