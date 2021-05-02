plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("de.mannodermaus.android-junit5")
}

android {
    compileSdkVersion(Config.Versions.compileSdk)
    buildToolsVersion = Config.Versions.buildTools

    defaultConfig {
        minSdkVersion(Config.Versions.minSdk)
        targetSdkVersion(Config.Versions.targetSdk)
    }

    buildTypes {
        val apiUrlResName = "api_url"
        val apiUrl = "http://api.tvmaze.com"

        getByName("release") {
            resValue("string", apiUrlResName, apiUrl)
        }
        getByName("debug") {
            resValue("string", apiUrlResName, apiUrl)
        }
    }

    compileOptions {
        sourceCompatibility = Config.Versions.Compile.sourceCompatibility
        targetCompatibility = Config.Versions.Compile.targetCompatibility
    }

    kotlinOptions {
        jvmTarget = Config.Versions.Kotlin.jvmTarget
    }
}

dependencies {
    implementation(project(Config.Projects.domain))

    api(Libs.Networking.moshi)
    api(Libs.Networking.retrofit)
    api(Libs.Networking.retrofitMoshi)
    api(Libs.Networking.interceptor)

    api(Libs.Room.runtime)
    implementation(Libs.Room.ktx)
    kapt(Libs.Room.compiler)

    implementation(Libs.Hilt.android)
    kapt(Libs.Hilt.compiler)

    testImplementation(Libs.Test.turbine)
    testImplementation(Libs.Test.coroutinesCore)
    testImplementation(Libs.Test.mockk)
    testImplementation(Libs.Test.junitAPI)
    testRuntimeOnly(Libs.Test.junitEngine)
}
