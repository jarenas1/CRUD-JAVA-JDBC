package zona_fit.conexion;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;

//GENERAMOS LA CONECCION CON LA DB
public class Conexion {

    //CREAMOS UN METODO QUE DEVUELVA LA CONEXION POR MEDIO DE Connection

    public static Connection connection(){
        //DEFINIMOS UNA CONEXION NULA PARA QUE SIEMPRE COMIENCE SIENDO NULA LA CONEXION
        Connection conexion = null;

        //CREAMOS LA CADENA DE CONECCION DE LA BASE DE DATOS, jdbc:mysql://host:port/dbName
        String URL = "jdbc:mysql://bfemnekk91sbh0oam9wb-mysql.services.clever-cloud.com:3306/bfemnekk91sbh0oam9wb";

        //CREAMOS LAS VARIABLES CON LAS CREDENCIALES
        String user = "ukuulgz0rk3zh2dg";
        String password = "Bwd3e1fOjg9HlBE4Cdp8";

        //PARA INTENTAR CONECTARNOS CREAMOS UN TRYCATCH YA QUE ESTO NOS PUEDE GENERAR UN ERROR

        try {
            //llamamos la clase de coneccion con el paquete llamado a maven
            Class.forName("com.mysql.cj.jdbc.Driver");

            //La coneccion que habiamos creado como null, ahora vamos a llamarla para establecerle la coneccion.
            conexion = DriverManager.getConnection(URL,user,password);//Añadimos las variables de credenciales creadas anteriormente
        }catch (Exception e){
            System.out.println("ERROR EN LA CONECCION A LA DB" + e.getMessage());
        }
        //AHORA RETORNAMOS ESTE VALOR DE CONEXXION
        return conexion;
    }
}
