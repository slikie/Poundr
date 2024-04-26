package com.github.poundr.network

import com.github.poundr.network.model.UpdateLocationRequest
import retrofit2.http.Body
import retrofit2.http.PUT

interface SettingsRestService {
    //@GET("v3/me/prefs/settings")
    //    /* renamed from: a */
    //    Object m25432a(Continuation<? super AbstractC10929a<HttpExceptionResponse, GrindrSettings>> continuation);
    //
    //    @POST("v4/recognition/face")
    //    /* renamed from: b */
    //    Object m25433b(@Body FaceDetectionResult faceDetectionResult, Continuation<? super AbstractC10929a<HttpExceptionResponse, ? extends ResponseBody>> continuation);
    //
    //    @PUT("v3/me/location")
    //    /* renamed from: c */
    //    Object putMeLocation(@Body UpdateLocationRequest updateLocationRequest, Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);

    @PUT("v3/me/location")
    suspend fun putMeLocation(@Body updateLocationRequest: UpdateLocationRequest)

    //
    //    @PUT("v3/me/prefs/settings")
    //    /* renamed from: d */
    //    Object m25435d(@Body UpdateSettingsRequest updateSettingsRequest, Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);
}