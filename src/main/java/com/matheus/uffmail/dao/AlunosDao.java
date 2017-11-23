package com.matheus.uffmail.dao;

import com.matheus.uffmail.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AlunosDao extends JpaRepository<Aluno, Long>{

    Aluno findByMatricula(String matricula);

}
