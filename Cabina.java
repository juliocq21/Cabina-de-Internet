package internet;

public class Cabina {
    private Cliente[] computadoras;

    public Cabina(int capacidad) {
        computadoras = new Cliente[capacidad];
    }

    public boolean registrarCliente(Cliente cliente) {
        if (computadoras[cliente.computadora] == null) {
            computadoras[cliente.computadora] = cliente;
            return true;
        }
        return false;
    }

    public Cliente buscarCliente(String dni) {
        for (Cliente c : computadoras) {
            if (c != null && c.usuario.equals(dni)) {
                return c;
            }
        }
        return null;
    }

    public boolean eliminarCliente(String dni) {
        for (int i = 0; i < computadoras.length; i++) {
            if (computadoras[i] != null && computadoras[i].usuario.equals(dni)) {
                computadoras[i].timer.stop(); // detener temporizador
                computadoras[i] = null;
                return true;
            }
        }
        return false;
    }

    public void liberarComputadora(int pc) {
        computadoras[pc] = null;
    }

    public String mostrar() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < computadoras.length; i++) {
            if (computadoras[i] != null) {
                sb.append("PC ").append(i).append(" → ").append(computadoras[i]).append("\n");
            } else {
                sb.append("PC ").append(i).append(" → [vacío]\n");
            }
        }
        return sb.toString();
    }
}