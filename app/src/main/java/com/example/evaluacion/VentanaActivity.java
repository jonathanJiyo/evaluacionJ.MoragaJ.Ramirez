package com.example.evaluacion;

import android.os.Bundle;
//Librerias a Utilizar
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Adapter;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.Polyline;

import androidx.appcompat.app.AppCompatActivity;

public class VentanaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState ) {{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Cargar la configuracion del mapa predeterminada
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        //Obtenet la referencia del mapa
        MapView mapView = findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);

        //Agrego un marcador en el mapa
        double ipSantoTomasLatitud = -33.4493141;
        double ipSantoTomasLongitud = -70.6624069;
        //Creo un Objeto GeoPoint para el marcador
        GeoPoint SantoTomasPoint = new GeoPoint(ipSantoTomasLatitud, ipSantoTomasLongitud);
        //Configuro la vista Inicial del Mapa
        mapView.getController().setZoom(15.0);
        //Centro el Mapa
        mapView.getController().setCenter(SantoTomasPoint);

        //Creamos un Marcador
        Marker marcadorSantoTomas = new Marker(mapView);
        marcadorSantoTomas.setPosition(SantoTomasPoint);
        marcadorSantoTomas.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marcadorSantoTomas.setTitle("IP Santo Tomas, Chile");
        marcadorSantoTomas.setSnippet("Tu Puedes");
        mapView.getOverlays().add(marcadorSantoTomas);

        double miCasaLatitud = -33.4651209;
        double miCasaLongitud = -70.7047496;
        GeoPoint miCasaPoint = new GeoPoint(miCasaLatitud, miCasaLongitud);
        Marker marcadorMiCasa = new Marker(mapView);
        marcadorMiCasa.setPosition(miCasaPoint);
        marcadorMiCasa.setAnchor(0.2f, 0.4f);
        marcadorMiCasa.setInfoWindowAnchor(0.2f, 0.4f);
        marcadorMiCasa.setTitle("IP Mi Casita, Chile");
        marcadorMiCasa.setSnippet("Tu Puedes");
        marcadorMiCasa.setIcon(getResources().getDrawable(R.drawable.lupa));
        mapView.getOverlays().add(marcadorMiCasa);

        //Agrego una linea entre Marcadores
        Polyline linea = new Polyline();
        //Añado una linea entre los marcadores
        linea.addPoint(SantoTomasPoint);
        linea.addPoint(miCasaPoint);
        linea.setColor(0xFF000FF);
        linea.setWidth(5);
        //Añado la linea en el mapa
        mapView.getOverlayManager().add(linea);

        Spinner mapTypeSpinner = findViewById(R.id.mapTypeSpinner);
        String[] mapTypes = {"Mapa Normal", "Mapar de Transporte", "Mapa Topografico"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mapTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mapTypeSpinner.setAdapter(adapter);

        mapTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { switch (position){
                case 0:
                    mapView.setTileSource(TileSourceFactory.MAPNIK);
                    break;
                case 1:
                    mapView.setTileSource(new XYTileSource(
                            "PublicTransport",
                            0, 18, 256, ".png", new String[]{
                            "https://tile.memomaps.de/tilegen/"}));
                    break;
                case 2:
                    mapView.setTileSource(new XYTileSource(
                            "USGS_Satellite", 0, 18, 256, ".png", new String[]{
                            "https://a.tile.opentopomap.org/",
                            "https://b.tile.opentopomap.org/",
                            "https://c.tile.opentopomap.org/"}));
                    break;
            }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana);
    }
}
