package zona_fit.datos;

import zona_fit.dominio.Cliente;

import java.util.List;

public interface IClienteDAO {

    //CREAMOS PRIMERO EL LISTAR (UNA LISTA QUE ALMACENE TODOS LOS CLIENTES QUE TRAIGMOS)
    List<Cliente> listarClientes();

    //BUSCAR POR ID(DEVUELVE TRUE SI LO ENCUENTRA) recibe un objeto de tipo cliente y lo va a buscar
    boolean buscarId(Cliente cliente);

    //AÑADIR, RECIBE UN CLIENTE QUE ES EL QUE SE VA A AÑADIR
    boolean añadirCliente(Cliente cliente);

    //MODIFICAR UN CLIENTE
    Boolean modificarCliente (Cliente cliente);

    //ELIMINAR

    boolean  eliminarCliente (Cliente cliente);
}
