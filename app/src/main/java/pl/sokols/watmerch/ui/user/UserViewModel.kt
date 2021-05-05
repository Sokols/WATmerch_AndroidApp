package pl.sokols.watmerch.ui.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import pl.sokols.watmerch.data.model.User
import pl.sokols.watmerch.data.model.UserDetails
import pl.sokols.watmerch.data.repository.UserRepository
import pl.sokols.watmerch.utils.AppPreferences
import pl.sokols.watmerch.utils.Resource
import pl.sokols.watmerch.utils.Utils
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository,
    private val prefs: AppPreferences
) : ViewModel() {

    var isEditable: Boolean = true
    lateinit var user: User

    var firstName: String = ""
    var lastName: String = ""
    var phoneNumber: String = ""
    var avatar: String? = null
    var birthDate: String? = null
    var company: String? = null
    var nip: String? = null

    fun getUser() = liveData(Dispatchers.Main) {
        emit(Resource.loading(data = null))
        try {
            user = repository.loginUser(
                User(
                    username = prefs.userUsername.toString(),
                    password = prefs.userPassword.toString()
                )
            )
            emit(Resource.success(data = user))
            setUserData()
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            Log.d("ERROR", exception.message.toString())
        }
    }

    fun updateUserData() = liveData(Dispatchers.Main) {
        emit(Resource.loading(data = null))
        try {
            if (checkRequiredData()) {
                val userDetails = UserDetails(
                    id = user.userDetails!!.id,
                    avatar = avatar,
                    birthDate = birthDate,
                    company = company,
                    firstName = firstName,
                    lastName = lastName,
                    nip = nip,
                    phoneNumber = phoneNumber
                )
                emit(Resource.success(data = repository.editUserDetails(userDetails)))
            } else {
                emit(Resource.error(data = null, message = "Uzupełnij wszystkie wymagane pola!"))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            Log.d("ERROR", exception.message.toString())
        }
    }

    fun updateEditable() {
        isEditable = !isEditable
    }

    private fun setUserData() {
        val userDetails = user.userDetails
        if (userDetails != null) {
            firstName = userDetails.firstName
            lastName = userDetails.lastName
            phoneNumber = userDetails.phoneNumber
            avatar = userDetails.avatar
            birthDate = Utils.getDateStringFromString(userDetails.birthDate)
            company = userDetails.company
            nip = userDetails.nip
        }
    }

    private fun checkRequiredData(): Boolean =
        firstName.isNotEmpty() && lastName.isNotEmpty() && phoneNumber.isNotEmpty()
}
