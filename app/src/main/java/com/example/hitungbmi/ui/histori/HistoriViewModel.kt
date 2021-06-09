package com.example.hitungbmi.ui.histori

import androidx.lifecycle.ViewModel
import com.example.hitungbmi.db.BmiDao

class HistoriViewModel(db: BmiDao) : ViewModel() {
    val data = db.getLastBmi()
}