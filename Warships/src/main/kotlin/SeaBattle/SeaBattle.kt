package SeaBattle

import kotlin.random.Random


object Main {
    var playerName1 = "Player#1"
    var playerName2 = "Player#2"
    var battlefield1 = Array(10) { IntArray(10) }
    var battlefield2 = Array(10) { IntArray(10) }
    var monitor1 = Array(10) { IntArray(10) }
    var monitor2 = Array(10) { IntArray(10) }
    var counter1 = 0
    var counter2 = 0


    @JvmStatic
    fun main(args: Array<String>) {
        println("Player#1, please enter your name:")
        playerName1 = readLine().toString()
        println("Do you want to play against comp? Write yes/no")
        var answer = readLine().toString()

        if(answer == "yes" || answer == "Yes"){
            placeShips(playerName1, battlefield1)
            placeShipsComp(battlefield2)
            while (counter1 <= 10 && counter2 <= 10) {

                if (isWinCondition) {
                    break;
                } else {
                    makeTurn(playerName1, monitor1, battlefield2);
                }

                if (isWinCondition) {
                    break;
                } else {
                    makeTurn(monitor2, battlefield1);
                }
            }
        }
        else if(answer == "no" || answer == "No"){
            println("Player#2, please enter your name:")
            playerName2 = readLine().toString()

            placeShips(playerName1, battlefield1)
            placeShips(playerName2, battlefield2)

            while (counter1 <= 10 && counter2 <= 10) {

                if (isWinCondition) {
                    break;
                } else {
                    makeTurn(playerName1, monitor1, battlefield2);
                }

                if (isWinCondition) {
                    break;
                } else {
                    makeTurn(monitor2, battlefield1);
                }
            }
        }else{
            println("Try again and write correct answer")
        }
    }



    fun placeShips(playerName: String, battlefield: Array<IntArray>) {
        var deck = 4
        while (deck >= 1) {
            println()
            println("$playerName, please place your $deck-deck ship on the battlefield: ")
            println()
            drawField(battlefield)
            println("Please enter OY coordinate: ")
            val x = inputNumbers()
            println("Please enter OX coordinate: ")
            val y = inputNumbers()
            println("Choose direction:")
            println("1. Vertical.")
            println("2. Horizontal.")
            val direction = inputNumbers()

            if (!isAvailable(x, y, deck, direction, battlefield)) {
                println("Wrong coordinates!")
                continue
            }

            for (i in 0 until deck) {
                if (direction == 1) {
                    battlefield[x][y - i] = 1
                } else {
                    battlefield[x + i][y] = 1
                }
            }
            deck--
        }

    }


    fun placeShipsComp(battlefield: Array<IntArray>){
        try {
            var deck = 4
            while (deck >= 1) {
                var direction = Random.nextInt(0, 2)
                var x = Random.nextInt(0, 11)
                var y = Random.nextInt(0, 11)
                if (!isAvailable(x, y, deck, direction, battlefield)) {
                    continue
                }
                for (i in 0 until deck) {
                    if (direction == 1) {
                        battlefield[x][y - i] = 1
                    } else {
                        battlefield[x + i][y] = 1
                    }
                }
                deck--
            }
        }
        catch (e : ArrayIndexOutOfBoundsException){
            println()
        }
    }





    fun drawField(battlefield: Array<IntArray>) {
        println("  0 1 2 3 4 5 6 7 8 9")
        for (i in battlefield.indices) {
            print("$i ")
            for (j in battlefield[1].indices) {
                if (battlefield[j][i] == 0) {
                    print("- ")
                } else {
                    print("X ")
                }
            }
            println()
        }
    }

    fun makeTurn(playerName: String, monitor: Array<IntArray>, battlefield: Array<IntArray>) {
        while (true) {
            println("$playerName , please, make your turn.")
            println("  0 1 2 3 4 5 6 7 8 9")
            for (i in monitor.indices) {
                print("$i ")
                for (j in monitor[1].indices) {
                    if (monitor[j][i] == 0) {
                        print("- ")
                    } else if (monitor[j][i] == 1) {
                        print(". ")
                    } else {
                        print("X ")
                    }
                }
                println()
            }
            println("Please enter OY coordinate: ")
            val x = inputNumbers()
            println("Please enter OX coordinate: ")
            val y = inputNumbers()
            if (battlefield[x][y] == 1) {
                println("Hit! Make your turn again!")
                monitor[x][y] = 2
            } else {
                println("Miss! Your opponents's turn!")
                monitor[x][y] = 1
                break
            }
        }
    }

//для компа
    private fun makeTurn(monitor: Array<IntArray>, battlefield: Array<IntArray>) {
        while (true) {
            println("  0 1 2 3 4 5 6 7 8 9")
            for (i in monitor.indices) {
                print("$i ")
                for (j in monitor[1].indices) {
                    if (monitor[j][i] == 0) {
                        print("- ")
                    } else if (monitor[j][i] == 1) {
                        print(". ")
                    } else {
                        print("X ")
                    }
                }
                println()
            }


            val x = Random.nextInt(0, 10)
            val y = Random.nextInt(0, 10)
            if (battlefield[x][y] == 1) {
                println("Computer hit!")
                monitor[x][y] = 2
            }

            else {
                println("Computer miss! Your turn")
                monitor[x][y] = 1
                break
            }
        }
    }



     val isWinCondition: Boolean
        get() {
            counter1 = 0
            for (i in monitor1.indices) {
                for (j in monitor1[i].indices) {
                    if (monitor1[i][j] == 2) {
                        counter1++
                    }
                }
            }
            counter2 = 0
            for (i in monitor2.indices) {
                for (j in monitor2[i].indices) {
                    if (monitor2[i][j] == 2) {
                        counter2++
                    }
                }
            }
            return winnerCheck(counter1, counter2)
        }

    fun winnerCheck(count1 : Int, count2 : Int) : Boolean {
        if (count1 >= 10) {
            println("Player 1 WIN")
            return true
        }
        if (count2 >= 10) {
            println("Player 2  WIN")
            return true
        }
        return false
    }


    fun isAvailable(x: Int, y: Int, deck: Int, rotation: Int, battlefield: Array<IntArray>): Boolean {
        // out of bound check
        var deck = deck
        if (rotation == 1) {
            if (y + deck > battlefield.size) {
                return false
            }
        }
        if (rotation == 2) {
            if (x + deck > battlefield[0].size) {
                return false
            }
        }

        //neighbours check without diagonals
        //XXXX
        while (deck != 0) {
            for (i in 0 until deck) {
                var xi = 0
                var yi = 0
                if (rotation == 1) {
                    yi = i
                } else {
                    xi = i
                }
                //battlefield [x][y]
                if (x + 1 + xi < battlefield.size && x + 1 + xi >= 0) {
                    if (battlefield[x + 1 + xi][y + yi] != 0) {
                        return false
                    }
                }
                if (x - 1 + xi < battlefield.size && x - 1 + xi >= 0) {
                    if (battlefield[x - 1 + xi][y + yi] != 0) {
                        return false
                    }
                }
                if (y + 1 + yi < battlefield.size && y + 1 + yi >= 0) {
                    if (battlefield[x + xi][y + 1 + yi] != 0) {
                        return false
                    }
                }
                if (y - 1 + yi < battlefield.size && y - 1 + yi >= 0) {
                    if (battlefield[x + xi][y - 1 + yi] != 0) {
                        return false
                    }
                }
            }
            deck--
        }
        return true
    }

    fun inputNumbers (): Int {
        return readLine()?.toIntOrNull() ?: inputNumbers()
    }
}