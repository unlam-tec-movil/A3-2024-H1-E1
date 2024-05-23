package ar.edu.unlam.mobile.scaffolding.domain.models

data class UserInfoGoogle(
    val userId: String,
    val displayName: String?,
    val email: String?,
    val photoUrl: String?,
)
