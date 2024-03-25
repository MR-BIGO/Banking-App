package com.example.bankingapp.presentation.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoryPres (
    val id: Int,
    val picture: String,
    val text:String,
    val title: String,
    val color: String
): Parcelable