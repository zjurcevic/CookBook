package com.example.cookbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CookBookActivity extends AppCompatActivity implements View.OnClickListener, RecyclerAdapter.onItemClickListener {

    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_DESCRIPTION = "description";
    public static final String EXTRA_RECIPE_URL = "recipeUrl";

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;
    private ArrayList<RecyclerItemConstructor> mRecyclerList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_book);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.bringToFront();

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true); //increased performance, sticks to dimensions
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();

        mRecyclerAdapter = new RecyclerAdapter(CookBookActivity.this, mRecyclerList);
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    private void parseJSON() {
        String url = "https://www.food2fork.com/api/search?key=8f008dc3f7f8cef8b152966782caa5d7";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("recipes");

                        for (int i = 0; i < jsonArray.length(); i++) { //fill with items
                            JSONObject recipe = jsonArray.getJSONObject(i);
                            String recipeTitle = recipe.getString("title"); //naslov recepta
                            String recipeUrl = recipe.getString("source_url"); //link za izvor receptu
                            String imageUrl = recipe.getString("image_url"); //slika recepta
                            String recipeDescription = recipe.getString("publisher"); //publisher recepta,
                            // steta trebalo je biti neki opis ali prekasno skuzio da API nema nikakav opis.
                            // koristiti će se publisher da se popuni ta praznina.
                            mRecyclerList.add(new RecyclerItemConstructor(imageUrl, recipeTitle, recipeDescription, recipeUrl));
                        }

                        mRecyclerAdapter.notifyDataSetChanged();
                        mRecyclerAdapter.setOnItemClickListener(CookBookActivity.this);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it has any
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.signout) {
            signOut(); //izbaci korisnika van
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    @Override
    public void onClick(View view) {
    }

    private void updateUI(GoogleSignInAccount account) {
        //odbaci korisnika ako nije logiran
        if (account == null) {
            openLogin();
        } else {
            //continue as normal I guess
        }
    }

    private void signOut() { //self explanatory. user signs out here itd
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInClient.signOut()
                .addOnCompleteListener(this, (task) -> {
                    openLogin(); //throw back to Login Activity
                });
    }

    private void openLogin() { //Intent to throw to Login Activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        RecyclerItemConstructor clickedItem = mRecyclerList.get(position); //get item out of clicked position on List
        detailIntent.putExtra(EXTRA_URL, clickedItem.getmImageUrl());
        detailIntent.putExtra(EXTRA_TITLE, clickedItem.getmTitle());
        detailIntent.putExtra(EXTRA_DESCRIPTION, clickedItem.getmDescription());
        detailIntent.putExtra(EXTRA_RECIPE_URL, clickedItem.getmRecipeUrl());

        startActivity(detailIntent);
    }
}
