package taxipark


/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
    allDrivers.filter { driver ->
        trips.none { it.driver == driver }
    }.toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
    allPassengers.filter { passenger ->
        trips.count {
            passenger in it.passengers
        } >= minTrips
    }.toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
    allPassengers.filter { passenger ->
        trips.count { it.driver == driver && passenger in it.passengers } > 1
    }.toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
    allPassengers.filter { passenger ->
        trips.count { passenger in it.passengers && it.discount != null } >
                trips.count { passenger in it.passengers && it.discount == null }
    }.toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there are no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    /*fun getRange(n: Int?): IntRange? {
        val res = n?.div(10)
        if (res != null) {
            return if (res == 0) IntRange(0, 9)
            else IntRange(res * 10, (res * 10) + 9)
        } else return null
    }

    val durationRange = trips.map { getRange(it.duration) }
    val numbersByElement = durationRange.groupingBy { it }.eachCount()
    val mostCommon = numbersByElement.maxBy { it.value }
    return mostCommon?.key*/
    return trips
        .groupBy {
            val start = it.duration / 10 * 10
            val end = start + 9
            start..end
        }.maxByOrNull { (_, group) -> group.size }
        ?.key
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    val distinctList = trips
        .groupBy { it.driver }
        .mapValues { entry ->
            entry.value.sumByDouble { it.cost }
        }
    if (distinctList.isEmpty()) return false
    val totalIncome = distinctList.values.sum()
    var numOfDrivers = 0
    var cost = 0.0
    for ((_, value) in distinctList.toList().sortedByDescending { it.second }.toMap()) {
        numOfDrivers += 1
        cost += value
        if (cost >= (totalIncome * 0.8)) break
    }
    return numOfDrivers <= (allDrivers.size * 0.2)
}