package io.raveerocks.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.raveerocks.marsrealestate.network.MarsApi
import io.raveerocks.marsrealestate.network.MarsApiFilter
import io.raveerocks.marsrealestate.network.MarsProperty
import kotlinx.coroutines.launch

enum class MarsApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel : ViewModel() {

    private val _status = MutableLiveData<MarsApiStatus>()
    private val _properties = MutableLiveData<List<MarsProperty>>()

    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty?>()

    val status: LiveData<MarsApiStatus>
        get() = _status
    val properties: LiveData<List<MarsProperty>>
        get() = _properties

    val navigateToSelectedProperty: LiveData<MarsProperty?>
        get() = _navigateToSelectedProperty

    init {
        getMarsRealEstateProperties(MarsApiFilter.SHOW_ALL)
    }

    private fun getMarsRealEstateProperties(filter: MarsApiFilter) {
        viewModelScope.launch {
            try {
                _status.value = MarsApiStatus.LOADING
                _properties.value = MarsApi.retrofitService.getPropertiesAsync(filter.value)
                _status.value = MarsApiStatus.DONE
            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

    fun updateFilter(filter: MarsApiFilter) {
        getMarsRealEstateProperties(filter)
    }

    fun displayPropertyDetails(marsProperty: MarsProperty) {
        _navigateToSelectedProperty.value = marsProperty
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }


}
