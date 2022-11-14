package org.sopt.sample.fragments.models

import androidx.lifecycle.ViewModel
import org.sopt.sample.data.remote.ResponseGetUsersDTO


class HomeViewModel : ViewModel(){
    val userList = listOf<ResponseGetUsersDTO>()
}