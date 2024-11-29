package com.mula.lamulaappc.Models

import android.net.Uri

class SelectedImageModel {

    var id = ""
    var imageUri : Uri?= null
    var imageUrl : String ?= null
    var deinternet = false

    constructor()

    constructor(id: String, imageUri: Uri?, imageUrl: String?, deinternet: Boolean) {
        this.id = id
        this.imageUri = imageUri
        this.imageUrl = imageUrl
        this.deinternet = deinternet
    }

}