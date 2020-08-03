package com.example.chatconversa;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.PusherEvent;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MensajesFragment extends Fragment {


    private static final String CHANEL_ID= "PUSHER_MSG";
    private static final int PHOTO_SEND = 1;
    private final static int REQUEST_PERMISSION = 1001;
    private final static int REQUEST_CAMERA = 1002;
    private final static String[] PERMISSION_REQUIRED =
            new String[]{"android.permission.CAMERA","android.permission.WRITE_EXTERNAL_STORAGE"};

    RecyclerView recyclerView;
    MensajesAdapter adapter;
    List <Mensaje> mensajeList;
    MensajesViwModel mensajesViwModel;

    //private List<Mensaje> mensajesList;
    private ImageButton fotoMensaje;
    private ImageView fotoPerfil;
    private ImageView contfoto;
    private TextView nombre;
    private TextView date;
    private TextView mensaje;
    private TextInputEditText txtMensaje;
    private Button enviarMensaje;
    private ImageButton enviarimagen;
    private String textomensaje;
    private String auxim;
    private float latitude;
    private float longitude;


    private String token;
    private int user_id;
    private String username;
    private ServicioWeb servicio;

   // private File fotofile;
    private String pathphoto;

    Mensaje message;


    public MensajesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    if(getArguments() != null){
        token = getArguments().getString("token");
        user_id = getArguments().getInt("user_id");
        username = getArguments().getString("username");
        DatosCall datosCall = new DatosCall();
        datosCall.setToken(token);
        datosCall.setUser_id(user_id);
        datosCall.setUsername(username);
        //System.out.println("Params Fragment: " + token + " - " + user_id + " - "+ username);
        System.out.println("Datos call : " + datosCall.toString());
    }else{
        System.out.println("Agrs nulos");
    }
        createChannel();
        PusherOptions options = new PusherOptions();
        options.setCluster("us2");
        Pusher pusher = new Pusher("46e8ded9439a0fef8cbc",options);

        pusher.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange change) {
                Log.d("PUSHER", "Estado actual" + change.getCurrentState().name()
                        + " - Estado previo"
                        + change.getPreviousState().name());
            }

            @Override
            public void onError(String message, String code, Exception e) {
                Log.d("PUSHER", "ERROR Pusher\n"
                        +"Mensaje: " + message+ "\n"
                        +"Codigo: " + code+ "\n"
                        +"e: " + e);
            }
        }, ConnectionState.ALL);
        Channel channel = pusher.subscribe("my-channel");

        channel.bind("my-event", new SubscriptionEventListener() {
            @Override
            public void onEvent(PusherEvent event) {
                Log.d("PUSHER", "Nuevo mensaje" + event.toString());
                JSONObject jsonObject = null;

                NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(getActivity(), CHANEL_ID)
                        .setSmallIcon(R.drawable.ic_chat_notification)
                        .setContentTitle("Notificacion")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true);

                try {
                    jsonObject= new JSONObject(event.toString());
                    nBuilder = new NotificationCompat.Builder(getActivity(), CHANEL_ID)
                            .setSmallIcon(R.drawable.ic_chat_notification)
                            .setContentTitle("Menseje de "+ jsonObject.getJSONObject("data").getJSONObject("message").getJSONObject("user").getString("username"))
                            .setContentText("Mensaje "+ jsonObject.getJSONObject("data").getJSONObject("message").getString("message"))
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setAutoCancel(true);
                    Gson gson = new Gson();
                    Mensaje message = gson.fromJson(jsonObject.getJSONObject("data").getJSONObject("message").toString(),Mensaje.class);
                    System.out.println("Message => "+ message.toString());

                    System.out.println("Activity ----> " + getActivity());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addMessage(message);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }


                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat
                        .from(getActivity());
                notificationManagerCompat.notify(5, nBuilder.build());

            }
        });
        System.out.println("WHATTTTTTTT");
        System.out.println("Get activity "+ getActivity().toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View root = inflater.inflate(R.layout.fragment_mensajes,container,false);
        enviarMensaje = root.findViewById(R.id.enviarmensaje);
        txtMensaje = root.findViewById(R.id.textomensaje);
        recyclerView = root.findViewById(R.id.recyclerview);
        enviarimagen = root.findViewById(R.id.sendfoto);
        fotoMensaje = root.findViewById(R.id.contenedorimagendeperfil);
        contfoto=root.findViewById(R.id.contfoto);

        LinearLayoutManager l = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(l);
        recyclerView.setAdapter(adapter);

        MensajesViwModel model = ViewModelProviders.of(this).get(MensajesViwModel.class);
        model.getMensajes(token,user_id,username).observe(getActivity(), new Observer<List<Mensaje>>() {
            @Override
            public void onChanged(List<Mensaje> mensajes) {
                adapter = new MensajesAdapter(getActivity(), mensajes);
                adapter.notifyDataSetChanged();
                /*adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("Seleccion: " + mensajes.get(recyclerView.getChildAdapterPosition(v)).getMessage());
                    }
                });*/
                recyclerView.setAdapter(adapter);
            }
        });


        enviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://chat-conversa.unnamed-chile.com/ws/")
                        .addConverterFactory(GsonConverterFactory.create())
                        //.client(cliente)
                        .build();
                servicio = retrofit.create(ServicioWeb.class);

                textomensaje = txtMensaje.getText().toString();
                latitude=0;
                longitude=0;
                Call<RespuestaSendMessage> resp;
                RequestBody user = RequestBody.create(MediaType.parse("multipart/form-data"), username);
                RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(user_id));
                RequestBody mensaje = RequestBody.create(MediaType.parse("multipart/form-data"), textomensaje);
                RequestBody lat = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(latitude));
                RequestBody lon = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(longitude));
                String tokenBearer = "Bearer " + token;

                if(pathphoto!=null){
                    File archivoImage = new File(pathphoto);
                    RequestBody imagen = RequestBody.create(MediaType.parse("multipart/form-data"), archivoImage);
                    MultipartBody.Part file = MultipartBody.Part.createFormData("image", archivoImage.getName(),imagen);
                    resp = servicio.sendmensaje(file,user,id,mensaje,lat,lon,tokenBearer);

                }else{
                    resp = servicio.sendmensaje(null,user,id,mensaje,lat,lon,tokenBearer);
                }
                resp.enqueue(new Callback<RespuestaSendMessage>() {
                    @Override
                    public void onResponse(Call<RespuestaSendMessage> call, Response<RespuestaSendMessage> response) {
                        if(response.isSuccessful() && response != null && response.body() != null){
                            RespuestaSendMessage respuesta = response.body();
                            //adapter.addMensaje(respuesta.getData());
                            System.out.println(respuesta.toString());
                            System.out.println("Mensaje enviado correctamente");
                            txtMensaje.setText("");
                            pathphoto=null;
                            contfoto.setVisibility(View.GONE);
                        }else{
                            System.out.println("Error de llamada");
                            contfoto.setVisibility(View.GONE);
                            pathphoto=null;
                            try {
                                System.out.println(response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                            alert.setMessage("Error al enviar mensaje");
                            alert.setTitle("Envio de mensaje");
                            alert.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RespuestaSendMessage> call, Throwable t) {

                    }
                });
                /*
                RequestBody imagen = RequestBody.create(MediaType.parse("multipart/form-data"), auxim);
                MultipartBody.Part file = MultipartBody.Part.createFormData("image", auxim);
                RequestBody user = RequestBody.create(MediaType.parse("multipart/form-data"), username);
                RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(user_id));
                RequestBody mensaje = RequestBody.create(MediaType.parse("multipart/form-data"), textomensaje);
                RequestBody lat = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(latitude));
                RequestBody lon = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(longitude));
                String tokenBearer = "Bearer " + token;

                Call<RespuestaSendMessage> resp = servicio.sendmensaje(file,user,id,mensaje,lat,lon,tokenBearer);
                resp.enqueue(new Callback<RespuestaSendMessage>() {
                    @Override
                    public void onResponse(Call<RespuestaSendMessage> call, Response<RespuestaSendMessage> response) {
                        if(response.isSuccessful() && response != null && response.body() != null){
                            RespuestaSendMessage respuesta = response.body();
                            //  adapter.addMensaje(respuesta.getData());
                            System.out.println(respuesta.toString());
                            System.out.println("Mensaje enviado correctamente");
                            txtMensaje.setText("");
                        }else{
                            System.out.println("Error de llamada");
                            try {
                                System.out.println(response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                            alert.setMessage("Error al enviar mensaje");
                            alert.setTitle("Envio de mensaje");
                            alert.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RespuestaSendMessage> call, Throwable t) {
                        t.printStackTrace();
                    }
                });*/


            }
        });

        /*enviarimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                token = getArguments().getString("token");
                user_id = getArguments().getInt("user_id");
                username = getArguments().getString("username");

                if (verfyPermission()){
                    startCameraInit();
                }else {
                    ActivityCompat.requestPermissions(getActivity(),PERMISSION_REQUIRED,REQUEST_PERMISSION);
                }

                textomensaje = txtMensaje.getText().toString();
                latitude=0;
                longitude=0;

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://chat-conversa.unnamed-chile.com/ws/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                servicio = retrofit.create(ServicioWeb.class);
                System.out.println("Path photo camera: " + pathphoto);
                if(pathphoto!=null){
                    File archivoImage = new File(pathphoto);
                    RequestBody imagen = RequestBody.create(MediaType.parse("multipart/form-data"), archivoImage);
                    MultipartBody.Part file = MultipartBody.Part.createFormData("image", archivoImage.getName(),imagen);
                    RequestBody user = RequestBody.create(MediaType.parse("multipart/form-data"), username);
                    RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(user_id));
                    RequestBody mensaje = RequestBody.create(MediaType.parse("multipart/form-data"), textomensaje);
                    RequestBody lat = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(latitude));
                    RequestBody lon = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(longitude));
                    String tokenBearer = "Bearer " + token;

                    Call<RespuestaSendMessage> resp = servicio.sendmensaje(file,user,id,mensaje,lat,lon,tokenBearer);
                    resp.enqueue(new Callback<RespuestaSendMessage>() {
                        @Override
                        public void onResponse(Call<RespuestaSendMessage> call, Response<RespuestaSendMessage> response) {
                            if(response.isSuccessful() && response != null && response.body() != null){
                                RespuestaSendMessage respuesta = response.body();
                                //Log31adapter.addMensaje(respuesta.getData());
                                System.out.println(respuesta.toString());
                                System.out.println("Mensaje enviado correctamente");
                                txtMensaje.setText("");
                            }else{
                                System.out.println("Error de llamada");
                                try {
                                    System.out.println(response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                alert.setMessage("Error al enviar mensaje");
                                alert.setTitle("Envio de mensaje");
                                alert.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RespuestaSendMessage> call, Throwable t) {

                        }
                    });


                }

            }
        });*/

        //------------------------------------------------------------------------------------------

        if (verfyPermission()){
            startCameraInit();
        }else {
            ActivityCompat.requestPermissions(getActivity(),PERMISSION_REQUIRED,REQUEST_PERMISSION);
        }

        System.out.println(pathphoto);






        //------------------------------------------------------------------------------------------
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
        enviarimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contfoto.setVisibility(View.VISIBLE);
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
                    //contfoto.setVisibility(View.VISIBLE);
                    Uri phoUri = FileProvider.getUriForFile(getContext(),
                            "com.example.chatconversa.fileprovide",
                            photoFile);
                    System.out.println("Nose donde se tomaaa");
                    iniciarCamara.putExtra(MediaStore.EXTRA_OUTPUT, phoUri);
                    startActivityForResult(iniciarCamara, REQUEST_CAMERA);
                }
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CAMERA && resultCode == getActivity().RESULT_OK){
            contfoto.setVisibility(View.VISIBLE);
            System.out.println("W AND HE EN ACTIVITY ON RESULT: " + contfoto.getWidth() + " " + contfoto.getHeight());
            System.out.println("Imagen tomada");
            if (false){
                Bitmap imageBitman = (Bitmap) data.getExtras().get("data");
                contfoto.setImageBitmap(imageBitman);
            }else{
                showPhoto();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showPhoto(){
        int targetW = contfoto.getWidth();
        int targetH = contfoto.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        int scale = (int) targetW/targetH;

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scale;
        bmOptions.inPurgeable = true;


        Bitmap bitmap = BitmapFactory.decodeFile(pathphoto, bmOptions);
        contfoto.setImageBitmap(bitmap);


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

    private void createChannel(){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANEL_ID,"PUSHER", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = getContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void addMessage(Mensaje mensaje){
        adapter.addMensaje(mensaje);
        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(0);
    }
}
