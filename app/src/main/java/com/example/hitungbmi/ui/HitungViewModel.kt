package com.example.hitungbmi.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.hitungbmi.R
import com.example.hitungbmi.data.HasilBmi
import com.example.hitungbmi.data.KategoriBmi
import com.example.hitungbmi.databinding.FragmentHitungBinding

class HitungViewModel : ViewModel() {

    // Hasil BMI bisa null jika pengguna belum menghitung BMI//
    private val hasilBmi = MutableLiveData<HasilBmi?>()

    fun hitungBmi(berat: String, tinggi: String, isMale: Boolean) {
        val tinggiCm = tinggi.toFloat() / 100
        val bmi = berat.toFloat() / (tinggiCm * tinggiCm)
        val kategori = if (isMale) {
            when {
                bmi < 20.5 -> KategoriBmi.KURUS
                bmi >= 27.0 -> KategoriBmi.GEMUK
                else -> KategoriBmi.IDEAL
            }
        }
        else {
            when {
                bmi < 18.5 -> KategoriBmi.KURUS
                bmi >= 25.0 -> KategoriBmi.GEMUK
                else -> KategoriBmi.IDEAL
            }
        }

        hasilBmi.value = HasilBmi(bmi, kategori)
    }
    fun getHasilBmi() : LiveData<HasilBmi?> = hasilBmi
}