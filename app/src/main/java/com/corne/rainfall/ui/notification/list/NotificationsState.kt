package com.corne.rainfall.ui.notification.list

import com.corne.rainfall.data.model.AlertModel
import com.corne.rainfall.ui.base.state.IBaseState
import dev.shreyaspatil.mutekt.core.annotations.GenerateMutableModel
import javax.annotation.concurrent.Immutable

@GenerateMutableModel
@Immutable
interface NotificationsState : IBaseState {
    val isLoading: Boolean
    val error: Int?
    val items: List<AlertModel>

    companion object {
        val initialState = NotificationsState(
            isLoading = false,
            error = null,
            items = emptyList()
        )
    }


}
