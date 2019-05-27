package com.example.controledecursos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class cursoBD extends AppCompatActivity {
    private ArrayList<CadastroC> arrayListCurso;
    private ListView listaCursos;
    private ArrayAdapter<CadastroC> arrayAdapterCurso;
    private CadastroC curso = new CadastroC();
    private int id1, id2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curso_bd);
        listaCursos = findViewById(R.id.listaCursos);
        Bundle args = getIntent().getExtras();
        String nome = args.getString("chave_usuario");
        registerForContextMenu(listaCursos);
        preenche();
        listaCursos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CadastroC cursoEnviado = (CadastroC) arrayAdapterCurso.getItem(position);
                Bundle param = new Bundle();
                param.putString("cs_nome", cursoEnviado.getNome());
                param.putString("cs_horas", cursoEnviado.getQtdHoras());

                Intent intent = new Intent(cursoBD.this, CadastroCursos.class);
                intent.putExtras(param);
                startActivity(intent);
            }
        });
        listaCursos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                curso = arrayAdapterCurso.getItem(position);
                return false;
            }
        });
    }

    public void preenche(){
        BDHelper2 contatoDB = new BDHelper2(cursoBD.this);
        arrayListCurso = contatoDB.selectAllContatos();
        contatoDB.close();
        if(listaCursos != null){
            arrayAdapterCurso = new ArrayAdapter<CadastroC>(cursoBD.this,
                    android.R.layout.simple_list_item_1, arrayListCurso);
            listaCursos.setAdapter(arrayAdapterCurso);
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        MenuItem delete = menu.add(Menu.NONE, id1, 1, "Apaga");
        MenuItem sair = menu.add(Menu.NONE, id2, 2, "Cancela");
        delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                long retornBD;
                BDHelper2 helper = new BDHelper2(cursoBD.this);
                retornBD = helper.excluirCurso(curso);
                if(retornBD == -1){
                    Toast.makeText(cursoBD.this, "Erro de exclusão",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(cursoBD.this, "Excluído",
                            Toast.LENGTH_SHORT).show();
                    preenche();
                }
                return false;
            }
        });
        //super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    protected  void onResume(){
        super.onResume();
        preenche();
    }
}
