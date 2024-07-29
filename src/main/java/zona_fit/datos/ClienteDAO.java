package zona_fit.datos;
//EN ESTA CLASE DE ACA VAMOS A CREAR TODOS LOS METODOS CON LAS FUNCIONALIDADES QUE VA A TENER ESTA APLICACION, POR LO QUE PRIMERO LOS CREAMOS EN LA INTERFAZ PARA LUEGO ACTUALIZARLOS

import zona_fit.conexion.Conexion;
import zona_fit.dominio.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

//IMPLEMENTAMOS LOS ABSTRACTOS
public class ClienteDAO implements IClienteDAO{


    @Override
    public List<Cliente> listarClientes() {
        //CREAMOS LA LISTA QUE VA A CONTENER LOS USUARIOS
        List<Cliente> clientes =new ArrayList<Cliente>();

        //AHORA PREPARAMOS LA QUERY CON EL PREPAREDSTATEMNET
        PreparedStatement ps;

        //RECIBIMO LA INFORMACION DE LA CONSULTA QUE HEMOS REALIZADO POR MEDIO DE RESULTSET
        ResultSet rs;

        //INICIAMOS LA CONECCION (creamos un objeto de tipo connection y le asignamos a este el valor que nos devuelva el metodo que creamos en a clase Conexion
        Connection con = Conexion.connection();

        //CREAMOS UNA VARIABLE QUE CONTENGA LA QUERY (order by id los ordena y ya)
        String query = "SELECT * FROM cliente ORDER BY id";

        //lo siguiente va aca ya que puede fallar
        try {

            //VAMOS A CREAR UN PREPARE STATEMENT POR MEDIO DE LA CONECCION y dentro del prepareStatement metemos la query
            ps = con.prepareStatement(query);

            //EJECUTAMOS LA SENTENCIA POR MEDIO DEL METODO EXECUTE QUERY APLICANDO ESTO AL PREPARE STATEMENT, TODO ESTO SE LE ASIGNA AL RESULT SET PARA QUE LO GUARDE
            rs = ps.executeQuery();

            //AHORA VAMOS A ITERAR CADA REGISTRO PARA AÑADIRLO AL ARRAY POR MEDIO DE .NEXT(), ESYE ITERA LOS REGISTROS, Y SI NO HAY NADA QUE ITERAR VA A CANCELAR EL CICLO
            while (rs.next()){

                //AHORA VAMOS TOMANDO LOS VALORES DE CADA OBJETO USANDO EL RS COMO SI FUERA UN FOR EACH, TAMBIEN CREAMOS UN OBJETO DE TIPO PERSONA PARA IRLE AÑADIENDO LOS RESULTADOS
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id")); //el getint devuelve un entero de la columna especificada en los aprentesis

                //AHORA COMO YA SACAMOS EL ID, VAMOS A SACAR EL NOMBRE Y A ASIGNARLO A ESE OBJETO, COMO EL NOMBRE ES STRING EN VEZ DE GETINT VA GETTRING
                cliente.setName(rs.getString("nombre"));

                //APELLIDO
                cliente.setLastName(rs.getString("apellido"));

                //MEMBRESIA
                cliente.setMemership(rs.getInt("membresia"));

                //AHORA COMO YA CREAMOS EL CLIENTE CON TODAS SUS CARACTERISTICAS LOAÑADIMOS AL ARRAY DE CLIENTES
                clientes.add(cliente);

                //RECORDAR QUE ESTO SE VA A ITERAR HASTA QUE EL .NEXT DE FALSO PORQUE NI TENGA NADA PARA ITERAR
            }
        }
        catch (Exception e){
            System.out.println("ERROR AL LISTAR CLIENTES"+e.getMessage());
        }

        //CERRAMOS LA CONECCION YA QUE ESTA NO PUEDE DEJARSE ABIERTA, COMO ESTO PUEDE FALLAR USAMOS UN TRYCATCH
        try {
            con.close();
        }catch (Exception e){
            System.out.println("La coneccion con la db no se puede serrar "+e.getMessage());
        }
        //RETORNAMOS EL ARRAY CLIENTES
        return clientes;
    }


    @Override
    public boolean buscarId(Cliente cliente) {
        //CREAMOS LAS INSTANCIAS NECESARIAS, LA DE PREPARAR LA QUERY Y LA DE OBTENER EL RESULTADO
        PreparedStatement ps;
        ResultSet rs;

        //CREAMOS LA CONECCION
        Connection con = Conexion.connection();

        //CREAMOS LA QUERY
        //PONEMOS UN SIGNO ? YA QUE EN ESTE PODEMOS INYECYAR EL VALOR DEL ID QUE DESEEMOS BUSCAR
        String query = "SELECT * FROM cliente WHERE id = ?";
        return false;
    }

    @Override
    public boolean añadirCliente(Cliente cliente) {
        return false;
    }

    @Override
    public Boolean modificarCliente(Cliente cliente) {
        return null;
    }

    @Override
    public boolean eliminarCliente(Cliente cliente) {
        return false;
    }

//    public static void main(String[] args) {
//        //LISTAR CLIENTES
//        System.out.println("---listar cadena---");
//        ClienteDAO clienteDAO = new ClienteDAO();
//        //CREAMOS UNA INSTANCIA DE CLIENTEDAO, PARA PODER LLAMAR AL METODO DE LISTAR, Y ASI AÑADIRLO A UNA VARIABLE LISTA YA QUE ESTE METODO DEVUELVE UNA LISTA
//        List clientes = clienteDAO.listarClientes();
//        for (Object iterator : clientes ){
//            System.out.println(iterator);
//        }
//    }
}
