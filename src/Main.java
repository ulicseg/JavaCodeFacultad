/**
 * Clase principal que inicia el sistema de gestión de la tienda de ropa
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando Sistema de Gestión de Tienda de Ropa...");
        System.out.println("Conectando a la base de datos...");
        
        // Verificar la conexión a la base de datos
        if (ConexionBD.obtenerConexion() != null) {
            System.out.println("Conexión exitosa a la base de datos.");
            
            // Iniciar el menú principal
            menu_principal.main(args);
            
            // Cerrar la conexión al finalizar
            ConexionBD.cerrarConexion();
        } else {
            System.out.println("Error: No se pudo conectar a la base de datos.");
            System.out.println("Por favor, verifica la configuración en ConexionBD.java");
        }
    }
}
