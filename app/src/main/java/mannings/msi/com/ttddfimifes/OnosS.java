package mannings.msi.com.ttddfimifes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import mannings.msi.com.ttddfimifes.adapter.RegistroAdapter;
import mannings.msi.com.ttddfimifes.model.RegistroOnos;

public class OnosS extends AppCompatActivity {

    private ListView listView;
    private Toolbar toolbar;
    SharedPreferences preferences;
    private int proxima_tela;
    private int count_proximo;
    private Document doc;
    private int id,indice,proximo;
    private String texto;
    private String grupo,kind,assunto,subassunto;
    private String[] array_classes = new String[40];
    private int[] array_proximo = new int[40];
    private ArrayList<RegistroOnos> registros;
    private ArrayAdapter<RegistroOnos> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onos_s);

        listView=(ListView)findViewById(R.id.listViewOnosS);
        toolbar = (Toolbar) findViewById(R.id.toolbarOnosS);
        setSupportActionBar(toolbar);

        preferences = getSharedPreferences("status_app", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("app_encerrado", false);
        editor.apply();

        Bundle extras = getIntent().getExtras();
        proxima_tela = extras.getInt("proxima_tela");

//        mensagens = new ArrayList<String>();
//        adapter = new ArrayAdapter(OnosS.this, android.R.layout.simple_list_item_1,mensagens);
//        listView.setAdapter( adapter );
//        mensagens.clear();

        registros = new ArrayList<>();
        registros.clear();
        adapter = new RegistroAdapter(getApplicationContext(), registros );
        listView.setAdapter( adapter );
        adapter.notifyDataSetChanged();

        try{
            AssetManager mngr = this.getAssets();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(mngr.open("xml/onosmatico.xml"));

            doc.getDocumentElement().normalize();
            NodeList nodeContatos = doc.getElementsByTagName("row");
            int counter = nodeContatos.getLength();
            count_proximo=1;
            RegistroOnos registro0 = new RegistroOnos();
            registro0.setAssunto("<<<");
            registro0.setSubAssunto(" ");
            registros.add(registro0);

            for (int i = 0; i < counter; i++) {
                Node item = nodeContatos.item(i);
                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) item;
                    Node nodeNome = element.getElementsByTagName("field").item(0).getChildNodes().item(0);
                    nodeNome = element.getElementsByTagName("field").item(0).getChildNodes().item(0);
                    id = Integer.parseInt(nodeNome.getNodeValue().toString());
                    nodeNome = element.getElementsByTagName("field").item(1).getChildNodes().item(0);
                    grupo = nodeNome.getNodeValue().toString();
                    nodeNome = element.getElementsByTagName("field").item(3).getChildNodes().item(0);
                    assunto = nodeNome.getNodeValue().toString();
                    nodeNome = element.getElementsByTagName("field").item(4).getChildNodes().item(0);
                    subassunto = nodeNome.getNodeValue().toString();
                    if (!subassunto.equals(" ")) subassunto = "ver "+subassunto;
                    nodeNome = element.getElementsByTagName("field").item(5).getChildNodes().item(0);
                    indice = Integer.parseInt(nodeNome.getNodeValue().toString());
                    nodeNome = element.getElementsByTagName("field").item(6).getChildNodes().item(0);
                    proximo = Integer.parseInt(nodeNome.getNodeValue().toString());

                    if (indice==proxima_tela) {
                        Display display = getWindowManager().getDefaultDisplay();
                        Point size = new Point();
                        display.getSize(size);
                        int width = size.x;
                        int height = size.y;
                        Resources r = OnosS.this.getResources();
                        float densidade = r.getDisplayMetrics().density;
                        float widthsp = (int) ( width / getResources().getDisplayMetrics().scaledDensity );
                        int bordas = 16;
                        int largura_caracter = 10;
                        int intervalo = (int)((widthsp - bordas)/largura_caracter) - 1;

                        texto = assunto;
                        int total = intervalo - (assunto.length() + grupo.length());
                        if (total<0) {

                            int delta;
                            if (width==480)
                                delta = 17;
                            else if (width==720)
                                delta = 13;
                            else if (width==768)
                                delta = 11;
                            else if (width==1080)
                                delta = 8;
                            else if (width==1440)
                                delta = 8;
                            else if (width<720)
                                delta = (int) (((17-13)*width + 480*13 - 720*17)/(480-720));
                            else if (width>720 && width<768)
                                delta = (int) (((13-11)*width + 720*11 - 768*13)/(720-768));
                            else if (width>768 && width<1080)
                                delta = (int) (((11-8)*width + 768*8 - 1080*11)/(768-1080));
                            else
                                delta = 8;

                            if (id==81) // cadastramento de Curso de pós-graduação lato sensu
                                total=27-delta;
                            else if (id==111) // Registro acadêmico - Curso de Graduação
                                total=31-delta;
                            else if (id==114) // Registro escolar - Ensino técnico
                                total=31-delta;
                            else if (id==164) // ATENDIMENTO AOS PORTADORES DE NECESSIDADES ESPECIAIS
                                total=33-delta;
                            else if (id==188) // curso de pós-graduação lato-sensu
                                total=30-delta;
                            else if (id==211) // curso de pós-graduação lato-sensu
                                total=27-delta;
                            else if (id==214) // outros assuntos referentes programas, convênios e projetos de concessão de benefícios e auxílios. Bolsa auxílio
                                total=25-delta;
                            else if (id==229) // moradia estudantil, moradia universitária
                                total=28-delta;
                            else if (id==241) // transferência e inovação tecnológica
                                total=26-delta;
                            else if (id==273) // trabalho de conclusão de curso de graduação
                                total=27-delta;
                            else if (id==278) // assistência à saúde física e mental - Assistência estudantil
                                total=29-delta;
                            else if (id==316) // atendimento aos portadores de necessidades especiais
                                total=20-delta;
                            else if (id==333) // transporte - Assistência estudantil
                                total=27-delta;
                            else if (id==352) // transporte - Assistência estudantil
                                total=26-delta;
                            else if (id==354) // atendimento aos portadores de necessidades especiais
                                total=26-delta;
                            else if (id==363 || id==364 || id==365) // curso de graduação. iniciação científica
                                total=27-delta;
                            else if (id==384) // outros assuntos referentes a concessão de benefícios e auxílios
                                total=29-delta;
                            else if (id==386) // outros assuntos referentes a concessão de benefícios e auxílios
                                total=15-delta;
                            else if (id==399) // CALENDÁRIO ACADÊMICO, planejamento de atividade acadêmica
                                total=27-delta;
                            else
                                total=35-delta;

                            texto = texto + " ";
                        }

                        if (!grupo.equals(" ")){
                            for (int j=0; j<=total; j++) texto = texto + ".";
                        }

                        texto = texto + grupo;

                        RegistroOnos registro1 = new RegistroOnos();
                        registro1.setAssunto(texto);
                        registro1.setSubAssunto(subassunto);
                        registros.add( registro1 );

                        array_proximo[count_proximo]=proximo;
                        array_classes[count_proximo]=grupo;
                        count_proximo = count_proximo+1;
                    }
                }
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    finish();
                }else {
                    grupo = array_classes[position];
                    if (grupo.equals(" ")) {
                        if (array_proximo[position] == 0) {
                            String str2 = array_classes[position];
                            Toast.makeText(getBaseContext(), "Não há subníveis para esta palavra", Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(OnosS.this, OnosG.class);
                            proxima_tela = array_proximo[position];
                            intent.putExtra("proxima_tela", proxima_tela);
                            startActivity(intent);
                        }
                    }
                    else
                    {
                        Intent intent = new Intent(OnosS.this, GruposPesquisa.class);
                        intent.putExtra("grupo", grupo);
                        startActivity(intent);
                    }
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
                pesquisa();
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
