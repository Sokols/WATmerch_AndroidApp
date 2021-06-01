package pl.sokols.watmerch.utils

import android.transition.Fade
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup

class TransitionUtils {
    companion object {
        fun fade(view: View) {
            val transition = Fade()
            transition.duration = 500
            transition.addTarget(view)
            TransitionManager.beginDelayedTransition(view.parent as ViewGroup, transition)
            view.visibility = View.VISIBLE
        }
    }
}