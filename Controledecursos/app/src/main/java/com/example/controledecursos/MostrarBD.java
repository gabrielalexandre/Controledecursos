package com.example.controledecursos;

import android.content.Intent;
import android.support.v4.app.BundleCompat;
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

public class MostrarBD extends AppCompatActivity {
    //private TextView txtNome;
    private ListView listaAlunos;
    private ArrayList<CadastroA> arrayListCadastro;
    private ArrayAdapter<CadastroA> arrayAdapterCadastro;
    private int id1, id2;

    private CadastroA cadastro = new CadastroA();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_bd);
        //txtNome = findViewById(R.id.txtNome);
        listaAlunos = findViewById(R.id.listaAlunos);
        Bundle args = getIntent().getExtras();
        String nome = args.getString("chave_usuario");
        //txtNome.setText(nome);
        registerForContextMenu(listaAlunos);
        insere();
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                CadastroA alunoEnviado = (CadastroA)arrayAdapterCadastro.getItem(position);
                Bundle param = new Bundle();
                param.putString("al_nome", alunoEnviado.getNome());
                param.putString("al_email", alunoEnviado.getEmail());
                param.putString("al_tel", alunoEnviado.getTelefone());

                Intent intent = new Intent(MostrarBD.this, CadastroAluno.class);
                intent.putExtras(param);
                startActivity(intent);
            }
        });
        listaAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                cadastro = arrayAdapterCadastro.getItem(position);
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        MenuItem delete = menu.add(Menu.NONE, id1, 1, "Apaga Registro");
        MenuItem sair = menu.add(Menu.NONE,  id2, 2, "Cancela");

        delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                long retornDB;
                BDHelper helper = new BDHelper(MostrarBD.this);
                retornDB = helper.excluirCadastro(cadastro);

                if(retornDB == -1){
                    Toast.makeText(MostrarBD.this, "Erro", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MostrarBD.this, "Excluido", Toast.LENGTH_SHORT).show();
                    insere();
                }
                return false;
            }
        });
        //super.onCreateContextMenu(menu, v, menuInfo);
    }
    public void insere(){
        BDHelper conexao = new BDHelper(MostrarBD.this);
        arrayListCadastro = conexao.selectAllCadastros();
        conexao.close();
        if(listaAlunos != null){
            arrayAdapterCadastro = new ArrayAdapter<CadastroA>(MostrarBD.this, android.R.layout.simple_list_item_1, arrayListCadastro);
            listaAlunos.setAdapter(arrayAdapterCadastro);
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        insere();
    }
}
