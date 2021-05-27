package br.com.kevinlucas.whatsappmvvm

interface APIListener<T> {

    fun onSucess(model: T)

    fun onFailure(str: String)

}