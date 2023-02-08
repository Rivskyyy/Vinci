package com.rivskyinc.studynow

class Video {
    var title: String? = null
    var url: String? = null
    var thumbnail: String? = null
//    var timestamp : String? = null
    var rating : String? = null
    constructor(){

    }

constructor(title : String?, url : String?, thumbnail : String?, rating : String?   ){
    this.title = title
    this.url = url
    this.thumbnail = thumbnail
    this.rating = rating
//    this.timestamp = timestamp
    }
}
