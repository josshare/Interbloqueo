public class Main {
    public static void main(String[] args) {
        Agenda agenda = new Agenda();
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