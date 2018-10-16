package com.tp.myapplication;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


/**
 * Created by yoann on 09/10/2018.
 * classe principal de l'application, permettant d'initialiser la zone de dessin, de récupéré les valeur des capteur et de déplacer les éléments
 */


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //Création du menu pour l'acitivité principal de l'application
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Méthode permettant de savoir quelle item dans le menu a été sélectionner
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Parcours les différents items pouvant être sélectionner
        switch (item.getItemId()) {
            case R.id.Help:
                Toast toast = Toast.makeText(MainActivity.this , "Vous avez choisit le menu d'aide", Toast.LENGTH_LONG);
                toast.show();

                Intent i = new Intent (MainActivity.this, HelpActivity.class);
                startActivity(i);
                return true;

            case R.id.item1:
                //Si l'utilisateur appuye sur le menu dessin, on ouvre l'activité de dessin
                Intent intent = new Intent(MainActivity.this, DessinActivity.class);
                startActivity(intent);
                return true;

            case R.id.item2:
                //Si l'utilisateur choisit activity_capteur, on ouvre l'activité d'activity_capteur des capteurs

                Toast toast1 = Toast.makeText(MainActivity.this , "Vous avez choisit CapteurActivity dans le menu", Toast.LENGTH_LONG);
                toast1.show();

                Intent intent2 = new Intent(MainActivity.this, CapteurActivity.class);
                startActivity(intent2);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}