import java.util.ArrayList;
import java.util.List;

public class Agenda implements Runnable {
    private List<Contacto> contactos;
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public Agenda() {
        contactos = new ArrayList<>();
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
        synchronized (lock1) {
            try {
                Thread.sleep(100); // Increase chance of deadlock
                synchronized (lock2) {
                    agregarContacto("Juan", "juan@email.com", "123456");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void agregarContacto2() {
        synchronized (lock2) {
            try {
                Thread.sleep(100); // Increase chance of deadlock
                synchronized (lock1) {
                    agregarContacto("Maria", "maria@email.com", "789012");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
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
