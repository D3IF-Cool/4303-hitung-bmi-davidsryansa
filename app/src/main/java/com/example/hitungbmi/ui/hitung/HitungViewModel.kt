package com.example.hitungbmi.ui.hitung

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.example.hitungbmi.data.HasilBmi
import com.example.hitungbmi.data.KategoriBmi
import java.util.function.ToDoubleBiFunction
import com.example.hitungbmi.databinding.FragmentHitungBinding
import com.example.hitungbmi.db.BmiDao
import com.example.hitungbmi.db.BmiEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//class HitungViewModel : ViewModel() {
class HitungViewModel(private val db: BmiDao) : ViewModel() {
    fun mulaiNavigasi(){
        navigasi.value = hasilBmi.value?.kategori
    }

    fun selesaiNavigasi(){
        navigasi.value=null
    }

    fun getHasilBmi() : LiveData<HasilBmi?> = hasilBmi
    fun getNavigasi() : LiveData<KategoriBmi?> = navigasi


    // Hasil BMI bisa null jika pengguna belum menghitung BMI//
    private val hasilBmi = MutableLiveData<HasilBmi?>()

    // Navigasi akan bernilai null ketika tidak bernavigasi//
    private val navigasi = MutableLiveData<KategoriBmi?>()

        // Variabel ini sudah berupa LiveData (tidak mutable),
        // sehingga tidak perlu dijadikan private
        val data = db.getLastBmi()

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

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val dataBmi = BmiEntity(
                    berat = berat.toFloat(),
                    tinggi = tinggi.toFloat(),
                    isMale = isMale )
                db.insert(dataBmi)
            }
        }
    }
}