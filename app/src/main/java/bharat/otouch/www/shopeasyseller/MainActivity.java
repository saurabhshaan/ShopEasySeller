package bharat.otouch.www.shopeasyseller;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private EditText ProductName, Company, Warenty, Price, Description;
    private ImageView proimage;
    private Spinner spinner;
    private String item;
    Button browseimage, clickimage, submitproduct;
    private static int RESULT_LOAD_IMAGE = 1;
    String EncodedImage, imageString, imgDecodableString;

    String productname,productcompany, productwarenty,productprice,productdes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //EditTest start
        ProductName = (EditText) findViewById(R.id.productname);
        Company= (EditText) findViewById(R.id.company);
        Warenty = (EditText) findViewById(R.id.warenty);
        Price = (EditText) findViewById(R.id.price);
        Description = (EditText) findViewById(R.id.description);

        spinner = (Spinner) findViewById(R.id.spinerall);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.SelectType,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //ImageView start
        proimage = (ImageView) findViewById(R.id.imageView);

        //Buttons start
        browseimage = (Button) findViewById(R.id.browseimage);
        clickimage = (Button) findViewById(R.id.clickimage);
        submitproduct = (Button) findViewById(R.id.submitproduct);

    }

    public void Browseimage(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 260);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }
    public void Clickimage(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            if (requestCode == 0){
                switch (resultCode){
                    case Activity.RESULT_OK:
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        Toast.makeText(this, "Image Set", Toast.LENGTH_SHORT).show();
                        proimage.setImageBitmap(bitmap);
                        ByteArrayOutputStream image = new ByteArrayOutputStream();
                        if (bitmap !=null){
                            bitmap.compress(Bitmap.CompressFormat.JPEG,100,image);
                        }
                        byte[] b = image.toByteArray();
                        EncodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                        Toast.makeText(this, "Wait a Moment...", Toast.LENGTH_SHORT).show();
                        break;

                    case Activity.RESULT_CANCELED:
                        Toast.makeText(this, "Back Button Pressed", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                if (cursor != null) {
                    cursor.moveToFirst();
                }

                int columnIndex = 0;
                if (cursor != null) {
                    columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                }
                if (cursor != null) {
                    imgDecodableString = cursor.getString(columnIndex);
                }
                if (cursor != null) {
                    cursor.close();
                }
                // Set the Image in ImageView after decoding the String
                proimage.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                //imageUploadSTART

                Bitmap bm = BitmapFactory.decodeFile(imgDecodableString);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object//0 for low quality
                byte[] b = baos.toByteArray();
                EncodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                Toast.makeText(MainActivity.this, "ImageSet", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "Wait for moment ....", Toast.LENGTH_SHORT).show();
                Log.d("error", "images" + EncodedImage);
                //close
            }
          /*  if (requestCode == RESULT_LOAD_IMAGE && requestCode == RESULT_OK  && null != data){
                Uri selectedImage = data.getData();
                String[] filepath = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage,filepath,null,null,null);
                if (cursor !=null){
                    cursor.moveToFirst();
                }
                int index =0;
                if (cursor !=null){
                    index = cursor.getColumnIndex(filepath[0]);

                }
                if (cursor != null){
                    imageString = cursor.getString(index);
                }
                if (cursor != null){
                    cursor.close();
                }
                proimage.setImageBitmap(BitmapFactory.decodeFile(imageString));

                Bitmap bitmap = BitmapFactory.decodeFile(imageString);
                ByteArrayOutputStream Out = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,Out);
                byte[] bb = Out.toByteArray();
                EncodedImage = Base64.encodeToString(bb,Base64.DEFAULT);
                Toast.makeText(this, "Image Set", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Wait for Moment...", Toast.LENGTH_LONG).show();
                Log.d("TAG","Image"+EncodedImage);
            } */

        }catch (Exception e){
            Toast.makeText(this, "Problem Found", Toast.LENGTH_SHORT).show();

        }
    }

    public void Upload(View view){

        if (ProductName.getText().toString().length() == 0){
            ProductName.setError("Please Enter Product Name");
            return;
        }else if (Company.getText().toString().length() == 0){
            Company.setError("Please Enter Company Name");
            return;
        }else if (Warenty.getText().toString().length() == 0){
            Warenty.setError("Please enter Warenty");
            return;
        }else if (Price.getText().toString().length() ==0){
            Price.setError("Please enter Price");
            return;
        }else if (Description.getText().toString().length()==0){
            Description.setError("Please describe your product");
        }else {
            Toast.makeText(this, "All Information filled successfully", Toast.LENGTH_SHORT).show();
        }

        productname = ProductName.getText().toString().trim();
        productcompany = Company.getText().toString().trim();
        productwarenty = Warenty.getText().toString().trim();
        productprice = Price.getText().toString().trim();
        productdes = Description.getText().toString().trim();

        Log.d("TAG","BackString"+productname+""+productcompany+""+productwarenty+""+productprice+""+productdes);

        if (isOnline()){
            String method = "registerproduct";
            Log.d("TAG","Connection");

            Log.d("TAG"," BG start");
            Background background = new Background(MainActivity.this);
            background.execute(method,productname,item,productcompany,productwarenty,productprice,productdes,EncodedImage);

            Log.d("TAG","BG end");
        }else {
            Toast.makeText(this, "Not Connected to Network", Toast.LENGTH_LONG).show();
        }

    }
    public boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), ""+item, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}