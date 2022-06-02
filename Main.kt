package search

import java.io.File

class PeopleFinder {
    private val people = mutableListOf<String>()

    private fun enterPeople() {
        println("Enter the number of people:")
        var numPeople = readln().toIntOrNull() ?: 0

        println("Enter all people:")
        while (numPeople-- > 0) {
            people.add(readln())
        }
    }

    fun loadPeople(fileName: String) {
        for (person in File(fileName).readLines()) {
            people.add(person)
        }
    }

    private fun findPerson() {
        println("\nEnter a name or email to search all suitable people.")
        val query = readln()

        val results = people.filter { it.contains(query, true) }
        if (results.isEmpty()) {
            println("No matching people found.")
        } else {
            for (line in results) {
                println(line)
            }
        }
    }

    private fun showPeople() {
        println("\n=== List of people ===")
        for (line in people) {
            println(line)
        }
    }

    private fun showMenu() {
        println()
        println(
            """
            |=== Menu ==="
            |1. Find a person
            |2. Print all people
            |0. Exit
            """.trimMargin()
        )
    }

    fun run() {
        while (true) {
            showMenu()
            when (readln().toInt()) {
                1 -> findPerson()
                2 -> showPeople()
                0 -> { println("Bye!"); return }
                else -> println("\nIncorrect option! Try again.")
            }
        }
    }
}

fun main(args: Array<String>) {
    val pf = PeopleFinder()

    pf.loadPeople(args[1])
    pf.run()
}