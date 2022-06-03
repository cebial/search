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
        println("\nSelect a matching strategy: ALL, ANY, NONE")
        val strategy = readln()

        println("\nEnter a name or email to search all matching people.")
        val query = readln().lowercase().split(Regex("\\s+"))

        var matches = if (strategy == "ANY") setOf() else List(people.size) { it }.toSet()

        for (term in query) {
            val set = lookup[term] ?: setOf()
            when (strategy) {
                "ALL" -> matches = matches.intersect(set)
                "ANY" -> matches += set
                "NONE" -> matches -= set
            }
        }

        if (matches.isEmpty()) {
            println("No matching people found.")
        } else {
            println("${matches.size} person${if (matches.size > 1) "s" else ""} found.")
            for (idx in matches) {
                println(people[idx])
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
                0 -> {
                    println("Bye!"); return
                }
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