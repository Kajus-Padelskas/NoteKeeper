package academy.learnprogramming.notekeeper

object DataManager {

    val courses = HashMap<String, CourseInfo>()
    val notes = ArrayList<NoteInfo>()

    init {
        initializeCourses()
        initializeNotes()
    }

    private fun initializeCourses() {
        var course = CourseInfo("android_intents", "Android Programming with Intents")
        courses[course.courseId] = course

        course = CourseInfo("android_async", "Android Async Programming and Services")
        courses[course.courseId] = course

        course = CourseInfo(title ="Java Fundamentals: The Java Language", courseId = "java_lang")
        courses[course.courseId] = course


    }

    private fun initializeNotes() {
        for(i in courses){
            notes.add(NoteInfo(i.value, i.value.title, "Hello" ))
        }
    }

}