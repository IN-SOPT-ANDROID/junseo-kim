package org.sopt.sample.fragments.models

data class UserRepo (
    val image: Int?,
    val name: String?,
    val repositoryName: String?,
    val dataType : Int // 타입이 설명이면 type1, 레포지토리면 type2 저장
)