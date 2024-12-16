// Clase principal que demuestra el uso a prueba de hilos del singleton Agenda.
// La clase Agenda está diseñada para manejar adiciones de contactos concurrentes de forma segura.
public class Main {
    public static void main(String[] args) {
        try {
            // Obtener la instancia singleton de Agenda
            Agenda agenda = Agenda.getInstance();
            
            // Iniciar ambos hilos de trabajos
            agenda.startThreads();
            
            // Esperar a que ambos hilos terminen
            agenda.waitForThreads();
            
            // Mostrar los contactos en la agenda
            agenda.mostrarContactos();
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }

    }
}