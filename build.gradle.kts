// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.4.0" apply false
    id("com.android.library") version "7.4.0" apply false
    kotlin("android") version "1.8.20-Beta" apply false
    kotlin("plugin.serialization") version "1.8.0"
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id ("androidx.navigation.safeargs.kotlin") version "2.5.3" apply false
}