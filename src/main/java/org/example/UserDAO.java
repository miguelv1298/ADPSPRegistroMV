package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.List;

public class UserDAO {
    private static MongoCollection<Document> usersCollection;
    private static String url = "mongodb://localhost:27017";


    public UserDAO() {
    }


    public static boolean insertUser(User user) {
        MongoClient cliente = MongoClients.create(url);
        MongoDatabase db = cliente.getDatabase("appregistro");
        usersCollection = db.getCollection("users");
        Document usuarioBusqueda = usersCollection.find(new Document("username", user.getUsername())).first();

        if (usuarioBusqueda == null) {

            Document userDocument = new Document("username", user.getUsername())
                    .append("password", user.getPasswordHash());
            if (user.getName() != null) {
                userDocument.append("name", user.getName());
            }
            if (user.getAge() != null) {
                userDocument.append("age", user.getAge());
            }
            if (user.getEmails() != null) {
                userDocument.append("emails", user.getEmails());
            }
            if (user.getAddress() != null) {
                Document addressDocument = null;
                if (user.getAddress().getStreet() != null) {
                    addressDocument = new Document("street", user.getAddress().getStreet());
                }
                if (user.getAddress().getNumber() != null) {
                    addressDocument.append("number", user.getAddress().getNumber());
                }
                if (user.getAddress().getZipCode() != null) {
                    addressDocument.append("zipCode", user.getAddress().getZipCode());
                }
                userDocument.append("address", addressDocument);
            }
            usersCollection.insertOne(userDocument);
            return true;
        }
        return false;
    }
    public static boolean  checkRegister(User user){
        MongoClient cliente = MongoClients.create(url);
        MongoDatabase db = cliente.getDatabase("appregistro");
        usersCollection = db.getCollection("users");
        Document usuarioBusqueda = usersCollection.find(Filters.and(
                Filters.eq("username", user.getUsername()),
                Filters.eq("password", user.getPasswordHash()))).first();
        if(usuarioBusqueda!=null){
            return true;
        }
        return false;
    }


    //public static User findUserByUsername(String username) { /* Código para buscar usuario */ }
    //public static List<User> findUsersByAgeRange(int min, int max) { /* Código para buscar por edad */ }
    public static void updatePassword(String username, byte[] newPasswordHash) { /* Código para actualizar contraseña */ }
    public static void deleteUser(String username) { /* Código para eliminar usuario */ }
}
