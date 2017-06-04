package ru.littlebrains.telegraph.model

/**
 * Created by evgeniy on 03.06.2017.
 */
class ViewPageModel(val ok: Boolean,
                val error: String = "",
                val path: String="",
                 val url: String="",
                 val title: String="",
                 val author_name:String="",
                val content: String = "")