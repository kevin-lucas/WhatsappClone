package br.com.kevinlucas.whatsappmvvm.service.repository.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object FirebaseClient {

    fun getFirebaseInstance(): DatabaseReference = FirebaseDatabase.getInstance().reference

    fun getFirebaseInstanceAuth(): FirebaseAuth = FirebaseAuth.getInstance()
}