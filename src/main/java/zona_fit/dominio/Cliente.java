package zona_fit.dominio;
//ACA VAMOS A CREAR LOS OBJETOS DE TIPO CLIENTE QUE VAN A SER AÑADIDOS A LA TABLA CLIENTES DE LA BASE DE DATOS
//POR ENDE ESTA CLASE TIENE LOS MISMOS ATRIBUTOS QUE LA TABLA CLIENTES

import java.util.Objects;

public class Cliente {
    private int id;
    private String name;
    private String lastName;
    private int memership;

    //CREAMOS SUS CONSTRUCTORES Y TODO LO QUE DEBE DE TENER


    public Cliente() {
    }

    //BUSCAR POR ID
    public Cliente(int id) {
        this.id = id;
    }

    //CREAR REGISTRO(NO SE PONE ID YA QUE ESTE ES AUTOENERABLE)
    public Cliente(int memership, String lastName, String name) {
        this.id = id;
        this.memership = memership;
        this.lastName = lastName;
        this.name = name;
    }

    //COMPLETO(SOLO AÑADIMOS EL ID Y LLAMAMOS AL METODO QUE TENIA LOS OTROS ATRIBUTOS Y MELO)
    public Cliente(int id, String lastName, int memership, String name) {
        this(memership, name, lastName);//esto invoca al metodo con esos atributos
        this.id = id;
    }


    //getters and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemership() {
        return memership;
    }

    public void setMemership(int memership) {
        this.memership = memership;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //TOSTRING
    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", memership=" + memership +
                '}';
    }


    //EQUALS: COMPARA 2 OBJETOS DE TIPO CLIENTE

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Cliente cliente = (Cliente) object;
        return id == cliente.id && memership == cliente.memership && Objects.equals(name, cliente.name) && Objects.equals(lastName, cliente.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, memership);
    }
}
