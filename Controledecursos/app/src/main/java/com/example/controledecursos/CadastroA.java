package com.example.controledecursos;

public class CadastroA{
    String id;
    String nome;
    String email;
    String telefone;

    public CadastroA(){

    }

    public CadastroA(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return  " nome = " + nome +"\n" +
                " email = " + email +"\n" +
                " telefone = " + telefone;
    }
}
