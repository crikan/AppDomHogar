package com.example.jigokushoujo.appdomhogar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

/* TODO comentar https://android.jlelse.eu/custom-dialog-with-circular-reveal-animation-ef7dc77ba1e*/

public class MainActivity extends AppCompatActivity {

    Button dom; //botón DOM
    Button exit; //botón para salir de la aplicación
    Button serv; //botón para alcceder al catálogo de servicios
    Button prod; //botón para acceder al catálogo de productos
    Button cont; //botón para ir al formulario de contacto


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide(); //oculta la barra superior

        //----------------Funcionalidad de los botones---------------
        dom = (Button) findViewById(R.id.home_btn);
        dom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDiag();
            }
        });
        exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        serv = (Button) findViewById(R.id.serv_btn);
        serv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ServicesActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        prod = (Button) findViewById(R.id.prod_btn);
        prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProductsActivity.class);
                startActivity(intent);
            }
        });
        cont = (Button) findViewById(R.id.cont_btn);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ContactActivity.class);
                startActivityForResult(intent,0);
            }
        });



    }


    // este método crea el dialog y lo pone en visible o transparente según el resultado de sus comprobaciones
    private void showDiag() {

        final View dialogView = View.inflate(this, R.layout.dialog, null);
        //creamos el objeto Dialog y lo damos un estilo personalizado
        final Dialog dialog = new Dialog(this, R.style.MyAlertDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //creamos un dialog sin título
        dialog.setContentView(dialogView);
        //localizamos el botón de cerrar y le colocamos un listener
        ImageView imageView = (ImageView) dialog.findViewById(R.id.closeDialogImg);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //este método arranca la animación. necesita 3 argumentos: el view, un booleano que
                // será true para abrir y false para cerrar, y el propio Dialog
                revealShow(dialogView, false, dialog);
            }
        });

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                revealShow(dialogView, true, null);
            }
        });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {

                    revealShow(dialogView, false, dialog);
                    return true;
                }

                return false;
            }
        });
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }

    // método que ejecuta la animación de revelado del dialog
    private void revealShow(View dialogView, boolean b, final Dialog dialog) {
        final View view = dialogView.findViewById(R.id.dialog);
        int w = view.getWidth();
        int h = view.getHeight();
        int endRadius = (int) Math.hypot(w, h);
        int cx = (int) (dom.getX() + (dom.getWidth() / 2));
        int cy = (int) (dom.getY()) + dom.getHeight() + 56;
        if (b) {
            Animator revealAnimator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, endRadius);

            view.setVisibility(View.VISIBLE);
            revealAnimator.setDuration(700);
            revealAnimator.start();
        } else {
            Animator anim =
                    ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius, 0);

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    dialog.dismiss();
                    view.setVisibility(View.INVISIBLE);

                }
            });
            anim.setDuration(700);
            anim.start();
        }
    }


}



