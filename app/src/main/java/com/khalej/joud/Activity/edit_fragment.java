package com.khalej.joud.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.khalej.joud.R;
import com.khalej.joud.model.Apiclient_home;
import com.khalej.joud.model.apiinterface_home;
import com.khalej.joud.model.contact_general_user_update;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class edit_fragment extends Fragment {

    ImageView notification;
    TextView logout,terms,whous,callus,language,login,bank;
    Intent intent;
    EditText name,phone,password;
    AppCompatButton appCompatButtonRegister;
    private  static final int IMAGEUser = 100;
    Bitmap bitmapUser;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    CircleImageView image;
    private static final int MY_CAMERA_PERMISSION_CODE = 1;
    private static final int CAMERA_REQUEST_User = 1;
    String mediaPath;
    String imagePath;
    int x;
    private contact_general_user_update userData;
    ProgressDialog progressDialog;
    private apiinterface_home apiinterface;
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_edit, container, false);
        notification=view.findViewById(R.id.notification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new Notification_fragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, fragment);
                fragmentTransaction.addToBackStack("tag");
                fragmentTransaction.commit();
            }
        });
        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        name=view.findViewById(R.id.textInputEditTextname);
        phone=view.findViewById(R.id.textInputEditTextphone);
        password=view.findViewById(R.id.password);
        image=view.findViewById(R.id.image);
        appCompatButtonRegister=view.findViewById(R.id.appCompatButtonRegisterservcies);
        name.setText(sharedpref.getString("name",""));
        phone.setText(sharedpref.getString("phone",""));
        if (Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
     //   whous=view.findViewById(R.id.text);
        image.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    showPictureDialog();
                }
            }
        });
        appCompatButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sharedpref.getString("remember","").equals("yes")){
                    fetchInfo();
                    // payment();
                }
                else{
                    //  Toast.makeText(getContext(),"قم بتسجيل الدخول أولا" ,Toast.LENGTH_LONG).show();
                }

            }
        });

        return view;
    }
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    public void choosePhotoFromGallary() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        x=0;
        startActivityForResult(galleryIntent, IMAGEUser);

    }

    private void takePhotoFromCamera() {
        x=1;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",  /* suffix */
                    storageDir     /* directory */
            );
            Uri uri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName(), image);
            imagePath=image.getAbsolutePath();//Store this path as globe variable

            Toast.makeText(getContext(), "camera permission granted", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, CAMERA_REQUEST_User);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    private void selectImageUser() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, IMAGEUser);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_User && resultCode == RESULT_OK)
        {

            mediaPath = imagePath;
            String name=random();

            mediaPath=resizeAndCompressImageBeforeSend(getActivity(),mediaPath  ,name);

            image.setImageBitmap(BitmapFactory.decodeFile(mediaPath));

        }
        if(requestCode == IMAGEUser && resultCode == RESULT_OK && null != data)
        {
            Uri pathImag = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(pathImag, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mediaPath = cursor.getString(columnIndex);
            String name=random();
            mediaPath=resizeAndCompressImageBeforeSend(getContext(),mediaPath,name);
            cursor.close();
            image.setImageBitmap(BitmapFactory.decodeFile(mediaPath));

            // Toast.makeText(EditProfile.this,mediaPath,Toast.LENGTH_LONG).show();




        }

    }
    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
    public static String resizeAndCompressImageBeforeSend(Context context, String filePath, String fileName){
        final int MAX_IMAGE_SIZE = 300 * 1024; // max final file size in kilobytes

        // First decode with inJustDecodeBounds=true to check dimensions of image
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath,options);

        // Calculate inSampleSize(First we are going to resize the image to 800x800 image, in order to not have a big but very low quality image.
        //resizing the image will already reduce the file size, but after resizing we will check the file size and start to compress image
        options.inSampleSize = calculateInSampleSize(options, 800, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        options.inPreferredConfig= Bitmap.Config.ARGB_8888;

        Bitmap bmpPic = BitmapFactory.decodeFile(filePath,options);


        int compressQuality = 100; // quality decreasing by 5 every loop.
        int streamLength;
        do{
            ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();
            Log.d("compressBitmap", "Quality: " + compressQuality);
            bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream);
            byte[] bmpPicByteArray = bmpStream.toByteArray();
            streamLength = bmpPicByteArray.length;
            compressQuality -= 5;
            Log.d("compressBitmap", "Size: " + streamLength/1024+" kb");
        }while (streamLength >= MAX_IMAGE_SIZE);

        try {
            //save the resized and compressed file to disk cache
            Log.d("compressBitmap","cacheDir: "+context.getCacheDir());
            FileOutputStream bmpFile = new FileOutputStream(context.getCacheDir()+fileName);
            bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpFile);
            bmpFile.flush();
            bmpFile.close();
        } catch (Exception e) {
            Log.e("compressBitmap", "Error on saving file");
        }
        //return the path of resized and compressed file
        return  context.getCacheDir()+fileName;
    }



    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        String debugTag = "MemoryInformation";
        // Image nin islenmeden onceki genislik ve yuksekligi
        final int height = options.outHeight;
        final int width = options.outWidth;
        Log.d(debugTag,"image height: "+height+ "---image width: "+ width);
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        Log.d(debugTag,"inSampleSize: "+inSampleSize);
        return inSampleSize;
    }
    public void fetchInfo() {
        String image="";
        if(mediaPath==null||mediaPath.equals("")){
            fetchwithoutImage();
            return;
        }
        File file = new File(mediaPath);

        // Parsing any Media type file
        final RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("logo", file.getName(), requestBody);


        progressDialog = ProgressDialog.show(getContext(), "جاري تعديل البيانات", "Please wait...", false, false);
        progressDialog.show();

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));


        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_general_user_update> call = apiinterface.getcontacts_updateProfile(headers,fileToUpload);
        call.enqueue(new Callback<contact_general_user_update>() {
            @Override
            public void onResponse(Call<contact_general_user_update> call, Response<contact_general_user_update> response) {
                progressDialog.dismiss();
                if (response.code() == 422) {
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Toast.makeText(Regester.this,jObjError.toString(),Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(),"هناك بيانات مستخدمة من قبل  أو تأكد من انك ادخلت البيانات بشكل صحيح",Toast.LENGTH_LONG).show();
                    Log.d("tag", jObjError.toString());

                    return;
                }
                userData=response.body();
                JSONObject jObjError = null;
                try {
                    jObjError = new JSONObject(userData.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Toast.makeText(getContext(),jObjError.toString()+"",Toast.LENGTH_LONG).show();
                edt.putString("name",userData.getPayload().getFull_name());
                edt.putString("phone",userData.getPayload().getPhone());
                edt.apply();
                fetchwithoutImage();
            }

            @Override
            public void onFailure(Call<contact_general_user_update> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    public void fetchwithoutImage(){
        progressDialog = ProgressDialog.show(getContext(), "جاري تعديل البيانات", "Please wait...", false, false);
        progressDialog.show();

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_general_user_update> call = apiinterface.updateProfile(headers,name.getText().toString(),
                phone.getText().toString());
        call.enqueue(new Callback<contact_general_user_update>() {
            @Override
            public void onResponse(Call<contact_general_user_update> call, Response<contact_general_user_update> response) {
                progressDialog.dismiss();
                if (response.code() == 422) {
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Toast.makeText(EditProfile.this,jObjError.toString(),Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(),"هناك بيانات مستخدمة من قبل  أو تأكد من انك ادخلت البيانات بشكل صحيح",Toast.LENGTH_LONG).show();
                    Log.d("tag", jObjError.toString());

                    return;
                }
                userData=response.body();
              //  Toast.makeText(getContext(),userData.isStatus()+"",Toast.LENGTH_LONG).show();
                edt.putString("name",userData.getPayload().getFull_name());
                edt.putString("phone",userData.getPayload().getPhone());
                edt.putString("image",""+userData.getPayload().getUserMedia().getLogo());
                edt.apply();
                Toast.makeText(getContext(),"تم التعديل بنجاح",Toast.LENGTH_LONG).show();
                if(password.getText()==null||password.getText().length()==0){
                    return;
                }
                else{
                    if(password.getText().length()<6){
                        Toast.makeText(getContext(),"لم يتم تعديل كلمة المرور ادخل كلمة مرور قوية اكثر من 6 احرف او أرقام",Toast.LENGTH_LONG).show();
 return;
                    }
                    fetchchangepassword();
                }

            }

            @Override
            public void onFailure(Call<contact_general_user_update> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    public void fetchchangepassword(){
//        progressDialog = ProgressDialog.show(EditProfile.this, "جاري تعديل البيانات", "Please wait...", false, false);
//        progressDialog.show();

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_general_user_update> call = apiinterface.updateProfile_pass(headers,password.getText().toString());
        call.enqueue(new Callback<contact_general_user_update>() {
            @Override
            public void onResponse(Call<contact_general_user_update> call, Response<contact_general_user_update> response) {
             //   progressDialog.dismiss();
                if (response.code() == 422) {
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Toast.makeText(EditProfile.this,jObjError.toString(),Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(),"هناك بيانات مستخدمة من قبل  أو تأكد من انك ادخلت البيانات بشكل صحيح",Toast.LENGTH_LONG).show();
                    Log.d("tag", jObjError.toString());

                    return;
                }
                userData=response.body();
                //  Toast.makeText(getContext(),userData.isStatus()+"",Toast.LENGTH_LONG).show();
//                edt.putString("name",userData.getPayload().getUser_info().getFull_name());
//                edt.putString("phone",userData.getPayload().getUser_info().getPhone());
//                edt.putString("image",""+userData.getPayload().getUser_info().getUserMedia().getLogo());
//                edt.apply();

            }

            @Override
            public void onFailure(Call<contact_general_user_update> call, Throwable t) {
             //   progressDialog.dismiss();
            }
        });
    }
}
