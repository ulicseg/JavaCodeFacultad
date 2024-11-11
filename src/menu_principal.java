import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class menu_principal {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== SISTEMA DE GESTIÓN DE TIENDA DE ROPA ===");
            System.out.println("1. Gestión de Productos");
            System.out.println("2. Gestión de Clientes");
            System.out.println("3. Gestión de Ventas");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    menuProductos();
                    break;
                case 2:
                    menuClientes();
                    break;
                case 3:
                    menuVentas();
                    break;
                case 4:
                    salir = true;
                    System.out.println("¡Gracias por usar el sistema!");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
        scanner.close();
        ConexionBD.cerrarConexion();
    }

    // Menú de Productos
    private static void menuProductos() {
        while (true) {
            System.out.println("\n=== GESTIÓN DE PRODUCTOS ===");
            System.out.println("1. Agregar producto");
            System.out.println("2. Buscar producto");
            System.out.println("3. Actualizar producto");
            System.out.println("4. Eliminar producto");
            System.out.println("5. Listar todos los productos");
            System.out.println("6. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    agregarProducto();
                    break;
                case 2:
                    buscarProducto();
                    break;
                case 3:
                    actualizarProducto();
                    break;
                case 4:
                    eliminarProducto();
                    break;
                case 5:
                    listarProductos();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }

    // Menú de Clientes
    private static void menuClientes() {
        while (true) {
            System.out.println("\n=== GESTIÓN DE CLIENTES ===");
            System.out.println("1. Agregar cliente");
            System.out.println("2. Buscar cliente");
            System.out.println("3. Actualizar cliente");
            System.out.println("4. Eliminar cliente");
            System.out.println("5. Listar todos los clientes");
            System.out.println("6. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    agregarCliente();
                    break;
                case 2:
                    buscarCliente();
                    break;
                case 3:
                    actualizarCliente();
                    break;
                case 4:
                    eliminarCliente();
                    break;
                case 5:
                    listarClientes();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }

    // Menú de Ventas
    private static void menuVentas() {
        while (true) {
            System.out.println("\n=== GESTIÓN DE VENTAS ===");
            System.out.println("1. Nueva venta");
            System.out.println("2. Buscar venta");
            System.out.println("3. Listar todas las ventas");
            System.out.println("4. Ver detalles de venta");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    nuevaVenta();
                    break;
                case 2:
                    buscarVenta();
                    break;
                case 3:
                    listarVentas();
                    break;
                case 4:
                    verDetallesVenta();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }

    // Métodos para gestión de Productos
    private static void agregarProducto() {
        System.out.println("\n=== AGREGAR NUEVO PRODUCTO ===");
        Producto producto = new Producto();
        
        System.out.print("Nombre: ");
        producto.setNombre(scanner.nextLine());
        
        System.out.print("Descripción: ");
        producto.setDescripcion(scanner.nextLine());
        
        System.out.print("Precio: ");
        producto.setPrecio(new BigDecimal(scanner.nextLine()));
        
        System.out.print("Stock: ");
        producto.setStock(scanner.nextInt());
        
        producto.crear();
    }

    private static void buscarProducto() {
        System.out.print("\nIngrese el ID del producto: ");
        int id = scanner.nextInt();
        Producto producto = Producto.buscarPorId(id);
        if (producto != null) {
            System.out.println(producto);
        } else {
            System.out.println("Producto no encontrado");
        }
    }

    private static void actualizarProducto() {
        System.out.print("\nIngrese el ID del producto a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        
        Producto producto = Producto.buscarPorId(id);
        if (producto != null) {
            System.out.println("Producto actual: " + producto);
            
            System.out.print("Nuevo nombre (Enter para mantener actual): ");
            String nombre = scanner.nextLine();
            if (!nombre.isEmpty()) producto.setNombre(nombre);
            
            System.out.print("Nueva descripción (Enter para mantener actual): ");
            String descripcion = scanner.nextLine();
            if (!descripcion.isEmpty()) producto.setDescripcion(descripcion);
            
            System.out.print("Nuevo precio (Enter para mantener actual): ");
            String precio = scanner.nextLine();
            if (!precio.isEmpty()) producto.setPrecio(new BigDecimal(precio));
            
            System.out.print("Nuevo stock (0 para mantener actual): ");
            int stock = scanner.nextInt();
            if (stock > 0) producto.setStock(stock);
            
            producto.actualizar();
        } else {
            System.out.println("Producto no encontrado");
        }
    }

    private static void eliminarProducto() {
        System.out.print("\nIngrese el ID del producto a eliminar: ");
        int id = scanner.nextInt();
        
        Producto producto = Producto.buscarPorId(id);
        if (producto != null) {
            System.out.println("¿Está seguro de eliminar este producto? (S/N)");
            System.out.println(producto);
            if (scanner.next().equalsIgnoreCase("S")) {
                producto.eliminar();
            }
        } else {
            System.out.println("Producto no encontrado");
        }
    }

    private static void listarProductos() {
        System.out.println("\n=== LISTA DE PRODUCTOS ===");
        List<Producto> productos = Producto.listarTodos();
        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }

    // Métodos para gestión de Clientes
    private static void agregarCliente() {
        System.out.println("\n=== AGREGAR NUEVO CLIENTE ===");
        clientes cliente = new clientes();
        
        System.out.print("Nombre: ");
        cliente.setNombre(scanner.nextLine());
        
        System.out.print("Apellido: ");
        cliente.setApellido(scanner.nextLine());
        
        System.out.print("Teléfono: ");
        cliente.setTelefono(scanner.nextLine());
        
        System.out.print("Email: ");
        cliente.setEmail(scanner.nextLine());
        
        cliente.crear();
    }

    private static void buscarCliente() {
        System.out.print("\nIngrese el ID del cliente: ");
        int id = scanner.nextInt();
        clientes cliente = clientes.buscarPorId(id);
        if (cliente != null) {
            System.out.println(cliente);
        } else {
            System.out.println("Cliente no encontrado");
        }
    }

    private static void actualizarCliente() {
        System.out.print("\nIngrese el ID del cliente a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        
        clientes cliente = clientes.buscarPorId(id);
        if (cliente != null) {
            System.out.println("Cliente actual: " + cliente);
            
            System.out.print("Nuevo nombre (Enter para mantener actual): ");
            String nombre = scanner.nextLine();
            if (!nombre.isEmpty()) cliente.setNombre(nombre);
            
            System.out.print("Nuevo apellido (Enter para mantener actual): ");
            String apellido = scanner.nextLine();
            if (!apellido.isEmpty()) cliente.setApellido(apellido);
            
            System.out.print("Nuevo teléfono (Enter para mantener actual): ");
            String telefono = scanner.nextLine();
            if (!telefono.isEmpty()) cliente.setTelefono(telefono);
            
            System.out.print("Nuevo email (Enter para mantener actual): ");
            String email = scanner.nextLine();
            if (!email.isEmpty()) cliente.setEmail(email);
            
            cliente.actualizar();
        } else {
            System.out.println("Cliente no encontrado");
        }
    }

    private static void eliminarCliente() {
        System.out.print("\nIngrese el ID del cliente a eliminar: ");
        int id = scanner.nextInt();
        
        clientes cliente = clientes.buscarPorId(id);
        if (cliente != null) {
            System.out.println("¿Está seguro de eliminar este cliente? (S/N)");
            System.out.println(cliente);
            if (scanner.next().equalsIgnoreCase("S")) {
                cliente.eliminar();
            }
        } else {
            System.out.println("Cliente no encontrado");
        }
    }

    private static void listarClientes() {
        System.out.println("\n=== LISTA DE CLIENTES ===");
        List<clientes> listaClientes = clientes.listarTodos();
        for (clientes cliente : listaClientes) {
            System.out.println(cliente);
        }
    }

    // Métodos para gestión de Ventas
    private static void nuevaVenta() {
        System.out.println("\n=== NUEVA VENTA ===");
        venta venta = new venta();
        
        System.out.print("ID del cliente: ");
        venta.setIdCliente(scanner.nextInt());
        
        venta.crear();
        
        char continuar;
        BigDecimal total = BigDecimal.ZERO;
        
        do {
            detalle_venta detalle = new detalle_venta();
            detalle.setIdVenta(venta.getIdVenta());
            
            System.out.print("ID del producto: ");
            detalle.setIdProducto(scanner.nextInt());
            
            System.out.print("Cantidad: ");
            detalle.setCantidad(scanner.nextInt());
            
            Producto producto = Producto.buscarPorId(detalle.getIdProducto());
            if (producto != null) {
                detalle.setPrecioUnitario(producto.getPrecio());
                detalle.calcularSubtotal();
                detalle.crear();
                
                total = total.add(detalle.getSubtotal());
            }
            
            System.out.print("¿Agregar otro producto? (S/N): ");
            continuar = scanner.next().toUpperCase().charAt(0);
            
        } while (continuar == 'S');
        
        venta.setTotal(total);
        venta.actualizar();
        
        System.out.println("Venta finalizada. Total: " + total);
    }

    private static void buscarVenta() {
        System.out.print("\nIngrese el ID de la venta: ");
        int id = scanner.nextInt();
        venta ventaEncontrada = venta.buscarPorId(id);
        if (ventaEncontrada != null) {
            System.out.println(ventaEncontrada);
            verDetallesVenta(id);
        } else {
            System.out.println("Venta no encontrada");
        }
    }

    private static void listarVentas() {
        System.out.println("\n=== LISTA DE VENTAS ===");
        List<venta> ventas = venta.listarTodos();
        for (venta venta : ventas) {
            System.out.println(venta);
        }
    }

    private static void verDetallesVenta(int id) {
        List<detalle_venta> detalles = detalle_venta.listarPorVenta(id);
        if (!detalles.isEmpty()) {
            System.out.println("\nDetalles de la venta #" + id);
            for (detalle_venta detalle : detalles) {
                System.out.println(detalle);
            }
        } else {
            System.out.println("No se encontraron detalles para esta venta");
        }
    }

    private static void verDetallesVenta() {
        System.out.print("\nIngrese el ID de la venta: ");
        int id = scanner.nextInt();
        verDetallesVenta(id);
    }
}
