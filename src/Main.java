public class Main {
    public static void main(String[] args) {
        // Accedemos a la instancia del Singleton Agenda
        Agenda agenda = Agenda.getInstance();
        Thread thread1 = new Thread(agenda, "Thread1");
        Thread thread2 = new Thread(agenda, "Thread2");

        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        thread1.start();
        thread2.start();
        Thread t1 = new Thread(() -> {
            agenda.agregarContacto("Juan Pérez", "juan@example.com", "123456789");
        });

        Thread t2 = new Thread(() -> {
            agenda.mostrarContactos();
        });

        t1.start();
        t2.start();
    }
}