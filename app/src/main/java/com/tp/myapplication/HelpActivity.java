package com.tp.myapplication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

    //Méthode permettant de faire l'activity_capteur de l'aide
    private void affichage(){

        helpDisplay.setText("Voici un prémice du vrai fichier d'aide qui viendra plus tard");

    }

}
