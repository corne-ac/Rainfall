package com.corne.rainfall.ui.account.login

import androidx.lifecycle.viewModelScope
import com.corne.rainfall.R
import com.corne.rainfall.ui.account.register.RegisterForm
import com.corne.rainfall.ui.base.form.FormItem
import com.corne.rainfall.ui.base.form.IKey
import com.corne.rainfall.ui.base.state.BaseStateViewModel
import com.corne.rainfall.ui.home.IHomeState
import com.corne.rainfall.ui.home.MutableIHomeState
import com.corne.rainfall.ui.home.mutable
import com.corne.rainfall.utils.DataValidator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseStateViewModel<ILoginState>(){
    private val stateStore = ILoginState.initialState.mutable()
    override val state: StateFlow<ILoginState> = stateStore.asStateFlow()
    private val auth = FirebaseAuth.getInstance()
    private var currentJob: Job? = null

    //The below login code and the related register code was derived from firebase
    //https://firebase.google.com/docs/auth

    fun signIn() {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            try {
                val result = auth.signInWithEmailAndPassword(
                    state.value.formValues[LoginForm.EMAIL]!!.getValue()!!,
                    state.value.formValues[LoginForm.PASSWORD]!!.getValue()!!
                ).await()
                result.user?.let {
                    setState { success = true }
                    Result.success(it)
                }
                    ?: Result.failure(AuthException("Login failed: Signed out"))
            } catch (e: FirebaseAuthInvalidUserException) {
                setState { error = R.string.login_error_not_found }
                Result.failure(AuthException("Login failed: User not found"))
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                setState { error = R.string.login_error_invalid_creds }
                Result.failure(AuthException("Login failed: Invalid credentials"))
            } catch (e: Exception) {
                setState { error = R.string.login_error}
                Result.failure(AuthException("Login failed: ${e.message}"))
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
            put(LoginForm.EMAIL,    FormItem(validationTest = DataValidator::emailConfirmation))
            put(LoginForm.PASSWORD, FormItem(validationTest = DataValidator::passwordValidation))
        }
    }

    fun isFormValid(): Boolean {
        return state.value.formValues.all { it.value.isValid }
    }

    private fun setState(update: MutableILoginState.() -> Unit) = stateStore.update(update)
}

class AuthException(message: String) : Exception(message)