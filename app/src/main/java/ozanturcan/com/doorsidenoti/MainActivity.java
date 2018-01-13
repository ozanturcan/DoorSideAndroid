package ozanturcan.com.doorsidenoti;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;

import ozanturcan.com.doorsidenoti.Models.UserInformation;
import ozanturcan.com.doorsidenoti.Operations.FirebaseAuthOperations;
import ozanturcan.com.doorsidenoti.Operations.FirebaseDatabaseOperations;
import ozanturcan.com.doorsidenoti.nav_fragments.AllMessagesFragment;
import ozanturcan.com.doorsidenoti.nav_fragments.BoardsFragment;
import ozanturcan.com.doorsidenoti.nav_fragments.HomeFragment;
import ozanturcan.com.doorsidenoti.nav_fragments.ToolsFragment;
import ozanturcan.com.doorsidenoti.nav_fragments.UnseenFragment;

public class MainActivity extends FirebaseAuthOperations
        implements NavigationView.OnNavigationItemSelectedListener , View.OnClickListener {
    public  TextView t ;
    private static final String TAG ="MainActivity:" ;
    private TextView name;
    private TextView email;
    private ImageView imageView;
    FloatingActionButton fab;
    private  FirebaseDatabaseOperations FireDB = new FirebaseDatabaseOperations().getAnInnerClass();
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
                Intent ıntent=new Intent(MainActivity.this,MessageActivity.class);
                startActivity(ıntent);
            }
        });
        ///


        ///
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        android.app.FragmentManager fragmentManager=getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,new HomeFragment()).commit();


        //

        UserInformation MyUserInfo = new UserInformation().getAnInnerClass();


        View header = navigationView.getHeaderView(0);
        name = (TextView)header.findViewById(R.id.NavHead__UserName);
        email = (TextView)header.findViewById(R.id.NavHead_UserMail);
        imageView = header.findViewById(R.id.NavHead_UseImage);
        name.setText( MyUserInfo.getPersonName());
        email.setText(MyUserInfo.getPersonEmail());

        if(MyUserInfo.getPersonPhoto() != null)
        {
            Glide.with(this).load(MyUserInfo.getPersonPhoto().toString()).apply(RequestOptions.circleCropTransform()).into(imageView);
        }
        mAuth = FirebaseAuth.getInstance();


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

        Handler handlerTimer;



        final android.app.FragmentManager fragmentManager=getFragmentManager();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_homescreen) {
              fragmentManager.beginTransaction().replace(R.id.content_frame,new HomeFragment()).commit();
              getSupportActionBar().setTitle("Door Side Noti");

//            FireDB.FireSetData();
              FireDB.getUnseenMessagesFromUser();

        }
        else if (id == R.id.nav_unseen) {
            FireDB.getUnseenMessagesFromUser();
            handlerTimer = new Handler();
            handlerTimer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fragmentManager.beginTransaction().replace(R.id.content_frame,new UnseenFragment()).commit();
                    getSupportActionBar().setTitle("Unseen");

                }
            },300);


        }

        else if (id == R.id.nav_allmessages) {

            FireDB.getAllMessagesFromUser();
            handlerTimer = new Handler();
            handlerTimer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fragmentManager.beginTransaction().replace(R.id.content_frame,new AllMessagesFragment()).commit();
                    getSupportActionBar().setTitle("All Messages");

                }
            },300);


        } else if (id == R.id.nav_boards) {
            FireDB.GetBoard();
            handlerTimer = new Handler();
            handlerTimer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fragmentManager.beginTransaction().replace(R.id.content_frame,new BoardsFragment()).commit();
                    getSupportActionBar().setTitle("My Boards");

                }
            },300);

        } else if (id == R.id.nav_tools) {
            fragmentManager.beginTransaction().replace(R.id.content_frame,new ToolsFragment()).commit();
            getSupportActionBar().setTitle("Tools");


        } else if (id == R.id.nav_SignOut) {
            signOut();
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);

        }
        else if(id==R.id.nav_send){

            new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                    .setIcon(R.drawable.ic_visibility_button)
                    .setTitle("HAKKIMIZDA ")
                    .setMessage("Bu uygulama Ozan Türcan ve Derya Yanal tarafından yapılmıştır.")
                    .setPositiveButton("Tamam",null)
                    .show();
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

    @Override
    public void onClick(View v) {

        int id = v.getId();



    }
}
