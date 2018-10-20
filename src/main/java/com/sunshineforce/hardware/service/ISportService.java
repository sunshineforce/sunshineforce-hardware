package com.sunshineforce.hardware.service;

import com.sunshineforce.hardware.domain.request.HeartRateRequest;
import com.sunshineforce.hardware.domain.request.SportClassRequest;
import com.sunshineforce.hardware.domain.request.SportPersonalRequest;
import com.sunshineforce.hardware.domain.request.SportStrengthRequest;
import com.sunshineforce.hardware.domain.response.HeartRateResponse;
import com.sunshineforce.hardware.domain.response.SportClassResponse;
import com.sunshineforce.hardware.domain.response.SportPersonalResponse;
import com.sunshineforce.hardware.domain.response.SportStrengthResponse;

public interface ISportService {
    SportClassResponse getClassData(SportClassRequest sportClassRequest);
    SportPersonalResponse getPersonalData(SportPersonalRequest sportPersonalRequest);
    HeartRateResponse getHeartRateData(HeartRateRequest heartRateRequest);
    HeartRateResponse getClassHeartRateData(HeartRateRequest heartRateRequest);
    SportStrengthResponse getPersonalSportStrength(SportStrengthRequest sportStrengthRequest);
    SportStrengthResponse getClassSportStrength(SportStrengthRequest sportStrengthRequest);

}
