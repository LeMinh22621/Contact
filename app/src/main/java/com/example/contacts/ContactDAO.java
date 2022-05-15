package com.example.contacts;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ContactDAO {
    @Query("SELECT * FROM ContactEntity")
    List<ContactEntity> getAll();
    @Query("SELECT * FROM ContactEntity WHERE firstName || ' ' || lastName LIKE '%' || :s || '%'")
    List<ContactEntity> search(String s);

    @Insert
    void insert(ContactEntity... contactEntities);

    @Query("DELETE FROM ContactEntity")
    void delete();

    @Query("UPDATE ContactEntity SET " +
            "firstName = :firstName," +
            "lastName = :lastName," +
            "phone = :phone," +
            "email = :email " +
            "WHERE id = :id")
    void update(int id, String firstName, String lastName, String phone, String email);
}
