package com.example.sndtcsi.newsfeed;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    FragmentManager fm=getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.openDrawer(GravityCompat.START);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        MainFragment mainFragment = new MainFragment();
        Bundle bundle = new Bundle();

        if (id == R.id.nav_ABCNews)
        {
            bundle.putString("sourceTitle", "ABC News");
            bundle.putString("sourceID", "abc-news");
        }
        else if (id == R.id.nav_BBCNews)
        {
            bundle.putString("sourceTitle", "BBC News");
            bundle.putString("sourceID", "bbc-news");
        }
        else if (id == R.id.nav_CBSNews)
        {
            bundle.putString("sourceTitle", "CBS News");
            bundle.putString("sourceID", "cbs-news");
        }
        else if (id == R.id.nav_FoxNews)
        {
            bundle.putString("sourceTitle", "Fox News");
            bundle.putString("sourceID", "fox-news");
        }
        else if (id == R.id.nav_BBCSport)
        {
            bundle.putString("sourceTitle", "BBC Sport");
            bundle.putString("sourceID", "bbc-sport");
        }
        else if (id == R.id.nav_ESPNCric)
        {
            bundle.putString("sourceTitle", "ESPN Cric Info");
            bundle.putString("sourceID", "espn-cric-info");
        }
        else if (id == R.id.nav_Lequipe)
        {
            bundle.putString("sourceTitle", "L'equipe");
            bundle.putString("sourceID", "lequipe");
        }
        else if (id == R.id.nav_NFLNews)
        {
            bundle.putString("sourceTitle", "NFL News");
            bundle.putString("sourceID", "nfl-news");
        }
        else if (id == R.id.nav_CryptoCoinsNews)
        {
            bundle.putString("sourceTitle", "Crypto Coins News");
            bundle.putString("sourceID", "crypto-coins-news");
        }
        else if (id == R.id.nav_Engadget)
        {
            bundle.putString("sourceTitle", "Engadget");
            bundle.putString("sourceID", "engadget");
        }
        else if (id == R.id.nav_Recode)
        {
            bundle.putString("sourceTitle", "Recode");
            bundle.putString("sourceID", "recode");
        }

        mainFragment.setArguments(bundle);
        fm.beginTransaction().replace(R.id.content_frame, mainFragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
        return true;
    }
}
