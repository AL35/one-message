package onemessagecompany.onemessage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import onemessagecompany.onemessage.model.ConfigurationRequest;
import onemessagecompany.onemessage.model.ConfigurationResponse;
import onemessagecompany.onemessage.rest.ApiClient;
import onemessagecompany.onemessage.rest.ConfigurationApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfigActivity extends AppCompatActivity {


    private NumberPicker numberpicker;
    private TextView textview;
    private ConfigurationApi configurationApi;
    private int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);


        setContentView(R.layout.activity_config);
        numberpicker = (NumberPicker) findViewById(R.id.numberPicker1);

        textview = (TextView) findViewById(R.id.textView1);

        numberpicker.setMinValue(0);

        numberpicker.setMaxValue(100);

        numberpicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                textview.setText("Selected Time is : " + newVal);
                time = newVal;
            }
        });
        GetConfig();


        Button mSave = (Button) findViewById(R.id.config_button);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveConfig();
            }
        });
    }

    private void GetConfig() {

        ConfigurationRequest configurationRequest = new ConfigurationRequest();
        configurationRequest.setTime(time);

        configurationApi = ApiClient.getAuthorizedClient().create(ConfigurationApi.class);
        configurationApi.GetConfiguration().enqueue(new Callback<ConfigurationResponse>() {
            @Override
            public void onResponse(Call<ConfigurationResponse> call, Response<ConfigurationResponse> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    int time=response.body().getTime();
                    numberpicker.setValue(time);
                    textview.setText("Selected Time is : " + time);
                }
            }

            @Override
            public void onFailure(Call<ConfigurationResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Invalid Login username or password", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void SaveConfig() {

        ConfigurationRequest configurationRequest = new ConfigurationRequest();
        configurationRequest.setTime(time);


        configurationApi = ApiClient.getAuthorizedClient().create(ConfigurationApi.class);
        configurationApi.SetConfiguration(configurationRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    Toast.makeText(getApplicationContext(), "Save Success", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Invalid Login username or password", Toast.LENGTH_LONG).show();
            }
        });

    }

}

