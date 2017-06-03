package ru.littlebrains.telegraph.model

/**
 * Created by evgeniy on 03.06.2017.
 */
class PageModel(val path: String="",
                 val url: String="",
                 val title: String="",
                 val description: String="",
                 val author_name:String="",
                 val image_url: String="",
                 val views:Int=0,
                 val can_edit: Boolean=false,
                val content: String = "")