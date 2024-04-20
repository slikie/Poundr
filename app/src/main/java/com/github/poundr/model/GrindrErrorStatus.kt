package com.github.poundr.model

import androidx.compose.ui.util.fastFirstOrNull

enum class GrindrErrorStatus(
    val code: Int?,
    val message: String
) {
    ERR_CANNOT_SAVE(2, "Unable to save"),
    ERR_INVALID_PARAMETERS(4, "Invalid input parameters"),
    ERR_UNKNOWN_ERROR(5, "Unknown error"),
    ERR_INVALID_TOKEN(6, "Invalid token"),
    ERR_FAILED_TO_CREATE_SESSION(7, "Failed to create session"),
    ERR_DEPRECATED_CLIENT_VERSION(8, "The client version is deprecated"),
    ERR_DUPLICATED_USER(9, "Duplicated user"),
    ERR_USER_IS_NOT_ACTIVE(10, "User is not active"),
    ERR_SAVE_FAILURE(11, "Save failure"),
    ERR_UNKNOWN_PLATFORM(13, "Unknown platform"),
    ERR_FAILED_TO_VALIDATE_CAPTCHA(15, "Failed to validate captcha"),
    ERR_SHORT_PASSWORD(16, "Your password is too short.  Please enter a password with at least six characters"),
    ERR_FAILED_TO_CREATE_PROFILE(17, "Failed to create profile"),
    ERR_WEAK_PASSWORD(17, "Your password does not meet complexity requirements"),
    ERR_INVALID_CAPTCHA_TOKEN(18, "Invalid captcha token"),
    ERR_INVALID_AGE(19, "Invalid age"),
    ERR_EMAIL_EXISTS(22, "Email id already exists"),
    ERR_EMAIL_NOT_FOUND(23, "Email id not found"),
    ERR_AUTH_FAILURE(24, "User Authentication failed"),
    ERR_RESET_TOKEN_NOT_FOUND(25, "Token not found"),
    ERR_PROFILE_BANNED(27, "Profile is banned"),
    ERR_DEVICE_BANNED(28, "ACCOUNT_BANNED"),
    ERR_INVALID_EMAIL(29, "Invalid email"),
    ERR_VERIFICATION_REQUIRED(30, "Verification required"),
    ERR_IP_BANNED(31, "SUSPICIOUS_NETWORK"),
    ERR_ACCOUNT_REGISTRATION_FAILED(32, "Account Registration Failed"),
    ERR_ACCOUNT_REGISTRATION_REJECTED(33, "Account registration rejected"),
    ERR_EXCEED_FAVE_DAILY_LIMIT(33, "Favorite daily limit reached"),
    ERR_CANNOT_UNBLOCK_NONBLOCKED(36, "Can not unblock a non-blocked user"),
    ERR_AGE_RESTRICTED(35, "Underage ban, ages 13-17"),
    ERR_UNDER_THIRTEEN(36, "Underage ban, ages 5-12"),
    ERR_INVALID_THIRD_PARTY_TOKEN(80, "Invalid third party token"),
    ERR_THIRD_PARTY_USER_ID_EXISTS(81, "Third party user id exists"),
    ERR_THIRD_PARTY_CREATE_ACCOUNT(83, "Error when third party create account"),
    ERR_GROUPCHAT_MAX_CREATE_LIMIT(200, "Exceeding create limit."),
    ERR_GROUPCHAT_CHECK_NAME(201, "Check name fail."),
    ERR_GROUPCHAT_MEMBER_NUM_LIMIT(202, "The number of members exceeds the limit."),
    ERR_GROUPCHAT_BLOCK_LIMIT(203, "block limit."),
    ERR_GROUPCHAT_REPEATED_INVITATION(204, "repeated invitation."),
    ERR_GROUPCHAT_NO_PERMISSION_OPERATION(205, "no permission operation"),
    ERR_GROUPCHAT_MORE_THAN_INVITATION_LIMIT_PER_DAY(206, "more than invitation limit per day"),
    ERR_GROUPCHAT_INVITATION_LINK_CREATE(207, "create invitation link code error"),
    ERR_GROUPCHAT_CODE_GROUPID_MISMATCH(209, "Code and GroupId do not match"),
    ERR_PHONE_EXISTS(300, "Phone number already exists"),
    ERR_PHONE_NOT_FOUND(301, "Phone number not found"),
    ERR_INVALID_PHONE_NUMBER(302, "Invalid phone number"),
    ERR_INVALID_VERIFICATION_CODE(303, "Invalid verification code"),
    ERR_INVALID_PHONE_NUMBER_REGISTRATION_AFTER_DELETION(304, "Another profile with this phone number was deleted recently"),
    ERR_NOT_AVAILABLE_IN_THIS_REGION(305, "Not available in this region"),
    ERR_SMS_ENDPOINT_LIMIT_REACHED(306, "SMS endpoint limit reached"),
    ERR_PROFILE_ALREADY_VERIFIED(null, "Profile is already verified"),
    ERR_PROFILE_NOT_REQUIRED_VERIFY(null, "Profile is not verification required"),
    ERR_PHONE_NUM_TOO_MANY_TIMES(null, "Phone number was used too many times"),
    ERR_PHONE_NUMBER_IS_BANNED(null, "Phone number is banned"),
    ERR_TARGET_PROFILE_OFFLINE(null, "TARGET_PROFILE_OFFLINE"),
    ERR_EXCEED_LENGTH_LIMIT(null, "EXCEED_LENGTH_LIMIT");

    companion object {
        fun fromCodeAndMessage(code: Int?, message: String?): GrindrErrorStatus {
            return entries.fastFirstOrNull {
                it.code == code && it.message == message
            } ?: ERR_UNKNOWN_ERROR
        }
    }
}