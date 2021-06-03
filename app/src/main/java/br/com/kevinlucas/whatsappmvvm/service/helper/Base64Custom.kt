package br.com.kevinlucas.whatsappmvvm.service.helper

import android.util.Base64

class Base64Custom {

    companion object {
        fun encodeBase64(str: String) =
            String(Base64.encode(str.toByteArray(), Base64.DEFAULT))
                .replace("\\n".toRegex(), "")

        fun decodeBase64(str: String) =
            String(Base64.decode(str, Base64.DEFAULT))
    }
}
