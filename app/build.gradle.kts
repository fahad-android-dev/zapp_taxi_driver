plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-android")
    id ("kotlin-kapt")
    id ("kotlinx-serialization")
}
android {
    namespace = "com.example.zapp_taxi_driver"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.zapp_taxi_driver"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
        dataBinding = true
    }
    bundle {
        language {
            enableSplit = false
        }
    }
}

dependencies {

    implementation(libs.androidx.activity)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.google.code.gson:gson:2.9.1")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    /** Navigation graph */
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")

    /** firebase */
    platform("com.google.firebase:firebase-bom:32.5.0")
    implementation("com.google.firebase:firebase-crashlytics-buildtools:2.9.9")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-core:21.1.1")
    implementation("com.google.firebase:firebase-messaging:23.3.1")

    /** google */
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")

    /** facebook */
    implementation("com.facebook.android:facebook-login:16.0.0")

    /** PushWoosh */
    implementation("com.pushwoosh:pushwoosh-firebase:6.6.16")

    /** Network operations
    Ktor Network dependencies */
    val ktorVersion = "2.2.4"
    implementation ("io.ktor:ktor-client-core:$ktorVersion")
    implementation ("io.ktor:ktor-client-android:$ktorVersion")
    implementation ("io.ktor:ktor-client-logging:$ktorVersion")
    implementation ("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation ("io.ktor:ktor-client-content-negotiation:$ktorVersion")

    /** Coil For Image Loader */
    implementation("io.coil-kt:coil:2.4.0")
    implementation("io.coil-kt:coil-gif:2.4.0")
    implementation("io.coil-kt:coil-base:2.4.0")

    /** SDP (dimension) & SSP (TextSizes) */
    implementation("com.intuit.sdp:sdp-android:1.1.0")
    implementation("com.intuit.ssp:ssp-android:1.1.0")

    /** Room Local Database */
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    /** ViewPager2 */
    implementation("androidx.viewpager2:viewpager2:1.1.0-beta02")

    /** coroutines */
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")

    /** GITHUB REPOS*/
    implementation("de.hdodenhof:circleimageview:3.1.0")                                        //circle imageview     //snackbar
    implementation("com.github.egek92:ProteinBar:1.4")                                          //snackbar
    implementation("com.github.cachapa:ExpandableLayout:2.9.2")                                 //ExpandableLayout
    implementation("com.github.zcweng:switch-button:0.0.3@aar")                                 //Switch button
    implementation("com.github.prolificinteractive:material-calendarview:2.0.1")                //Calender view

    /** Datastore */
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.datastore:datastore-core:1.0.0")

    /** Branch IO */
    implementation("io.branch.sdk.android:library:5.2.7")


}