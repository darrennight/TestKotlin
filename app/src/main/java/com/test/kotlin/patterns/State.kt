package com.test.kotlin.patterns

/**
 * Created by zenghao on 2017/6/5.
 */

sealed class AuthorizationState {

    class Unauthorized : AuthorizationState()

    class Authorized(val userName: String) : AuthorizationState()
}

class AuthorizationPresenter {

    private var state: AuthorizationState = AuthorizationState.Unauthorized()

    fun loginUser(userLogin: String) {
        state = AuthorizationState.Authorized(userLogin)
    }

    fun logoutUser() {
        state = AuthorizationState.Unauthorized()
    }

    val isAuthorized: Boolean
        get() {
            when (state) {
                is AuthorizationState.Authorized -> return true
                else -> return false
            }
        }

    val userLogin: String
        get() {
            when (state) {
                is AuthorizationState.Authorized -> return (state as AuthorizationState.Authorized).userName
                is AuthorizationState.Unauthorized -> return "Unknown"
            }
        }

    override fun toString(): String {
        return "User '$userLogin' is logged in: $isAuthorized"
    }
}

fun main(args: Array<String>) {
    val authorization = AuthorizationPresenter()
    authorization.loginUser("admin")
    println(authorization)
    authorization.logoutUser()
    println(authorization)
}
