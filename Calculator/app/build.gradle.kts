plugins {
}

    android {
    namespace = "hu.bme.aut.android.calculator"
    compileSdk = 34

    defaultConfig {
      applicationId = "hu.bme.aut.android.calculator"
    minSdk = 24
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
    }

  dependencies {

  }