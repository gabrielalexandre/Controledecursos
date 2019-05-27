package com.example.controledecursos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroAluno extends AppCompatActivity {

    private EditText telefone, nome, email;
    private Button btCadastro;
    BDHelper helper = new BDHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_aluno);

        this.nome = findViewById(R.id.nomeAluno);
        this.telefone = findViewById(R.id.telAluno);
        this.email = findViewById(R.id.emailAluno);
        this.btCadastro = findViewById(R.id.cadastroAluno);

        Bundle args = getIntent().getExtras();
        if(args != null){
            btCadastro.setText("Alterar");
            String alNome = args.getString("al_nome");
            String alEmail = args.getString("al_email");
            String alTelefone = args.getString("al_tel");

            nome.setText(alNome);
            email.setText(alEmail);
            telefone.setText(alTelefone);
        }else{
            btCadastro.setText("Cadastrar");
        }
    }

    public void cadastrar(View view){
        String alNome = nome.getText().toString();
        String alEmail = email.getText().toString();
        String alTel = telefone.getText().toString();

            CadastroA a = new CadastroA(alNome, alEmail, alTel);
            if(btCadastro.getText().toString().equalsIgnoreCase("Alterar")){
                helper.alterarCadastro(a);
            }else {
                helper.insereAluno(a);
            }
            helper.close();
            Toast toast = Toast.makeText(CadastroAluno.this, "Operação realizada com sucesso!", Toast.LENGTH_SHORT);
            toast.show();
            finish();



    }
    public void verBD(View view){
        String alNome = nome.getText().toString();
        String alEmail = email.getText().toString();
        String alTel = telefone.getText().toString();

        Intent intent = new Intent(this, MostrarBD.class);
        intent.putExtra("chave_usuario", alNome);
        startActivity(intent);
    }
}
