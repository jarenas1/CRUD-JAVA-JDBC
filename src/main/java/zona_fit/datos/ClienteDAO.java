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

        //INICIAMOS LA CONEXION (creamos un objeto de tipo connection y le asignamos a este el valor que nos devuelva el metodo que creamos en a clase Conexion
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

        //AHORA VAMOS A EJECUTAR LA QUERY Y COMO ESTO PUEDE FALLAR PODEMOS USAR EL TRYCATCH
        try {
            //PREPARAMOS EL STATEMENT CON LA QUERY
            ps = con.prepareStatement(query);

            //PASAMOS EL PARAMETRO INYECTABLE DE LA QUERY, EN ESTE CASO EL ID
            ps.setInt(1, cliente.getId());  //Cuando usamos parametros inyectables estos tienen una posicion de izquiera a derecha, y comienzan en 1
            //En este caso solo hay un ?, por ende la posicion es 1, y luego indicamos que en esa posicion va a ir el id que se va a buscar
            //el cual viene del objeto de tipo Cliente (SE CREO UN CONSTRUCTOR QUE SOLO RECIBE EL ID) que se introduce como parametro al metodo, y se extrae por medio de un getter
            //EJECUTAMOS QUERY
            rs = ps.executeQuery();

            // VERIFICAMOS SI ENCONTRAMOS UN CLIENTE, MIRANDO SI HAY ALGO PARA ITERAR, Y PONIENDO LOS DATOS DEL USUARIO EN EL OBJETO DE CLIENTE QUE ENTRO
            if (rs.next()){
                //seteamos los datos
                cliente.setName(rs.getString("nombre"));
                cliente.setLastName(rs.getString("apellido"));
                cliente.setMemership(rs.getInt("membresia"));
                //devolvemos que true ya que si lo encontro
                return true;
            }
        }catch (Exception e){
            System.out.println("No se puede encontrar el cliente "+e.getMessage());
        }
        //CERRAMOS LA CONECCION EN EL TRYCATCH DEBIDO A QUE PUEDE FALLAR
        try {
            con.close();
        }catch (Exception e){
            System.out.println("Coneccion no se puede cerrar "+e.getMessage());
        }
        //DEVOLVEMOS FALSO SI NO LO ENCONTRO
        return false;
    }


    @Override
    public boolean añadirCliente(Cliente cliente) {
        //INICIAMOS LOS OBJETOS NECESARIOS
        PreparedStatement ps;
//        ResultSet rs; NO SE CREA YA QUE ESTAMOS MANDANDO Y NO TRAYENDO COSAS

        //NOS CONECTAMOS
        Connection con = Conexion.connection();

        //CREAMOS LA QUERY
        String query = "INSERT INTO cliente (nombre,apellido,membresia)VALUES(?,?,?)";

        //PREPARAMOS EL STATEMENT
        try {
            ps = con.prepareStatement(query);

            //SETEAMOS LOS VALORES INYECTABLES
            ps.setString(1,cliente.getName()); // PRIMER ?, TOMA VALOR DE NOMBRE DEL OBJETO DADO COMO PARAMETRO
            ps.setString(2,cliente.getLastName());
            ps.setInt(3,cliente.getMemership());

            //EJECUTAMOS
//            rs = ps.execute(); EN ESTE CASO NO ASIGNAMOS NADA A RESULT SET YA QUE NO NECESITAMOS TRAER NADA DE LA QUERY
            ps.execute(); //usamos el execute ya que el execute query nos sirve para traer

            //RETORNAMOS VERDADERO SI PUDIMOS ENVIAR EL USUARIO
            return true;
        }catch (Exception e){
            System.out.println("No se pudo añadir el cliente" + e.getMessage());
        }

        //CERRAMOS CONECCION
        try {
            con.close();
        }catch (Exception e){
            System.out.println("No se pudo cerrar la coneccion "+e.getMessage());
        }

        //SI NO PUDIMOS ENVIAR EL USUARIO VAMOR A RETORNAR FALSO
        return false;
    }

    @Override
    public Boolean modificarCliente(Cliente cliente) {

        //CREAMOS LAS INSTANCIAS NECESARIAS
        PreparedStatement ps;
        ResultSet rs;

        //CREAMOS LA CONEXION
        Connection con = Conexion.connection();

        //Creamos la query
        String query = "UPDATE cliente SET nombre = ?, apellido = ?, membresia = ? WHERE id = ?";

        //Preparamos el statement
        try {
            ps = con.prepareStatement(query);

            //AÑADIMOS LOS INYECTYABLES
            ps.setString(1,cliente.getName()); //AÑADIMO A CADA ? DE LA QUERY LOS DATOS DEL CLIENTE QUE ENTRA COMO PARAMETRO
            ps.setString(2,cliente.getLastName());
            ps.setInt(3,cliente.getMemership());

            //RECIBIMOS TAMBIEN EL ID A MODIFICAR DEL OBJETO DE TIPO CLIENTE INGRESADO
            ps.setInt(4,cliente.getId());

            //EJECUTAMOS LA QUERY Y DEVOLVEMOS TRUE SI TODO FUNCIONA
            ps.execute();
            return true;


        }catch (Exception e){
            System.out.println("Error al actualizar el cliente "+e.getMessage());

        }// VAMOS A USAR EL FINALLY, EL CUAL NOS PERMITE EJECUTAR UNA SENTENCIA PASE O NO POR EL CATCH:
        finally {
            //CERRAMOS CONECCION
            try {
                con.close();
            }catch (Exception e){
                System.out.println("No se ha podido cerrar la coneccion "+e.getMessage());
            }
        }

        return null;
    }

    @Override
    public boolean eliminarCliente(Cliente cliente) {
        //CREAMOS CONECCION Y OTROS
        PreparedStatement ps;
        Connection con = Conexion.connection();
        String query = "DELETE FROM cliente WHERE id = ?";

        //EJECUCION DE QUERY
        try {
            //PREPARAMOS EL STATEMENT
            ps = con.prepareStatement(query);
            //INYECTAMOS LA QUERY EN LA POSICION 1
            ps.setInt(1,cliente.getId());
            ps.execute();
            return true;
        }catch (Exception e){
            System.out.println("No se pudo eliminar el usuario "+e.getMessage());
        }
        //CERRAMOS CONECCION
        try {
            con.close();
        }catch (Exception e){
            System.out.println("No se pudo cerrar la coneccion "+e.getMessage());
        }
        return false;
    }

