package ozanturcan.com.doorsidenoti;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

import ozanturcan.com.doorsidenoti.Models.UserInformation;
import ozanturcan.com.doorsidenoti.Operations.FirebaseAuthOperations;
import ozanturcan.com.doorsidenoti.nav_fragments.AllMessagesFragment;
import ozanturcan.com.doorsidenoti.nav_fragments.BoardsFragment;
import ozanturcan.com.doorsidenoti.nav_fragments.HomeFragment;
import ozanturcan.com.doorsidenoti.nav_fragments.ToolsFragment;
import ozanturcan.com.doorsidenoti.nav_fragments.UnseenFragment;

public class MainActivity extends FirebaseAuthOperations
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG ="MainActivity:" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        android.app.FragmentManager fragmentManager=getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,new HomeFragment()).commit();

        ImageView userİmage = (ImageView) findViewById(R.id.NavHead_UseImage);

        UserInformation MyUserInfo = new UserInformation().getAnInnerClass();
        String a =  MyUserInfo.getPersonEmail();
        a =  MyUserInfo.getPersonName();
         MyUserInfo.getPersonPhoto();

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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        android.app.FragmentManager fragmentManager=getFragmentManager();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_homescreen) {
            fragmentManager.beginTransaction().replace(R.id.content_frame,new HomeFragment()).commit();
            getSupportActionBar().setTitle("Door Side Noti");


        }
        else if (id == R.id.nav_unseen) {
             fragmentManager.beginTransaction().replace(R.id.content_frame,new UnseenFragment()).commit();
            getSupportActionBar().setTitle("Unseen");
        }

        else if (id == R.id.nav_allmessages) {
            fragmentManager.beginTransaction().replace(R.id.content_frame,new AllMessagesFragment()).commit();
            getSupportActionBar().setTitle("All Messages");


        } else if (id == R.id.nav_boards) {
            fragmentManager.beginTransaction().replace(R.id.content_frame,new BoardsFragment()).commit();
            getSupportActionBar().setTitle("My Boards");

        } else if (id == R.id.nav_tools) {
            fragmentManager.beginTransaction().replace(R.id.content_frame,new ToolsFragment()).commit();
            getSupportActionBar().setTitle("Tools");

        } else if (id == R.id.nav_SignOut) {
                signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 0)
        {
            Log.d(TAG, "signIn:" );
        }

    }

}
