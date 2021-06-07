package br.com.kevinlucas.whatsappmvvm.service.listener

import br.com.kevinlucas.whatsappmvvm.service.model.ChatModel

interface APIListener<T> {

    fun onSuccess(validation: T)

    fun onFailure(str: String)

}