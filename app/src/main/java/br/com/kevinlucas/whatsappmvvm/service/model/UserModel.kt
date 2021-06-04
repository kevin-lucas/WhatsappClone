package br.com.kevinlucas.whatsappmvvm.service.model

import com.google.firebase.database.Exclude

data class UserModel(@Exclude var id: String? = null, var name: String? = null, var email: String? = null)