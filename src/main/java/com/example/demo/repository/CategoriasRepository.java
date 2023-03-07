package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Categoria;

@Repository
public interface CategoriasRepository extends JpaRepository<Categoria, Integer> {

}
