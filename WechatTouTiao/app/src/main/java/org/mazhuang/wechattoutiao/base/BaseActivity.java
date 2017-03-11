package org.mazhuang.wechattoutiao.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.mazhuang.wechattoutiao.R;

/**
 * Created by mazhuang on 2017/2/26.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setupToolbar(Toolbar toolbar, final String title, final String url) {
        if (title != null) {
            toolbar.setTitle(title);
        }

        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.inflateMenu(R.menu.detail_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.share:
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_template, title, url));
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(Intent.createChooser(intent, getString(R.string.share_to)));
                        } else {
                            Toast.makeText(BaseActivity.this, getString(R.string.cannot_share), Toast.LENGTH_SHORT).show();
                        }
                        break;
                }

                return true;
            }
        });
    }
}
