package com.corne.rainfall.data.model

import com.google.gson.annotations.SerializedName

class AlertResponseModel {
    data class AlertsMain(
        @SerializedName("alerts") var alertsMain: Alert? = Alert(),
    )

    data class Alert(
        @SerializedName("alert" ) var alert : ArrayList<AlertModel> = arrayListOf()
    )
}