package com.movielist.view.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.movielist.R;
import com.movielist.database.KeyDbHelper;
import com.movielist.model.entity.auth.LoginData;
import com.movielist.presenter.activity_presenters.LoginPresenter;
import com.movielist.view.view_interfaces.LoginView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsClient;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.CustomTabsServiceConnection;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {


    @BindView(R.id.signin)
    Button signIn;

    @BindView(R.id.guest)
    TextView guest;

    @BindView(R.id.error)
    TextView error;

    private boolean clicked;
    private boolean loaded;
    private LoginPresenter mPresenter;
    private CustomTabsServiceConnection mConnection;

    public static final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        KeyDbHelper dbHelper = new KeyDbHelper(this);
        LoginData loginData = new LoginData(dbHelper);
        mPresenter = new LoginPresenter(this, loginData);
        clicked = false;
        loaded = false;

    }

    @Override
    protected void onStart() {
        super.onStart();
        String action = getIntent().getAction();
        if(Intent.ACTION_VIEW.equals(action)&& !loaded){
            mPresenter.getAccessToken();
            loaded = true;
        }else {
            mConnection = new CustomTabsServiceConnection() {
                @Override
                public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {
                    client.warmup(0);

                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            };
            CustomTabsClient.bindCustomTabsService(this, CUSTOM_TAB_PACKAGE_NAME, mConnection);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(mConnection != null) {
            unbindService(mConnection);
        }
    }

    @Override
    @OnClick(R.id.signin)
    public void onLogin() {
        if (!clicked) {
            mPresenter.getRequestToken();
            clicked = true;
        }
    }

    @Override
    @OnClick(R.id.guest)
    public void onGuest() {
        mPresenter.getGuestSession();
    }

    @Override
    public void onError(String text) {
        error.setText(text);
        error.setVisibility(View.VISIBLE);
    }

    @Override
    public void launchNext(String data, String type){

        Intent intent = new Intent(this, CatalogActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(type, data);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();

    }


    @Override
    public void checkToken(String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(this,R.color.colorPrimary));
        CustomTabsIntent intent = builder.build();
        intent.launchUrl(this, Uri.parse(url));
        clicked = false;
    }


}