public static void main(String[] args) {
//        //LISTAR CLIENTES
//        System.out.println("---listar cadena---");
//        ClienteDAO clienteDAO = new ClienteDAO();
//        //CREAMOS UNA INSTANCIA DE CLIENTEDAO, PARA PODER LLAMAR AL METODO DE LISTAR, Y ASI AÑADIRLO A UNA VARIABLE LISTA YA QUE ESTE METODO DEVUELVE UNA LISTA
//        List clientes = clienteDAO.listarClientes();
//        for (Object iterator : clientes ){
//            System.out.println(iterator);
//        }

    //-----------------------------------------------------------------------------------------------------------------------------


//    //BUSCAR POR ID
//    System.out.println("--BUSCAR POR ID--");
//    //CREAMOS UN OBJETO DE TIPO CLIENTE QUE RECIBE SOLO ID POR MEDIO DEL CONSTRUCTOR DE SOLO ID
//    Cliente cliente = new Cliente(1);
//
//    //CREAMOS UN CLIENTE DAO PARA PODER ACCEDER A LOS METODOS
//    ClienteDAO clienteDAO = new ClienteDAO();
//
//    //PARA VER SU LO ENCONTRO PONEMOS UNA VARIABLE BOOLEANA
//    boolean encontrado = clienteDAO.buscarId(cliente); //ESTO ES DEBIDO A QUE EL METODO RETURNA VERDADERO O FALSO
//
//    //VERIFICAMOS SI DEVOLVIO O NO
//    if (encontrado){
//        System.out.println("Cliente encontrado: "+cliente); //CLIENTE TAMBIEN SE MODIFICA CON ESTE METODO, EL METODO SETEA LOS VALORES RECIBIDOS Y A SU VEZ DEVUELVE TRUE/FALSE
//    }else{
//        System.out.println("Cliente no encontrado "+cliente); //DEVUELVE SOLO EL ID YA QUE NO SE SETEAN VALORES EN EL METODO
//    }

    //----------------------------------------------------------------------------------------------------------------------------------

//    //INSERTAR CLIENTE
//
//    System.out.println("---INSERTAR CLIENTE---");
//
//    //CREAMOS UN CLIENTE
//    Cliente cliente = new Cliente(2,"Bros","Mario de jesus");
//
//    //CREAMOS UN DAO PARA ACCEDER AL METODO DE AÑADIR
//    ClienteDAO clienteDAO = new ClienteDAO();
//
//    //LLAMAMOS LA METODO
//    boolean añadido = clienteDAO.añadirCliente(cliente); //EL METODO DEVUELE TRUE SI SE AÑADE Y FALSE SI NO
//
//    //VERIFICAMOS SI SE AÑADIO O NO
//    if (añadido){
//        System.out.println("Cliente añadido");
//    }else {
//        System.out.println("No se pudo añadir ");
//    }

    //BORAR
//    System.out.println("BORRAR");
//
//    //CREAMOS UN CLIENTE
//    Cliente cliente = new Cliente(2);
//
//    //CREAMOS UN DAO PARA ACCEDER AL METODO DE AÑADIR
//    ClienteDAO clienteDAO = new ClienteDAO();
//
//    boolean borrado = clienteDAO.eliminarCliente(cliente);
//    System.out.println(borrado);


    //ACTUALIZAR

    //CREAMOS EL CLIENTE QUE VAMOS A MODIFICAR CON EL CONTRUCTOR COMPLETO
    Cliente cliente = new Cliente(4,"Marin",1,"Pacho");

    //CREAMOS AL CLIENTEDAO PARA ACCEDER A LOS METODOS
    ClienteDAO clienteDAO = new ClienteDAO();

    //CREAMOS EL BOOLEANO QUE ACTUALIZA O NO
    boolean actualizado = clienteDAO.modificarCliente(cliente);
    if (actualizado){
        System.out.println("Actualizado con exito");
    }else{
        System.out.println("No se pudo axtualizar");
    }
  }
}
