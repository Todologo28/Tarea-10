package utils;

import java.util.concurrent.Callable;
import java.util.regex.Pattern;

public class Pass implements Callable<Boolean> {
    private String password;

    public Pass(String password) {
        this.password = password;
    }

    @Override
    public Boolean call() {
        return validarPassword(password);
    }

    public static boolean validarPassword(String password) {
        // Verificar longitud mínima
        if (password.length() < 8) {
            return false;
        }

        // Verificar caracteres especiales
        if (!Pattern.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*", password)) {
            return false;
        }

        // Verificar al menos una mayúscula
        if (!Pattern.matches(".*[A-Z].*", password)) {
            return false;
        }

        // Verificar al menos una minúscula
        if (!Pattern.matches(".*[a-z].*", password)) {
            return false;
        }

        // Verificar al menos un número
        if (!Pattern.matches(".*\\d.*", password)) {
            return false;
        }

        return true;
    }
}