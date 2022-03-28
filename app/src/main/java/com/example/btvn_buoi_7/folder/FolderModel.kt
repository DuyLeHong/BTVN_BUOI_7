package com.example.btvn_buoi_7.folder

import java.io.Serializable

class FolderModel : Serializable {
    var id = 0
    var name: String
        get() = field

        set(value) {
            field = value
        }
    var description: String
        get() = field

        set(value) {
            field = value
        }

    constructor(id: Int, name: String, description: String) {
        this.id = id
        this.name = name
        this.description = description
    }

    constructor(name: String, description: String) {
        this.name = name
        this.description = description
    }
}