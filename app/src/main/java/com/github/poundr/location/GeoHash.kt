package com.github.poundr.location

object GeoHash {
    const val MAX_PRECISION = 12
    private const val BASE32 = "0123456789bcdefghjkmnpqrstuvwxyz"
    private val BITS = intArrayOf(16, 8, 4, 2, 1)

    fun encode(lat: Double, lon: Double, precision: Int): String {
        var latInterval = doubleArrayOf(-90.0, 90.0)
        var lonInterval = doubleArrayOf(-180.0, 180.0)
        val geohash = StringBuilder()
        var isEven = true
        var bit = 0
        var ch = 0

        while (geohash.length < precision.coerceAtMost(MAX_PRECISION)) {
            var mid: Double
            if (isEven) {
                mid = (lonInterval[0] + lonInterval[1]) / 2
                if (lon > mid) {
                    ch = ch or BITS[bit]
                    lonInterval[0] = mid
                } else {
                    lonInterval[1] = mid
                }
            } else {
                mid = (latInterval[0] + latInterval[1]) / 2
                if (lat > mid) {
                    ch = ch or BITS[bit]
                    latInterval[0] = mid
                } else {
                    latInterval[1] = mid
                }
            }

            isEven = !isEven
            if (bit < 4) {
                bit++
            } else {
                geohash.append(BASE32[ch])
                bit = 0
                ch = 0
            }
        }

        return geohash.toString()
    }
}