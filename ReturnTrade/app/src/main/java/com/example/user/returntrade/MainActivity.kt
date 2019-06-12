package com.example.user.returntrade

import android.graphics.drawable.AnimationDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import com.example.user.returntrade.R.drawable.pot_animation
import com.example.user.returntrade.R.id.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.button_item.*
import kotlinx.android.synthetic.main.circle_small.*
import kotlinx.android.synthetic.main.circle_small.view.*
import kotlinx.android.synthetic.main.circle_xsmall.*
import kotlinx.android.synthetic.main.circle_xsmall.view.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val companys =
        listOf(R.drawable.doodleblue, R.drawable.sbi, R.drawable.swiggy, R.drawable.vivo, R.drawable.himalaya)
    private var activeCompany = 0
    private var activePot = 0
    private var hits = 3
    private var first_click = true

    private var hammerX = 0.0F
    private var hammerY = 0.0F

    private lateinit var rocketAnimation: AnimationDrawable

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playImage?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (first_click) {
                hammerX = hammer.x
                hammerY = hammer.y
                first_click = false
            }
            if (isChecked) {
                gameOver()
            } else {
                gameStart()
            }
        }
    }

    private fun gameStart() {
        start_uderbar.setBackgroundResource(R.drawable.button_item_black);
        start_uderbar.text = "PAUSE"
        setCompany()
        circle_company.visibility = View.VISIBLE
        circle_find.visibility = View.VISIBLE
        circle_find.setImageResource(R.drawable.circle_gray)
        breakPots()
    }

    private fun gameOver() {
        start_uderbar.setBackgroundResource(R.drawable.button_item_green);
        start_uderbar.text = "PLAY"
        playImage.isChecked = true
        removePot()
        circle_company.visibility = View.GONE
        circle_find.visibility = View.GONE
        hits = 3
        hits_count.text = "${hits}"
        originalPots()
        hammer.x = hammerX
        hammer.y = hammerY
    }

    private fun setCompany() {
        activeCompany = Random().nextInt(5)
        activePot = Random().nextInt(10) + 1

        circle_logo_large.setImageResource(companys[activeCompany])

        setPot()
    }

    private fun setPot() {
        when (activePot) {
            1 -> {
                circle_small_1.visibility = View.VISIBLE
                circle_small_1.circle_logo_small.setImageResource(companys[activeCompany])
            }
            2 -> {
                circle_small_2.visibility = View.VISIBLE
                circle_small_2.circle_logo_small.setImageResource(companys[activeCompany])
            }
            3 -> {
                circle_small_3.visibility = View.VISIBLE
                circle_small_3.circle_logo_small.setImageResource(companys[activeCompany])
            }
            4 -> {
                circle_small_4.visibility = View.VISIBLE
                circle_small_4.circle_logo_small.setImageResource(companys[activeCompany])
            }
            5 -> {
                circle_small_5.visibility = View.VISIBLE
                circle_small_5.circle_logo_xsmall.setImageResource(companys[activeCompany])
            }
            6 -> {
                circle_small_6.visibility = View.VISIBLE
                circle_small_6.circle_logo_xsmall.setImageResource(companys[activeCompany])
            }
            7 -> {
                circle_small_7.visibility = View.VISIBLE
                circle_small_7.circle_logo_xsmall.setImageResource(companys[activeCompany])
            }
            8 -> {
                circle_small_8.visibility = View.VISIBLE
                circle_small_8.circle_logo_xsmall.setImageResource(companys[activeCompany])
            }
            9 -> {
                circle_small_9.visibility = View.VISIBLE
                circle_small_9.circle_logo_small.setImageResource(companys[activeCompany])
            }
            10 -> {
                circle_small_10.visibility = View.VISIBLE
                circle_small_10.circle_logo_small.setImageResource(companys[activeCompany])
            }
        }
    }

    private fun removePot() {
        circle_small_1.visibility = View.GONE
        circle_small_2.visibility = View.GONE
        circle_small_3.visibility = View.GONE
        circle_small_4.visibility = View.GONE
        circle_small_5.visibility = View.GONE
        circle_small_6.visibility = View.GONE
        circle_small_7.visibility = View.GONE
        circle_small_8.visibility = View.GONE
        circle_small_9.visibility = View.GONE
        circle_small_10.visibility = View.GONE
    }

    private fun breakPots() {

        pot1.setOnClickListener {
            hammer.x = pot1.x
            hammer.y = pot1.y
            pot1.setBackgroundResource(R.drawable.pot_animation)
            val potAnimation = pot1.background as AnimationDrawable
            potAnimation.start()
            hits--
            if (activePot == 1) {
                nonBreakPots()
                circle_find.setImageResource(R.drawable.find_green)
            } else {
                hits_count.text = "${hits}"
            }
            if (isGameOver()) {
                nonBreakPots()
                circle_find.setImageResource(R.drawable.find_fail)
            }
        }

        pot2.setOnClickListener {
            hammer.x = pot2.x
            hammer.y = pot2.y
            pot2.setBackgroundResource(R.drawable.pot_animation)
            val potAnimation = pot2.background as AnimationDrawable
            potAnimation.start()
            hits--
            if (activePot == 2) {
                nonBreakPots()
                circle_find.setImageResource(R.drawable.find_green)
            } else {
                hits_count.text = "${hits}"
            }
            if (isGameOver()) {
                nonBreakPots()
                circle_find.setImageResource(R.drawable.find_fail)
            }
        }
        pot3.setOnClickListener {
            hammer.x = pot3.x
            hammer.y = pot3.y
            pot3.setBackgroundResource(R.drawable.pot_animation)
            val potAnimation = pot3.background as AnimationDrawable
            potAnimation.start()
            hits--
            hits_count.text = "${hits}"
            if (activePot == 3) {
                nonBreakPots()
                circle_find.setImageResource(R.drawable.find_green)
            }
            if (isGameOver()) {
                nonBreakPots()
                circle_find.setImageResource(R.drawable.find_fail)
            }
        }
        pot4.setOnClickListener {
            hammer.x = pot4.x
            hammer.y = pot4.y
            pot4.setBackgroundResource(R.drawable.pot_animation)
            val potAnimation = pot4.background as AnimationDrawable
            potAnimation.start()
            hits--
            hits_count.text = "${hits}"
            if (activePot == 4) {
                nonBreakPots()
                circle_find.setImageResource(R.drawable.find_green)
            }
            if (isGameOver()) {
                nonBreakPots()
                circle_find.setImageResource(R.drawable.find_fail)
            }
        }
        pot5.setOnClickListener {
            hammer.x = pot5.x
            hammer.y = pot5.y
            pot5.setBackgroundResource(R.drawable.pot_animation)
            val potAnimation = pot5.background as AnimationDrawable
            potAnimation.start()
            hits--
            hits_count.text = "${hits}"
            if (activePot == 5) {
                nonBreakPots()
                circle_find.setImageResource(R.drawable.find_green)
            }
            if (isGameOver()) {
                nonBreakPots()
                circle_find.setImageResource(R.drawable.find_fail)
            }
        }
        pot6.setOnClickListener {
            hammer.x = pot6.x
            hammer.y = pot6.y
            pot6.setBackgroundResource(R.drawable.pot_animation)
            val potAnimation = pot6.background as AnimationDrawable
            potAnimation.start()
            hits--
            hits_count.text = "${hits}"
            if (activePot == 6) {
                nonBreakPots()
                circle_find.setImageResource(R.drawable.find_green)
            }
            if (isGameOver()) {
                nonBreakPots()
                circle_find.setImageResource(R.drawable.find_fail)
            }
        }
        pot7.setOnClickListener {
            hammer.x = pot7.x
            hammer.y = pot7.y
            pot7.setBackgroundResource(R.drawable.pot_animation)
            val potAnimation = pot7.background as AnimationDrawable
            potAnimation.start()
            hits--
            hits_count.text = "${hits}"
            if (activePot == 7) {
                nonBreakPots()
                circle_find.setImageResource(R.drawable.find_green)
            }
            if (isGameOver()) {
                nonBreakPots()
                circle_find.setImageResource(R.drawable.find_fail)
            }
        }
        pot8.setOnClickListener {
            hammer.x = pot8.x
            hammer.y = pot8.y
            pot8.setBackgroundResource(R.drawable.pot_animation)
            val potAnimation = pot8.background as AnimationDrawable
            potAnimation.start()
            hits--
            hits_count.text = "${hits}"
            if (activePot == 8) {
                nonBreakPots()
                circle_find.setImageResource(R.drawable.find_green)
            }
            if (isGameOver()) {
                nonBreakPots()
                circle_find.setImageResource(R.drawable.find_fail)
            }
        }
        pot9.setOnClickListener {
            hammer.x = pot9.x
            hammer.y = pot9.y
            pot9.setBackgroundResource(R.drawable.pot_animation)
            val potAnimation = pot9.background as AnimationDrawable
            potAnimation.start()
            hits--
            hits_count.text = "${hits}"
            if (activePot == 9) {
                nonBreakPots()
                circle_find.setImageResource(R.drawable.find_green)
            }
            if (isGameOver()) {
                nonBreakPots()
                circle_find.setImageResource(R.drawable.find_fail)
            }
        }
        pot10.setOnClickListener {
            hammer.x = pot10.x
            hammer.y = pot10.y
            pot10.setBackgroundResource(R.drawable.pot_animation)
            val potAnimation = pot10.background as AnimationDrawable
            potAnimation.start()
            hits--
            hits_count.text = "${hits}"
            if (activePot == 10) {
                nonBreakPots()
                circle_find.setImageResource(R.drawable.find_green)
            }
            if (isGameOver()) {
                nonBreakPots()
                circle_find.setImageResource(R.drawable.find_fail)
            }
        }
    }

    private fun isGameOver(): Boolean {
        return hits < 1
    }

    private fun originalPots() {
        pot1.setBackgroundResource(R.drawable.pot)
        pot2.setBackgroundResource(R.drawable.pot)
        pot3.setBackgroundResource(R.drawable.pot)
        pot4.setBackgroundResource(R.drawable.pot)
        pot5.setBackgroundResource(R.drawable.pot)
        pot6.setBackgroundResource(R.drawable.pot)
        pot7.setBackgroundResource(R.drawable.pot)
        pot8.setBackgroundResource(R.drawable.pot)
        pot9.setBackgroundResource(R.drawable.pot)
        pot10.setBackgroundResource(R.drawable.pot)

        nonBreakPots()
    }

    private fun nonBreakPots() {
        pot1.setOnClickListener(null)
        pot2.setOnClickListener(null)
        pot3.setOnClickListener(null)
        pot4.setOnClickListener(null)
        pot5.setOnClickListener(null)
        pot6.setOnClickListener(null)
        pot7.setOnClickListener(null)
        pot8.setOnClickListener(null)
        pot9.setOnClickListener(null)
        pot10.setOnClickListener(null)
    }
}
