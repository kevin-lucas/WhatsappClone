package br.com.kevinlucas.whatsappmvvm.service.repository.remote

interface PersonService {

    fun create(name: String, email: String, password: String) : Boolean

}