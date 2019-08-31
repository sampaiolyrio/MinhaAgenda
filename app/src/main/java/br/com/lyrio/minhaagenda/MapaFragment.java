package br.com.lyrio.minhaagenda;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import br.com.lyrio.minhaagenda.dao.PessoaDAO;
import br.com.lyrio.minhaagenda.modelo.Pessoa;

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        LatLng posicaoInicial = pegaCoordenadaDoEndereco
                                ("Centro, Niteroi-RJ");
           if(posicaoInicial != null) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(posicaoInicial, 17);
            googleMap.moveCamera(update);
        }

        PessoaDAO dao = new PessoaDAO(getContext());
        for(Pessoa pessoa : dao.buscaPessoas()){
            LatLng coordenada = pegaCoordenadaDoEndereco(pessoa.getEndereco());
            if(coordenada != null){
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(coordenada);
                marcador.title(pessoa.getNome());
                marcador.snippet(String.valueOf(pessoa.getNota()));
                googleMap.addMarker(marcador);
            }
        }
        dao.close();



    }

    private LatLng pegaCoordenadaDoEndereco(String endereco){

        try {
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> resultados = geocoder.getFromLocationName( endereco,1);
            if(!resultados.isEmpty()){
                LatLng posicao = new LatLng(resultados.get(0).getLatitude(), resultados.get(0).getLongitude());
                return posicao;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;


    }
}
