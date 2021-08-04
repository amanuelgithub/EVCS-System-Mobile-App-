package com.amanuel.evscsystem.utilities.constants

/**
 * Constants used throughout the App
 */
class Constants {
    companion object {
        const val DATABASE_NAME = "evscdb"

        // This was the notificaitons json data that is used to prepopulate the data base
        // before i getting the notifications from the remote object
        const val NOTIFICATION_DATA_FILENAME = "notifications.json"


//        const val BASE_URL = "http://simplifiedcoding.tech/mywebapp/public/api/"
//        const val BASE_URL = "http://apix.simplifiedcoding.in/api/"
        const val BASE_AUTH_LOGIN_URL = "http://10.240.72.34:8000/api/rest-auth/"
        const val BASE_AUTH_LOGOUT_URL = "http://10.240.72.34:8000/api/rest-auth/"
        const val BASE_URL = "http://10.240.72.34:8000/api/"
//        const val BASE_URL = "http://10.240.72.198:8000"
//        const val BASE_URL = "http://192.168.43.212:8000/"
//        const val BASE_URL = "http://192.168.137.1:3000/"
    }
}



