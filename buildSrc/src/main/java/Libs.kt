object Libs {
    private const val kotlinVersion = "1.4.32"
    private const val hiltVersion = "2.33-beta"
    private const val navVersion = "2.3.5"

    object Gradle {
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        const val android = "com.android.tools.build:gradle:4.1.3"
        const val junit5 = "de.mannodermaus.gradle.plugins:android-junit5:1.7.1.1"
        const val hilt = "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"
        const val navigation = "androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion"
    }

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3"
    }

    object Android {
        const val startup = "androidx.startup:startup-runtime:1.0.0"
        const val fragment = "androidx.fragment:fragment-ktx:1.3.0-beta02"
        const val materialDesign = "com.google.android.material:material:1.3.0-alpha04"
        const val lifecycleCommon = "androidx.lifecycle:lifecycle-common-java8:2.2.0"
    }

    object Navigation {
        const val fragment = "androidx.navigation:navigation-fragment-ktx:$navVersion"
        const val ui = "androidx.navigation:navigation-ui-ktx:$navVersion"
    }

    object Networking {
        const val moshi = "com.squareup.moshi:moshi-kotlin:1.11.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        const val retrofitMoshi = "com.squareup.retrofit2:converter-moshi:2.9.0"
        const val interceptor = "com.squareup.okhttp3:logging-interceptor:4.9.1"
    }

    object Room {
        private const val roomVersion = "2.2.6"
        const val runtime = "androidx.room:room-runtime:$roomVersion"
        const val ktx = "androidx.room:room-ktx:$roomVersion"
        const val compiler = "androidx.room:room-compiler:$roomVersion"
    }

    object Hilt {
        const val android = "com.google.dagger:hilt-android:$hiltVersion"
        const val compiler = "com.google.dagger:hilt-compiler:$hiltVersion"
    }

    object Test {
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.3"
        const val mockk = "io.mockk:mockk:1.11.0"
        const val androidTestCore = "androidx.arch.core:core-testing:2.1.0"
        const val junitAPI = "org.junit.jupiter:junit-jupiter-api:5.7.0"
        const val junitEngine = "org.junit.jupiter:junit-jupiter-engine:5.7.0"
    }
}
