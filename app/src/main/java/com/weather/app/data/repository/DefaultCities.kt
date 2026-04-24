package com.weather.app.data.repository

/**
 * Curated list of 15 globally recognized cities used as the app's default dataset.
 * These are stable OpenWeatherMap city IDs.
 */
object DefaultCities {
    val CITY_IDS = listOf(
        2643743L,  // London, GB
        5128581L,  // New York, US
        1850147L,  // Tokyo, JP
        2968815L,  // Paris, FR
        2147714L,  // Sydney, AU
        1796236L,  // Shanghai, CN
        292223L,   // Dubai, AE
        1275339L,  // Mumbai, IN
        3448439L,  // São Paulo, BR
        360630L,   // Cairo, EG
        2759794L,  // Amsterdam, NL
        1816670L,  // Beijing, CN
        184745L,   // Nairobi, KE
        3369157L,  // Cape Town, ZA
        3530597L,  // Mexico City, MX
    )

    val IDS_QUERY_STRING: String get() = CITY_IDS.joinToString(",")
}
