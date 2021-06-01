package br.com.kevinlucas.whatsappmvvm.service.listener

interface APIListener<T> {

    fun onSuccess(validation: T)

    fun onFailure(str: String)

}