package com.example.orden.General;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.orden.R;

import java.util.List;

public class AdapterPagerView extends PagerAdapter {

    private List<ModeloPagerView> models;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdapterPagerView(List<ModeloPagerView> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_view_pager,container,false);


        ImageView imageView;
        TextView title,desc;

        imageView = view.findViewById(R.id.Img);
        title =view.findViewById(R.id.titulo);
        desc = view.findViewById(R.id.desc);

        imageView.setImageResource(models.get(position).getImagen());
        title.setText(models.get(position).getTitulo());
        desc.setText(models.get(position).getDesc());

        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
}
