plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.somesh.textextraction"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.somesh.textextraction"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("com.google.firebase:firebase-ml-vision-text-model:24.0.0") // Check for the latest version

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //ml vision used older version
    implementation("com.google.mlkit:text-recognition:16.0.0")

    //for using model in fgoogle play services
    implementation("com.google.android.gms:play-services-mlkit-text-recognition:19.0.0")

    //cardview
    implementation("androidx.cardview:cardview:1.0.0")




}
