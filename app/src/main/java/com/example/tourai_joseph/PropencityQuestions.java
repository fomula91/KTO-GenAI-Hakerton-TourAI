package com.example.tourai_joseph;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tourai_joseph.API.API;
import com.example.tourai_joseph.API.RetrofitClient;
import com.example.tourai_joseph.model.AnswerModel;
import com.example.tourai_joseph.model.PersonalityRequest;
import com.example.tourai_joseph.model.PersonalityResponse;
import com.example.tourai_joseph.model.Question;
import com.example.tourai_joseph.model.QustionSingleton;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropencityQuestions extends AppCompatActivity {
    private int[] openness = new int[5];
    private int[] conscientiousness = new int[5];
    private int[] extraversion = new int[5];
    private int[] agreeableness = new int[5];
    private int[] neuroticism = new int[5];

    private List<Integer> selectedOptions = new ArrayList<>();
    private AnswerModel viewModel;
    private int currentQuestionSet = 0;

    private void CreateQuestionView(LinearLayout linearLayout, String[] options, Question question){

        TextView tv = new TextView(this);
        tv.setText(question.getText());
        tv.setTextSize(20);
        tv.setTextColor(Color.parseColor("#2B2B2B"));
        tv.setTypeface(null, Typeface.BOLD);
        tv.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.topMargin = dpToPx(50);
        tv.setLayoutParams(layoutParams);
        linearLayout.addView(tv);

        RadioGroup rg = new RadioGroup(this);
        rg.setOrientation(RadioGroup.HORIZONTAL);
        rg.setGravity(Gravity.CENTER);
        rg.setPadding(dpToPx(23), 0, 0, 0);
        for (int j = 0; j < options.length; j++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(options[j]);
            rg.addView(radioButton);
            radioButton.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
            radioButton.setTextColor(Color.parseColor("#A6A6A6"));
            radioButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpToPx(25));
            radioButton.setBackgroundResource(R.drawable.custom_radio_button_bg);
            radioButton.setLayoutParams(new RadioGroup.LayoutParams(dpToPx(65), dpToPx(65)));
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        radioButton.setTextColor(Color.parseColor("#FFFFFF"));
                    } else {
                        radioButton.setTextColor(Color.parseColor("#A6A6A6"));
                    }
                }
            });

            if (j != options.length - 1) {
                RadioGroup.LayoutParams params = (RadioGroup.LayoutParams) radioButton.getLayoutParams();
                params.rightMargin = dpToPx(10);
                radioButton.setLayoutParams(params);
            }
        }
        linearLayout.addView(rg);
    }

    private void loadQuestions(LinearLayout linearLayout, String[] options, List<Question> questions){
        linearLayout.removeAllViews();
        int start = currentQuestionSet * 5;
        int end = Math.min(start + 5, questions.size());
        for(int i = start; i < end; i++) {
            CreateQuestionView(linearLayout, options, questions.get(i));
        }
    }

    private void updateData(int[] data, String tag) {
        for(int i = 0 ; i < selectedOptions.size(); i++) {
            data[i] = selectedOptions.get(i) + 1;
        }
        switch(tag) {
            case "Openness" :
                viewModel.setOpennessData(data);
                break;
            case "Conscientiousness":
                viewModel.setConscientiousnessData(data);
                break;
            case "Extraversion" :
                viewModel.setExtraversionData(data);
                break;
            case "Agreeableness" :
                viewModel.setAgreeablenessData(data);
                break;
            case "Neuroticism" :
                viewModel.setNeuroticismData(data);
                break;
        }
        int[] resultData = getDataByTag(tag);
        if(resultData != null) {
            Log.d("PropencityQuestions", tag + " data: " + Arrays.toString(resultData));
        }
    }

    private int[] getDataByTag(String tag){
        switch(tag) {
            case "Openness":
                return viewModel.getOpennessData().getValue();
            case "Conscientiousness" :
                return viewModel.getConscientiousnessData().getValue();
            case "Extraversion" :
                return viewModel.getExtraversionData().getValue();
            case "Agreeableness" :
                return viewModel.getAgreeablenessData().getValue();
            case "Neuroticism" :
                return viewModel.getNeuroticismData().getValue();
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_propencity_questions);

        QustionSingleton myApplication = (QustionSingleton) getApplicationContext();
        viewModel = myApplication.getSharedViewModel();

        List<Question> questions = loadQuestionsFromGson();
        LinearLayout linearLayout = findViewById(R.id.question_linearlayout);

        String[] options = {"1","2","3","4","5"};
        loadQuestions(linearLayout, options, questions);

        Button nextBtn = findViewById(R.id.propencity_submit_button);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allQuestionsAnswered()) {
                    Log.d("currentQuestions", "current : " + String.valueOf(currentQuestionSet));
                    Log.d("currentQuestions", "openness : "+ openness.toString());
                    switch(currentQuestionSet) {
                        case 0 :
                            updateData(openness, "Openness");
                            break;
                        case 1:
                            updateData(conscientiousness, "Conscientiousness");
                            break;
                        case 2 :
                            updateData(extraversion, "Extraversion");
                            break;
                        case 3 :
                            updateData(agreeableness, "Agreeableness");
                            break;
                        case 4 :
                            updateData(neuroticism, "Neuroticism");
                            break;
                    }

                    currentQuestionSet++;
                    if(currentQuestionSet * 5 < questions.size()){
                        loadQuestions(linearLayout, options, questions);
                    }
                    else{
                        Log.d("end", "end of state");

                        //Intent intent = new Intent(PropencityQuestions.this, PropencityQuestions.class);
                        //startActivity(intent);
                        sendDataToServer();
                    }

                } else {
                    Toast.makeText(PropencityQuestions.this, "모든 질문에 답변해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView text1 = findViewById(R.id.text1);
        String textType = text1.getText().toString();
        Spannable spannableType = new SpannableString(textType);
        int startTypeIndex = textType.indexOf("자신의 선호도");
        int endTypeIndex = startTypeIndex + "자신의 선호도".length();
        spannableType.setSpan(new StyleSpan(Typeface.BOLD), startTypeIndex, endTypeIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableType.setSpan(new RelativeSizeSpan(1.2f), startTypeIndex, endTypeIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text1.setText(spannableType);
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    private boolean allQuestionsAnswered() {
        LinearLayout linearLayout = findViewById(R.id.question_linearlayout);
        selectedOptions.clear();

        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View childView = linearLayout.getChildAt(i);
            if (childView instanceof RadioGroup) {
                RadioGroup radioGroup = (RadioGroup) childView;
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (checkedRadioButtonId == -1) {
                    return false;
                } else {
                    int optionIndex = radioGroup.indexOfChild(findViewById(checkedRadioButtonId));
                    selectedOptions.add(optionIndex);
                    Log.d("addOptions", "add option");
                    Log.d("addOptins", selectedOptions.toString());
                }
            }
        }
        return selectedOptions.size() == 5;
    }

    private List<Question> loadQuestionsFromGson() {
        List<Question> questions = new ArrayList<>();
        try {
            InputStream is = getAssets().open("question.json");
            Reader reader  = new InputStreamReader(is, "UTF-8");

            Gson gson = new Gson();
            questions = Arrays.asList(gson.fromJson(reader, Question[].class));


        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
    }

    private void sendDataToServer() {
        // ViewModel에서 데이터를 가져오기
        int[] opennessData = viewModel.getOpennessData().getValue();
        int[] conscientiousnessData = viewModel.getConscientiousnessData().getValue();
        int[] extraversionData = viewModel.getExtraversionData().getValue();
        int[] agreeablenessData = viewModel.getAgreeablenessData().getValue();
        int[] neuroticismData = viewModel.getNeuroticismData().getValue();

        Log.d("PropencityQuestions5", "Openness data: " + Arrays.toString(opennessData));
        Log.d("PropencityQuestions5", "Conscientiousness data: " + Arrays.toString(conscientiousnessData));
        Log.d("PropencityQuestions5", "Extraversion data: " + Arrays.toString(extraversionData));
        Log.d("PropencityQuestions5", "Agreeableness data: " + Arrays.toString(agreeablenessData));
        Log.d("PropencityQuestions5", "Neuroticism data: " + Arrays.toString(neuroticismData));


        PersonalityRequest request = new PersonalityRequest();
        request.setOpenness1(opennessData[0]);
        request.setOpenness2(opennessData[1]);
        request.setOpenness3(opennessData[2]);
        request.setOpenness4(opennessData[3]);
        request.setOpenness5(opennessData[4]);

        request.setConscientiousness1(conscientiousnessData[0]);
        request.setConscientiousness2(conscientiousnessData[1]);
        request.setConscientiousness3(conscientiousnessData[2]);
        request.setConscientiousness4(conscientiousnessData[3]);
        request.setConscientiousness5(conscientiousnessData[4]);

        request.setExtraversion1(extraversionData[0]);
        request.setExtraversion2(extraversionData[1]);
        request.setExtraversion3(extraversionData[2]);
        request.setExtraversion4(extraversionData[3]);
        request.setExtraversion5(extraversionData[4]);

        request.setAgreeableness1(agreeablenessData[0]);
        request.setAgreeableness2(agreeablenessData[1]);
        request.setAgreeableness3(agreeablenessData[2]);
        request.setAgreeableness4(agreeablenessData[3]);
        request.setAgreeableness5(agreeablenessData[4]);

        request.setNeuroticism1(neuroticismData[0]);
        request.setNeuroticism2(neuroticismData[1]);
        request.setNeuroticism3(neuroticismData[2]);
        request.setNeuroticism4(neuroticismData[3]);
        request.setNeuroticism5(neuroticismData[4]);

        API api = RetrofitClient.getRetrofitInstance().create(API.class);
        Call<PersonalityResponse> call = api.getTravelPersonality(request);

        Log.d("iscall", "call");

        call.enqueue(new Callback<PersonalityResponse>() {
            @Override
            public void onResponse(Call<PersonalityResponse> call, Response<PersonalityResponse> response) {
                Log.d("isresponse?", "get response");
                if (response.isSuccessful()) {
                    PersonalityResponse personalityResponse = response.body();

                    String character = personalityResponse.getCharacter();
                    String travelPreferences = personalityResponse.getTravelPreferences();
                    Log.d("PropencityQuestions5", "character: " + character);
                    Log.d("PropencityQuestions5", "travelPreferences: " + travelPreferences);

                    Intent intent = new Intent(PropencityQuestions.this, PropencityResult.class);
                    intent.putExtra("character", character);
                    intent.putExtra("travelPreferences", travelPreferences);
                    startActivity(intent);
                }
                else {
                    Log.d("Retrofit", response.code() + " : Data not sent");
                }
            }

            @Override
            public void onFailure(Call<PersonalityResponse> call, Throwable t) {
                Log.d("PropencityQuestions5", "responseFailed" + t);
            }
        });
    }
}