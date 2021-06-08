package com.example.hitungbmi.ui

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.hitungbmi.R
import com.example.hitungbmi.data.KategoriBmi
import com.example.hitungbmi.databinding.FragmentHitungBinding

class HitungFragment : Fragment() {

    private lateinit var binding: FragmentHitungBinding
    private lateinit var kategoriBmi: KategoriBmi

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentHitungBinding.inflate(layoutInflater, container, false)
        binding.button.setOnClickListener{
            hitungBmi()
        }
        binding.saranButton.setOnClickListener { view: View ->
            view.findNavController().navigate(HitungFragmentDirections.
            actionHitungFragmentToSaranFragment(kategoriBmi))
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_about) { findNavController()
            .navigate( R.id.aboutFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun hitungBmi() {

        val berat = binding.beratEditText.text.toString()
        if (TextUtils.isEmpty(berat)) {
            Toast.makeText(context, R.string.berat_invalid,
                Toast.LENGTH_LONG).show()
            return
        }

        val tinggi = binding.tinggiEditText.text.toString()
        if (TextUtils.isEmpty(tinggi)) {
            Toast.makeText(context, R.string.tinggi_invalid,
                Toast.LENGTH_LONG).show()
            return
        }

        val tinggiCm = tinggi.toFloat() / 100

        val selectedId = binding.radioGroup.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(context, R.string.gender_invalid,
                Toast.LENGTH_LONG).show()
            return
        }
        val isMale = selectedId == R.id.priaRadioButton
        val bmi = berat.toFloat() / (tinggiCm * tinggiCm)
        val kategori = getKategori(bmi, isMale)

        binding.bmiTextView.text = getString(R.string.bmi_x, bmi)
        binding.kategoriTextView.text = getString(R.string.kategori_x, kategori)
        binding.saranButton.visibility = View.VISIBLE
    }

    private fun getKategori(bmi: Float, isMale: Boolean): String {
        //val stringRes = if (isMale) {
        kategoriBmi = if (isMale) {
            when {
                //bmi < 20.5 -> R.string.kurus
                //bmi >= 27.0 -> R.string.gemuk
                //else -> R.string.ideal
                bmi < 20.5 -> KategoriBmi.KURUS
                bmi >= 27.0 -> KategoriBmi.GEMUK
                else -> KategoriBmi.IDEAL
            }
        }
        else {
            when {
                //bmi < 18.5 -> R.string.kurus
                //bmi >= 25.0 -> R.string.gemuk
                //else -> R.string.ideal
                bmi < 18.5 -> KategoriBmi.KURUS
                bmi >= 25.0 -> KategoriBmi.GEMUK
                else -> KategoriBmi.IDEAL
            }
        }

        val stringRes =
            when (kategoriBmi) {
                KategoriBmi.KURUS -> R.string.kurus
                KategoriBmi.IDEAL -> R.string.ideal
                KategoriBmi.GEMUK -> R.string.gemuk
            }

        return getString(stringRes)
    }

}

private fun NavController.navigate(actionHitungFragmentToSaranFragment: Unit) {

}

private fun Any.actionHitungFragmentToSaranFragment(kategoriBmi: KategoriBmi){

}
