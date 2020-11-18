package mannings.msi.com.ttddfimifes;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;

public class Pesquisa extends AppCompatActivity {

    private Button buttonSearch,buttonWord;
    private EditText editTextSearch,editTextWord;
    private String grupo,palavra;
    private Toolbar toolbar;
    SharedPreferences preferences;
    String[] simbolosValidos = {"100","110","120","121","121.1","121.2","121.21","121.3","122","122.1","122.2","122.3","122.31","122.32","123","123.1","123.2","124","124.1","125","125.1","125.11","125.111","125.112","125.113","125.114","125.115","125.116","125.12","125.13","125.131","125.132","125.14","125.19","125.2","125.21","125.22","125.23","125.24","125.241","125.242","125.25","125.251","125.252","125.253","125.26","125.3","125.31","125.32","125.321","125.322","125.323","125.33","125.34","125.4","125.41","125.42","125.421","125.422","125.423","125.424","125.425","125.43","125.5","125.51","125.52","125.6","125.61","125.611","125.612","125.613","125.62","125.63","125.631","125.632","125.633","125.64","125.7","125.71","125.72","125.8","130","131","131.1","131.2","131.21","131.3","131.4","132","132.1","132.2","132.3","132.31","133","134","134.1","134.11","134.111","134.112","134.113","134.114","134.115","134.116","134.12","134.13","134.19","134.2","134.21","134.22","134.23","134.24","134.241","134.242","134.25","134.251","134.252","134.253","134.3","134.31","134.32","134.321","134.322","134.323","134.324","134.33","134.331","134.332","134.333","134.334","134.34","134.4","134.41","134.42","134.421","134.422","134.423","134.424","134.43","134.5","134.6","134.61","134.611","134.612","134.613","134.62","134.63","134.7","134.71","134.72","134.8","140","141","141.1","141.2","142","142.1","142.2","142.3","142.31","143","144","144.1","144.11","144.111","144.112","144.113","144.114","144.115","144.116","114.19","144.2","144.21","144.22","144.23","144.24","144.241","144.242","144.25","144.251","144.252","144.253","144.3","144.31","144.32","144.321","144.322","144.323","144.33","144.4","144.41","144.42","144.421","144.422","144.423","144.424","144.43","144.5","144.6","144.61","144.611","144.612","144.613","144.62","144.63","144.7","190","200","210","220","221","222","230","231","232","240","241","242","243","244","250","251","251.1","251.2","252","252.1","252.2","252.3","253","253.1","253.2","254","254.1","254.2","254.21","254.22","254.23","254.24","260","261","261.1","261.2","262","262.1","262.2","263","263.1","263.2","290","300","310","320","321","322","330","331","332","340","341","342","343","344","345","346","350","351","352","353","354","355","356","360","361","362","370","371","372","380","381","382","383","384","390","400","410","420","421","421.1","421.2","422","423","424","425","425.1","425.11","425.111","425.12","425.2","425.21","425.3","425.9","425.91","430","431","431.1","431.2","432","432.1","432.2","433","434","435","435.1","435.11","435.111","435.112","435.113","435.114","435.115","435.116","435.12","435.13","435.2","435.21","435.211","435.3","435.31","435.32","435.4","435.41","435.42","435.43","435.5","435.6","435.9","435.91","440","441","441.1","441.2","442","442.1","442.2","443","444","445","445.1","445.11","445.111","445.112","445.113","445.114","445.115","445.116","445.12","445.19","445.2","445.21","445.211","445.3","445.31","445.32","445.4","445.41","445.42","445.421","445.422","445.43","445.5","445.6","445.7","445.9","445.91","450","451","451.1","451.2","452","452.1","452.2","452.3","452.31","452.32","453","454","455","455.1","456","456.1","456.11","456.111","456.112","456.113","456.114","456.115","456.116","456.12","456.121","456.122","456.19","456.2","456.21","456.211","456.22","456.23","456.3","456.31","456.32","456.4","456.41","456.42","456.421","456.422","456.423","456.43","456.5","456.6","456.7","490","500","510","520","521","521.1","521.2","521.3","522","522.1","522.2","522.3","523","523.1","523.2","523.3","524","524.1","524.2","524.3","525","525.1","525.2","525.3","526","526.1","526.2","526.3","527","527.1","527.2","527.3","528","528.1","528.2","528.3","529","529.1","529.2","529.3","529.4","529.5","529.51","529.52","529.53","590"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);
        buttonSearch = (Button)findViewById(R.id.buttonSearch);
        buttonWord = (Button)findViewById(R.id.buttonWord);
        editTextSearch = (EditText)findViewById(R.id.editTextSearch);
        editTextWord = (EditText)findViewById(R.id.editTextWord);

        toolbar = (Toolbar) findViewById(R.id.toolbarPesquisa);
        setSupportActionBar(toolbar);

        preferences = getSharedPreferences("status_app", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("app_encerrado", false);
        editor.apply();

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                grupo = editTextSearch.getText().toString();

                if (!grupo.equals("")) {

                    grupo = grupo.replaceAll(" ", "");
                    grupo = grupo.toUpperCase();

                    if (Arrays.asList(simbolosValidos).contains(grupo)) {

                        Intent intent = new Intent(Pesquisa.this, GruposPesquisa.class);
                        intent.putExtra("grupo", grupo);
                        startActivity(intent);

                    } else {

                        Toast.makeText(Pesquisa.this, "Símbolo não identificado", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(Pesquisa.this, "Nenhum símbolo digitado", Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                palavra = editTextWord.getText().toString();

                if (!palavra.equals("")) {

                    palavra = palavra.replaceAll(" ", "");
                    palavra = palavra.toUpperCase();
                    Intent intent = new Intent(Pesquisa.this, BuscaPalavra.class);
                    intent.putExtra("palavra", palavra);
                    startActivity(intent);

                }
                else{
                    Toast.makeText(Pesquisa.this, "Nenhuma palavra digitada", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    protected void onStart(){
        super.onStart();
        preferences = getSharedPreferences("status_app", MODE_PRIVATE);
        if (preferences.getBoolean("app_encerrado",false)) finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item_sair:
                preferences = getSharedPreferences("status_app", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("app_encerrado", true);
                editor.apply();
                finish();
                return true;
            case R.id.item_pesquisa:
                //pesquisa();
                return true;
            case R.id.item_inicio:
                vaParaTelaInicial();
                return true;
            case R.id.item_about:
                TelaAbout();
                return true;
            case R.id.item_ajuda:
                TelaAjuda();
                return true;
            //case R.id.item_onosmatico:
            //    pesquisa_onosmatico();
            //    return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    public void pesquisa_onosmatico(){
        Intent intent = new Intent(getApplicationContext(),Onosmatico.class);
        startActivity(intent);
        //finish();
    }

    public void pesquisa(){
        Intent intent = new Intent(getApplicationContext(),Pesquisa.class);
        startActivity(intent);
        //finish();
    }

    public void vaParaTelaInicial(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void TelaAbout(){
        Intent intent = new Intent(getApplicationContext(),About.class);
        startActivity(intent);
        finish();
    }

    public void TelaAjuda(){
        Intent intent = new Intent(getApplicationContext(),Ajuda.class);
        startActivity(intent);
        finish();
    }

}
