package com.corne.rainfall.ui.base.form

class FormItem(
    private val valueStored: String? = null,
    private val validationTest: ((String?) -> Int?)? = null,
) {
    var isValid: Boolean = false
        private set
    var errorMessage: Int? = null
        get

    private fun validate() {
        isValid = valueStored != null && validationTest?.invoke(valueStored) == null
        errorMessage = if (!isValid) validationTest?.invoke(valueStored) else null
    }

    fun setValue(value: String): FormItem {
        return FormItem(value, validationTest).also(FormItem::validate)
    }


}
