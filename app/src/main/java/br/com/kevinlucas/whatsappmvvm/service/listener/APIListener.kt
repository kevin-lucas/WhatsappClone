package br.com.kevinlucas.whatsappmvvm.service.listener

interface APIListener<T> {

    fun onSucess(model: T)

    fun onFailure(str: String)

}