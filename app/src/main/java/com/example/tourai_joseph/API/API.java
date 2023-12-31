package com.example.tourai_joseph.API;

import com.example.tourai_joseph.model.PersonalityRequest;
import com.example.tourai_joseph.model.PersonalityResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API {
    //여행 성향
    @POST("travel_personality")
    Call<PersonalityResponse> getTravelPersonality(
            @Body PersonalityRequest request);

    //추천 여행지
    /*@POST("travel_suggestions")
    Call<RecommendResponse> getTravelRecommend(
            @Body RecommendRequest request);*/
}
