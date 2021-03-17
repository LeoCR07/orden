package com.example.orden.Principal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.orden.Actividad.MesasActivity;
import com.example.orden.Comanda.ComandaA1Activity;
import com.example.orden.Configuracion.ConfiguracionActivity;
import com.example.orden.General.AdapterPagerView;
import com.example.orden.General.ModeloPagerView;
import com.example.orden.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected Button BtnViewPager;
    protected ViewPager viewPager;
    protected AdapterPagerView adapterPagerView;
    protected ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private List<ModeloPagerView> model1;
    private Integer[]colors = null;
    private Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        window = getWindow();


        ActionBar actionbar = getSupportActionBar();
      //  actionbar.hide();

       getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
       getSupportActionBar().setCustomView(R.layout.logo_toolbar);

        try{
            model1 = new ArrayList<>();

            BtnViewPager = findViewById(R.id.BtnSeleccion);

            model1.add(new ModeloPagerView(R.drawable.comanda,"Crear orden","Toma el pedido de forma rápida y ágil, ya sea para llevar o para comer aquí"));
            model1.add(new ModeloPagerView(R.drawable.actividad_main,"Salon","Realizar cambios a las mesas, como también ver sus platos y cancelar la cuenta "));
            model1.add(new ModeloPagerView(R.drawable.configuracion,"Configuración ","Cambia algunos parámetros para un uso más personalizado"));


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
                    Toast.makeText(MainActivity.this, ""+position, Toast.LENGTH_SHORT).show();
                    BtnViewPager.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            if(position==0){
                                startActivity(new Intent(MainActivity.this, ComandaA1Activity.class));
                            }else if(position==1){
                                startActivity(new Intent(MainActivity.this, MesasActivity.class));
                            }else if(position==2){
                                startActivity(new Intent(MainActivity.this, ConfiguracionActivity.class));
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