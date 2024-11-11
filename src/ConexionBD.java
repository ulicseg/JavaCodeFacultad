import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/tienda_ropa?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
    private static Connection conexion = null;
    
    public static Connection obtenerConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            }
            return conexion;
        } catch (SQLException e) {
            System.out.println("Error: No se pudo conectar a la base de datos - " + e.getMessage());
        }
        return null;
    }
    
    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión - " + e.getMessage());
        }
    }
} 