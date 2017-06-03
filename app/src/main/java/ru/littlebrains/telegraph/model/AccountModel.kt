package ru.littlebrains.telegraph.model

/**
 * Created by evgeniy on 03.06.2017.
 */

class AccountModel(
        val ok: Boolean,
        val short_name: String = "",
                   val author_name: String = "",
                   var author_url: String = "",
                   val access_token: String = "",
                   val auth_url :String = ""){
}