package com.example.paises;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import WebServices.Asynchtask;
import WebServices.WebService;

public class MainActivity extends AppCompatActivity implements Asynchtask, AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService("http://www.geognos.com/api/en/countries/info/all.json",datos,MainActivity.this,MainActivity.this);
        ws.execute("");


    }

    @Override
    public void processFinish(String result) throws JSONException {
        List<Paises> listaPais = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(result);
        JSONObject jprimerResult = jsonObject.getJSONObject("Results");
        Iterator<?> iterator = jprimerResult .keys();//Iterator le permite recorrer una colección, obtener o eliminar elementos
        while (iterator.hasNext()){
            String key =(String)iterator.next();
            JSONObject paisJ = jprimerResult .getJSONObject(key);
            Paises paises = new Paises();
            paises.setNombre(paisJ.getString("Name"));
            JSONObject jCountryCodes = paisJ.getJSONObject("CountryCodes");
            paises.setImagen(jCountryCodes.getString("iso2"));
            listaPais.add(paises);
        }

        
        ListView lstOpciones= (ListView)findViewById(R.id.lvPais);
        AdaptadorPaises adaptadorrevistas = new AdaptadorPaises(this, (ArrayList<Paises>) listaPais );
        lstOpciones.setAdapter(adaptadorrevistas);

        getPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        getPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

        lstOpciones.setOnItemClickListener(this);
    }
    public void getPermission(String permission){
        if (Build.VERSION.SDK_INT >= 23) {
            if (!(checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED))
                ActivityCompat.requestPermissions(this, new String[]{permission}, 1);
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1)
        {
            Toast.makeText(this.getApplicationContext(),"OK", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
        String imagen= (((Paises)adapterView.getItemAtPosition(i)).getImagen());
        String Pais= (((Paises)adapterView.getItemAtPosition(i)).getNombre());
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(imagen));
        request.setDescription(Pais);
        request.setTitle(Pais+".png");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "filedownload.png");
        DownloadManager manager= (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        try {
            manager.enqueue(request);
        } catch (Exception e) {
            Toast.makeText(this.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();


        }



    }
}
