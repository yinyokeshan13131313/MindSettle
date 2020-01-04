package com.example.mindsettle

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="user")
data class User(@PrimaryKey val username: String,
                val email: String,
                val password: String,
                val birthyear: Int,
                val country: String) {
}