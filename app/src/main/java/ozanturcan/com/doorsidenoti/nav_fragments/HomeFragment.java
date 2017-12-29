package ozanturcan.com.doorsidenoti.nav_fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ozanturcan.com.doorsidenoti.Operations.FirebaseDatabaseOperations;
import ozanturcan.com.doorsidenoti.R;

/**
 * Created by DELL on 23.10.2017.
 */

public class HomeFragment extends Fragment {
    private FirebaseDatabaseOperations FireDB = new FirebaseDatabaseOperations().getAnInnerClass();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        FireDB.GetCurrentUserInfo();

        View rootview=inflater.inflate(R.layout.fragment_main,container,false);
        return rootview;
    }
}
