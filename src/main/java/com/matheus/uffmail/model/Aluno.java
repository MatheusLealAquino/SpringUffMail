package com.matheus.uffmail.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Aluno implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    private String nome;
    private String uffMail;
    private String email;
    private String telefone;
    private String matricula;
    private String status;

    private ArrayList<String> gerarUffMail;

    private final String dominio = "@id.uff.br";


    public String getMatricula(){
        return this.matricula;
    }

    public void setMatricula(String matricula){
        this.matricula = matricula;
    }



    public String getUffMail() {
        return uffMail;
    }

    public void setUffMail(String uffMail) {
        this.uffMail = uffMail;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }



    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public ArrayList<String> getGerarUffMail(){
        List<String> possibilidades = new ArrayList<>();

        String[] nomeCompleto = nome.toLowerCase().split(" ");

        for (int i = 1; i < nomeCompleto.length; i++) {
            possibilidades.add(nomeCompleto[0] + nomeCompleto[i] + dominio);
            possibilidades.add(nomeCompleto[0].charAt(0) + nomeCompleto[i] + dominio);
            possibilidades.add(nomeCompleto[0] + "_" + nomeCompleto[i] + dominio);
        }

        return (ArrayList<String>) possibilidades;
    }

}
