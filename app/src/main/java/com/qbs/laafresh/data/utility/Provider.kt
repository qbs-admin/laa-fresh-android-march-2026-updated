package com.qbs.laafresh.data.utility


/**
 * Class Provider is inspired by **Delegate** and **Singleton** design pattern
 *
 * It could be use to create singleton in class or in interface
 *
 * @param T is the result type
 */
abstract class Provider<T> {

    /**
     * Singleton instance for T
     */

    @Volatile
    private var instance: T? = null

    /**
     * In case of test, it could be useful to call [override] to get a custom initiation
     */

    var override: T? = null
    /**
     * This method must describe how create a object of [T]
     *
     * @param args: You must cast every parameter here
     */

    protected abstract fun create(args: Array<out Any> = emptyArray()): T

    /**
     * Get the instance of provider or create and affect it to the singleton instance
     *
     * This is method is thread-safe
     */

    fun get(vararg args: Any): T = override ?: instance ?: synchronized(this) {
        instance ?: create(args).also { instance = it }
    }

}