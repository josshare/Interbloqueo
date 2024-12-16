import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Agenda implements Runnable {
    private final List<Contacto> contactos;
    
    private static Thread t1;
    private static Thread t2;
    
    // Declaramos una variable estática que contendrá la única instancia de la clase Agenda
    private static volatile Agenda instance;
    
    // El constructor es privado para evitar que otras clases creen nuevas instancias de Agenda
    private Agenda() {
        contactos = Collections.synchronizedList(new ArrayList<Contacto>());
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
   //El método run es el punto de entrada para la ejecución de hilos.
   //Cuando se inicia un hilo, ejecuta este método que se encarga
   //de añadir contactos a través de diferentes puntos de entrada.
    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        try {
            switch (threadName) {
                case "Thread1":
                    agregarContacto1();
                    break;
                case "Thread2":
                    agregarContacto2();
                    break;
                default:
                    System.err.println("Unknown thread: " + threadName);
            }
        } catch (Exception e) {
            System.err.println("Error in thread " + threadName + ": " + e.getMessage());
        }
    }

    //Punto de entrada para la primera operación del hilo.
    //Añade un contacto llamado «Juan» a través del mecanismo de recursos compartidos.
    public void agregarContacto1() {
        usar_ambos_recursos("Juan", "juan@email.com", "123456");
    }

    public void agregarContacto2() {
        usar_ambos_recursos("Maria", "maria@email.com", "789012");
    }
    //Creamos un Metodo usar_ambos_recursos() en la que la estructura es una lista
    /**
     * Critical section where shared resources are accessed.
     * This method acts as a gateway for adding contacts, potentially
     * accessed by multiple threads simultaneously.
     */
    public void usar_ambos_recursos(String nombre, String email, String telefono) {
        agregarContacto(nombre, email, telefono);
    }

    public void agregarContacto(String nombre, String email, String telefono) {
        contactos.add(new Contacto(nombre, email, telefono));
    }

    public void mostrarContactos() {
        synchronized (contactos) {
            for (Contacto c : contactos) {
                System.out.println(c);
            }
        }
    }
    
    // Inicializa e inicia dos hilos que se ejecutarán concurrentemente.
    // Ambos subprocesos comparten el mismo ejecutable (esta instancia de Agenda) pero
    // operan independientemente para añadir contactos a la agenda.
    public void startThreads() {
        t1 = new Thread(this, "Thread1");
        t2 = new Thread(this, "Thread2");
        t1.start();
        t2.start();
    }
    
    //Método de sincronización que asegura que todos los hilos completan su ejecución.
    //Utiliza Thread.join() para hacer que el hilo principal espere hasta que ambos hilos trabajadores
    //hayan terminado sus tareas antes de continuar.
    //@throws InterruptedException si el hilo es interrumpido mientras espera.
    public void waitForThreads() throws InterruptedException {
        if (t1 != null) t1.join();
        if (t2 != null) t2.join();
    }
}
