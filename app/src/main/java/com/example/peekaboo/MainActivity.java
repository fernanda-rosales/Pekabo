package com.example.peekaboo;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Sensor sp;
    //Para obtener acceso a cualquier sensor de hardware necesitas un objetoManager
    private SensorManager sm;
    ObjectAnimator an1,an2;
    public ImageView tuImageView;



    //leyendo los datos del sensor que nos permite leer datos del sensor de proximidad cada dos segundos:

    SensorEventListener sel=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if(sensorEvent.values[0]<sp.getMaximumRange()){
                tuImageView.setImageResource(R.drawable.imagen);
            }
            else{
                tuImageView.setImageResource(R.drawable.imagenes);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tuImageView=(ImageView)findViewById(R.id.imageView);
        obtenerSensor();
        an1=ObjectAnimator.ofFloat(tuImageView,"translationX",300f);
        an1.setDuration(1000);
        an2=ObjectAnimator.ofFloat(tuImageView,"translationX",-300f);
        an2.setDuration(1000);
    }

    private void obtenerSensor(){

        //Obtener Acceso al Sensor de Proximidad
        //Para obtener acceso al sensor del móvil,se crea un objeto SensorManager con
        // el método getSystemService() y le
        //pasamos la constante SENSOR_SERVICE.

        sm= (SensorManager)getSystemService(SENSOR_SERVICE);

        //Creamos un objeto Sensor para el sensor de proximidad,
        // invocando el método getDefaultSensor() y le pasamos la constante TYPE_PROXIMITY.
        sp=sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        //revismaos que el objeto Sensor no sea null si es null significa
        // que el sensor de proximidad no está disponible
        if(sp==null){
            Toast.makeText(this,"No tienes sensor de giroscopio",Toast.LENGTH_LONG).show();
        }
    }
//nvocando el método registerListener() del objeto SensorManager.
// Al hacerlo, también debes especificar la frecuencia
// con la cual deberían leerse datos del sensor.
    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(sel,sp,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(sel);
    }
}
