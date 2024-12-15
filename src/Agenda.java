import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private List<Contacto> contactos;

    public Agenda() {
        contactos = new ArrayList<>();
    }

    public synchronized void agregarContacto(String nombre, String email, String telefono) {
        contactos.add(new Contacto(nombre, email, telefono));
    }

    public synchronized void mostrarContactos() {
        for (Contacto c : contactos) {
            System.out.println(c);
        }
    }
}
