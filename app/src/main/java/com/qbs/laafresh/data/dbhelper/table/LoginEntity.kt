package com.qbs.laafresh.data.dbhelper.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class LoginEntity {

    @PrimaryKey(autoGenerate = true)
    var sno: Int = 0
    var userName : String=""
    var surName : String=""
    var email : String=""
    var phone : String=""
    var user_id: String=""

    constructor(userName: String, surName: String, email: String, phone: String, user_id: String) {
        this.userName = userName
        this.surName = surName
        this.email = email
        this.phone = phone
        this.user_id = user_id
    }
}