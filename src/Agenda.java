import java.util.ArrayList;
import java.util.List;

public class Agenda implements Runnable {
    private List<Contacto> contactos;
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();
    private static Thread t1 = new Thread();
    private static Thread t2 = new Thread();
    
    // Declaramos una variable estática que contendrá la única instancia de la clase Agenda
    private static Agenda instance;
    
    // El constructor es privado para evitar que otras clases creen nuevas instancias de Agenda
    private Agenda() {
        contactos = new ArrayList<>();
    }
    
    // Este patrón asegura que solo haya una instancia de Agenda en toda la aplicación. 
    public static Agenda getInstance() {
        if (instance == null) {
            synchronized (Agenda.class) {
                if (instance == null) {
                    instance = new Agenda();
                }
            }
        }
        return instance;
    }
    @Override
    public void run() {
        if (Thread.currentThread().getName().equals("Thread1")) {
            agregarContacto1();
        } else {
            agregarContacto2();
        }
    }

    public void agregarContacto1() {
        use_both_resources("Juan", "juan@email.com", "123456");
    }

    public void agregarContacto2() {
        use_both_resources("Maria", "maria@email.com", "789012");
    }

    public void use_both_resources(String nombre, String email, String telefono) {
        // Always acquire locks in the same order to prevent deadlock
        synchronized (lock1) {
            synchronized (lock2) {
                agregarContacto(nombre, email, telefono);
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
