package com.example.dasom.screen.main2;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.dasom.R;
import com.example.dasom.api.NetworkHelper;
import com.example.dasom.util.TokenCache;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateEmergencyDialog  {
    private Context context;
    private String updating_name;
    private String updating_phone;
    private static final String BASE_URL = "https://api.taemin.dev/dasomi/";
    private EditText update_name,update_phone;
    private Button update_button;

    public UpdateEmergencyDialog(Context context) {
        this.context = context;
    }
    public void showDialog(){

        final Dialog dlg = new Dialog(context);
        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.dialog_update_info);

        dlg.show();

        update_name = dlg.findViewById(R.id.update_name_et);
        update_phone = dlg.findViewById(R.id.update_phone_et);
        update_button = dlg.findViewById(R.id.update_info_bt);

        update_button.setOnClickListener(v -> {
            updating_name = update_name.getText().toString();
            updating_phone = update_phone.getText().toString();
            String token = TokenCache.getToken(context);
            UpdateInfo(BASE_URL, token,updating_phone,updating_name);
            dlg.dismiss();
        });
    }

    public void UpdateInfo(String url,String token,String phone,String name){
        UpdateInfoValue updateInfoValue = new UpdateInfoValue(phone,name);
        NetworkHelper.getInstance(url).UpdateInfo("Bearer "+token,updateInfoValue).enqueue(new Callback<UpdateInfo>() {
            @Override
            public void onResponse(Call<UpdateInfo> call, Response<UpdateInfo> response) {
                if (response.isSuccessful()){
                    UpdateInfo updateInfo = response.body();
                }
            }

            @Override
            public void onFailure(Call<UpdateInfo> call, Throwable t) {

            }
        });

    }
}
