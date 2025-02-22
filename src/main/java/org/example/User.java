package org.example;

import java.util.List;
import java.util.Objects;

public class User {
    private String username;
    private byte[] passwordHash; // Hash de la contraseña
    private String name;
    private Integer age;
    private List<String> emails;
    private Address address;

    public User() {}

    public User(String username, byte[] passwordHash, String name, Integer age, List<String> emails, Address address) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.name = name;
        this.age = age;
        this.emails = emails;
        this.address = address;
    }

    public User(String username, byte[] passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", emails=" + emails +
                ", address=" + (address != null ? address.toString() : "No Address") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
                Objects.equals(name, user.name) &&
                Objects.equals(age, user.age) &&
                Objects.equals(emails, user.emails) &&
                Objects.equals(address, user.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, name, age, emails, address);
    }
}
