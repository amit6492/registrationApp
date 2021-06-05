package com.example.registrationapp.viewmodel

import androidx.lifecycle.*
import com.example.registrationapp.utility.CombineLiveData

class RegistrationViewModel(lifecycleOwner: LifecycleOwner) : ViewModel() {
    val editTextContent = MutableLiveData<String>()
    val dayContent = MutableLiveData<String>()
    val monthContent = MutableLiveData<String>()
    val yearContet = MutableLiveData<String>()

    val isActive = MutableLiveData<Boolean>()


    val dateRegex = "(^(((0[1-9]|1[0-9]|2[0-8])[\\/](0[1-9]|1[012]))|((29|30|31)[\\/](0[13578]|1[02]" +
            "))|((29|30)[\\/](0[4,6,9]|11)))[\\/](19|[2-9][0-9])\\d\\d\$)|(^29[\\/]02[\\/](19|[2-9]" +
            "[0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)\$)"

    val panRegex = "[A-Z]{5}[0-9]{4}[A-Z]{1}"

    init {


        CombineLiveData.combinesLiveData(dayContent, monthContent, yearContet, editTextContent)
            .observe(lifecycleOwner, Observer {
                data ->
                val newDate = fixDate(data.second.first) + "/" + fixDate(data.second.second) + "/" + data.second.third
                isActive.value = newDate.matches(Regex(dateRegex)) && data.first
                    .matches(Regex(panRegex))
            })
    }

    fun fixDate(date: String): String{
        var newDate = date
        if (date.length == 1){
            newDate = "0$date"
        }
        return newDate
    }


    class Factory(private val lifecycleOwner: LifecycleOwner): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RegistrationViewModel(lifecycleOwner) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }


}