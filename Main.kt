package search

import java.io.File

class PeopleFinder {
    private val people = mutableListOf<String>()
    private var lookup = mutableMapOf<String, MutableList<Int>>()

    fun loadPeople(fileName: String) {
        for (person in File(fileName).readLines()) {
            people.add(person)
        }
        buildIndex()
    }

    private fun buildIndex() {
        people.forEachIndexed { idx, data ->
            data.split(Regex("\\s+")).forEach { entry ->
                lookup.getOrPut(entry.lowercase()) { mutableListOf() }.add(idx)
            }
        }
    }

    private fun findPerson() {
        println("\nEnter a name or email to search all suitable people.")
        val query = readln().lowercase()

        val results = people.filterIndexed { idx, _ ->
            lookup.contains(query) && lookup[query]!!.contains(idx)
        }

        if (results.isEmpty()) {
            println("No matching people found.")
        } else {
            println("${results.size} person${if (results.size > 1) " " else ""} found.")
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