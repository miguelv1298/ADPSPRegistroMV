package org.example;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    /**
     *
     */
    public static void imprimirMenu(){
        System.out.println("---MENÚ PRINCIPAL---");
        System.out.println("1. Registrar a una persona");
        System.out.println("2. Iniciar sesión");
        System.out.println("3. Ver datos");
        System.out.println("0. Salir");
    }
    private static byte[] generarHashMD5(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        return digest.digest(password.getBytes(StandardCharsets.UTF_8));
    }

    /**
     *
     */
    public static void registarPersona(){

                Scanner scanner = new Scanner(System.in);

                System.out.println("Registro");

                System.out.println("Introduce username:");
                String username = scanner.nextLine();

                System.out.println("Introduce password:");
                String password = scanner.nextLine();

                if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
                    System.out.println("Usuario no guardado, falta username o password");
                    return;
                }

                System.out.println("Introduce name (enter para dejar vacío):");
                String name = comprobarNull(scanner);

                System.out.println("Introduce age (enter para dejar vacío):");
                Integer age = comprobarInteger(scanner);

                List<String> emails = new ArrayList<>();
                while (true) {
                    System.out.println("Introduce dato o enter para dejar vacío:");
                    String email = comprobarNull(scanner);
                    if (email == null) {
                        emails  =null;
                        break;
                    }
                    emails.add(email);
                    System.out.println("Añade otro email, enter para no añadir más:");
                }

                System.out.println("Introduce street (enter para dejar vacío):");
                String street = comprobarNull(scanner);

                System.out.println("Introduce number (enter para dejar vacío):");
                Integer number = comprobarInteger(scanner);

                System.out.println("Introduce zipCode (enter para dejar vacío):");
                String zipCode = comprobarNull(scanner);

                Address address = null;
                if (street != null || number != null || zipCode != null) {
                    address = new Address(street, number, zipCode);
                }
                try {
                    byte[] passwordHash = generarHashMD5(password);
                    User usuario = new User(username, passwordHash, name, age, emails, address);
                    if(UserDAO.insertUser(usuario)){
                        System.out.println("Usuario registrado");
                    }
                    else{
                        System.out.println("Usuario ya existente");
                    }
                }
                catch (NoSuchAlgorithmException e){
                    System.err.println("Error al generar hash");
                }

            }




    private static Integer comprobarInteger(Scanner scanner) {
        String input = scanner.nextLine();
        if (input.isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return null;
        }
    }



        private static String comprobarNull(Scanner scanner) {
            String input = scanner.nextLine();
            return input.isEmpty() ? null : input;
        }

    /**
     *
     */
    public static void iniciarSesion(){
        Scanner sc = new Scanner(System.in);

        String usuario = sc.nextLine();
        System.out.println("Introduce usuario");

        String password = sc.nextLine();
        System.out.println("introduce contraseña");
        if(UserDAO)
    }

    /**
     *
     */
    public static void mostrarPersonas(){}

    /**
     *
     */
    public static void mostrarPersonasEntreEdades(){}

    /**
     *
     */
    public static void mostrarPersonasConMail(){}

    /**
     *
     */
    public static void mostrarPersonasPorCodigoPostal(){}

    /**
     *
     */
    public static void eliminarPersonas(){}


    /**
     *
     */
    public static void actualizarContrasena(){}

    /**
     *
     */
    public static void crearClaveSimetrica(){}

    /**
     *
     */
    public static void cifrarClaveSimetrica(){}

    /**
     *
     */
    public static void descifrarClaveSimetrica(){}

    /**
     *
     */
    public static void verificarFirmaDigital(){}

    /**
     *
     */
    public static void cifrarClaveAsimetrica(){}

    /**
     *
     */
    public static void verDatos() {


        boolean salir = true;
        while (salir) {
            System.out.println("---MENÚ BASE DE DATOS---");
            System.out.println("1. Mostrar todas las personas");
            System.out.println("2. Mostrar personas entre edades");
            System.out.println("3. Mostrar personas con email");
            System.out.println("4. Mostrar personas por código postal");
            System.out.println("5. Eliminar persona");
            System.out.println("6. Actualizar contraseña");
            System.out.println("0. Salir");
            Scanner sc = new Scanner(System.in);
            String opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    mostrarPersonas();
                    break;

                case "2":
                    mostrarPersonasEntreEdades();
                    break;

                case "3":
                    mostrarPersonasConMail();
                    break;

                case "4":
                    mostrarPersonasPorCodigoPostal();
                    break;

                case "5":
                    eliminarPersonas();
                    break;

                case "6":
                    actualizarContrasena();
                    break;

                case "0":
                    salir = !salir;
                    break;

                default:
                    break;

            }
        }
    }
    public void menuCriptografia () {

        boolean salir = true;
        while (salir) {
            System.out.println("---MENÚ CRIPTOGRAFÍA---");
            System.out.println("1. Crear clave simétrica");
            System.out.println("2. Cifrar mensaje con clave simétrica");
            System.out.println("3. Descrifrar mensaje con clave simétrica");
            System.out.println("4. Verificar firma digital");
            System.out.println("5. Cifrar mensaje con clave asimétrica");
            System.out.println("0. Salir");
            Scanner sc = new Scanner(System.in);
            String opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    crearClaveSimetrica();
                    break;

                case "2":
                    cifrarClaveSimetrica();
                    break;

                case "3":
                    descifrarClaveSimetrica();
                    break;

                case "4":
                    verificarFirmaDigital();
                    break;

                case "5":
                    cifrarClaveAsimetrica();
                    break;

                case "0":
                    salir = !salir;
                    break;

                default:
                    break;

            }
        }
    }

    public static void main(String[] args) {

        boolean salir = true;
        while( salir) {
            imprimirMenu();
            Scanner sc = new Scanner(System.in);
            String opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    registarPersona();
                    break;

                case "2":
                    iniciarSesion();
                    break;

                case "3":
                    verDatos();
                    break;

                case "0":
                    salir = !salir;
                    break;
                default:
                    break;

            }
        }




    }
}