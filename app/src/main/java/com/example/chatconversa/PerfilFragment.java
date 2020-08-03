package com.example.chatconversa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PerfilFragment extends Fragment {
    private String token;
    private int user_id;
    private String username;
    private String fotoperfil;
    private ImageView contenedorfoto;

    private final static int REQUEST_PERMISSION = 1001;
    private final static int REQUEST_CAMERA = 1002;
    private final static String[] PERMISSION_REQUIRED =
            new String[]{"android.permission.CAMERA","android.permission.WRITE_EXTERNAL_STORAGE"};

    private Context myCtx;
    private Button volver;
    private Button tomarfoto;
    private Button subirfoto;

    private String pathphoto;
    private ServicioWeb servicio;

    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_perfil,container,false);



        subirfoto = root.findViewById(R.id.subirfoto);
        volver = root.findViewById(R.id.back);
        tomarfoto = root.findViewById(R.id.buttontomarfoto);
        contenedorfoto= root.findViewById(R.id.contenedorimagendeperfil);

        user_id = getArguments().getInt("user_id");
        username = getArguments().getString("username");
        token = getArguments().getString("token");
        fotoperfil = getArguments().getString("fotoperfil");

        System.out.println("Params: " + user_id + " - "+ username + " - "+ token + " - "+ fotoperfil);

        if (!fotoperfil.equals("")){
            Glide.with(getContext())
                    .load(fotoperfil)
                    .into(contenedorfoto);
        }



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://chat-conversa.unnamed-chile.com/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        servicio = retrofit.create(ServicioWeb.class);

        subirfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_id = getArguments().getInt("user_id");
                username = getArguments().getString("username");
                token = getArguments().getString("token");
                fotoperfil = getArguments().getString("fotoperfil");
                System.out.println("Params Before Call: " + user_id + " - "+ username + " - "+ token + " - "+ fotoperfil);
                System.out.println("Path foto perfil" + pathphoto);

                if (pathphoto != null){
                    File archivoImage = new File(pathphoto);
                    RequestBody imagen = RequestBody.create(MediaType.parse("multipart/form-data"), archivoImage);
                    MultipartBody.Part file = MultipartBody.Part.createFormData("user_image", archivoImage.getName(),imagen);
                    RequestBody user = RequestBody.create(MediaType.parse("multipart/form-data"), username);
                    RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(user_id));
                    String tokenBearer = "Bearer " + token;

                    Call<RespuestaSubirImagen> resp = servicio.subirimagen(file,user,id,tokenBearer);
                    resp.enqueue(new Callback<RespuestaSubirImagen>() {
                        @Override
                        public void onResponse(Call<RespuestaSubirImagen> call, Response<RespuestaSubirImagen> response) {
                            if(response.isSuccessful() && response != null && response.body() != null){
                                RespuestaSubirImagen respuesta = response.body();
                                System.out.println(respuesta.toString());
                                System.out.println("Imagen subida correctamente");
                                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                                alert.setMessage("Foto subida correctamente");
                                alert.setTitle("Actualizacion de foto");
                                alert.show();
                            }else {
                                Gson gson = new Gson();
                                ErrorResponse respuestaerror = new ErrorResponse();
                                try {
                                    respuestaerror = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
                                    if (respuestaerror.getStatus_code() == 401) {
                                        Log.d("Retrofit", "ERROR: " + respuestaerror.getMessage());
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<RespuestaSubirImagen> call, Throwable t) {

                        }
                    });


                }else{
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setMessage("Debe tomar una foto porfavor");
                    alert.setTitle("Error");
                    alert.show();

                }
            }
        });


        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MensajesFragment msgfrag = new MensajesFragment();

                Bundle params2 = new Bundle();
                params2.putString("token", token);
                params2.putInt("user_id", user_id);
                params2.putString("username", username);
                params2.putString("fotoperfil", fotoperfil);
                msgfrag.setArguments(params2);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.idfragmentmsg, msgfrag).commit();

            }
        });



        //--------------------------------------------------------------------------------------------

        if (verfyPermission()){
            startCameraInit();
        }else {
            ActivityCompat.requestPermissions(getActivity(),PERMISSION_REQUIRED,REQUEST_PERMISSION);
        }

        return root;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION){
            if (verfyPermission()){
                startCameraInit();
            }else {
                Toast.makeText(getContext(),"Debe autorizar lo permisos", Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private  boolean verfyPermission(){
        for (String permission: PERMISSION_REQUIRED){
            if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    private void startCameraInit(){
        tomarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Iniciar Camara");
                startCamera();
            }
        });
    }
    private void startCamera(){
        System.out.println("Iniciar Camara dentro del start camara");
        if (false){
            System.out.println("Iniciar Camara dentro del start camara start  camera false");
            Intent iniciarCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (iniciarCamara.resolveActivity(getActivity().getPackageManager())!= null){
                startActivityForResult(iniciarCamara, REQUEST_CAMERA);
            }
        }else {
            System.out.println("Iniciar Camara dentro del start camara true");
            Intent iniciarCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (iniciarCamara.resolveActivity(getActivity().getPackageManager())!= null){
                File photoFile = null;
                try {
                    photoFile = createFilePhoto();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (photoFile != null){
                    System.out.println("Iniciar Camara dentro URI");

                    Uri phoUri = FileProvider.getUriForFile(getContext(),
                            "com.example.chatconversa.fileprovide",
                            photoFile);
                    iniciarCamara.putExtra(MediaStore.EXTRA_OUTPUT, phoUri);
                    startActivityForResult(iniciarCamara, REQUEST_CAMERA);
                }
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CAMERA && resultCode == getActivity().RESULT_OK){
            if (false){
                Bitmap imageBitman = (Bitmap) data.getExtras().get("data");
                contenedorfoto.setImageBitmap(imageBitman);
            }else{
                showPhoto();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showPhoto(){
        int targetW = contenedorfoto.getWidth();
        int targetH = contenedorfoto.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        int scale = (int) targetW/targetH;

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scale;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(pathphoto, bmOptions);
        contenedorfoto.setImageBitmap(bitmap);
    }

    private File createFilePhoto() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = "JPEG_" + timestamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photo = File.createTempFile(
                filename,
                ".jpg",
                storageDir
        );
        pathphoto = photo.getAbsolutePath();
        return photo;
    }
}
