package model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import config.ConfiguracaoFirebase;


public class User {
    private String id, name, lastName, email, password, cpf, date;
    private boolean isAccountChecked;

    public User() {
    }

    public User(String id, String name, String lastName, String email, String password, String cpf, String date, boolean isAccountChecked) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.cpf = cpf;
        this.date = date;
        this.isAccountChecked = isAccountChecked;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String data) {
            this.date = data;
    }

    public boolean isAccountChecked() {
        return isAccountChecked;
    }

    public void setAccountChecked(boolean accountChecked) {
        isAccountChecked = accountChecked;
    }

    public void saveUser() {
        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebaseDataBase();
        databaseReference.child("users").child(getId()).setValue(this);
    }

    public void saveDataUser() {
        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebaseDataBase();
        databaseReference.child("data_user").child(getId()).setValue(this);
    }
}
