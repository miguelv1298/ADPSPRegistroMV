package org.example;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
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


    public static List<User> listAllUsers(){
        MongoClient cliente = MongoClients.create(url);
        MongoDatabase db = cliente.getDatabase("appregistro");
        usersCollection = db.getCollection("users");
        FindIterable<Document> usersDocument = usersCollection.find();
        List<User> users = new ArrayList<>();
        for(Document d : usersDocument){
            String username = d.getString("username");
            String name = d.getString("name");
            Integer age = d.getInteger("age");
            List<String> emails = d.getList("emails",String.class);
            Address address = null;
            if (d.containsKey("address")) {
                Document addressDoc = d.get("address", Document.class);
                if (addressDoc != null) {  // Verificar que addressDoc no sea null
                    String street = addressDoc.getString("street");
                    Integer number = addressDoc.getInteger("number");
                    String zipCode = addressDoc.getString("zipCode");
                    address = new Address(street, number, zipCode);
                }
            }
            User user = new User(username,null,name,age,emails,address);
            users.add(user);
        }
        return users;
    }

    //public static User findUserByUsername(String username) { /* Código para buscar usuario */ }
    //public static List<User> findUsersByAgeRange(int min, int max) { /* Código para buscar por edad */ }
    public static boolean updatePassword(String username, byte[] newPasswordHash) {
        MongoClient cliente = MongoClients.create(url);
        MongoDatabase db = cliente.getDatabase("appregistro");
        usersCollection = db.getCollection("users");
        Bson busqueda = new Document().append("username",username);
        UpdateResult u = usersCollection.updateOne(busqueda,Updates.set("password",newPasswordHash));
        if(u.getModifiedCount()>0){
            return true;
        }
        return false;
    }
    public static boolean deleteUser(String username) {
        MongoClient cliente = MongoClients.create(url);
        MongoDatabase db = cliente.getDatabase("appregistro");
        usersCollection = db.getCollection("users");
        DeleteResult d = usersCollection.deleteOne(Filters.eq("username",username));
        if(d.getDeletedCount()>0){
            return true;
        }
        return false;
    }

    public static List<String> usersBetweenAges(int minima, int maxima) {
        MongoClient cliente = MongoClients.create(url);
        MongoDatabase db = cliente.getDatabase("appregistro");
        usersCollection = db.getCollection("users");
        FindIterable<Document> usersDocument = usersCollection.find(Filters.and(Filters.gte("age",minima),Filters.lte("age",maxima)));
        List<String> users = new ArrayList<>();
        for (Document d : usersDocument){
            users.add(d.getString("username"));
        }
        return users;
    }

    public static List<String> listAllUsernames() {
        MongoClient cliente = MongoClients.create(url);
        MongoDatabase db = cliente.getDatabase("appregistro");
        usersCollection = db.getCollection("users");
        FindIterable<Document> usersDocument = usersCollection.find().projection(Projections.include("username"));
        List<String> users = new ArrayList<>();
        for (Document d : usersDocument){
            users.add(d.getString("username"));
        }
        return users;
    }

    public static List<String> showUsersWithEmail() {
        MongoClient cliente = MongoClients.create(url);
        MongoDatabase db = cliente.getDatabase("appregistro");
        usersCollection = db.getCollection("users");
        FindIterable<Document> usersDocument = usersCollection.find(Filters.exists("emails")).projection(Projections.include("username"));
        List<String> users = new ArrayList<>();
        for (Document d : usersDocument){
            users.add(d.getString("username"));
        }
        return users;

    }

    public static List<String> showUserByZipCode(String zipCode) {
        MongoClient cliente = MongoClients.create(url);
        MongoDatabase db = cliente.getDatabase("appregistro");
        usersCollection = db.getCollection("users");
        FindIterable<Document> usersDocument = usersCollection.find(Filters.eq("address.zipCode",zipCode));
        List<String> users = new ArrayList<>();
        for (Document d : usersDocument){
            users.add(d.getString("username"));
        }
        return users;
    }
}
