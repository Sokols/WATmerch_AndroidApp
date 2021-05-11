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

    init {
        setUser()
    }

    lateinit var user: User

    var firstName: ObservableField<String> = ObservableField("")
    var lastName: ObservableField<String> = ObservableField("")
    var phoneNumber: ObservableField<String> = ObservableField("")
    var country: ObservableField<String> = ObservableField("")
    var street: ObservableField<String> = ObservableField("")
    var postalCode: ObservableField<String> = ObservableField("")
    var city: ObservableField<String> = ObservableField("")

    fun provideAddressData() = liveData(Dispatchers.Main) {
        emit(Resource.loading(data = null))
        try {
            if (checkRequiredData()) {
                val address = Address(
                    firstName = firstName.get().toString(),
                    lastName = lastName.get().toString(),
                    phoneNumber = phoneNumber.get().toString(),
                    country = country.get().toString(),
                    street = street.get().toString(),
                    postalCode = postalCode.get().toString(),
                    city = city.get().toString()
                )
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
            val userDetails = user.userDetails
            if (userDetails != null) {
                firstName.set(userDetails.firstName)
                lastName.set(userDetails.lastName)
                phoneNumber.set(userDetails.phoneNumber)
            }
        } else {
            firstName.set("")
            lastName.set("")
            phoneNumber.set("")
        }
    }

    private fun setUser() = viewModelScope.launch {
        user = userRepository.loginUser(
            User(
                username = prefs.userUsername.toString(),
                password = prefs.userPassword.toString()
            )
        )
    }

    private fun checkRequiredData(): Boolean =
        firstName.get().toString().isNotEmpty() && lastName.get().toString().isNotEmpty()
                && phoneNumber.get().toString().isNotEmpty()
                && country.get().toString().isNotEmpty() && street.get().toString().isNotEmpty()
                && postalCode.get().toString().isNotEmpty() && city.get().toString().isNotEmpty()
}