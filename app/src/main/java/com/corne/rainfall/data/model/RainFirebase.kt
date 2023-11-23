package com.corne.rainfall.data.model


class User {
    val prefs: PrefModel? = null
    val locations: List<FbLocationModel>? = null
}

class FbLocationModel(
    val locationUID: Int,
    val name: String,
    val rainfallData: List<RainfallData>? = null,
)