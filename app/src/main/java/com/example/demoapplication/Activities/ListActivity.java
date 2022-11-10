package com.example.demoapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.telecom.Call;
import android.view.MenuItem;
import android.view.View;

import com.example.demoapplication.Adapter.BuyAdapter;
import com.example.demoapplication.Adapter.CallAdapter;
import com.example.demoapplication.Adapter.SellAdapter;
import com.example.demoapplication.Models.BuyModel;
import com.example.demoapplication.Models.CallModel;
import com.example.demoapplication.Models.SellModel;
import com.example.demoapplication.R;
import com.example.demoapplication.Utils.AppUrls;
import com.example.demoapplication.Utils.DBHelper;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class ListActivity extends AppCompatActivity {
    private String page;
    private String url = AppUrls.liveUrl;
    ArrayList<BuyModel> buyList;
    ArrayList<SellModel> sellList;
    ArrayList<CallModel> callList;
    public ShimmerFrameLayout mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        page = getIntent().getStringExtra("PAGE");
        mFrameLayout = findViewById(R.id.shimmerLayout);
        callList = new ArrayList<>();
        buyList = new ArrayList<>();
        sellList = new ArrayList<>();
        DBHelper db = new DBHelper(this);
        db.addSellerList(new SellModel("Table", "12000", "1", "2"));
        db.addSellerList(new SellModel("TV", "38000", "2", "2"));
        db.addSellerList(new SellModel("iPhoneX", "150000", "1", "2"));
        switch (page) {
            case "Call":
                getCallList();
                break;
            case "Buy":
                getBuyList();
                break;
            case "Sell":
                getSellList();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void getCallList() {
        OkHttpClient client = new  OkHttpClient.Builder().build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url+"call")
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = Objects.requireNonNull(response.body()).string();
                    ListActivity.this.runOnUiThread(() -> {
                        try {
                            callList.clear();
                            JSONArray jsonArray = new JSONArray(myResponse);
                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                CallModel callModel = new CallModel();
                                callModel.setId(jsonObject.getInt("id"));
                                callModel.setName(jsonObject.getString("name"));
                                callModel.setNumber(jsonObject.getString("number"));
                                callList.add(callModel);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        setCallAdapter(callList);
                        mFrameLayout.startShimmer();
                        mFrameLayout.setVisibility(View.GONE);
                    });
                }
            }

            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {

            }
        });
    }

    void getBuyList() {
        OkHttpClient client = new  OkHttpClient.Builder().build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url+"buy")
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = Objects.requireNonNull(response.body()).string();
                    ListActivity.this.runOnUiThread(() -> {
                        try {
                            buyList.clear();
                            JSONArray jsonArray = new JSONArray(myResponse);
                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                BuyModel buyModel = new BuyModel();
                                buyModel.setId(jsonObject.getInt("id"));
                                buyModel.setName(jsonObject.getString("name"));
                                buyModel.setPrice(jsonObject.getInt("price"));
                                buyModel.setQuantity(jsonObject.getInt("quantity"));
                                buyModel.setType(jsonObject.getInt("type"));
                                buyList.add(buyModel);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        setBuyAdapter(buyList);
                        mFrameLayout.startShimmer();
                        mFrameLayout.setVisibility(View.GONE);
                    });
                }
            }

            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {

            }
        });
    }

    void getSellList() {
        DBHelper db = new DBHelper(this);
        sellList.clear();
        sellList.addAll(db.getAllSellerList());
        mFrameLayout.startShimmer();
        mFrameLayout.setVisibility(View.GONE);
        setSellAdapter(sellList);
    }

    public void setCallAdapter(ArrayList<CallModel> arrayList) {
        RecyclerView callRecycler = findViewById(R.id.recycle_list);
        callRecycler.setVisibility(View.VISIBLE);
        callRecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        callRecycler.setLayoutManager(linearLayoutManager);
        CallAdapter callAdapter = new CallAdapter(getApplicationContext(), arrayList);
        callRecycler.setAdapter(callAdapter);
    }

    public void setBuyAdapter(ArrayList<BuyModel> arrayList) {
        RecyclerView buyRecycler = findViewById(R.id.recycle_list);
        buyRecycler.setVisibility(View.VISIBLE);
        buyRecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        buyRecycler.setLayoutManager(linearLayoutManager);
        BuyAdapter buyAdapter = new BuyAdapter(getApplicationContext(), arrayList);
        buyRecycler.setAdapter(buyAdapter);
    }

    public void setSellAdapter(ArrayList<SellModel> arrayList) {
        RecyclerView sellRecycler = findViewById(R.id.recycle_list);
        sellRecycler.setVisibility(View.VISIBLE);
        sellRecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        sellRecycler.setLayoutManager(linearLayoutManager);
        SellAdapter sellAdapter = new SellAdapter(getApplicationContext(), arrayList);
        sellRecycler.setAdapter(sellAdapter);
    }
}