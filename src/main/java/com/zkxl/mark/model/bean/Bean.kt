package com.zkxl.mark.model.bean

/**
 * Create by Panda on 2019/3/12
 */

data class Bean<T>(val Code: Int, val Value: T?, val Message: String) {

    companion object {
        const val SUCCESS = 1
        const val ERROR = 2
        const val EXCEPTION = 3
        const val LOADING = 4
        const val OTHER = 5

        fun <T> success(data: T?) = Bean(SUCCESS, data, "success")

        fun <T> error(msg: String, data: T?) = Bean(ERROR, data, msg)

        fun <T> exception(msg: String, data: T?) = Bean(ERROR, data, msg)

        fun <T> loading(data: T?) = Bean(LOADING, data, "loading")

        fun <T> other(msg: String,data: T?) = Bean(OTHER, data, msg)
    }

}