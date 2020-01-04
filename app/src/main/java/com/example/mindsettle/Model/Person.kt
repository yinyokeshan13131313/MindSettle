package com.example.mindsettle.Model

class Person {
    var userName: String? = null
    var email: String? = null
    var password: String? = null
    var birthYear: Int = 0
    var country: String? = null
    constructor(){}

    constructor(id: Int, userName: String, email: String, password: String, birthYear: Int, country: String) {
        this.userName = userName
        this.email = email
        this.password = password
        this.birthYear = birthYear
        this.country = country
    }
    constructor(userName: String, email: String, password: String, birthYear: Int, country: String) {
        this.userName = userName
        this.email = email
        this.password = password
        this.birthYear = birthYear
        this.country = country
    }
}