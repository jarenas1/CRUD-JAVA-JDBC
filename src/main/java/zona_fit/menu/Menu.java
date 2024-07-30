package zona_fit.menu;

import zona_fit.datos.ClienteDAO;
import zona_fit.dominio.Cliente;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Menu extends ClienteDAO {
    public static void main(String[] args) {
        boolean menu = true;

        while (menu){
            System.out.println("-----MENU-----");
            System.out.println("----Ingrese una opcion----");
            System.out.println("1. listar clientes");
            System.out.println("2. Buscar cliente por id");
            System.out.println("3. Eliminar cliente");
            System.out.println("4. Añadir cliente");
            System.out.println("5. Actualizar");
            System.out.println("6. Salir");
            Scanner sc = new Scanner(System.in);
            int op = sc.nextInt();
            sc.nextLine();
            ClienteDAO clienteDAO = new ClienteDAO();

            if (op == 1){
                System.out.println("-----Listado de clientes-----");
                List clientes = clienteDAO.listarClientes();
                for (Object cliente : clientes){
                    System.out.println(cliente);
                }
            }else if (op == 2){
                System.out.println("----Buscar cliente por id----");
                System.out.println("Ingrese el id del cliente que desea buscar");
                int id = sc.nextInt();
                sc.nextLine();
                Cliente cliente = new Cliente(id);
                clienteDAO.buscarId(cliente);
                System.out.println(cliente);

            } else if (op == 3) {
                System.out.println("----Eliminar cliente----");
                System.out.println("Ingrese el id del cliente a eliminar");
                int id = sc.nextInt();
                sc.nextLine();
                Cliente cliente = new Cliente(id);
                boolean eliminado = clienteDAO.eliminarCliente(cliente);
                if (eliminado){
                    System.out.println("Usuario eliminado");
                }else{
                    System.out.println("No se ha podido eliminar el usuario");
                }
            } else if (op == 4) {
                System.out.println("----Añadir cliente----");
                System.out.println("Ingrese el nombre del cliente");
                String name = sc.nextLine();
                System.out.println("Ingrese el apellido del cliente");
                String lastName = sc.nextLine();
                System.out.println("Ingrese el tipo de membresia del usuario");
                int membership = sc.nextInt();
                sc.nextLine();
                Cliente cliente = new Cliente(membership,lastName,name);
                boolean creado = clienteDAO.añadirCliente(cliente);
                System.out.println(cliente);
            } else if (op == 5) {
                
            }
        }
    }
}
