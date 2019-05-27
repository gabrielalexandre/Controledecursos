package com.example.controledecursos;

public class CadastroC {

    String id, nome, qtdHoras;

    public CadastroC(){}


    public CadastroC(String nome, String qtdHoras) {
        this.nome = nome;
        this.qtdHoras = qtdHoras;
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

    public String getQtdHoras() {
        return qtdHoras;
    }

    public void setQtdHoras(String qtdHoras) {
        this.qtdHoras = qtdHoras;
    }

    @Override
    public String toString() {
        return  " nome = " + nome + "\n" +
                " qtdHoras = " + qtdHoras + "\n" +
                " id = " + id;
    }
}
