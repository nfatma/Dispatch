package com.example.nfatma.dispatch;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsDisplay extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    private NavigationView nv;
    private ActionBarDrawerToggle t;
    private SearchView searchView;
    private String[] country_list = {"US", "India", "Australia", "South Korea", "Japan", "South America", "Brazil"};
    private String country_final = "us";
    private String spinner_item = "us";
    private static int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_display);

        drawerMethod();

        spinnerMethod();

        callingApi1();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (flag == 0) {
            callingApi1();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchedItem = menu.findItem(R.id.menu_toolbarsearch);
        searchView = (SearchView) searchedItem.getActionView();
        searchView.setQueryHint("enter Text");
        searchView.setOnQueryTextListener(this);
        return true;
    }


    private void generateNewsList(List<NewsModelClass.Articles> newsArrayList) {
        RecyclerView rv = findViewById(R.id.recycler_view);
        ApiAdapter aa = new ApiAdapter(this, newsArrayList);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, 0);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(aa);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (t.onOptionsItemSelected(item))
        return true;

        switch (id) {
            case R.id.item1:
                callingApi1();
                Toast.makeText(getApplicationContext(), "Refreshing", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(getApplicationContext(), "Coming soon", Toast.LENGTH_LONG).show();
                return true;
            case R.id.item3:
                alert_box();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void callingApi1() {

        Retrofit r = new Retrofit.Builder().baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        ApiInterface ai = r.create(ApiInterface.class);
        ai.getResults1(decideCountry()).enqueue(new Callback<NewsModelClass>() {
            @Override
            public void onResponse(Call<NewsModelClass> call, Response<NewsModelClass> response) {
                try {
                    if (response.body().getStatus().equalsIgnoreCase("ok")) {
                        flag = flag + 1;
                        generateNewsList(response.body().getArticles());
                    }
                } catch (Exception e) {
                    Toast.makeText(NewsDisplay.this, "some Unexpected Error Occured", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<NewsModelClass> call, Throwable t) {
                Toast.makeText(NewsDisplay.this, "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void callingApi2(String query) {
        Retrofit r = new Retrofit.Builder().baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        ApiInterface ai = r.create(ApiInterface.class);
        ai.getResults2(query).enqueue(new Callback<NewsModelClass>() {
            @Override
            public void onResponse(Call<NewsModelClass> call, Response<NewsModelClass> response) {
                try {
                    if (response.body().getStatus().equalsIgnoreCase("ok")) {
                        generateNewsList(response.body().getArticles());
                    }
                } catch (Exception e) {
                    Toast.makeText(NewsDisplay.this, "some Unexpected Error Occured", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<NewsModelClass> call, Throwable t) {
                Toast.makeText(NewsDisplay.this, "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id) {
            case R.id.country:
                Toast.makeText(NewsDisplay.this, "updating", Toast.LENGTH_SHORT).show();
            case R.id.sign_in:
                Toast.makeText(NewsDisplay.this, "coming soon", Toast.LENGTH_SHORT).show();
            case R.id.about:
                Toast.makeText(NewsDisplay.this, "coming soon", Toast.LENGTH_SHORT).show();
            default:
                return true;
        }
    }

    public void spinnerMethod(){
        final Spinner spin = (Spinner) nv.getMenu().findItem(R.id.country).getActionView();
        ArrayAdapter ad = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1, country_list);
        spin.setAdapter(ad);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_item = spin.getSelectedItem().toString();
                callingApi1();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void drawerMethod(){

        DrawerLayout drawer = findViewById(R.id.activity_news_display);
        t = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);

        drawer.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = findViewById(R.id.navigation_drawer);
        nv.setNavigationItemSelectedListener(this);
    }

    public String decideCountry() {
        if (spinner_item.equalsIgnoreCase(country_list[0])) {
            country_final = "us";
        } else if (spinner_item.equalsIgnoreCase(country_list[1])) {
            country_final = "in";
        } else if (spinner_item.equalsIgnoreCase(country_list[2])) {
            country_final = "au";
        } else if (spinner_item.equalsIgnoreCase(country_list[3])) {
            country_final = "sk";
        } else if (spinner_item.equalsIgnoreCase(country_list[4])) {
            country_final = "jp";
        } else if (spinner_item.equalsIgnoreCase(country_list[5])) {
            country_final = "sa";
        } else if (spinner_item.equalsIgnoreCase(country_list[6])) {
            country_final = "ba";
        }
        return country_final;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        String q = searchView.getQuery().toString();
        callingApi2(q);
        searchView.onActionViewCollapsed();
        Toast.makeText(NewsDisplay.this, "Updating",Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    public void alert_box() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert);
        builder.setTitle(getString(R.string.alert));
        builder.setMessage(getString(R.string.alrt_mssg));

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finishAffinity();
                    }
                });
        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // dismiss dialog, start counter again
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        alert_box();
    }
}

