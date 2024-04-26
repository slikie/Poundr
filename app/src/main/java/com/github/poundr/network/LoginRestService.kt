package com.github.poundr.network

import com.github.poundr.network.model.AuthResponse
import com.github.poundr.network.model.FcmPushRequest
import com.github.poundr.network.model.LoginEmailRequest
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginRestService {
//    @Headers("requireRealDeviceInfo: true")
//    @POST("v3/users/password-validation")
//    suspend fun postPasswordValidation(@Body validatePasswordComplexityRequest: ValidatePasswordComplexityRequest): Unit

    @Headers("requireRealDeviceInfo: true")
    @POST("v3/gcm-push-tokens")
    suspend fun postGcmPushTokens(@Body fcmPushRequest: FcmPushRequest)

//    @Headers("requireRealDeviceInfo: true")
//    @POST("v3/users/update-password")
//    suspend fun postUpdatePassword(@Body changePasswordRequest: ChangePasswordRequest): ChangePasswordResponse

    @Headers("requireRealDeviceInfo: true")
    @POST("v4/sessions")
    suspend fun postSessions(@Body loginEmailRequest: LoginEmailRequest): AuthResponse

//    @Headers("requireRealDeviceInfo: true")
//    @POST("v3/users/email")
//    suspend fun postEmail(@Body updateEmailRequest: UpdateEmailRequest): AuthResponse
//
//    @Headers("requireRealDeviceInfo: true")
//    @POST("v4/sms/users/update-password")
//    suspend fun postSmsUpdatePassword(@Body changePasswordPhoneRequest: ChangePasswordPhoneRequest): ChangePasswordResponse
//
//    @Headers("requireRealDeviceInfo: true")
//    @POST("v7/users/thirdparty")
//    suspend fun postThirdParty(@Body createThirdPartyAccountRequest: CreateThirdPartyAccountRequest): ThirdPartyCreateAccountResponse
//
//    @Headers("requireRealDeviceInfo: true")
//    @POST("v7/users")
//    suspend fun postUsers(@Body createAccountEmailRequest: CreateAccountEmailRequest): FirstPartyCreateAccountResponse
//
//    @Headers("requireRealDeviceInfo: true")
//    @POST("v3/users/forgot-password")
//    suspend fun postForgotPassword(@Body forgotPwdEmailRequest: ForgotPwdEmailRequest): ForgotPwdEmailResponse
//
//    @Headers("requireRealDeviceInfo: true")
//    @POST("v3/users/thirdparty/exchange")
//    suspend fun postThirdPartyExchange(@Body googleAccessTokenRequest: GoogleAccessTokenRequest): GoogleAccessTokenResponse
//
//    @Headers("requireRealDeviceInfo: true")
//    @POST("v4/sms/sessions")
//    suspend fun postSmsSessions(@Body loginPhoneRequest: LoginPhoneRequest): AuthResponse
//
//    @Headers("requireRealDeviceInfo: true")
//    @POST("v4/sessions/thirdparty")
//    suspend fun postThirdPartySessions(@Body thirdPartyRequest: ThirdPartyRequest): ThirdPartyAuthResponse
}