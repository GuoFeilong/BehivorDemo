package com.behivordemo.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.behivordemo.R;
import com.behivordemo.material.MaterialAimUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    private List<Entity> testData;
    private BehivorAdapter behivorAdapter;
    private TabLayout tabLayout;
    private MaterialAimUtils materialAimUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        materialAimUtils = new MaterialAimUtils
                .Builder()
                .materialAimType(MaterialAimUtils.MaterialAimType.EXPLODE)
                .animDuration(600)
                .slideGrivaty(Gravity.END)
                .build();

        materialAimUtils.setEixtMaterial(this);
        materialAimUtils.setEnterMaterial(this);

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
        recyclerView = (RecyclerView) findViewById(R.id.rv_test);
        testData = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            testData.add(new Entity(false, "测试--->>position=" + i));
        }
        behivorAdapter = new BehivorAdapter();
        behivorAdapter.setData(testData);
        behivorAdapter.setOnReClickLister(new OnReClickLister() {
            @Override
            public void onReItemClick(int position) {
                MaterialAimUtils.startActivityWithMaterialAim(MainActivity.this, CustomShowActivity.class);
            }
        });
        behivorAdapter.setOnCBCheckedListener(new OnCBCheckedListener() {
            @Override
            public void onChecked(int position) {
                boolean hasChecked = testData.get(position).isHasChecked();
                testData.get(position).setHasChecked(!hasChecked);
                behivorAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "--点击复选框--index--" + position + "---", Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(behivorAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tab_temp);

        for (int i = 0; i < 4; i++) {
            tabLayout.addTab(tabLayout.newTab().setText("娱乐" + i));
        }


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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class BehivorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<Entity> data;
        private OnReClickLister onReClickLister;
        private OnCBCheckedListener onCBCheckedListener;

        public void setOnReClickLister(OnReClickLister onReClickLister) {
            this.onReClickLister = onReClickLister;
        }

        public void setOnCBCheckedListener(OnCBCheckedListener onCBCheckedListener) {
            this.onCBCheckedListener = onCBCheckedListener;
        }

        public void setData(List<Entity> data) {
            this.data = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BehivorVH(LayoutInflater.from(MainActivity.this).inflate(R.layout.item, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            BehivorVH behivorVH = (BehivorVH) holder;
            Entity entity = data.get(position);
            behivorVH.testDesc.setText(entity.getDesc());
            behivorVH.checkBox.setChecked(entity.hasChecked);
            if (onCBCheckedListener != null) {
                behivorVH.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCBCheckedListener.onChecked(position);
                    }
                });
            }
            if (null != onReClickLister) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onReClickLister.onReItemClick(position);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class BehivorVH extends RecyclerView.ViewHolder {
            private TextView testDesc;
            private CheckBox checkBox;

            BehivorVH(View itemView) {
                super(itemView);
                testDesc = (TextView) itemView.findViewById(R.id.tv_desc);
                checkBox = (CheckBox) itemView.findViewById(R.id.cb_status);
            }
        }
    }

    interface OnReClickLister {
        void onReItemClick(int position);
    }

    interface OnCBCheckedListener {
        void onChecked(int position);
    }

    static class Entity {
        private boolean hasChecked;
        private String desc;

        public Entity(boolean hasChecked, String desc) {
            this.hasChecked = hasChecked;
            this.desc = desc;
        }

        public String getDesc() {

            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public boolean isHasChecked() {
            return hasChecked;
        }

        public void setHasChecked(boolean hasChecked) {
            this.hasChecked = hasChecked;
        }
    }
}
