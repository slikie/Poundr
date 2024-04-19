package com.github.poundr.hack

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.content.pm.ApplicationInfo
import android.content.pm.ChangedPackages
import android.content.pm.FeatureInfo
import android.content.pm.InstrumentationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageInstaller
import android.content.pm.PackageManager
import android.content.pm.PermissionGroupInfo
import android.content.pm.PermissionInfo
import android.content.pm.ProviderInfo
import android.content.pm.ResolveInfo
import android.content.pm.ServiceInfo
import android.content.pm.SharedLibraryInfo
import android.content.pm.VersionedPackage
import android.content.res.Resources
import android.content.res.XmlResourceParser
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.UserHandle
import com.github.poundr.App

@Suppress("DEPRECATION")
@SuppressLint("NewApi", "QueryPermissionsNeeded")
class GrindrPackageManager(
    private val mBase: PackageManager
) : PackageManager() {
    private fun getGrindrPackageInfo(): PackageInfo {
        val packageInfo = PackageInfo()
        packageInfo.versionCode = App.SPOOFED_VERSION_CODE
        packageInfo.versionName = App.SPOOFED_VERSION_NAME
        packageInfo.signatures = App.SPOOFED_SIGNATURES
        return packageInfo
    }

    override fun getPackageInfo(packageName: String, flags: Int): PackageInfo {
        return if (packageName == App.SPOOFED_PACKAGE_NAME) {
            getGrindrPackageInfo()
        } else {
            mBase.getPackageInfo(packageName, flags)
        }
    }

    override fun getPackageInfo(versionedPackage: VersionedPackage, flags: Int): PackageInfo {
        return mBase.getPackageInfo(versionedPackage, flags)
    }

    override fun currentToCanonicalPackageNames(packageNames: Array<out String>): Array<String> {
        return mBase.currentToCanonicalPackageNames(packageNames)
    }

    override fun canonicalToCurrentPackageNames(packageNames: Array<out String>): Array<String> {
        return mBase.canonicalToCurrentPackageNames(packageNames)
    }

    override fun getLaunchIntentForPackage(packageName: String): Intent? {
        return mBase.getLaunchIntentForPackage(packageName)
    }

    override fun getLeanbackLaunchIntentForPackage(packageName: String): Intent? {
        return mBase.getLeanbackLaunchIntentForPackage(packageName)
    }

    override fun getPackageGids(packageName: String): IntArray {
        return mBase.getPackageGids(packageName)
    }

    override fun getPackageGids(packageName: String, flags: Int): IntArray {
        return mBase.getPackageGids(packageName, flags)
    }

    override fun getPackageUid(packageName: String, flags: Int): Int {
        return mBase.getPackageUid(packageName, flags)
    }

    override fun getPermissionInfo(permName: String, flags: Int): PermissionInfo {
        return mBase.getPermissionInfo(permName, flags)
    }

    override fun queryPermissionsByGroup(
        permissionGroup: String?,
        flags: Int
    ): MutableList<PermissionInfo> {
        return mBase.queryPermissionsByGroup(permissionGroup, flags)
    }

    override fun getPermissionGroupInfo(groupName: String, flags: Int): PermissionGroupInfo {
        return mBase.getPermissionGroupInfo(groupName, flags)
    }

    override fun getAllPermissionGroups(flags: Int): MutableList<PermissionGroupInfo> {
        return mBase.getAllPermissionGroups(flags)
    }

    override fun getApplicationInfo(packageName: String, flags: Int): ApplicationInfo {
        return mBase.getApplicationInfo(packageName, flags)
    }

    override fun getActivityInfo(component: ComponentName, flags: Int): ActivityInfo {
        return mBase.getActivityInfo(component, flags)
    }

    override fun getReceiverInfo(component: ComponentName, flags: Int): ActivityInfo {
        return mBase.getReceiverInfo(component, flags)
    }

    override fun getServiceInfo(component: ComponentName, flags: Int): ServiceInfo {
        return mBase.getServiceInfo(component, flags)
    }

    override fun getProviderInfo(component: ComponentName, flags: Int): ProviderInfo {
        return mBase.getProviderInfo(component, flags)
    }

    override fun getInstalledPackages(flags: Int): MutableList<PackageInfo> {
        return mBase.getInstalledPackages(flags)
    }

    override fun getPackagesHoldingPermissions(
        permissions: Array<out String>,
        flags: Int
    ): MutableList<PackageInfo> {
        return mBase.getPackagesHoldingPermissions(permissions, flags)
    }

    override fun checkPermission(permName: String, packageName: String): Int {
        return mBase.checkPermission(permName, packageName)
    }

    override fun isPermissionRevokedByPolicy(permName: String, packageName: String): Boolean {
        return mBase.isPermissionRevokedByPolicy(permName, packageName)
    }

    override fun addPermission(info: PermissionInfo): Boolean {
        return mBase.addPermission(info)
    }

    override fun addPermissionAsync(info: PermissionInfo): Boolean {
        return mBase.addPermissionAsync(info)
    }

    override fun removePermission(permName: String) {
        mBase.removePermission(permName)
    }

    override fun checkSignatures(packageName1: String, packageName2: String): Int {
        return mBase.checkSignatures(packageName1, packageName2)
    }

    override fun checkSignatures(uid1: Int, uid2: Int): Int {
        return mBase.checkSignatures(uid1, uid2)
    }

    override fun getPackagesForUid(uid: Int): Array<String>? {
        return mBase.getPackagesForUid(uid)
    }

    override fun getNameForUid(uid: Int): String? {
        return mBase.getNameForUid(uid)
    }

    override fun getInstalledApplications(flags: Int): MutableList<ApplicationInfo> {
        return mBase.getInstalledApplications(flags)
    }

    override fun isInstantApp(): Boolean {
        return mBase.isInstantApp
    }

    override fun isInstantApp(packageName: String): Boolean {
        return mBase.isInstantApp(packageName)
    }

    override fun getInstantAppCookieMaxBytes(): Int {
        return mBase.instantAppCookieMaxBytes
    }

    override fun getInstantAppCookie(): ByteArray {
        return mBase.instantAppCookie
    }

    override fun clearInstantAppCookie() {
        mBase.clearInstantAppCookie()
    }

    override fun updateInstantAppCookie(cookie: ByteArray?) {
        mBase.updateInstantAppCookie(cookie)
    }

    override fun getSystemSharedLibraryNames(): Array<String>? {
        return mBase.systemSharedLibraryNames
    }

    override fun getSharedLibraries(flags: Int): MutableList<SharedLibraryInfo> {
        return mBase.getSharedLibraries(flags)
    }

    override fun getChangedPackages(sequenceNumber: Int): ChangedPackages? {
        return mBase.getChangedPackages(sequenceNumber)
    }

    override fun getSystemAvailableFeatures(): Array<FeatureInfo> {
        return mBase.systemAvailableFeatures
    }

    override fun hasSystemFeature(featureName: String): Boolean {
        return mBase.hasSystemFeature(featureName)
    }

    override fun hasSystemFeature(featureName: String, version: Int): Boolean {
        return mBase.hasSystemFeature(featureName, version)
    }

    override fun resolveActivity(intent: Intent, flags: Int): ResolveInfo? {
        return mBase.resolveActivity(intent, flags)
    }

    override fun queryIntentActivities(intent: Intent, flags: Int): MutableList<ResolveInfo> {
        return mBase.queryIntentActivities(intent, flags)
    }

    override fun queryIntentActivityOptions(
        caller: ComponentName?,
        specifics: Array<out Intent>?,
        intent: Intent,
        flags: Int
    ): MutableList<ResolveInfo> {
        return mBase.queryIntentActivityOptions(caller, specifics, intent, flags)
    }

    override fun queryBroadcastReceivers(intent: Intent, flags: Int): MutableList<ResolveInfo> {
        return mBase.queryBroadcastReceivers(intent, flags)
    }

    override fun resolveService(intent: Intent, flags: Int): ResolveInfo? {
        return mBase.resolveService(intent, flags)
    }

    override fun queryIntentServices(intent: Intent, flags: Int): MutableList<ResolveInfo> {
        return mBase.queryIntentServices(intent, flags)
    }

    override fun queryIntentContentProviders(intent: Intent, flags: Int): MutableList<ResolveInfo> {
        return mBase.queryIntentContentProviders(intent, flags)
    }

    override fun resolveContentProvider(authority: String, flags: Int): ProviderInfo? {
        return mBase.resolveContentProvider(authority, flags)
    }

    override fun queryContentProviders(
        processName: String?,
        uid: Int,
        flags: Int
    ): MutableList<ProviderInfo> {
        return mBase.queryContentProviders(processName, uid, flags)
    }

    override fun getInstrumentationInfo(className: ComponentName, flags: Int): InstrumentationInfo {
        return mBase.getInstrumentationInfo(className, flags)
    }

    override fun queryInstrumentation(
        targetPackage: String,
        flags: Int
    ): MutableList<InstrumentationInfo> {
        return mBase.queryInstrumentation(targetPackage, flags)
    }

    override fun getDrawable(
        packageName: String,
        resid: Int,
        appInfo: ApplicationInfo?
    ): Drawable? {
        return mBase.getDrawable(packageName, resid, appInfo)
    }

    override fun getActivityIcon(activityName: ComponentName): Drawable {
        return mBase.getActivityIcon(activityName)
    }

    override fun getActivityIcon(intent: Intent): Drawable {
        return mBase.getActivityIcon(intent)
    }

    override fun getActivityBanner(activityName: ComponentName): Drawable? {
        return mBase.getActivityBanner(activityName)
    }

    override fun getActivityBanner(intent: Intent): Drawable? {
        return mBase.getActivityBanner(intent)
    }

    override fun getDefaultActivityIcon(): Drawable {
        return mBase.defaultActivityIcon
    }

    override fun getApplicationIcon(info: ApplicationInfo): Drawable {
        return mBase.getApplicationIcon(info)
    }

    override fun getApplicationIcon(packageName: String): Drawable {
        return mBase.getApplicationIcon(packageName)
    }

    override fun getApplicationBanner(info: ApplicationInfo): Drawable? {
        return mBase.getApplicationBanner(info)
    }

    override fun getApplicationBanner(packageName: String): Drawable? {
        return mBase.getApplicationBanner(packageName)
    }

    override fun getActivityLogo(activityName: ComponentName): Drawable? {
        return mBase.getActivityLogo(activityName)
    }

    override fun getActivityLogo(intent: Intent): Drawable? {
        return mBase.getActivityLogo(intent)
    }

    override fun getApplicationLogo(info: ApplicationInfo): Drawable? {
        return mBase.getApplicationLogo(info)
    }

    override fun getApplicationLogo(packageName: String): Drawable? {
        return mBase.getApplicationLogo(packageName)
    }

    override fun getUserBadgedIcon(drawable: Drawable, user: UserHandle): Drawable {
        return mBase.getUserBadgedIcon(drawable, user)
    }

    override fun getUserBadgedDrawableForDensity(
        drawable: Drawable,
        user: UserHandle,
        badgeLocation: Rect?,
        badgeDensity: Int
    ): Drawable {
        return mBase.getUserBadgedDrawableForDensity(drawable, user, badgeLocation, badgeDensity)
    }

    override fun getUserBadgedLabel(label: CharSequence, user: UserHandle): CharSequence {
        return mBase.getUserBadgedLabel(label, user)
    }

    override fun getText(
        packageName: String,
        resid: Int,
        appInfo: ApplicationInfo?
    ): CharSequence? {
        return mBase.getText(packageName, resid, appInfo)
    }

    override fun getXml(
        packageName: String,
        resid: Int,
        appInfo: ApplicationInfo?
    ): XmlResourceParser? {
        return mBase.getXml(packageName, resid, appInfo)
    }

    override fun getApplicationLabel(info: ApplicationInfo): CharSequence {
        return mBase.getApplicationLabel(info)
    }

    override fun getResourcesForActivity(activityName: ComponentName): Resources {
        return mBase.getResourcesForActivity(activityName)
    }

    override fun getResourcesForApplication(app: ApplicationInfo): Resources {
        return mBase.getResourcesForApplication(app)
    }

    override fun getResourcesForApplication(packageName: String): Resources {
        return mBase.getResourcesForApplication(packageName)
    }

    override fun verifyPendingInstall(id: Int, verificationCode: Int) {
        mBase.verifyPendingInstall(id, verificationCode)
    }

    override fun extendVerificationTimeout(
        id: Int,
        verificationCodeAtTimeout: Int,
        millisecondsToDelay: Long
    ) {
        mBase.extendVerificationTimeout(id, verificationCodeAtTimeout, millisecondsToDelay)
    }

    override fun setInstallerPackageName(targetPackage: String, installerPackageName: String?) {
        mBase.setInstallerPackageName(targetPackage, installerPackageName)
    }

    @Deprecated("Deprecated in Java")
    override fun getInstallerPackageName(packageName: String): String? {
        return mBase.getInstallerPackageName(packageName)
    }

    @Deprecated("Deprecated in Java")
    override fun addPackageToPreferred(packageName: String) {
        mBase.addPackageToPreferred(packageName)
    }

    @Deprecated("Deprecated in Java")
    override fun removePackageFromPreferred(packageName: String) {
        mBase.removePackageFromPreferred(packageName)
    }

    @Deprecated("Deprecated in Java")
    override fun getPreferredPackages(flags: Int): MutableList<PackageInfo> {
        return mBase.getPreferredPackages(flags)
    }

    @Deprecated("Deprecated in Java")
    override fun addPreferredActivity(
        filter: IntentFilter,
        match: Int,
        set: Array<out ComponentName>?,
        activity: ComponentName
    ) {
        mBase.addPreferredActivity(filter, match, set, activity)
    }

    @Deprecated("Deprecated in Java")
    override fun clearPackagePreferredActivities(packageName: String) {
        mBase.clearPackagePreferredActivities(packageName)
    }

    @Deprecated("Deprecated in Java")
    override fun getPreferredActivities(
        outFilters: MutableList<IntentFilter>,
        outActivities: MutableList<ComponentName>,
        packageName: String?
    ): Int {
        return mBase.getPreferredActivities(outFilters, outActivities, packageName)
    }

    override fun setComponentEnabledSetting(
        componentName: ComponentName,
        newState: Int,
        flags: Int
    ) {
        mBase.setComponentEnabledSetting(componentName, newState, flags)
    }

    override fun getComponentEnabledSetting(componentName: ComponentName): Int {
        return mBase.getComponentEnabledSetting(componentName)
    }

    override fun setApplicationEnabledSetting(packageName: String, newState: Int, flags: Int) {
        mBase.setApplicationEnabledSetting(packageName, newState, flags)
    }

    override fun getApplicationEnabledSetting(packageName: String): Int {
        return mBase.getApplicationEnabledSetting(packageName)
    }

    override fun isSafeMode(): Boolean {
        return mBase.isSafeMode
    }

    override fun setApplicationCategoryHint(packageName: String, categoryHint: Int) {
        mBase.setApplicationCategoryHint(packageName, categoryHint)
    }

    override fun getPackageInstaller(): PackageInstaller {
        return mBase.packageInstaller
    }

    override fun canRequestPackageInstalls(): Boolean {
        return mBase.canRequestPackageInstalls()
    }
}