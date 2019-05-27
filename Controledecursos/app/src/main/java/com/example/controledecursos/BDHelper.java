//Classe para criar BD para cadastro de novos alunos
package com.example.controledecursos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import com.example.controledecursos.BDHelper2;

public class BDHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "cadastraAluno.db";
    private static final String TABLE_NAME = "cadastraAluno";
    private static final String COLUM_ID = "id_aluno";
    private static final String COLUM_NOME = "nome_aluno";
    private static final String COLUM_EMAIL = "email_aluno";
    private static final String COLUM_TEL = "tel_aluno";
    SQLiteDatabase db;

    private static final String TABLE_CREATE = "create table cadastraAluno " +
            "(id_aluno integer primary key autoincrement, nome_aluno text not null, " +
            " email_aluno text not null, tel_aluno integer not null, foreign key(id_c) references cadastraCurso(id_curso));";

    public BDHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }
    //inserir novo aluno
    public void insereAluno(CadastroA a){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUM_NOME, a.getNome());
        values.put(COLUM_EMAIL, a.getEmail());
        values.put(COLUM_TEL, a.getTelefone());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public long alterarCadastro(CadastroA a){
        long  retornoBD;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUM_NOME, a.getNome());
        values.put(COLUM_EMAIL, a.getEmail());
        values.put(COLUM_TEL, a.getTelefone());
        String[] args ={String.valueOf(a.getNome())};
        retornoBD = db.update(TABLE_NAME, values, COLUM_NOME + "=?", args);
        db.close();
        return retornoBD;
    }
    public ArrayList<CadastroA> selectAllCadastros(){
        String[]coluns = {COLUM_NOME, COLUM_EMAIL, COLUM_TEL};
        Cursor cursor = getWritableDatabase().query(TABLE_NAME, coluns, null, null, null, null, "upper(nome_aluno)", null);
        ArrayList<CadastroA> listaCadastros = new ArrayList<CadastroA>();
        while(cursor.moveToNext()){
            CadastroA a = new CadastroA();
            a.setNome(cursor.getString(0));
            a.setEmail(cursor.getString(1));
            a.setTelefone(cursor.getString(2));
            listaCadastros.add(a);
        }
        return listaCadastros;
    }

    public long excluirCadastro(CadastroA a){
        long retornBD;
        db = this.getWritableDatabase();
        String[] args = {String.valueOf(a.getNome())};
        retornBD = db.delete(TABLE_NAME, COLUM_NOME + "=?", args);
        db.close();
        return  retornBD;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query ="DROP TABLE IF EXISTS " + TABLE_NAME;
        this.db.execSQL(query);
        this.onCreate(db);
    }
}
