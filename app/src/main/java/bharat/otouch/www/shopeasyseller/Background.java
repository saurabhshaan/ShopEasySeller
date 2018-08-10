package bharat.otouch.www.shopeasyseller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by shaan on 11/09/2017.
 */

public class Background extends AsyncTask<String,Void,String> {
    Context context;

    public Background(Context context){
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String reg_url = "http://192.168.43.147/shopeasy/postproduct.php";
        Log.d("TAG","After URL");

        String method = params[0];
        if (method.equals("registerproduct")){
            String Product_Name = params[1];
            String Product_Type = params[2];
            String Product_Company = params[3];
            String Product_Warenty = params[4];
            String Product_Price = params[5];
            String Product_Description = params[6];
            String Product_Image = params[7];

            Log.d("TAG",Product_Name+""+Product_Type+""+Product_Company+""+Product_Warenty+""+Product_Price+""+Product_Description+""+Product_Image);


            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                Log.d("TAG0","Open Connet");

                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                Log.d("TAG","BufferWrite");

                String product = URLEncoder.encode("Product_Name","UTF-8")+"="+URLEncoder.encode(Product_Name,"UTF-8")+"&"+
                        URLEncoder.encode("Product_Type","UTF-8")+"="+ URLEncoder.encode(Product_Type,"UTF-8")+"&"+
                        URLEncoder.encode("Company","UTF-8")+"="+ URLEncoder.encode(Product_Company,"UTF-8")+"&"+
                        URLEncoder.encode("Warenty","UTF-8")+"="+URLEncoder.encode(Product_Warenty,"UTF-8")+"&"+
                        URLEncoder.encode("Price","UTF-8")+"="+URLEncoder.encode(Product_Price,"UTF-8")+"&"+
                        URLEncoder.encode("P_Description","UTF-8")+"="+URLEncoder.encode(Product_Description,"UTF-8")+"&"+
                        URLEncoder.encode("Image","UTF-8")+"="+URLEncoder.encode(Product_Image,"UTF-8");

                Log.d("TAG","Data parameter set");

                bufferedWriter.write(product);
                bufferedWriter.flush();
                bufferedWriter.close();
                Log.d("TAG","Buffer Close");

                os.close();

                InputStream inputStream = httpURLConnection.getInputStream();

                inputStream.close();

                return "Login Success...Welcome";
            }catch (MalformedURLException e ){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        Toast.makeText(context, "Thanks You", Toast.LENGTH_SHORT).show();
    }
}
