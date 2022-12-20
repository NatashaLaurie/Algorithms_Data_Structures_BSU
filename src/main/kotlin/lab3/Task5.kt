package lab3


class TasksProblemSolver {

    class Prefs(private val name: String) {
        internal lateinit var preferences: List<Prefs>
        internal var proposals = 0
        var match: Prefs? = null
            internal set

        // Do a lateinit to allow the circular dependency of people with their preferences of people.
        fun initializePreferences(preferences: List<Prefs>) {
            if (this::preferences.isInitialized) {
                throw IllegalStateException()
            }
            this.preferences = preferences
        }

        override fun toString() = name

        internal fun prefers(new: Prefs): Boolean {
            val old = match!!
            for (i in preferences.indices) {
                if (preferences[i] === new) {
                    return true
                }
                if (preferences[i] === old) {
                    return false
                }
            }
            throw NoSuchElementException()
        }
    }

    // Adds the matches to the Prefs in the Collection.
    fun solve(employees: Collection<Prefs>) {
        // Assume valid input.
        val employees = ArrayList(employees)
        while (employees.isNotEmpty()) {
            // Take the last element because it is efficient to remove from the ArrayList.
            val employee = employees.last()
            val potentialMatch = employee.preferences[employee.proposals++]
            val oldEmployee = potentialMatch.match
            if (oldEmployee == null) {
                potentialMatch.match = employee
                employee.match = potentialMatch
                employees -= employee
            } else {
                if (potentialMatch.prefers(employee)) {
                    oldEmployee.match = null
                    potentialMatch.match = employee
                    employee.match = potentialMatch
                    employees += oldEmployee
                    employees -= employee
                }
            }
        }
    }
}

private fun isStable(employees: List<TasksProblemSolver.Prefs>): Boolean {
    for (i in employees.indices) {
        val employee = employees[i]
        val prefs = employee.match!!
        var j = 0
        while (true) {
            val betterEmployee = prefs.preferences[j++]
            if (betterEmployee === employee) {
                break
            }
            val betterEmployeeBetterTasks = betterEmployee.preferences
            var k = 0
            while (true) {
                val betterEmployeesBetterTask = betterEmployeeBetterTasks[k++]
                if (betterEmployeesBetterTask === betterEmployee.match) {
                    break
                }
                if (betterEmployeesBetterTask === prefs) {
                    return false
                }
            }
        }
    }
    return true
}

fun main() {
    val solver = TasksProblemSolver()
    val number = 4
    val employees = ArrayList<TasksProblemSolver.Prefs>(number).apply {
        for (i in 0 until number) {
            add(TasksProblemSolver.Prefs("Employee $i"))
        }
    }
    val tasks = ArrayList<TasksProblemSolver.Prefs>(number).apply {
        for (i in 0 until number) {
            add(TasksProblemSolver.Prefs("Task $i"))
        }
    }

    for (i in 0 until number) {
        employees[i].initializePreferences(tasks.shuffled())
        tasks[i].initializePreferences(employees.shuffled())
    }

    println("Employees preferences: ")
    employees.forEach { println(it.preferences) }
    println()
    println("Task efficiency: ")
    tasks.forEach { println(it.preferences) }

    println()

    solver.solve(employees)
    for (i in 0 until number) {
        val empl = employees[i]
        val task = tasks[i]
        if (i != 0) {
            println()
        }
        println("${empl}\nPrefers: ${empl.preferences}\nMatched with: ${empl.match!!}")
        println()
        println("${task}\nPrefers: ${task.preferences}\nMatched with: ${task.match!!}")
    }
    println(isStable(employees))
}