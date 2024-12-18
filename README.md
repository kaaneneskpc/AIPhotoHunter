## AI Photo Hunter

AI Photo Hunter is an innovative mobile app that allows users to go photo hunting. Thanks to its AI-powered features, users can explore interesting places around them, take photos and share them with other users. The app appeals to a wide audience with its user-friendly interface and multi-language support.

</br>

## Features

<li><strong>Multi-Language Support:</strong> Users can use the app in their own language.</li>
<li><strong>Location-Based Discovery:</strong> You can hunt for photos with different location options such as home and outdoors.</li>
<li><strong>Real-Time Upload Indicator:</strong> The app provides a visual feedback to the user while the data is loading.</li>
<li><strong>User Friendly Interface:</strong> Easy to use with a modern and intuitive design.</li>
<li><strong>Gallery Photo Import:</strong> Instead of taking photos for analysis, you can add photos from the gallery.</li>
<li><strong>AI-Powered Photo Analysis:</strong> Taken photos are analyzed and evaluated by artificial intelligence.</li>
<li><strong>Sharing and Social Interaction:</strong> You can share your photos with the community and see what other users have shared.</li>

</br>

## App Contents

### English
<img src="https://github.com/user-attachments/assets/6ad87077-9e40-4ffe-ada4-464955219b01" height = "400px"/><img src="https://github.com/user-attachments/assets/02d2ae58-e64f-4e73-8698-a46b3ed77ffb" height = "400px"/><img src="https://github.com/user-attachments/assets/2c8121e3-d818-4bc2-b1d9-450661c63dcf" height = "400px"/><img src="https://github.com/user-attachments/assets/6a182057-c79c-430a-978f-30d1444eceeb" height = "400px"/><img src="https://github.com/user-attachments/assets/9ab163e9-c33c-4ad9-a264-b3ea58673c32" height = "400px"/><img src="https://github.com/user-attachments/assets/6ede46bd-f7e3-4e82-9efe-1a2f7eba777d" height = "400px"/><img src="https://github.com/user-attachments/assets/b3f7803b-1a25-4abb-8a9e-d35078602473" height = "400px"/><img src="https://github.com/user-attachments/assets/b8e852ba-75e7-48ac-b24e-cb7598c2be4a" height = "400px"/><img src="https://github.com/user-attachments/assets/2b5506ae-abde-4dfd-bcc0-ca45ced6e174" height = "400px"/><img src="https://github.com/user-attachments/assets/fb874f12-7744-426d-8106-ffbc13c45454" height = "400px"/>


### Turkish
<img src="https://github.com/user-attachments/assets/eb79d566-ee13-4fbb-b463-0fe821b68ceb" height = "400px"/><img src="https://github.com/user-attachments/assets/8258b9b8-451e-4f53-958c-b39ed1f51a0f" height = "400px"/><img src="https://github.com/user-attachments/assets/ca630ed1-84fb-42b9-8662-fec8877b43ee" height = "400px"/><img src="https://github.com/user-attachments/assets/042f883f-4476-448e-8d22-0d907b1a6ca1" height = "400px"/><img src="https://github.com/user-attachments/assets/25a7b717-99e1-418a-9017-859afe93fcfa" height = "400px"/><img src="https://github.com/user-attachments/assets/33ade1b8-86f3-447a-b226-78e70d0e1f14" height = "400px"/><img src="https://github.com/user-attachments/assets/6fd5f80b-bf9d-48ac-9fdd-a1b5212c962a" height = "400px"/><img src="https://github.com/user-attachments/assets/8a884d67-6313-4f1f-8c2c-d7ba1bf54168" height = "400px"/>


</br>

# Techs

<li><strong>Kotlin:</strong> A modern and secure programming language for Android app development.</li>
<li><strong>Jetpack Compose:</strong>  Declarative UI development library.</li>
<li><strong>MVVM (Model-View-ViewModel):</strong>  A flexible and scalable structure for application architecture.</li>
<li><strong>Navigation Component:</strong>  Structural component that facilitates navigation within the application.</li>
<li><strong>Material Design 3:</strong>  Modern and aesthetic user interface designs.</li>

</br>

## Installation

Clone the repository
```kotlin
git clone https://github.com/kaaneneskpc/AIPhotoHunter.git
```
Open In Android Studio, Click on File > Open and select the cloned project directory.

Add the dependency to your `libs.versions.toml` file:

