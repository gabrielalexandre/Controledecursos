package com.example.controledecursos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class BDHelper2 extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "cadastraCurso.db";
    private static final String TABLE_NAME = "cadastraCurso";
    private static final String COLUM_ID = "id_curso";
    private static final String COLUM_NOME = "nome_curso";
    private static final String COLUM_HORA = "horas_curso";
    SQLiteDatabase db;

    private static final String TABLE_CREATE = "create table cadastraCurso " +
            "(id_curso integer primary key autoincrement, nome_curso text not null, " +
            " horas_curso integer not null);";

    public BDHelper2(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    //inserir novo curso
    public void insereCurso(CadastroC c){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUM_NOME, c.getNome());
        values.put(COLUM_HORA, c.getQtdHoras());
        values.put(COLUM_ID, c.getId());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public long alterarCurso(CadastroC c){
        long retornoBD;
        db = this.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(COLUM_NOME, c.getNome());
        values.put(COLUM_HORA, c.getQtdHoras());
        String[] args = {String.valueOf(c.getNome())};
        retornoBD = db.update(TABLE_NAME, values, COLUM_NOME + "=?", args);
        db.close();
        return retornoBD;
    }

    public long excluirCurso(CadastroC c){
        long retornBD;
        db = this.getWritableDatabase();
        String[] args = {String.valueOf(c.getNome())};
        retornBD = db.delete(TABLE_NAME, COLUM_NOME + "=?", args);
        db.close();
        return  retornBD;
    }

    public ArrayList<CadastroC> selectAllContatos(){
        String[] coluns = {COLUM_NOME, COLUM_HORA, COLUM_ID};
        Cursor cursor = getWritableDatabase().query(TABLE_NAME, coluns, null, null, null,
                null, "upper(nome_curso)", null);
        ArrayList<CadastroC> listaCadastro = new ArrayList<CadastroC>();
        while(cursor.moveToNext()){
            CadastroC c = new CadastroC();
            c.setNome(cursor.getString(0));
            c.setQtdHoras(cursor.getString(1));
            c.setId(cursor.getString(2));
            listaCadastro.add(c);
        }
        return listaCadastro;
    }
    public String recuperarNome(String nome){
        db = this.getReadableDatabase();
        String query = "select nome_curso from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String a, b;
        b = "nao encontrado";
        if(cursor.moveToFirst()){
            do{
                a = cursor.getString(0);
                if(a.equals(nome)){
                    b = cursor.getString(1);
                    break;
                }
            }while(cursor.moveToNext());
        }
        return b;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query ="DROP TABLE IF EXISTS " + TABLE_NAME;
        this.db.execSQL(query);
        this.onCreate(db);
    }
}
