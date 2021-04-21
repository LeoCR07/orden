package com.example.orden.Configuracion;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orden.Actividad.MesasActivity;
import com.example.orden.Comanda.ComandaA1Activity;
import com.example.orden.General.AdapterPagerView;
import com.example.orden.General.ModeloFactura;
import com.example.orden.General.ModeloPagerView;
import com.example.orden.General.MyAdapter;
import com.example.orden.Principal.MainActivity;
import com.example.orden.R;
import com.google.android.gms.dynamic.IFragmentWrapper;

import java.util.ArrayList;
import java.util.List;

public class ConfiguracionActivity extends AppCompatActivity{

    protected Button BtnViewPager;
    protected ViewPager viewPager;
    protected AdapterPagerView adapterPagerView;
    protected ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private List<ModeloPagerView> model1;
    private Integer[]colors = null;
    private Window window;
    private CharSequence[]array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);


        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Configuracion");
        //  actionbar.hide();

//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
  //      getSupportActionBar().setCustomView(R.layout.logo_toolbar);


        try{
            model1 = new ArrayList<>();

            BtnViewPager = findViewById(R.id.BtnSeleccion);

            array = getResources().getStringArray(R.array.ArrayConfiguracion);
            model1.add(new ModeloPagerView(R.drawable.mesa,array[0]+"","Cambia la cantidad maxima de mesas que permite el local"));
            model1.add(new ModeloPagerView(R.drawable.saloneros,array[1]+"","Agrega o elimina los usuarios que usan la aplicacion"));
            model1.add(new ModeloPagerView(R.drawable.modificando,array[2]+"","Agrega, Modifica, borra y bloquea los platos"));
            model1.add(new ModeloPagerView(R.drawable.inventario,array[3]+"","Agrega,Modifica o borra los inventarios"));
            model1.add(new ModeloPagerView(R.drawable.seguridad,array[4]+"","Habilita parametros importantes"));



            adapterPagerView = new AdapterPagerView(model1,this);
            viewPager = findViewById(R.id.ViewPager);
            viewPager.setAdapter(adapterPagerView);
            viewPager.setPadding(130,0,130,0);

            Integer [] colors_temp = {getResources().getColor(R.color.colorComanda),getResources().getColor(R.color.colorActividad),
                    getResources().getColor(R.color.colorConfiguracion)};

            colors = colors_temp;

            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    //Toast.makeText(ConfiguracionActivity.this, ""+position, Toast.LENGTH_SHORT).show();
                    BtnViewPager.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            if(position==0){
                                startActivity(new Intent(ConfiguracionActivity.this,NumeroMesaActivity.class));
                            }else if(position==1){
                                startActivity(new Intent(ConfiguracionActivity.this,EditarUsuarioActivity.class));
                            }else if(position==2){
                                startActivity(new Intent(ConfiguracionActivity.this,ModificarPlatoActivity.class));
                            }

                        }
                    });

                }

                @Override
                public void onPageSelected(final int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                    //Toast.makeText(MainActivity.this, state+"", Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception x){
            Toast.makeText(this,x.getMessage().toString(),Toast.LENGTH_LONG).show();
        }


    }


}