```kotlin
[versions]
agp = "8.7.2"
kotlin = "2.0.0"
coreKtx = "1.15.0"
junit = "4.13.2"
junitVersion = "1.2.1"
espressoCore = "3.6.1"
lifecycleRuntimeKtx = "2.8.7"
lifecycleViewmodelCompose = "2.8.7"
activityCompose = "1.9.3"
composeBom = "2024.04.01"
generativeai = "0.9.0"
googleAndroidLibrariesMapsplatformSecretsGradlePlugin = "2.0.1"
materialIconsExtended = "1.7.5"
navigationCompose = "2.7.7"
ksp = "2.0.0-1.0.22"
hiltAndroid = "2.51.1"
hiltCompiler = "1.2.0"

[libraries]
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
androidx-material-icons-extended = { module = "androidx.compose.material:material-icons-extended", version.ref = "materialIconsExtended" }
junit = { group = "junit", name = "junit", version.ref = "junit" }
androidx-junit = { group = "androidx.test.ext", name = "junit", version.ref = "junitVersion" }
androidx-espresso-core = { group = "androidx.test.espresso", name = "espresso-core", version.ref = "espressoCore" }
androidx-lifecycle-runtime-ktx = { group = "androidx.lifecycle", name = "lifecycle-runtime-ktx", version.ref = "lifecycleRuntimeKtx" }
androidx-lifecycle-viewmodel-compose = { group = "androidx.lifecycle", name = "lifecycle-viewmodel-compose", version.ref = "lifecycleViewmodelCompose" }
androidx-activity-compose = { group = "androidx.activity", name = "activity-compose", version.ref = "activityCompose" }
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
androidx-ui = { group = "androidx.compose.ui", name = "ui" }
androidx-ui-graphics = { group = "androidx.compose.ui", name = "ui-graphics" }
androidx-ui-tooling = { group = "androidx.compose.ui", name = "ui-tooling" }
androidx-ui-tooling-preview = { group = "androidx.compose.ui", name = "ui-tooling-preview" }
androidx-ui-test-manifest = { group = "androidx.compose.ui", name = "ui-test-manifest" }
androidx-ui-test-junit4 = { group = "androidx.compose.ui", name = "ui-test-junit4" }
androidx-material3 = { group = "androidx.compose.material3", name = "material3" }
generativeai = { group = "com.google.ai.client.generativeai", name = "generativeai", version.ref = "generativeai" }
androidx-navigation-compose = { group = "androidx.navigation", name = "navigation-compose", version.ref = "navigationCompose" }
hilt-android = { module = "com.google.dagger:hilt-android", version.ref = "hiltAndroid" }
hilt-android-compiler = { module = "com.google.dagger:hilt-android-compiler", version.ref = "hiltAndroid" }
androidx-hilt-compiler = { module = "androidx.hilt:hilt-compiler", version.ref = "hiltCompiler" }
androidx-hilt-navigation-compose = { module = "androidx.hilt:hilt-navigation-compose", version.ref = "hiltCompiler" }


[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
kotlin-compose = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
google-android-libraries-mapsplatform-secrets-gradle-plugin = { id = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin", version.ref = "googleAndroidLibrariesMapsplatformSecretsGradlePlugin" }
hilt-android = { id = "com.google.dagger.hilt.android", version.ref = "hiltAndroid" }
jvm = { id = "org.jetbrains.kotlin.jvm", version.ref = "kotlin" }
ksp = { id = "com.google.devtools.ksp", version.ref = "ksp" }
```
</br>

Add the dependency to your `build.gradle.kts` file:

```kotlin
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.aiphotohunter"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.aiphotohunter"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    val localProperties = gradleLocalProperties(rootDir, providers)
    val apiKey: String = localProperties.getProperty("API_KEY") ?: ""

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "API_KEY", "\"$apiKey\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.generativeai)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.generativeai)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    ksp(libs.androidx.hilt.compiler)
    implementation (libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.material.icons.extended)
}

```

</br>

## Google AI API Key

Google AI Studio is the fastest way to start building with Gemini, our next generation family of multimodal generative AI models. You can get api key from here: https://aistudio.google.com/apikey

You can follow these photos for applying the key to your project:

<p align="center" width="100%">
    <img width="100%" src="https://github.com/user-attachments/assets/a6df9498-67ff-49c7-9e45-f3880d7d69c8">

</p>
<p align="center" width="100%">
    <img width="100%" src="https://github.com/user-attachments/assets/22fc2c51-0be7-4cce-859a-4fe3ef0df456"> 
</p>

# Acknowledegments
<a href="https://aistudio.google.com/apikey">Google AI Studio</a> for providing AI prompt key
Jetpack Compose for the modern UI toolkit.

# Contact
For questions or suggestions, feel free to reach out:

<li>Email: <a href="mailto:kaaneneskpc1@gmail.com">Kaan Enes KAPICI</a></li>
<li>GitHub: <a href="https://www.github.com/kaaneneskpc">kaaneneskpc</a></li>
