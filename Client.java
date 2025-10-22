/**
 * Represents a client in the Orders Management system.
 * This class stores client details such as ID, name, email, phone, address, and age.
 */
package org.example.a3.Model;

public class Client {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private int age;

    /**
     * Default constructor for Client.
     */
    public Client() {}

    /**
     * Constructs a new Client with specified details.
     * @param id The unique identifier for the client.
     * @param name The name of the client.
     * @param address The address of the client.
     * @param email The email address of the client.
     * @param phone The phone number of the client.
     * @param age The age of the client.
     */
    public Client(int id, String name, String address, String email, String phone, int age) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.age = age;
    }

    /**
     * Returns the ID of the client.
     * @return The client ID.
     */
    public int getId() { return id; }
    /**
     * Sets the ID of the client.
     * @param id The new client ID.
     */
    public void setId(int id) { this.id = id; }

    /**
     * Returns the name of the client.
     * @return The client name.
     */
    public String getName() { return name; }
    /**
     * Sets the name of the client.
     * @param name The new client name.
     */
    public void setName(String name) { this.name = name; }

    /**
     * Returns the email address of the client.
     * @return The client email.
     */
    public String getEmail() { return email; }
    /**
     * Sets the email address of the client.
     * @param email The new client email.
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Returns the phone number of the client.
     * @return The client phone number.
     */
    public String getPhone() { return phone; }
    /**
     * Sets the phone number of the client.
     * @param phone The new client phone number.
     */
    public void setPhone(String phone) { this.phone = phone; }

    /**
     * Returns the address of the client.
     * @return The client address.
     */
    public String getAddress() { return address; }
    /**
     * Sets the address of the client.
     * @param address The new client address.
     */
    public void setAddress(String address) { this.address = address; }

    /**
     * Returns the age of the client.
     * @return The client age.
     */
    public int getAge() { return age; }
    /**
     * Sets the age of the client.
     * @param age The new client age.
     */
    public void setAge(int age) { this.age = age; }

    /**
     * Returns a string representation of the client, primarily their name.
     * @return The client's name.
     */
    @Override
    public String toString() {
        return name ;
    }
}