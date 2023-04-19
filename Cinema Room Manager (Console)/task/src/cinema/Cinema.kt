package cinema

class Cinema {
    private val FIRST_HALF_PRICE = 10
    private val SECOND_HALF_PRICE = 8
    val rows = getRowsNumber()
    val seats = getSeatsNumber()
    var scheme: MutableList<MutableList<Any>> = createSeatsScheme()
    val moreThanSixty: Boolean = moreThanSixty()
    var selectedRow: Int = 0
    var selectedSeat: Int = 0
    val totalIncome: Int = count()
    var purchasedTickets = 0
    var currentIncome = 0
    var percentage = 0.00
    var busySeats = 0

    fun getRowsNumber(): Int {
        println("Enter the number of rows:")
        return readln().toInt()
    }

    fun getSeatsNumber(): Int {
        println("Enter the number of seats in each row:")
        return readln().toInt()
    }

    fun createSeatsScheme(): MutableList<MutableList<Any>> {
        var seatsScheme: MutableList<MutableList<Any>> = mutableListOf()
        for (row in 0..rows) {
                var rowSchema: MutableList<Any> = mutableListOf<Any>()
                if (row == 0) {
                    rowSchema.add(' ')
                    for (rowSeat in 1..seats) {
                        rowSchema.add(rowSeat)
                    }
                } else {
                    rowSchema.add(row)
                    for (rowSeat in 1..seats) {
                        rowSchema.add('S')
                    }
                }
                seatsScheme.add(rowSchema)

        }
        return seatsScheme
    }

    fun printSeatsScheme() {
        println("Cinema:")
        for (row in scheme) {
            println(row.joinToString(" "))
        }
    }

    fun moreThanSixty(): Boolean {
        return (rows * seats) > 60
    }

    fun totalSeats(): Int {
        return rows * seats
    }

    fun lessThenSixtySeatsProfit(): Int {
        return seats * rows * FIRST_HALF_PRICE
    }

    fun moreThenSixtyProfit(): Int {
        val firstHalf = rows / 2
        val secondHalf = rows - firstHalf

        return firstHalf * seats * FIRST_HALF_PRICE + secondHalf * seats * SECOND_HALF_PRICE
    }

    fun count(): Int {
        var profit = 0
        if (!moreThanSixty()) {
            profit = lessThenSixtySeatsProfit()
        } else {
            profit = moreThenSixtyProfit()
        }
        return profit
    }

    fun printProfit(profit: Int) {
        println("Total income:")
        println("$$profit")
    }

    fun getUserChoice(): Boolean {
        var correct = false
        while (!correct) {
            println("Enter a row number:")
            selectedRow = readln().toInt()
            println("Enter a seat number in that row:")
            selectedSeat = readln().toInt()

            if (selectedRow in 1..rows && selectedSeat in 1..seats) {
                if (!busySeat()) {
                    correct = true
                } else {
                    println("That ticket has already been purchased")
                }
            } else {
               println("Wrong input!")
            }
        }
        return true
    }

    fun ticketPriceCalculation(): Int {
        return if (!moreThanSixty) {
            FIRST_HALF_PRICE
        } else {
            if (selectedRow <= rows/2) {
                FIRST_HALF_PRICE
            } else {
                SECOND_HALF_PRICE
            }
        }
    }

    fun printTicketPrice() {
        println("Ticket price: $${ticketPriceCalculation()}")
    }

    fun busySeat(): Boolean = scheme[selectedRow][selectedSeat] == 'B'

    fun makeSeatBusy() {
        scheme[selectedRow][selectedSeat] = 'B'
    }


    fun printStats() {
        println("Number of purchased tickets: $purchasedTickets")
        println("Percentage: ${"%.2f".format(percentage)}%")
        println("Current income: $$currentIncome")
        println("Total income: $$totalIncome")
    }

    fun countPercentage() {
        println("Busy: ${busySeats(scheme)}")
        println("Total: ${totalSeats()}")
        percentage =  busySeats(scheme).toDouble() / totalSeats().toDouble() * 100
        println(percentage)
    }

    fun busySeats(schema: MutableList<MutableList<Any>>): Int {
        var busy = 0
        for (i in schema.indices) {
            for (j in schema[i].indices) {
                if (schema[i][j] == 'B') busy++
            }
        }
        return busy
    }

}

fun main() {
    val cinema = Cinema()

    var menuIterator = 4
    while (menuIterator != 0) {
        println("1. Show the seats")
        println("2. Buy a ticket")
        println("3. Statistics")
        println("0. Exit")
        menuIterator = readln().toInt()
        when (menuIterator) {
            1 -> cinema.printSeatsScheme()
            2 -> {
                var sussessfulPurchase = false
                while (!sussessfulPurchase) {
                    if (cinema.getUserChoice()) {
                            cinema.currentIncome += cinema.ticketPriceCalculation()
                            cinema.printTicketPrice()
                            cinema.makeSeatBusy()
                            cinema.purchasedTickets++
                            cinema.countPercentage()
                    }
                    sussessfulPurchase = true
                }
            }
            3 -> cinema.printStats()
            0 -> continue
            else -> println("\n")
        }
    }
}