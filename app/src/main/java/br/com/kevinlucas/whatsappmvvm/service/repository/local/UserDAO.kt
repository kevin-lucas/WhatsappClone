package br.com.kevinlucas.whatsappmvvm.service.repository.local

interface UserDAO {

    fun save(id: String, email: String, password: String): Boolean

}