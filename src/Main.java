import utils.Pass;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExecutorService executor = Executors.newCachedThreadPool();
        List<String> contraseñasValidas = new ArrayList<>();

        boolean continuar = true;
        while (continuar) {
            System.out.println("\nIngrese una contraseña para validar con las siguientes características: " +
                    "\n- Mínimo 8 caracteres" +
                    "\n- Debe contener al menos una mayúscula, una minúscula, un número y un carácter especial" +
                    "\nIngrese 'Salir' para terminar\n");

            String input;
            while (true) {
                System.out.print("Contraseña: ");
                input = scanner.nextLine();
                if (input.equalsIgnoreCase("Salir")) {
                    break;
                }
                Future<Boolean> resultado = executor.submit(new Pass(input));
                try {
                    if (resultado.get()) {
                        System.out.println("La contraseña '" + input + "' es válida.");
                        contraseñasValidas.add(input);
                    } else {
                        System.out.println("La contraseña '" + input + "' no es válida.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            System.out.println("\nContraseñas válidas:");
            for (String contraseña : contraseñasValidas) {
                System.out.println(contraseña);
            }

            System.out.print("\n¿Desea introducir más contraseñas? (Sí/No): ");
            String respuesta = scanner.nextLine();
            if (!respuesta.equalsIgnoreCase("Sí")) {
                continuar = false;
            }
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Verificación completada");
    }
}