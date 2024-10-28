package com.example.praktikum1n

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MatkulsViewModel(private val dao: matkulDAO) : ViewModel() {

    fun AddData(nama: String, tugas: String, isdone: Boolean ){
        viewModelScope.launch {
            val matkul = Matkuls(nama, tugas, isdone)
            dao.addMatkuls(matkul)
        }
    }

    fun getAll() : Flow<List<Matkuls>>{
        return dao.getMatkuls()
    }
}