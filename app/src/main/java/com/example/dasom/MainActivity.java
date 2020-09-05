package com.example.dasom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.dasom.databinding.ActivityMainBinding;
import com.example.dasom.fragment.Main1Fragment;
import com.example.dasom.fragment.Main2Fragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.fabMain.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, ChatActivity.class)));

        binding.bottomnavMain.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_1:
                    switchFragment(Main1Fragment.newInstance());
                    break;
                case R.id.action_2:
                    switchFragment(Main2Fragment.newInstance());
                    break;
            }
            return true;
        });
        switchFragment(Main1Fragment.newInstance());
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_main, fragment);
        transaction.commit();
    }
}