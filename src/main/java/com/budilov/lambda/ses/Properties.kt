package com.budilov.lambda.ses

/**
 * @author Vladimir Budilov
 *
 */
object Properties {
    val destinationEmail: String = System.getenv("DESTINATION_EMAIL")
    val region: String = System.getenv("REGION")

}
