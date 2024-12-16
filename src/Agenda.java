import java.util.ArrayList;
import java.util.List;

public class Agenda implements Runnable {
    private List<Contacto> contactos;
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();
    private static Thread t1 = new Thread();
    private static Thread t2 = new Thread();


    public Agenda() {
        contactos = new ArrayList<>();
    }
    @Override
    public void run() {
        synchronized (lock1) {
            synchronized (lock2) {
                for (Contacto c : contactos) {
                    System.out.println(c);
                }
            }
        }
        
    }

    public void agregarContacto(String nombre, String email, String telefono) {
        synchronized (lock1) {
            synchronized (lock2) {
                contactos.add(new Contacto(nombre, email, telefono));
            }
        }
    }

    public void mostrarContactos() {
        synchronized (lock2) {
            synchronized (lock1) {
                for (Contacto c : contactos) {
                    System.out.println(c);
                }
            }
        }
    }
}
