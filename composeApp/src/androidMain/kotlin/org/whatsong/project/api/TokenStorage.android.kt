package org.whatsong.project.api



actual fun createTokenStorage(): TokenStorage = AndroidTokenStorage()

class AndroidTokenStorage(
//    private val context: Context = getApplicationContext()
) : TokenStorage {

//    private val prefs: SharedPreferences by lazy {
//        context.getSharedPreferences("user_tokens", Context.MODE_PRIVATE)
//    }

    override fun getAccessToken(): String? {
//        return prefs.getString("UAT", null)
        return "1"
    }


    override fun getRefreshToken(): String? {
//        return prefs.getString("URT", null)
        return "1"
    }

    override fun setAccessToken(token: String) {
//        prefs.edit().putString("UAT", token).apply()
    }

    override fun setRefreshToken(token: String) {
//        prefs.edit().putString("URT", token).apply()
    }

    override fun clearTokens() {
//        prefs.edit()
//            .remove("UAT")
//            .remove("URT")
//            .remove("nickname")
//            .apply()
    }
}