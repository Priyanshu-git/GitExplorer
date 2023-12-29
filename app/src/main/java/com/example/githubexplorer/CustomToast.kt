package com.example.githubexplorer

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.widget.PopupWindow
import com.example.githubexplorer.databinding.ToastLayoutBinding
import com.example.githubexplorer.utils.AppUtility.Companion.toDp


class CustomToast {
    companion object {
        private lateinit var binding: ToastLayoutBinding
        private lateinit var toastWindow: PopupWindow
        private var context: Context? = null

        private const val ANIM_FADE_IN_DURATION = 500
        private const val ANIM_FADE_OUT_DURATION = 800
        private const val ANIM_SLIDE_IN_DURATION = 300
        private const val ANIM_SLIDE_OUT_DURATION = 800

        private const val TOAST_BOTTOM_MARGIN = 48

        const val LENGTH_LONG = 3500
        const val LENGTH_SHORT = 3000

        fun initialize(context: Context) {
            this.context = context
        }

        fun show(message: String, msDuration: Int = LENGTH_SHORT) {
            if (context != null) {
                val inflater: LayoutInflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                binding = ToastLayoutBinding.inflate(inflater)

                binding.tvText.text = message
                toastWindow = PopupWindow(
                    binding.root,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    true
                )
                toastWindow.isOutsideTouchable = true

                setupEnterAnimation()
                toastWindow.showAtLocation(
                    binding.root,
                    Gravity.BOTTOM,
                    0,
                    TOAST_BOTTOM_MARGIN.toDp().toInt()
                )

                binding.root.postDelayed({
                    setUpExitAnimation()
                }, msDuration.toLong())
            } else {
                throw IllegalStateException("Null context. initialize() must be called before show()")
            }
        }

        private fun setUpExitAnimation() {
            val fadeOut = AlphaAnimation(1f, 0f)
            fadeOut.duration = ANIM_FADE_OUT_DURATION.toLong()

            val slideOut = TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0f,
                TranslateAnimation.RELATIVE_TO_SELF, 1f
            )
            slideOut.duration = ANIM_SLIDE_OUT_DURATION.toLong()

            val exitAnimationSet = AnimationSet(true)
            exitAnimationSet.addAnimation(fadeOut)
//            exitAnimationSet.addAnimation(slideOut)

            binding.llRoot.startAnimation(exitAnimationSet)
            exitAnimationSet.setAnimationListener(object : AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    toastWindow.dismiss()
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
        }

        private fun setupEnterAnimation() {
            val fadeIn = AlphaAnimation(0f, 1f)
            fadeIn.duration = ANIM_FADE_IN_DURATION.toLong()

            val slideIn = TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0f,
                TranslateAnimation.RELATIVE_TO_SELF, 1f,
                TranslateAnimation.RELATIVE_TO_SELF, 0f
            )
            slideIn.duration = ANIM_SLIDE_IN_DURATION.toLong()

            val animationSet = AnimationSet(true)
            animationSet.addAnimation(fadeIn)
//            animationSet.addAnimation(slideIn)

            binding.llRoot.startAnimation(animationSet)
        }
    }
}