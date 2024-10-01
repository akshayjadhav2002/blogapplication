package com.example.myblogapplication.repositories;
import com.example.myblogapplication.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User,Integer>{

}
