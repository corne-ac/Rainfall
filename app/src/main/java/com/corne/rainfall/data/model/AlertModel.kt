package com.corne.rainfall.data.model

import com.google.gson.annotations.SerializedName

/**
 * This data class represents an Alert model.
 * It contains various properties related to a weather alert.
 * Each property is annotated with @SerializedName to map the JSON keys with the data class fields.
 */
data class AlertModel(
    /**
     * The headline of the alert.
     */
    @SerializedName("headline") var headline: String? = null,

    /**
     * The type of the message.
     */
    @SerializedName("msgtype") var msgtype: String? = null,

    /**
     * The severity of the alert.
     */
    @SerializedName("severity") var severity: String? = null,

    /**
     * The urgency of the alert.
     */
    @SerializedName("urgency") var urgency: String? = null,

    /**
     * The areas affected by the alert.
     */
    @SerializedName("areas") var areas: String? = null,

    /**
     * The category of the alert.
     */
    @SerializedName("category") var category: String? = null,

    /**
     * The certainty of the alert.
     */
    @SerializedName("certainty") var certainty: String? = null,

    /**
     * The event causing the alert.
     */
    @SerializedName("event") var event: String? = null,

    /**
     * Any additional notes regarding the alert.
     */
    @SerializedName("note") var note: String? = null,

    /**
     * The effective date and time of the alert.
     */
    @SerializedName("effective") var effective: String? = null,

    /**
     * The expiry date and time of the alert.
     */
    @SerializedName("expires") var expires: String? = null,

    /**
     * The description of the alert.
     */
    @SerializedName("desc") var desc: String? = null,

    /**
     * Any instructions related to the alert.
     */
    @SerializedName("instruction") var instruction: String? = null,
)