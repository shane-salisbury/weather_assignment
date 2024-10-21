plugins {
    alias(libs.plugins.weather.android.feature)
    alias(libs.plugins.weather.android.library.compose)
    kotlin("kapt")
}

android {
    namespace = "com.weather.feature.forecast"

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.data)
    implementation(projects.core.common)
    implementation(projects.core.designsystem)
    implementation(projects.core.location)

    implementation(libs.coil)
    implementation(libs.coil.network.okhttp)

    implementation(libs.androidx.fragment.compose)

    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.swiperefreshlayout)

    testImplementation(projects.core.testing)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)
    testImplementation(libs.truth)
}