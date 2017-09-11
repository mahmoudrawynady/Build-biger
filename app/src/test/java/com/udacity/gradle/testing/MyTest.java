package com.example.ph_data01221240053.buildbigger;
import android.content.Context;
import android.os.AsyncTask;
import org.junit.Test;
import junit.framework.Assert;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.sampleapp.backend.myApi.MyApi;





import java.io.IOException;

/**
 * Created by PH-Data™ 01221240053 on 10/05/2017.
 */

public class MyTest {


    Context mMockContext;

    @Test
    public void readStringFromContext_LocalizedString() {
        EndpointsAsyncTaskTest1 task=new EndpointsAsyncTaskTest1();
        task .execute();

       Assert.assertNotNull("not null", task.getRespon());
    }
    ////////////////////////////////////////////////////////////////
    private class EndpointsAsyncTaskTest1 extends AsyncTask<Void, Void, String> {
        boolean check;
        String respon;
        private MyApi myApiService = null;
        private Context context;

        public void setCheck(boolean check) {
            this.check = check;
        }
       public boolean getCheck(){
           return check;
       }

        public String getRespon() {
            return respon;
        }

        public void setRespon(String respon) {
            this.respon = respon;
        }

        @Override
        protected String doInBackground(Void... params) {
            if (myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        .setRootUrl("http://192.168.111.1:8080//_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                // end options for devappserver

                myApiService = builder.build();
            }

            //context = params[0].first;
            //String name = params[0].second;


            try {
                return myApiService.sayHi("").execute().getData();
            } catch (IOException e) {
                return  null;
            }


        }

        @Override
        protected void onPostExecute(String s) {
            respon=s;
            if(s==null)
                setCheck(false);
            else
                setCheck(true);
        }
    }
}
