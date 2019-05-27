package com.example.controledecursos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroCursos extends AppCompatActivity {
    private EditText nomeCurso, qtdHoras;
    private Button btCadastra, btnVerBD;
    BDHelper2 helper = new BDHelper2(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cursos);

        this.nomeCurso = findViewById(R.id.nomeCurso);
        this.qtdHoras = findViewById(R.id.qtdHoras);
        this.btCadastra = findViewById(R.id.btnCadastroCurso);
        this.btnVerBD = findViewById(R.id.btnVerCursos);
        Bundle args = getIntent().getExtras();
        if(args != null){
            btCadastra.setText("Alterar");
            String nome = args.getString("cs_nome");
            String horas = args.getString("cs_horas");

            nomeCurso.setText(nome);
            qtdHoras.setText(horas);
        }else{
            btCadastra.setText("Cadastrar");
        }
    }

    public void cadastro(View v){
        String nome = nomeCurso.getText().toString();
        String horas = qtdHoras.getText().toString();
        CadastroC c = new CadastroC(nome, horas);

        if (btCadastra.getText().toString().equalsIgnoreCase("Alterar")) {
            helper.alterarCurso(c);
        } else {
            helper.insereCurso(c);
        }
        Toast toast = Toast.makeText(CadastroCursos.this, "Inserido com sucesso", Toast.LENGTH_SHORT);
        toast.show();
        finish();
    }

    public void verCurso(View v){
        String nome = nomeCurso.getText().toString();

        Intent intent = new Intent(this, cursoBD.class);
        intent.putExtra("chave_usuario", nome);
        startActivity(intent);
    }
}
