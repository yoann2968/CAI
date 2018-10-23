package com.tp.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


/**
 * Created by yoann on 16/10/2018.
 * classe permettant l'activity_capteur de l'aide de l'application
 */

public class HelpActivity extends AppCompatActivity {

    //Déclaration des differentes variables pour l'application
    //Variable permettant de gérer l'activity_capteur de l'aide
    public TextView helpDisplay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        //Initialisation des variables pour l'activity_capteur de l'aide sur l'activité
        helpDisplay = findViewById(R.id.help_display);

        affichage();
    }

    //Création du menu identique pour toutes les activités sauf l'accueil
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_all, menu);
        return true;
    }

    //Gestion du choix de l'item dans le menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Parcours les différents items pouvant être sélectionner
        switch (item.getItemId()) {
            case R.id.Home:
                //Si l'utilisateur click sur le logo de maisson on revient a l'accueil
                Intent i = new Intent(HelpActivity.this, MainActivity.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Méthode permettant de faire l'activity_capteur de l'aide
    private void affichage() {

        String text = "Capteur à distance est une application permettant l'affichage des données relatives aux capteurs de son téléphone.\n" +
                "Pour cela cliquez sur l'icône en forme de maison en haut à droite, ceci vous permettra de revenir à l'écran d'accueil.\n" +
                "Une fois sur l'écran d'accueil deux choix s'offrent à vous pour obtenir les valeurs associées à vos capteurs: utiliser le bouton 'CAPTEUR DISPONIBLE' ou cliquer sur le menu en haut à gauche puis sur 'Affichage'.\n" +
                "Selectionnez ensuite le capteur et vos données s'affichent. Vous pouvez vous envoyer ces données par sms.";


        helpDisplay.setText(text);

    }

}
