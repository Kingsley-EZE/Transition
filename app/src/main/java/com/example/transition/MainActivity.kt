package com.example.transition

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.transition.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var mStaggeredTransition : TransitionSet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Create custom transition that 'staggers' the animations by offsetting
        // the individual start times

        mStaggeredTransition = TransitionSet()
        val first = ChangeBounds()
        val second = ChangeBounds()
        val third = ChangeBounds()
        val fourth = ChangeBounds()

        first.addTarget(binding.firstButton)
        second.startDelay = 50
        second.addTarget(binding.secondButton)
        third.startDelay = 100
        third.addTarget(binding.thirdButton)
        fourth.startDelay = 150
        fourth.addTarget(binding.fourthButton)

        mStaggeredTransition.addTransition(first).addTransition(second)
            .addTransition(third).addTransition(fourth)


    }

        private fun alignButtons(left: Boolean, top: Boolean){

            var params : RelativeLayout.LayoutParams

            // Trigger a transition to run after the next layout pass

            if (binding.staggerCB.isChecked){
                TransitionManager.beginDelayedTransition(binding.root, mStaggeredTransition)
            }else{
                TransitionManager.beginDelayedTransition(binding.root)
            }

            // Change layout parameters of the button stack


            // Change layout parameters of the button stack
            val oldAlignmentLR = if (left) RelativeLayout.ALIGN_PARENT_RIGHT else RelativeLayout.ALIGN_PARENT_LEFT
            val newAlignmentLR = if (left) RelativeLayout.ALIGN_PARENT_LEFT else RelativeLayout.ALIGN_PARENT_RIGHT
            val oldAlignmentTB = if (top) RelativeLayout.ABOVE else RelativeLayout.BELOW
            val newAlignmentTB = if (top) RelativeLayout.BELOW else RelativeLayout.ABOVE

            val mFirstButton : AppCompatButton = findViewById(R.id.firstButton)
            params = mFirstButton.layoutParams as RelativeLayout.LayoutParams
            params.addRule(if (top) RelativeLayout.ALIGN_PARENT_BOTTOM else RelativeLayout.BELOW, 0)
            params.addRule(oldAlignmentLR, 0)
            params.addRule(
                if (top) RelativeLayout.BELOW else RelativeLayout.ALIGN_PARENT_BOTTOM,
                if (top) R.id.staggerCB else 1
            )
            params.addRule(newAlignmentLR)
            mFirstButton.layoutParams = params

            params = binding.secondButton.layoutParams as RelativeLayout.LayoutParams
            params.addRule(oldAlignmentLR, 0)
            params.addRule(oldAlignmentTB, 0)
            params.addRule(newAlignmentLR)
            params.addRule(newAlignmentTB, R.id.firstButton)
            binding.secondButton.layoutParams = params

            params = binding.thirdButton.layoutParams as RelativeLayout.LayoutParams
            params.addRule(oldAlignmentLR, 0)
            params.addRule(oldAlignmentTB, 0)
            params.addRule(newAlignmentLR)
            params.addRule(newAlignmentTB, R.id.secondButton)
            binding.thirdButton.layoutParams = params

            params = binding.fourthButton.layoutParams as RelativeLayout.LayoutParams
            params.addRule(oldAlignmentLR, 0)
            params.addRule(oldAlignmentTB, 0)
            params.addRule(newAlignmentLR)
            params.addRule(newAlignmentTB, R.id.thirdButton)
            binding.fourthButton.layoutParams = params



        }



    fun onClick(view: View) {

        when ((view as Button).text.toString()) {
            "Top Left" -> alignButtons(true, true)
            "Top Right" -> alignButtons(false, true)
            "Bottom Left" -> alignButtons(true, false)
            "Bottom Right" -> alignButtons(false, false)
        }
    }

    }
