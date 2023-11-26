package com.corne.rainfall.data.model

import com.google.gson.annotations.SerializedName

/**
 * This class represents the main response model for the weather alerts API.
 * It contains a data class AlertsMain which encapsulates the Alert data class.
 */
class AlertResponseModel {

    /**
     * This data class represents the main structure of the alerts in the API response.
     * It contains a single property alertsMain which is an instance of the Alert data class.
     *
     * @property alertsMain An instance of the Alert data class.
     */
    data class AlertsMain(
        @SerializedName("alerts") var alertsMain: Alert? = Alert(),
    )

    /**
     * This data class represents the structure of an alert in the API response.
     * It contains a single property alert which is a list of AlertModel instances.
     *
     * @property alert A list of AlertModel instances.
     */
    data class Alert(
        @SerializedName("alert" ) var alert : ArrayList<AlertModel> = arrayListOf()
    )
}