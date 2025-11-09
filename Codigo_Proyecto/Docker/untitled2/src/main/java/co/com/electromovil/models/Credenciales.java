package co.com.electromovil.models;

public class Credenciales {
    private String email;
    private String password;

    public Credenciales(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}