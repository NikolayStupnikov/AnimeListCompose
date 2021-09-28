package ru.nikolay.stupnikov.animelistcompose.util

import androidx.activity.ComponentActivity
import androidx.test.ext.junit.rules.ActivityScenarioRule

fun <A : ComponentActivity> ActivityScenarioRule<A>.getActivity(): A {
    var activity: A? = null
    scenario.onActivity { activity = it }
    if (activity == null) {
        throw IllegalStateException("Activity was not set in the ActivityScenarioRule!")
    }
    return activity!!
}