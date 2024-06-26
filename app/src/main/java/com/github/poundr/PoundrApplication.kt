package com.github.poundr

import android.app.Application
import android.content.pm.PackageManager
import android.content.pm.Signature
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat
import com.github.poundr.utils.GrindrPackageManager
import com.google.firebase.installations.remote.FirebaseInstallationServiceClient
import dagger.hilt.android.HiltAndroidApp

private const val TAG = "App"

@HiltAndroidApp
class PoundrApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
        configureCoil()
    }

    private fun createNotificationChannels() {
        val notificationManager = NotificationManagerCompat.from(this)

        val chatsChannel = NotificationChannelCompat.Builder("chats", NotificationManagerCompat.IMPORTANCE_HIGH)
            .setName(getString(R.string.chats))
            .build()
        notificationManager.createNotificationChannel(chatsChannel)

        val tapsChannel = NotificationChannelCompat.Builder("taps", NotificationManagerCompat.IMPORTANCE_HIGH)
            .setName(getString(R.string.taps))
            .build()
        notificationManager.createNotificationChannel(tapsChannel)
    }

    private fun configureCoil() {
//        Coil.setImageLoader()
    }

    private fun isFirebaseInstallationServiceClient() = Thread.currentThread().stackTrace.any {
        it.className.startsWith(FirebaseInstallationServiceClient::class.java.name)
    }

    override fun getPackageManager(): PackageManager {
        val packageManager = super.getPackageManager()
        return if (isFirebaseInstallationServiceClient()) {
            GrindrPackageManager(packageManager)
        } else {
            packageManager
        }
    }

    override fun getPackageName(): String {
        return if (isFirebaseInstallationServiceClient()) {
            SPOOFED_PACKAGE_NAME
        } else {
            super.getPackageName()
        }
    }

    companion object {
        const val SPOOFED_PACKAGE_NAME = "com.grindrapp.android"
        const val SPOOFED_VERSION_NAME = "24.3.1"
        const val SPOOFED_VERSION_CODE = 122824
        val SPOOFED_SIGNATURES = arrayOf(Signature("308205873082036fa003020102021500b02d2ef98e9bbe94b84c3ac756c273d81aaa7d7e300d06092a864886f70d01010b05003074310b3009060355040613025553311330110603550408130a43616c69666f726e6961311630140603550407130d4d6f756e7461696e205669657731143012060355040a130b476f6f676c6520496e632e3110300e060355040b1307416e64726f69643110300e06035504031307416e64726f6964301e170d3139303532303232333333345a170d3439303532303232333333345a3074310b3009060355040613025553311330110603550408130a43616c69666f726e6961311630140603550407130d4d6f756e7461696e205669657731143012060355040a130b476f6f676c6520496e632e3110300e060355040b1307416e64726f69643110300e06035504031307416e64726f696430820222300d06092a864886f70d01010105000382020f003082020a0282020100e1efc55c8fdc9634f5246d93b44f2428fa84417894fb4725e3dcf09df4ad048370a4ac839f948cf1ce35f2fef24e03e885f2304673e501f10a023ae6d84e4538494c963336afe57c8c70cad9ecf259bf96eeb3d591ad4f3cd54c26d4be5293beb79ea7c3e2ce6436a0e33dca4cadf98c18f97243196c8eabe4f9e584f1acea2149b4c18b085375e2142d94cd458f88d9d29847a1d5d3001a18ce7a53ad3a47a7312904778d2171f148ab674b35d66e3d02f9ce60ab2b98e4c28b72bd6032c6e7d06896ba1639710c41595024d51cf54d5f6bf98550c11b1003df20262e06cc437d18f903b847274ec8c71fc25962570a6882dd35086b63a7ec1b2196438a35511a1f82589f61888bb7dff1d8c1668165831c304998874d96dc9baa34c8b86bf9c412124735e4e5cc451295cf68c5d2e3da501274bbec4abbdfa719fee44f78cb9a69e3c597d2967feabd5a34a4d1baea5dfbb0cc7cfba316768d7e0a74eea01339968b5620fde1b8c1e7c6d2b046669a05ad8426e369c8308fe6f6de721ba7b28c6c24b4f0f9d73f72f6029cde372fcf3edbedc6b407266e6362e9fb2e14b16b9891a2d6e2a7d862ddf4d1081704a0f200fefb19157dd96be180b41328e6be05238ac28f538d1818cabcbfe03ee5f267ee3d3f4bbf1bec5060e8f0c311ac73f08b0358865a7b1bc64597b03106ac314b8cac9fca85a6b1fdce7300d45baa8ec10203010001a310300e300c0603551d13040530030101ff300d06092a864886f70d01010b05000382020100734dfbd718e9508cbd97b139fbe735a799070997b34e1a926da4a44027bdf8ee4e3cf79c25f0ce0a3d1cdf43b7ea9451b11f1a8f809ac6721f5b7849e52de53f46cc7a67b08390c157fa2d027843178af034c3fc4fc230b3dc288e599de8f4e0ae4fd73b83f10d0ec6b6a73de9fc4e33cc4a59011236b6d89a6253513b45f7acf480252d2f20a938fd5d38f30f6fd4ab89ff6ea5c5bf625bae42b51c2caacdd60db2dec310a142335ddd5e31390483af643ad07ea1db868b41bd2a632aa13f824c513a2ebc1aefcbfdaf5f5237dd36e8be2c6e7fd260bd8d057efbeb3a1408eaf2aafdff61a4818cc9a76fb47fccd2a878a4fec18c3baba304577d72dd097cc0a81c11216a2494a814fa5c7f5fedab0798e0c0c871dc64452ccb7c478c75a12779e65dfbb69dacbb21dafb173a3882b38a084ac5cb048634996d470ebbafcfb6bad73e02a86164de94f9c3b474ade99f78bc2d8ea5bee48dd5107ca3f486d9ba906a9c3f0353be4b36063a0abc7677ae795753de73f72e5c5652e85207fcbcb892a47f3883b4f0b53a4680a2aadcf77b48a540c8d0f21670447226613b5936efa21baefa28c9fd0e2f8dc59b19a756765ccaf5dc658eeda46936037b0a5f2b8cff042e4ab29ef3c43126ed4d918427f61a0bef95b1511829342ff2acc627c6b214e770406a1ee1aa110d4752b171a4369a92010dc5636045fb0d43bebb174f90"))
    }
}