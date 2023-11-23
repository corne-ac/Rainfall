package com.corne.rainfall.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseHelper {

    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val currentUserId: String?
        get() = auth.currentUser?.uid

    val currentUserDocRef: DocumentReference
        get() = firestore.collection("Users")
            .document(currentUserId ?: throw NullPointerException("UID is null"))

    val currentUserLocationDocRef: CollectionReference
        get() = currentUserDocRef.collection("Locations")

    val currentUserRainDataDocRef: CollectionReference
        get() = currentUserDocRef.collection("RainData")


    val currentUserPreferencesDocRef: DocumentReference
        get() = currentUserDocRef.collection("Preferences")
            .document("Preferences")

    fun saveLocationsToFirebase() {

    }

}