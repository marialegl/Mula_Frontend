package com.mula.lamulaappc.Models

class CategoryModel {

    var category: String = ""
    var id: String = ""
    var name: String = ""

    constructor()

    constructor(id: String, category: String) {
        this.id = id
        this.category = category
    }
}