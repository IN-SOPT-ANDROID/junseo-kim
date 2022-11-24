package org.sopt.sample.presentation.main.viewmodel

import androidx.lifecycle.ViewModel
import org.sopt.sample.data.remote.ResponseGetUsersDTO

class HomeViewModel : ViewModel() {
    var userList = listOf<ResponseGetUsersDTO.User>()
}