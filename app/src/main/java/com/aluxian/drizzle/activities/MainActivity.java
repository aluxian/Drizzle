package com.aluxian.drizzle.activities;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;

import com.aluxian.drizzle.R;
import com.aluxian.drizzle.fragments.DrawerFragment;
import com.aluxian.drizzle.fragments.TabsFragment;
import com.aluxian.drizzle.utils.Config;
import com.aluxian.drizzle.utils.Log;
import com.aluxian.drizzle.utils.UserManager;
import com.aluxian.drizzle.views.toolbar.EnhancedToolbar;

import java.util.List;

import static com.aluxian.drizzle.fragments.DrawerFragment.DrawerIconState;

public class MainActivity extends FragmentActivity implements DrawerFragment.Callbacks {

    public static final String PREF_INTRO_FINISHED = "intro_finished";
    public static final String PREF_API_AUTH_TOKEN = "api_auth_token";

    private SharedPreferences mSharedPrefs;

    private DrawerFragment mDrawerFragment;
    private EnhancedToolbar mToolbar;
    private View mSearchContainer;
    private View mFragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Intro activity
        if (!mSharedPrefs.getBoolean(PREF_INTRO_FINISHED, false)) {
            startActivity(new Intent(this, IntroActivity.class));
        }

        setContentView(R.layout.activity_main);

        mSearchContainer = findViewById(R.id.search_results_container);
        mFragmentContainer = findViewById(R.id.fragment_container);

        // Set the toolbar
        mToolbar = (EnhancedToolbar) findViewById(R.id.toolbar);
        setActionBar(mToolbar.getNativeToolbar());

        // Set up the navigation drawer
        mDrawerFragment = (DrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    /**
     * Toggle search mode. When search mode is active, a view is drawn above the existing tabs and shots grid. Search results will be shown
     * in this view, along with search suggestions from the past.
     *
     * @param active Whether to make search mode active or not.
     */
    private void searchMode(boolean active) {
        if (active) {
            mToolbar.getSearchView().show();
            mSearchContainer.setVisibility(View.VISIBLE);

            //mSearchContainer.animate().alpha(1);

            mFragmentContainer.animate().alpha(0);

            mDrawerFragment.setDrawerLocked(true);
            mDrawerFragment.toggleDrawerIcon(DrawerIconState.ARROW);
        } else {
            mToolbar.getSearchView().hide();
            //mSearchContainer.setVisibility(View.GONE);
            //mSearchContainer.animate().alpha(0).withEndAction(() -> );

            mFragmentContainer.animate().alpha(1).withEndAction(() -> mSearchContainer.setVisibility(View.GONE));

            mDrawerFragment.setDrawerLocked(false);
            mDrawerFragment.toggleDrawerIcon(DrawerIconState.BURGER);
        }
    }

    /**
     * Replaces the fragment displayed in the main fragment container with the given one.
     *
     * @param fragment The fragment to display in the container.
     */
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Only use a fade animation if there's an existing fragment in the main container already
//        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) != null) {
//            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//        }

        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public boolean onDrawerItemSelected(int titleResourceId) {
        switch (titleResourceId) {
            case R.string.drawer_main_feed:
                replaceFragment(TabsFragment.newInstance(TabsFragment.Type.FEED));
                //replaceFragment(ShotsFragment.newInstance(FollowingShotsProvider.class, null));
                return true;

            case R.string.drawer_main_shots:
                replaceFragment(TabsFragment.newInstance(TabsFragment.Type.SHOTS));
                return true;

            case R.string.drawer_personal_buckets:

                return true;

            case R.string.drawer_personal_go_pro:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://dribbble.com/pro?ref=getdrizzle.co")));
                return false;

            case R.string.drawer_personal_account_settings:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://dribbble.com/account")));
                return true;

            case R.string.drawer_personal_sign_out:
                UserManager.getInstance().clearAccessToken();
                mDrawerFragment.checkAuthState();
                return false;

            case R.string.drawer_personal_sign_in:
                startActivity(new Intent(this, AuthActivity.class));
                return false;

            case R.string.drawer_app_rate:
                Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName()));
                List<ResolveInfo> list = getPackageManager().queryIntentActivities(rateIntent, 0);

                if (list.size() > 0) {
                    startActivity(rateIntent);
                } else {
                    rateIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                    startActivity(rateIntent);
                }

                return false;

            case R.string.drawer_app_feedback:
                String uri = "mailto:" + Uri.encode(Config.FEEDBACK_EMAIL)
                        + "?subject=" + Uri.encode(getString(R.string.send_feedback_subject))
                        + "&body=" + Uri.encode(getString(R.string.send_feedback_body));

                try {
                    startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse(uri)));
                } catch (ActivityNotFoundException e) {
                    Log.d(e);

                    new AlertDialog.Builder(this, R.style.Drizzle_Widget_Dialog)
                            .setMessage(getString(R.string.send_feedback_error, Config.FEEDBACK_EMAIL))
                            .setPositiveButton(R.string.dialog_ok, null)
                            .show();
                }

                return false;

            default:
                return true;
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mToolbar.getSearchView().isShownInToolbar()) {
                    searchMode(false);
                    return true;
                }

                return false;

            case R.id.action_search:
                searchMode(true);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerFragment.isDrawerOpen()) {
            mDrawerFragment.closeDrawer();
        } else if (mToolbar.getSearchView().isShownInToolbar()) {
            searchMode(false);
        } else {
            super.onBackPressed();
        }
    }

}
