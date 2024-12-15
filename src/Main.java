public class Main {
    public static void main(String[] args) {
        Agenda agenda = new Agenda();
        agenda.agregarContacto("Juan Pérez", "juan@example.com", "123456789");
        agenda.agregarContacto("Ana López", "ana@example.com", "987654321");
        agenda.mostrarContactos();
    }
}