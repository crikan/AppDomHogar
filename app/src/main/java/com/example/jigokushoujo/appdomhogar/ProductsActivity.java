package com.example.jigokushoujo.appdomhogar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {

    private ArrayList<ProductItem> listaproductos = new ArrayList<ProductItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        getSupportActionBar().hide();

        listaproductos.add(new ProductItem(R.string.prod_title1,R.string.prod_desc1, R.drawable.decowifi));
        listaproductos.add(new ProductItem(R.string.prod_title2,R.string.prod_desc2, R.drawable.cam));
        listaproductos.add(new ProductItem(R.string.prod_title3,R.string.prod_desc3, R.drawable.smartlamp));
        listaproductos.add(new ProductItem(R.string.prod_title4,R.string.prod_desc4, R.drawable.smartsocket));
        listaproductos.add(new ProductItem(R.string.prod_title5,R.string.prod_desc5, R.drawable.repeater));

        ProductsAdapter adapter = new ProductsAdapter(this);
        ListView lv = (ListView)findViewById(R.id.lista_prod);
        lv.setAdapter(adapter);

    }

    class ProductsAdapter extends ArrayAdapter {

        AppCompatActivity appCompatActivity;

        ProductsAdapter(AppCompatActivity context) {
            super(context, R.layout.product_item, listaproductos);
            appCompatActivity = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = appCompatActivity.getLayoutInflater();
            View item = inflater.inflate(R.layout.product_item, null);

            TextView titulo = (TextView)item.findViewById(R.id.prod_title);
            titulo.setText(listaproductos.get(position).getTitle());

            TextView descrip  = (TextView)item.findViewById(R.id.prod_text);
            descrip.setText(listaproductos.get(position).getDescription());

            ImageView imagen = (ImageView)item.findViewById(R.id.prod_img);
            imagen.setImageResource(listaproductos.get(position).getImage());

            return item;
        }
    }
}
