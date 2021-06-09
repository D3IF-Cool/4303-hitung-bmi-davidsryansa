package com.example.hitungbmi.ui.histori


import android.view.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.hitungbmi.R
import com.example.hitungbmi.databinding.FragmentHistoriBinding
import com.example.hitungbmi.db.BmiDb
import com.example.hitungbmi.db.BmiEntity
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class HistoriFragment : Fragment() {

    private val viewModel: HistoriViewModel by lazy {
        val db = BmiDb.getInstance(requireContext())
        val factory = HistoriViewModelFactory(db.dao)
        ViewModelProvider(this, factory).get(HistoriViewModel::class.java)
    }

    private lateinit var binding: FragmentHistoriBinding
    private lateinit var myAdapter: HistoriAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentHistoriBinding.inflate(layoutInflater,
            container, false)
        myAdapter = HistoriAdapter()
        with(binding.recyclerView) {
            addItemDecoration(
                DividerItemDecoration(context,
                RecyclerView.VERTICAL)
            )
            adapter = myAdapter
            setHasFixedSize(true)
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.data.observe(viewLifecycleOwner, {
            //Log.d("HistoriFragment", "Jumlah data: ${it.size}")
            binding.emptyView.visibility = if (it.isEmpty())
                    View.VISIBLE else View.GONE
            myAdapter.updateData(it as List<BmiEntity>)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.histori_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_hapus) { hapusData()
            return true
        }
        return super.onOptionsItemSelected(item)
    } private fun hapusData() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.konfirmasi_hapus)
            .setPositiveButton(getString(R.string.hapus)) { _, _ ->
                viewModel.hapusData() }
            .setNegativeButton(getString(R.string.batal)) {dialog, _ ->
                dialog.cancel()
            } .show()
    }
}