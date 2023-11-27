package com.corne.rainfall.ui.account.register

import androidx.lifecycle.viewModelScope
import com.corne.rainfall.ui.account.login.AuthException
import com.corne.rainfall.ui.account.login.ILoginState
import com.corne.rainfall.ui.account.login.MutableILoginState
import com.corne.rainfall.ui.account.login.mutable
import com.corne.rainfall.ui.base.form.FormItem
import com.corne.rainfall.ui.base.form.IKey
import com.corne.rainfall.ui.base.state.BaseStateViewModel
import com.corne.rainfall.ui.rainfall.capture.CaptureForm
import com.corne.rainfall.utils.DataValidator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RegisterViewModel @Inject constructor() : BaseStateViewModel<IRegisterState>(){
    private val stateStore = IRegisterState.initialState.mutable()
    override val state: StateFlow<IRegisterState> = stateStore.asStateFlow()
    private val auth = FirebaseAuth.getInstance()
    private var currentJob: Job? = null

    //The below register code and the related login code was derived from firebase
    //https://firebase.google.com/docs/auth
    fun registerUser() {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            try {
                val result = auth.createUserWithEmailAndPassword(state.value.formValues[RegisterForm.EMAIL]!!.getValue()!!, state.value.formValues[RegisterForm.PASSWORD]!!.getValue()!!).await()
                result.user?.let {
                    setState { success = true }
                    Result.success(it)
                }
                    ?: Result.failure(AuthException("Login failed: Signed out"))
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                Result.failure(AuthException("Create user failed: Invalid credentials"))
            } catch (e: Exception) {
                Result.failure(AuthException("Create user failed: ${e.message}"))
            }
        }
    }

    fun updateState(key: IKey, value: String) {
        setState {
            formValues = formValues.toMutableMap().apply {
                val oldFormItem = get(key)
                val newFormItem = oldFormItem!!.setValue(value)
                put(key, newFormItem)
            }
        }
    }

    fun setUpForm() {
        state.value.formValues.apply {
            put(RegisterForm.EMAIL,       FormItem(validationTest = DataValidator::emailConfirmation))
            put(RegisterForm.PASSWORD, FormItem(validationTest = DataValidator::passwordValidation))
        put(RegisterForm.CONFIRM_PASSWORD,   FormItem(validationTest = { DataValidator.confirmPasswordValidation( it, state.value.formValues[RegisterForm.PASSWORD]!!.getValue()!!) } ))
        }
    }

    fun isFormValid(): Boolean {
        return state.value.formValues.all { it.value.isValid }
    }

    private fun setState(update: MutableIRegisterState.() -> Unit) = stateStore.update(update)

}

class AuthException(message: String) : Exception(message)

