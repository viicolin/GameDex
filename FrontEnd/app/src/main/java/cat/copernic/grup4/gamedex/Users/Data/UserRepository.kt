package cat.copernic.grup4.gamedex.Users.Data

import cat.copernic.grup4.gamedex.Core.Model.LoginRequest
import cat.copernic.grup4.gamedex.Core.Model.LoginResponse
import cat.copernic.grup4.gamedex.Core.Model.ResetPasswordRequest
import cat.copernic.grup4.gamedex.Core.Model.User
import cat.copernic.grup4.gamedex.Users.Data.RetrofitInstance.api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {
    suspend fun registerUser(user: User): Response<User> {
        return RetrofitInstance.api.registerUser(user)
    }

    suspend fun loginUser(username: String, password: String): Response<LoginResponse> {
        val request = LoginRequest(username, password)  //
        return RetrofitInstance.api.loginUser(request)
    }

    suspend fun getAllUsers(): Response<List<User>> {
        return RetrofitInstance.api.getAllUsers()
    }

    suspend fun  getAllInactiveUsers(): Response<List<User>> {
        return RetrofitInstance.api.getAllInactiveUsers()
    }

    suspend fun getUser(username: String): Response<User>{
        return RetrofitInstance.api.getUser(username);
    }

    suspend fun validateUser(userId: String): Response<User>{
        return RetrofitInstance.api.validateUser(userId);
    }

    suspend fun resetPassword(username: String, email: String): Response<String>{
        val request = ResetPasswordRequest(username, email)
        return RetrofitInstance.api.resetPassword(request);
    }

}
