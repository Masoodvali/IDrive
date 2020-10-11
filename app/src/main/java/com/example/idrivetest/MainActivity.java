package com.example.idrivetest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

public class MainActivity extends AppCompatActivity {
    Retrofit retrofit;
    RecyclerView rvQuestionList;
    ArrayList<QuestionAndAnswerModel> qList = new ArrayList<>();
    //  /  ArrayList<String> qList=new ArrayList<>();
    QandAAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvQuestionList=findViewById(R.id.questionslist);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.remotepc.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String url = "android-help/android-faq-list.txt";
        Call<Object> networkCall = getRetrofit().makeGetCall(url);
        networkCall.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                String jsonResponse = response.body().toString();
                Log.e("Response", String.valueOf(response.body()));
                Gson gson = new Gson();
                JsonObject jsonObject = gson.toJsonTree(response.body()).getAsJsonObject();
                Log.e("Response", jsonObject.toString());
//                QuestionAndAnswerModel model = DataParser.parseJson(jsonObject.toString(), QuestionAndAnswerModel.class);
//                Log.e("Model",""+ model.Tree.item);
             JsonArray jsonArray=   jsonObject.get("tree").getAsJsonObject().get("item").getAsJsonArray();



                Log.e("Response", jsonArray.toString());
                for (int i=0;i<jsonArray.size();i++){
                    JsonObject jsonObject1=jsonArray.get(i).getAsJsonObject();
                    QuestionAndAnswerModel model=new QuestionAndAnswerModel();
                    model.set_question(""+jsonObject1.get("_question"));
                    model.set_answer(""+jsonObject1.get("_answer"));
                    qList.add(model);


                }

                // set up the RecyclerView
                RecyclerView recyclerView = findViewById(R.id.questionslist);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                adapter = new QandAAdapter(MainActivity.this, qList);
                  adapter.setClickListener(new QandAAdapter.ItemClickListener() {
                      @Override
                      public void onItemClick(View view, int position) {
                          Intent intent =new Intent(MainActivity.this, WebView.class);
                          QuestionAndAnswerModel model= qList.get(position);
                          intent.putExtra("url",model.get_answer());
                          startActivity(intent);

                      }
                  });
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    private MyInterface getRetrofit() {
        return retrofit.create(MyInterface.class);
    }


    public interface MyInterface {
        @GET
        Call<Object> makeGetCall(
                @Url String url
        );
    }


}

