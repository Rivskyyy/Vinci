package com.rivskyinc.studynow

class Comment {

    var text: String? = null
    var uId: String? = null
    var userImage: String? = null
    var userName: String? = null
    var videoId: String? = null
//    var timestamp: String? = null

    constructor() {

    }

    constructor(
        text: String?,
        uId: String?,
        userImage: String?,
        userName: String?,
        videoId: String?
    ) {
        this.text = text
        this.uId = uId
        this.userImage = userImage
        this.userName = userName
        this.videoId = videoId
//        this.timestamp = timestampі
    }
}

