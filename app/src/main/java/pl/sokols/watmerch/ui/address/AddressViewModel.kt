package pl.sokols.watmerch.ui.address

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.sokols.watmerch.data.model.Address
import pl.sokols.watmerch.data.model.User
import pl.sokols.watmerch.data.repository.UserRepository
import pl.sokols.watmerch.utils.AppPreferences
import pl.sokols.watmerch.utils.Resource
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val prefs: AppPreferences
) : ViewModel() {

    var user: User? = null

    var address: ObservableField<Address> = ObservableField()

    fun provideAddressData(isAllDataProvided: Boolean) = liveData(Dispatchers.Main) {
        emit(Resource.loading(data = null))
        try {
            if (isAllDataProvided) {
                val address = address.get()
                emit(Resource.success(data = address))
            } else {
                emit(Resource.error(data = null, message = "Uzupełnij wszystkie wymagane pola!"))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            Log.d("ERROR", exception.message.toString())
        }
    }

    fun setUserData(isDefaultData: Boolean) {
        if (isDefaultData) {
            setUserData()
        } else {
            address.set(null)
        }
    }

    private fun setUserData() = viewModelScope.launch {
        if (user == null) {
            user = userRepository.loginUser(
                User(
                    username = prefs.userUsername.toString(),
                    password = prefs.userPassword.toString()
                )
            )
        }
        address.set(user!!.shippingAddress)
    }
}