package mx.equipo6.proyectoapp.model.auth

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("birth_date") val birthDate: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("email") val email: String,
    @SerializedName("profile_pic") val profilePic: String,
    @SerializedName("shipping_address") val address: String,
    @SerializedName("password") val password: String
)